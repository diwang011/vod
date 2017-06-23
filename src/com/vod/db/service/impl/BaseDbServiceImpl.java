package com.vod.db.service.impl;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import com.vod.common.ErrorInfo.SEVERITY;
import com.vod.db.model.DbErrorInfo;
import com.vod.db.model.DbErrorInfo.CODE;
import com.vod.db.util.AuditStatus;
import com.vod.util.Response;


public abstract class BaseDbServiceImpl<T>
{
    protected final Logger logger;
    private final static int EXTRA_INFO_MAX_LONG = 8192;
    private final static String PRIMARY_KEY_FIELD_NAME = "id";
    private final static String CREATED_DATE_FIELD_NAME = "createdDate";
    private final static String CREATED_BY_FIELD_NAME = "createdBy";
    private final static String MODIFIED_DATE_FIELD_NAME = "modifiedDate";
    private final static String MODIFIED_BY_FIELD_NAME = "modifiedBy";

    private final static String INSERT_FUNC_NAME = "insert";
    private final static String UPDATE_FUNC_NAME = "update";
    private final static String DELETE_FUNC_NAME = "delete";

    private final ObjectMapper mapper;

    protected boolean loadCachedData()
    {
        return false;
    }

    public void clearCache()
    {

    }

    public synchronized void reloadCache()
    {
        clearCache();
        loadCachedData();
    }

    protected BaseDbServiceImpl()
    {
        logger = Logger.getLogger(this.getClass());
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Inclusion.NON_NULL);
        mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Response<Long> count()
    {
        printHasNotBeenImplemented();
        return null;
    }

    public Response<T> getById(Long id)
    {
        printHasNotBeenImplemented();
        return null;
    }

    public Response<List<T>> list(Integer offset, Integer limit)
    {
        printHasNotBeenImplemented();
        return null;
    }

    protected void addUpdateTimeAndOper(T record, Long operatorId)
    {
        Field updatedTimeField = getTableField(record, MODIFIED_DATE_FIELD_NAME, "Date");
        Field updatedByField = getTableField(record, MODIFIED_BY_FIELD_NAME, "Long");
        try
        {
            Date d = new Date();
            if (updatedTimeField != null)
            {
                setFieldValue(record, updatedTimeField, d);
            }
            if (updatedByField != null && operatorId != null)
            {
                setFieldValue(record, updatedByField, operatorId);
            }
        }
        catch (Exception e)
        {
            logger.error("addUpdateTimeAndOper error :" + e.getMessage(), e);
        }
    }

    private void addCreateTimeAndOper(T record, Long operatorId)
    {
        Field addedTimeField = getTableField(record, CREATED_DATE_FIELD_NAME, "Date");
        Field addedByField = getTableField(record, CREATED_BY_FIELD_NAME, "Long");
        Field updatedTimeField = getTableField(record, MODIFIED_DATE_FIELD_NAME, "Date");
        Field updatedByField = getTableField(record, MODIFIED_BY_FIELD_NAME, "Long");
        try
        {
            Date d = new Date();

            if (addedTimeField != null)
            {
                setFieldValue(record, addedTimeField, d);
            }
            if (addedByField != null && operatorId != null)
            {
                setFieldValue(record, addedByField, operatorId);
            }
            if (updatedTimeField != null)
            {
                setFieldValue(record, updatedTimeField, d);
            }
            if (updatedByField != null && operatorId != null)
            {
                setFieldValue(record, updatedByField, operatorId);
            }
        }
        catch (Exception e)
        {
            logger.error("addCreateTimeAndOper error :" + e.getMessage(), e);
        }
    }

    public final Response<List<T>> list()
    {
        return list(null, null);
    }

