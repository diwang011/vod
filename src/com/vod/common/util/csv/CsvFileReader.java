package com.vod.common.util.csv;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vod.common.util.report.Reportable;

public class CsvFileReader<T extends Reportable>
{
    private Class<T> rowClass;
    private CsvReader csvReader;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final Logger logger = Logger.getLogger(CsvFileReader.class);

    public CsvFileReader(Class<T> rowClass, String fileName)
    {
        this(rowClass, fileName, null);
    }

    public CsvFileReader(Class<T> rowClass, String fileName, String datetimePattern)
    {
        super();
        this.rowClass = rowClass;
        if (datetimePattern != null && datetimePattern.length() > 0)
        {
            try
            {
                sdf = new SimpleDateFormat(datetimePattern,Locale.ENGLISH);
            }
            catch (Exception e)
            {
                logger.error("CsvFileReader datetimePattern=" + datetimePattern + e.getMessage(), e);
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
        }
        try
        {
            csvReader = new CsvReader(fileName);
            if (fileName.endsWith(".tsv") || fileName.endsWith(".txt"))
            {
                csvReader.setDelimiter('\t');
            }
        }
        catch (Exception e)
        {
            logger.error("CsvFileReader datetimePattern=" + datetimePattern + e.getMessage(), e);
            csvReader = null;
        }
    }

    public CsvFileReader(Class<T> rowClass, byte[] content)
    {
        this(rowClass, content, null);
    }

    public CsvFileReader(Class<T> rowClass, byte[] content, String datetimePattern, String fileName)
    {
        super();
        this.rowClass = rowClass;
        if (datetimePattern != null && datetimePattern.length() > 0)
        {
            try
            {
                sdf = new SimpleDateFormat(datetimePattern,Locale.ENGLISH);
            }
            catch (Exception e)
            {
                logger.error("CsvFileReader datetimePattern=" + datetimePattern + e.getMessage(), e);
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
        }
        try
        {
            InputStream rd = new ByteArrayInputStream(content);
            csvReader = new CsvReader(rd);
            if (fileName.endsWith(".tsv") || fileName.endsWith(".txt"))
            {
                csvReader.setDelimiter('\t');
            }
        }
        catch (Exception e)
        {
            logger.error("CsvFileReader datetimePattern=" + datetimePattern + e.getMessage(), e);
            csvReader = null;
        }
    }

    public CsvFileReader(Class<T> rowClass, byte[] content, String datetimePattern)
    {
        super();
        this.rowClass = rowClass;
        if (datetimePattern != null && datetimePattern.length() > 0)
        {
            try
            {
                sdf = new SimpleDateFormat(datetimePattern,Locale.ENGLISH);
            }
            catch (Exception e)
            {
                logger.error("CsvFileReader datetimePattern=" + datetimePattern + e.getMessage(), e);
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
        }
        try
        {
            InputStream rd = new ByteArrayInputStream(content);
            csvReader = new CsvReader(rd);
        }
        catch (Exception e)
        {
            logger.error("CsvFileReader datetimePattern=" + datetimePattern + e.getMessage(), e);
            csvReader = null;
        }
    }

    private String[] getUsedFiledsName()
    {
        String[] fieldsNameUsed = null;
        try
        {
            String[] colsNameInFile = null;

            if (csvReader.readHeaders())
            {
                colsNameInFile = csvReader.getHeaders();
            }
            if (colsNameInFile == null)
                return null;
            fieldsNameUsed = new String[colsNameInFile.length];
            T obj = rowClass.newInstance();
            Map<String, String> csvHeaderFieldNameMap = obj.fetchFieldNameAndHeaderMapping().getHeaderToFieldNameMap();
            for (int i = 0; i < colsNameInFile.length; i++)
            {
                String fieldName = csvHeaderFieldNameMap.get(colsNameInFile[i]);
                if (fieldName != null && fieldName.length() > 0)
                {
                    fieldsNameUsed[i] = fieldName;
                }
            }
        }
        catch (Exception e)
        {
            logger.error("getUsedFiledsName exception :" + e.getMessage(), e);
            fieldsNameUsed = null;
        }
        return fieldsNameUsed;
    }

    public List<T> readFileHasColName()
    {
        if (csvReader == null)
        {
            return null;
        }
        String[] fieldsNameUsed = getUsedFiledsName();
        if (fieldsNameUsed == null)
        {
            return null;
        }
        List<T> rowObjectList = new ArrayList<T>();
        T obj = null;
        try
        {
            while (csvReader.readRecord())
            {
                String[] fieldValues = csvReader.getValues();
                obj = generateObjectFromCsvData(fieldsNameUsed, fieldValues);
                if (obj == null)
                {
                    return null;
                }
                rowObjectList.add(obj);
            }
            csvReader.close();
        }
        catch (Exception e)
        {
            logger.error("readFileHasColName exception :" + e.getMessage(), e);
            rowObjectList = null;
        }
        return rowObjectList;
    }

    private T generateObjectFromCsvData(String[] fieldsName, String[] fieldValues) throws IOException
    {
        int i = 0;
        String str;
        Field f;
        if (fieldValues == null)
        {
            return null;
        }
        T obj = null;
        try
        {
            obj = rowClass.newInstance();
            for (i = 0; i < fieldsName.length; i++)
            {
                f = getTheField(obj.getClass(), fieldsName[i]);
                if (f == null)
                    continue;
                f.setAccessible(true);
                if (i >= fieldValues.length || fieldValues[i] == null || fieldValues[i].length() == 0)
                {
                    continue;
                }
                str = f.getType().getSimpleName();
                if (str.equals("int") || str.equals("Integer"))
                {
                    f.set(obj, Integer.valueOf(fixNumericStr(fieldValues[i])));
                }
                else if (str.equals("byte") || str.equals("Byte"))
                {
                    f.set(obj, Byte.valueOf(fixNumericStr(fieldValues[i])));
                }
                else if (str.equals("short") || str.equals("Short"))
                {
                    f.set(obj, Short.valueOf(fixNumericStr(fieldValues[i])));
                }
                else if (str.equals("long") || str.equals("Long"))
                {
                    f.set(obj, Long.valueOf(fixNumericStr(fieldValues[i])));
                }
                else if (str.equals("float") || str.equals("Float"))
                {
                    f.set(obj, Float.valueOf(fixNumericStr(fieldValues[i])));
                }
                else if (str.equals("double") || str.equals("Double"))
                {
                    f.set(obj, Double.valueOf(fixNumericStr(fieldValues[i])));
                }
                else if (str.equals("char") || str.equals("Character"))
                {
                    f.set(obj, Character.valueOf(fieldValues[i].charAt(0)));
                }
                else if (str.equals("boolean") || str.equals("Boolean"))
                {
                    String s = fieldValues[i];
                    if ("true".equals(s) || "1".equals(s))
                    {
                        f.set(obj, Boolean.TRUE);
                    }
                    else
                    {
                        f.set(obj, Boolean.FALSE);
                    }

                }
                else if (str.equals("String"))
                {
                    f.set(obj, fieldValues[i]);
                }
                else if (str.equals("Date"))
                {
                    try
                    {
                        if (sdf != null)
                        {
                            f.set(obj, sdf.parse(convertCriterionDate(fieldValues[i])));
                        }
                    }
                    catch (Exception e)
                    {
                        if (sdf != null)
                        {
                            f.set(obj, sdf.parse(fieldValues[i]));
                        }
                    }
                }
                else if (str.equals("BigDecimal"))
                {
                    f.set(obj, new BigDecimal(fieldValues[i]));
                }
                else
                {
                    f.set(obj, fieldValues[i]);
                }
            }
        }
        catch (Exception e)
        {
            logger.error("generateObjectFromCsvData error :" + e.getMessage(), e);
            obj = null;
        }
        return obj;
    }

    private static String convertCriterionDate(String dateStr)
    {
        int index = dateStr.indexOf(" ") == -1 ? dateStr.length() : dateStr.indexOf(" ");
        String start = "";
        String dateStr1 = dateStr.substring(0, index);
        String separateStr1 = dateStr1.substring(4, 5);
        String[] dateArray1 = dateStr1.split(separateStr1);
        if (dateArray1.length != 3)
        {
            return null;
        }

        if (dateArray1[1].length() == 1)
        {
            dateArray1[1] = "0" + dateArray1[1];
        }
        if (dateArray1[2].length() == 1)
        {
            dateArray1[2] = "0" + dateArray1[2];
        }
        start = dateArray1[0] + "-" + dateArray1[1] + "-" + dateArray1[2];

        String end = "";
        String dateStr2 = dateStr.substring(index + 1);
        if (dateStr2 != null && dateStr2.length() > 0)
        {
            String[] dateArray2 = dateStr2.split(":");
            if (dateArray2.length < 2)
            {
                return null;
            }

            if (dateArray2[0].length() == 1)
            {
                dateArray2[0] = "0" + dateArray2[0];
            }

            if (dateArray2[1].length() == 1)
            {
                dateArray2[1] = "0" + dateArray2[1];
            }

            if (dateArray2.length == 3)
            {
                if (dateArray2[2].length() == 1)
                {
                    dateArray2[2] = "0" + dateArray2[2];
                }
                end = dateArray2[0] + ":" + dateArray2[1] + ":" + dateArray2[2];
            }
            else
            {
                end = dateArray2[0] + ":" + dateArray2[1] + ":00";
            }
        }
        return start + " " + end;
    }

    private Field getTheField(Class<?> clazz, String fieldName)
    {
        Field f = null;
        if (fieldName == null || fieldName.length() == 0)
        {
            return null;
        }
        if (clazz.getSimpleName().equalsIgnoreCase("Object"))
            return f;
        try
        {
            f = clazz.getDeclaredField(fieldName);
            if (f != null)
            {
                return f;
            }
        }
        catch (NoSuchFieldException e)
        {
            return getTheField(clazz.getSuperclass(), fieldName);
        }
        catch (Exception e)
        {
            logger.error("getTheField error :" + e.getMessage(), e);
            f = null;
        }
        return f;
    }

    private String removeComma(String str)
    {
        String[] StrArray = str.split(",");
        StringBuilder sb = new StringBuilder();
        for (String s : StrArray)
        {
            sb.append(s);
        }
        return sb.toString();
    }

    private String removeMoneyPrefix(String str)
    {
        char ch = str.charAt(0);
        if (!Character.isDigit(ch) && ch != '+' && ch != '-')
        {
            return str.substring(1);
        }
        return str;
    }

    private String fixNumericStr(String str)
    {
        str = removeComma(str);
        str = removeMoneyPrefix(str);
        return str;
    }

}
