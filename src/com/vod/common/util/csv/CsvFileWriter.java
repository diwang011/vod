package com.vod.common.util.csv;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vod.common.util.StringUtils;
import com.vod.common.util.report.Reportable;

public class CsvFileWriter<T>
{
    private CsvWriter          csvWriter;
    private String[]           displayOrder;
    private Map<String, Field> fields;
    private SimpleDateFormat   sdf    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final Logger       logger = Logger.getLogger(CsvFileWriter.class);

    public CsvFileWriter(String fileName, String datetimePattern, String[] displayOrder)
    {
        if (datetimePattern != null && datetimePattern.length() > 0)
        {
            try
            {
                sdf = new SimpleDateFormat(datetimePattern, Locale.ENGLISH);
            }
            catch (Exception e)
            {
                logger.error("CsvFileWriter datetimePattern=" + datetimePattern + e.getMessage(), e);
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
        }
        try
        {
            if (StringUtils.isEmpty(fileName))
            {
                csvWriter = new CsvWriter(new StringWriter(), ',');
            }
            else
            {
                csvWriter = new CsvWriter(fileName);

                if (fileName.endsWith(".tsv"))
                {
                    csvWriter.setDelimiter('\t');
                }
            }
        }
        catch (Exception e)
        {
            logger.error("CsvFileWriter datetimePattern=" + datetimePattern + e.getMessage(), e);
            csvWriter = null;
        }
        this.displayOrder = displayOrder;
    }

    public CsvFileWriter(String fileName, String datetimePattern)
    {
        this(fileName, datetimePattern, null);
    }

    public CsvFileWriter(String fileName)
    {
        this(fileName, null, null);
    }

    public String toStringWithColName(List<T> content)
    {
        writeFileWithColName(content);
        return csvWriter.contentToString();
    }

    public void writeFileWithColName(List<T> content)
    {
        if (content == null || content.size() == 0 || csvWriter == null)
        {
            if (csvWriter == null)
            {
                logger.info("Do not have csv file for writing.");
            }
            else
            {
                logger.info("Do not have content for writing.");
            }
            return;
        }
        try
        {
            String[] header = generateCsvString(content.get(0), false);
            if (header != null && header.length > 0)
            {
                csvWriter.writeRecord(header);
            }
            for (T obj : content)
            {
                String[] record = generateCsvString(obj, true);
                if (record != null && record.length > 0)
                {
                    csvWriter.writeRecord(record);
                }
            }
            csvWriter.flush();
            csvWriter.close();
        }
        catch (Exception e)
        {
            logger.error("writeFileWithColName error :" + e.getMessage(), e);
        }
    }

    private String[] generateOrderByFields(T obj)
    {
        if (fields == null)
        {
            if (obj instanceof Reportable)
            {
                Reportable csvObj = (Reportable) obj;
                Map<String, String> fieldCsvMap = csvObj.fetchFieldNameAndHeaderMapping().getFieldNameToHeaderMap();
                String[] fieldNames = fieldCsvMap.keySet().toArray(new String[fieldCsvMap.keySet().size()]);
                sortTheFieldsInOrder(obj.getClass(), fieldNames);
                return fieldNames;
            }
            else
            {
                fields = getTheFields(obj.getClass());
            }
        }
        if (fields == null || fields.size() == 0)
        {
            logger.error("No fields in the Class:" + obj.getClass().getSimpleName()
                    + ", when generate order from data model class!!");
            return null;
        }

        return fields.keySet().toArray(new String[fields.size()]);

    }

    private String[] generateCsvString(T obj, boolean isValue)
    {
        return generateCsvString(obj, isValue, false);
    }

    private String[] generateCsvString(T obj, boolean isValue, boolean nullToEmpty)
    {
        int i = 0;
        if (displayOrder == null || displayOrder.length == 0)
        {
            displayOrder = generateOrderByFields(obj);
        }
        if (displayOrder == null || displayOrder.length == 0)
        {
            return null;
        }
        String[] row = new String[displayOrder.length];
        Field f = null;
        String str = null;
        try
        {
            for (i = 0; i < displayOrder.length; i++)
            {

                f = fields.get(displayOrder[i]);

                if (f == null)
                {
                    logger.error("Field:" + displayOrder[i] + " is not found in the Class:"
                            + obj.getClass().getSimpleName() + "!!");
                    continue;
                }

                f.setAccessible(true);
                str = null;
                if (obj instanceof Reportable)
                {
                    if (isValue)
                    {
                        Object o = f.get(obj);
                        if (o != null)
                        {
                            str = fieldValueToString(o);
                        }
                    }
                    else
                    {
                        Reportable csvObj = (Reportable) obj;
                        Map<String, String> fieldCsvMap = csvObj.fetchFieldNameAndHeaderMapping()
                                .getFieldNameToHeaderMap();
                        String csvHeaderName = fieldCsvMap.get(f.getName());
                        if (csvHeaderName == null)
                        {
                            str = f.getName();
                        }
                        else
                        {
                            str = csvHeaderName;
                        }
                    }
                }
                else
                {
                    Object o = f.get(obj);
                    str = isValue ? ((o == null) ? null : fieldValueToString(o)) : f.getName();
                }
                row[i] = (nullToEmpty && str == null) ? "" : str;
            }
        }
        catch (Exception e)
        {
            logger.error("generateCsvString error :" + e.getMessage(), e);
            row = null;
        }
        return row;
    }

    private String fieldValueToString(Object fieldValue)
    {
        String result = "";
        if (fieldValue instanceof Date)
        {
            Date date = (Date) fieldValue;
            if (sdf != null && date != null)
            {
                result = sdf.format(date);
            }
        }
        else
        {
            result = fieldValue.toString();
        }
        return result;
    }

    private void sortTheFieldsInOrder(Class<?> clazz, String[] fieldNamesOrder)
    {
        Map<String, Field> unorderedFields = getTheFields(clazz);

        Map<String, Field> orderedfields = new LinkedHashMap<String, Field>();
        for (String fieldName : fieldNamesOrder)
        {
            orderedfields.put(fieldName, unorderedFields.get(fieldName));

        }
        this.fields = orderedfields;
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

    public void setDisplayOrder(String[] displayOrder)
    {
        if (displayOrder != null && displayOrder.length > 0)
        {
            this.displayOrder = displayOrder;
        }
    }

}