    @SuppressWarnings("unchecked")
    public final Response<Integer> save(T record, Long operatorId)
    {
        Response<Integer> Response = new Response<Integer>();
        Object pkId = null;
        Object preRecord = null;
        int savedRow = -1;
        Field idField = findGenericPK(record);
        if (idField == null)
        {
            logger.error("The database table must have a 'id' field as primary key.");
            Response.addError(DbErrorInfo.newInstance(CODE.DB_PARAM_ERROR, SEVERITY.ERROR));
            Response.setData(savedRow);
            return Response;
        }

        try
        {
            pkId = getFieldValue(record, idField);
            if (pkId == null)
            {
                addCreateTimeAndOper(record, operatorId);

                createAuditLog(record, null, INSERT_FUNC_NAME, AuditStatus.INSERT_SUCCESS.name(), null, operatorId);

                savedRow = insert(record);

                if (savedRow == -1)
                {
                    Response
                            .addError(DbErrorInfo.newInstance(CODE.DB_INSERT_ERROR, SEVERITY.ERROR, record.toString()));
                }

                Response.setData(savedRow);
            }
            else
            {
                addUpdateTimeAndOper(record, operatorId);

                if ("Long".equalsIgnoreCase(idField.getType().getSimpleName()))
                {
                    Response<T> response = getById(Long.valueOf(pkId.toString()));
                    if (response.getErrors() != null && response.getErrors().size() > 0)
                    {
                        Response.addErrors(response.getErrors());
                        return Response;
                    }
                    preRecord = response.getData();
                }

                createAuditLog((T) preRecord, Long.valueOf(pkId.toString()), UPDATE_FUNC_NAME,
                        AuditStatus.UPDATE_SUCCESS.name(), null, operatorId);

                savedRow = update(record);

                if (savedRow == -1)
                {
                    Response.addError(DbErrorInfo.newInstance(CODE.DB_UPDATE_ERROR, SEVERITY.ERROR, "record id="
                            + pkId));
                }

                Response.setData(savedRow);
            }
        }
        catch (Exception e)
        {
            // logger.error("save error :" + e.getMessage(), e);

            if (pkId != null)
            {
                createAuditLog(record, Long.valueOf(pkId.toString()), UPDATE_FUNC_NAME, AuditStatus.UPDATE_FAIL.name(),
                        e.getMessage(), operatorId);
                Response.addError(DbErrorInfo.newInstance(CODE.DB_UPDATE_ERROR, SEVERITY.ERROR, "id=" + pkId
                        + " error:" + e.getMessage()));
            }
            else
            {
                createAuditLog(record, null, INSERT_FUNC_NAME, AuditStatus.INSERT_FAIL.name(), e.getMessage(),
                        operatorId);
                Response.addError(DbErrorInfo.newInstance(CODE.DB_INSERT_ERROR, SEVERITY.ERROR,
                        "id=null error:" + e.getMessage()));
            }

            savedRow = -1;

            Response.setData(savedRow);
        }
        return Response;
    }

    private <C> String convertPojoToString(C obj)
    {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String json = null;
        try
        {
            json = ow.writeValueAsString(obj);
        }
        catch (Exception e)
        {
            logger.error("convertPojoToString obj=" + obj.getClass().getSimpleName() + e.getMessage(), e);
            return null;
        }

        return json;
    }

    protected abstract int update(T record);

    protected abstract int insert(T record);

    public final Response<Integer> delete(T record, Long operatorId)
    {
        Response<Integer> Response = new Response<Integer>();
        Object pkId = null;
        Field idField = findGenericPK(record);

        int deletedRow = -1;

        if (idField == null)
        {

            Response.addError(DbErrorInfo.newInstance(CODE.DB_DELETE_ERROR, SEVERITY.ERROR,
                    "The database table must have a 'id' field as primary key."));
            Response.setData(deletedRow);
            return Response;
        }

        try
        {
            pkId = getFieldValue(record, idField);
            if (pkId == null)
            {
                // logger.error();
                Response.addError(DbErrorInfo.newInstance(CODE.DB_DELETE_ERROR, SEVERITY.ERROR,
                        "This record does not have id value, can not be deleted."));
                Response.setData(deletedRow);
                return Response;
            }

            if ("Long".equalsIgnoreCase(idField.getType().getSimpleName()))
            {
                deletedRow = deleteById(Long.valueOf(pkId.toString()));
            }

            if (deletedRow != 1)
                createAuditLog(record, Long.valueOf(pkId.toString()), DELETE_FUNC_NAME, AuditStatus.DELETE_FAIL.name(),
                        null, operatorId);
            else
                createAuditLog(record, Long.valueOf(pkId.toString()), DELETE_FUNC_NAME,
                        AuditStatus.DELETE_SUCCESS.name(), null, operatorId);

        }
        catch (Exception e)
        {
            // logger.error("delete error :" + e.getMessage(), e);
            createAuditLog(record, Long.valueOf(pkId.toString()), DELETE_FUNC_NAME, AuditStatus.DELETE_FAIL.name(),
                    e.getMessage(), operatorId);
            Response.addError(DbErrorInfo.newInstance(CODE.DB_DELETE_ERROR, SEVERITY.ERROR,
                    "id=" + pkId + " " + e.getMessage()));
            return Response;

        }


        if (deletedRow == -1)
        {
            Response.addError(DbErrorInfo.newInstance(CODE.DB_DELETE_ERROR, SEVERITY.ERROR, "Unkown delete error id="
                    + pkId + " operator=" + operatorId));
        }
        Response.setData(deletedRow);
        return Response;
    }

    public Response<Integer> deleteById(Long id, Long operatorId)
    {
        Response<Integer> Response = new Response<Integer>();
        int deletedRow = -1;

        Response<T> response = getById(id);
        if (response.getErrors() != null && response.getErrors().size() > 0)
        {
            Response.addErrors(response.getErrors());
            return Response;
        }

        T record = response.getData();
        if (record == null)
        {
            Response.addError(DbErrorInfo.newInstance(CODE.DB_DELETE_ERROR, SEVERITY.ERROR,
                    "Unable to get record, id=" + id + "operator=" + operatorId));
            return Response;
        }

        try
        {
            deletedRow = deleteById(id);
        }
        catch (Exception e)
        {
            // logger.error("deleteById id=" + id + e.getMessage() + e);
            Response
                    .addError(DbErrorInfo.newInstance(CODE.DB_DELETE_ERROR, SEVERITY.ERROR, e, "deleteById id=" + id));
            return Response;
        }

        if (deletedRow != 1)
            createAuditLog(record, id, DELETE_FUNC_NAME, AuditStatus.DELETE_FAIL.name(), null, operatorId);
        else
            createAuditLog(record, id, DELETE_FUNC_NAME, AuditStatus.DELETE_SUCCESS.name(), null, operatorId);


        if (deletedRow == -1)
        {
            Response.addError(DbErrorInfo.newInstance(CODE.DB_DELETE_ERROR, SEVERITY.ERROR, "Unkown error, id=" + id
                    + "operator=" + operatorId));
        }
        Response.setData(deletedRow);
        return Response;
    }

 
    protected int deleteById(Long id)
    {
        return -1;
    }

    private Map<String, Field> getTheFields(Class<?> clazz)
    {
        Map<String, Field> fields = new HashMap<String, Field>();

        while (!clazz.getSimpleName().equalsIgnoreCase(Object.class.getSimpleName()))
        {
            Field[] fieldsTemp = clazz.getDeclaredFields();
            if (fieldsTemp != null && fieldsTemp.length != 0)
            {
                for (Field f : fieldsTemp)
                {
                    fields.put(f.getName(), f);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return fields;

    }

    private Field findGenericPK(T record)
    {
        return getTableField(record, PRIMARY_KEY_FIELD_NAME, null);
    }

    private Field getTableField(T record, String fieldName, String fieldType)
    {
        Map<String, Field> fs = getTheFields(record.getClass());
        for (Field f : fs.values())
        {
            if (fieldName.equals(f.getName()))
            {
                if (fieldType != null && fieldType.length() > 0)
                {
                    if (fieldType.equals(f.getType().getSimpleName()))
                    {
                        return f;
                    }
                    else
                    {
                        return null;
                    }
                }
                else
                {
                    return f;
                }
            }
        }
        return null;
    }

    private void setFieldValue(T record, Field f, Object value) throws IllegalArgumentException, IllegalAccessException
    {
        boolean existingAccessible = f.isAccessible();
        f.setAccessible(true);
        f.set(record, value);
        f.setAccessible(existingAccessible);
    }

    private Object getFieldValue(T record, Field f) throws IllegalArgumentException, IllegalAccessException
    {
        boolean existingAccessible = f.isAccessible();
        f.setAccessible(true);
        Object value = f.get(record);
        f.setAccessible(existingAccessible);
        return value;
    }

    private void printHasNotBeenImplemented()
    {
        logger.error("Has not been implemented.", new Throwable());
    }

    private void createAuditLog(T record, Long targetId, String funcType, String status, String errMessage,
            Long operatorId)
    {
        if (record == null)
        {
            return;
        }
        String className = record.getClass().getSimpleName();
        String json = convertPojoToString(record);

        if (funcType != null && funcType.length() > 0)
        {
            logger.info("funcType:" + funcType);
        }
        if (targetId != null)
        {
            logger.info("targetId:" + targetId);
        }
        if (className != null && className.length() > 0)
        {
            logger.info("Class Name:" + className);
        }
        if (status != null && status.length() > 0)
        {
            logger.info("status:" + status);
        }
        if (errMessage != null && errMessage.length() > 0)
        {
            logger.info("Error Message: " + errMessage);
        }
        if (json != null && json.length() > 0)
        {
            if (json.length() < EXTRA_INFO_MAX_LONG)
            {
                logger.info("json:" + json);
            }
            else
            {
                logger.info("json:" + json.substring(0, EXTRA_INFO_MAX_LONG));
            }
        }

      
    }
}
