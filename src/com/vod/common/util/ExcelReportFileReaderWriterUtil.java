package com.vod.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.vod.common.util.report.FieldNameAndHeaderMapping;
import com.vod.common.util.report.FileCharsetDetector;
import com.vod.common.util.report.ReportFileReaderWriter;
import com.vod.common.util.report.Reportable;

/**
 * 
 * @author dev007
 * 
 * @param <T>
 */
public class ExcelReportFileReaderWriterUtil<T extends Reportable> extends ReportFileReaderWriter<T>
{
    private String defaultChartSet = System.getProperties().getProperty("file.encoding");
    private String inputFileChartSet = "";
    Logger log = Logger.getLogger(this.getClass());

    /**
     * T filed dateformatStr chose the dateFormat
     */
    @Override
    public void writeReportFile(List<T> content, String fileName)
    {
        try
        {
            if (null == content || content.size() == 0)
            {
                log.error("writeReportFile(List<T> content, String fileName) the content cann't be empty");
                return;
            }

            // get simpleFileName
            String fileName_suffix = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
            String sheetName = fileName_suffix.substring(0, fileName_suffix.lastIndexOf(".")).toLowerCase()
                    .replace("/", "-");
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet(sheetName);
            FieldNameAndHeaderMapping f2hMap = content.get(0).fetchFieldNameAndHeaderMapping();
            Map<String, String> field2HeaderMap = f2hMap.getFieldNameToHeaderMap();

            Integer size = content.size() + 1;
            for (int rownum = 0; rownum < size; rownum++)
            {
                Row row = sheet.createRow(rownum);
                int cellnum = 0;
                sheet.autoSizeColumn((short) rownum);
                for (Map.Entry<String, String> map : field2HeaderMap.entrySet())
                {
                    Cell cell = row.createCell(cellnum++);
                    if (rownum == 0)
                    {
                        cell.setCellValue(map.getValue());

                    }
                    else
                    {
                        // Field field = content.get(rownum - 1).getClass().getDeclaredField(map.getKey());// null
                        // field.setAccessible(true);
                        // Object filedValue = field.get(content.get(rownum - 1));
                        Object filedValue = Helper.getFieldObjectValue(content.get(rownum - 1), map.getKey());
                        if (filedValue instanceof Date)
                        {
                            CellStyle cellStyle = workbook.createCellStyle();
                            // dateformatStr
                            String dateformatStr = this.getDateTimeFormat().toPattern();// "yyyy-MM-dd HH:mm:ss";
                            Field[] fields = content.get(rownum - 1).getClass().getDeclaredFields();
                            for (Field f : fields)
                            {
                                if ("dateformatStr".equals(f.getName()))
                                {
                                    f.setAccessible(true);
                                    dateformatStr = (String) f.get(content.get(rownum - 1));
                                }
                            }

                            cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat()
                                    .getFormat(dateformatStr));
                            cell.setCellValue((Date) filedValue);
                            cell.setCellStyle(cellStyle);
                        }
                        else if (filedValue instanceof Boolean)
                        {
                            cell.setCellValue((Boolean) filedValue);
                        }
                        else if (filedValue instanceof String)
                        {
                            cell.setCellValue((String) filedValue);
                        }
                        else if (filedValue instanceof Short)
                        {
                            cell.setCellValue((Short) filedValue);
                        }
                        else if (filedValue instanceof Integer)
                        {
                            cell.setCellValue((Integer) filedValue);
                        }
                        else if (filedValue instanceof Long)
                        {
                            cell.setCellValue((Long) filedValue);
                        }
                        else if (filedValue instanceof Float)
                        {
                            cell.setCellValue((Float) filedValue);
                        }
                        else if (filedValue instanceof Double)
                        {
                            cell.setCellValue((Double) filedValue);
                        }
                    }

                }
            }
            FileOutputStream out = new FileOutputStream(new File(fileName));
            workbook.write(out);
            out.close();
            log.info("Excel create successfully..");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    
    @Override
    public void writeReportFile(Map<String,List<? extends Reportable>> multiConentMap, String fileName) {
        try {
            if (null == multiConentMap || multiConentMap.size() == 0) {
                log.error("writeReportFile(List<T> content, String fileName) the content cann't be empty");
                return;
            }
            
            Workbook workbook = new HSSFWorkbook();
            
            for(Entry<String, List<? extends Reportable>> entry : multiConentMap.entrySet()) {
                String key = entry.getKey();
                List<? extends Reportable> content = entry.getValue();
                Sheet sheet = workbook.createSheet(key);
                FieldNameAndHeaderMapping f2hMap = content.get(0).fetchFieldNameAndHeaderMapping();
                Map<String, String> field2HeaderMap = f2hMap.getFieldNameToHeaderMap();

                Integer size = content.size() + 1;
                for (int rownum = 0; rownum < size; rownum++) {
                    Row row = sheet.createRow(rownum);
                    int cellnum = 0;
                    // sheet.autoSizeColumn((short) rownum);
                    for (Map.Entry<String, String> map : field2HeaderMap.entrySet()) {
                        Cell cell = row.createCell(cellnum++);
                        if (rownum == 0) {
                            cell.setCellValue(map.getValue());

                        } else {
                            // Field field = content.get(rownum -
                            // 1).getClass().getDeclaredField(map.getKey());// null
                            // field.setAccessible(true);
                            // Object filedValue = field.get(content.get(rownum -
                            // 1));
                            Object filedValue = Helper.getFieldObjectValue(content.get(rownum - 1), map.getKey());
                            if (filedValue instanceof Date) {
                                CellStyle cellStyle = workbook.createCellStyle();
                                // dateformatStr
                                String dateformatStr = this.getDateTimeFormat().toPattern();// "yyyy-MM-dd
                                                                                            // HH:mm:ss";
                                Field[] fields = content.get(rownum - 1).getClass().getDeclaredFields();
                                for (Field f : fields) {
                                    if ("dateformatStr".equals(f.getName())) {
                                        f.setAccessible(true);
                                        dateformatStr = (String) f.get(content.get(rownum - 1));
                                    }
                                }

                                cellStyle.setDataFormat(
                                        workbook.getCreationHelper().createDataFormat().getFormat(dateformatStr));
                                cell.setCellValue((Date) filedValue);
                                cell.setCellStyle(cellStyle);
                            } else if (filedValue instanceof Boolean) {
                                cell.setCellValue((Boolean) filedValue);
                            } else if (filedValue instanceof String) {
                                cell.setCellValue((String) filedValue);
                            } else if (filedValue instanceof Short) {
                                cell.setCellValue((Short) filedValue);
                            } else if (filedValue instanceof Integer) {
                                cell.setCellValue((Integer) filedValue);
                            } else if (filedValue instanceof Long) {
                                cell.setCellValue((Long) filedValue);
                            } else if (filedValue instanceof Float) {
                                cell.setCellValue((Float) filedValue);
                            } else if (filedValue instanceof Double) {
                                cell.setCellValue((Double) filedValue);
                            }
                        }

                    }
                }
            }
            
            FileOutputStream out = new FileOutputStream(new File(fileName));
            workbook.write(out);
            out.close();
            log.info("Excel create successfully..");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<T> readReportFile(Class<T> rowClass, String fileName) throws Exception
    {

        List<T> list = new ArrayList<T>();
        try
        {
            inputFileChartSet = new FileCharsetDetector().guestFileEncoding(fileName);
            // System.out.println(inputFileChartSet);//

            InputStream file = new FileInputStream(fileName);
            Workbook workbook = WorkbookFactory.create(file);

            List<String> mapName2List = new ArrayList<String>();
            Map<String, String> name2HeadMap = rowClass.newInstance().fetchFieldNameAndHeaderMapping()
                    .getFieldNameToHeaderMap();
            for (Map.Entry<String, String> map : name2HeadMap.entrySet())
            {
                mapName2List.add(map.getKey());
            }

            // modify by bigchun,only can get first sheet
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            int rownum = 0;

            outer: while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                if (!(rownum == 0))
                {
                    T t = rowClass.newInstance();
                    int cellCount = 0;
                    // For each row, iterate through each columns
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext())
                    {
                        Cell cell = cellIterator.next();
                        if (cellCount >= mapName2List.size())
                        {
                            log.error("excel data error at row#" + rownum);
                            break;
                        }

                        int colIndex = cell.getColumnIndex();

                        while (cellCount != colIndex)
                        {
                            // log.debug("cellCount : " + cellCount);
                            cellCount++;
                        }
                        // log.debug("colIndex : " + colIndex);

                        Field field = getTheField(rowClass, mapName2List.get(cellCount));
                        if (field == null)
                            continue;

                        field.setAccessible(true);
                        Class<?> fieldCz = field.getType();
                        if (fieldCz.equals(java.lang.Short.class) || fieldCz.equals(short.class))
                        {
                            Short shortValue = cell2Short(cell);
                            if (null != shortValue)
                            {
                                field.set(t, shortValue);
                            }
                        }
                        else if (fieldCz.equals(java.lang.Integer.class) || fieldCz.equals(int.class))
                        {
                            Integer intValue = cell2Int(cell);
                            if (null != intValue)
                            {
                                field.set(t, intValue);
                            }
                        }
                        else if (fieldCz.equals(java.lang.Long.class) || fieldCz.equals(long.class))
                        {
                            Long longValue = cell2Long(cell);
                            if (null != longValue)
                            {
                                field.set(t, longValue);
                            }
                        }
                        else if (fieldCz.equals(java.lang.Float.class) || fieldCz.equals(float.class))
                        {
                            Float floatValue = cell2Float(cell);
                            if (null != floatValue)
                            {
                                field.set(t, floatValue);
                            }
                        }
                        else if (fieldCz.equals(java.lang.Double.class) || fieldCz.equals(double.class))
                        {
                            Double d = cell2Double(cell);
                            if (null != d)
                            {
                                field.set(t, d);
                            }

                        }
                        else if (fieldCz.equals(java.lang.String.class))
                        {
                            String str = cell2String(cell);
                            if (cellCount == 0 && str.contains("#"))
                            {
                                continue outer;
                            }
                           
                                field.set(t, str);
                           
                           
                        }
                        else if (fieldCz.equals(java.util.Date.class))
                        {
                            Date d = cell2Date(cell);
                            field.set(t, d);
                        }
                        else if (fieldCz.equals(java.lang.Boolean.class) || fieldCz.equals(boolean.class))
                        {
                            Boolean b = cell2Boolean(cell);
                            if (null != b)
                            {
                                field.set(t, b);
                            }
                        }
                        else if (fieldCz.equals(java.math.BigDecimal.class))
                        {
                            BigDecimal big = cell2BigDecimal(cell);
                            if (null != big)
                            {
                                field.set(t, big);
                            }
                        }

                        cellCount++;
                    }
                    if (cellCount <= mapName2List.size())
                    {
                        list.add(t);
                    }
                    else
                    {
                        log.error(fileName + " near rowNum:" + (rownum + 1) + "  date error");
                    }

                }
                rownum++;
            }
            file.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        return list;
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

    private Short cell2Short(Cell cell)
    {
        Short s = null;
        switch (cell.getCellType())
        {
        case HSSFCell.CELL_TYPE_STRING:
            s = Short.parseShort(cell.getStringCellValue());
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
            s = (short) cell.getNumericCellValue();
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            break;
        default:
            break;
        }
        return s;
    }

    private Integer cell2Int(Cell cell)
    {
        switch (cell.getCellType())
        {
        case HSSFCell.CELL_TYPE_STRING:
            return Integer.parseInt(cell.getStringCellValue());
        case HSSFCell.CELL_TYPE_NUMERIC:
            return (int) cell.getNumericCellValue();
        case HSSFCell.CELL_TYPE_BOOLEAN:
            return null;
        case HSSFCell.CELL_TYPE_BLANK:
            return null;
        default:
            return null;
        }
    }

    private Long cell2Long(Cell cell)
    {
        Long s = null;
        switch (cell.getCellType())
        {
        case HSSFCell.CELL_TYPE_STRING:
            s = Long.parseLong(cell.getStringCellValue());
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
            s = (long) cell.getNumericCellValue();
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            break;
        default:
            break;
        }
        return s;
    }

    private Float cell2Float(Cell cell)
    {
        Float s = null;
        switch (cell.getCellType())
        {
        case HSSFCell.CELL_TYPE_STRING:
            s = Float.parseFloat(cell.getStringCellValue());
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
            s = (float) cell.getNumericCellValue();
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            break;
        default:
            break;
        }
        return s;
    }

    private BigDecimal cell2BigDecimal(Cell cell)
    {
        switch (cell.getCellType())
        {
        case HSSFCell.CELL_TYPE_STRING:
            return new BigDecimal(cell.getStringCellValue());
        case HSSFCell.CELL_TYPE_NUMERIC:
            return new BigDecimal(cell.getNumericCellValue());
        case HSSFCell.CELL_TYPE_BOOLEAN:
            return null;
        case HSSFCell.CELL_TYPE_BLANK:
            return null;
        default:
            return null;
        }
    }

    private Double cell2Double(Cell cell)
    {
        switch (cell.getCellType())
        {
        case HSSFCell.CELL_TYPE_STRING:
            return Double.parseDouble(cell.getStringCellValue());
        case HSSFCell.CELL_TYPE_NUMERIC:
            return cell.getNumericCellValue();
        case HSSFCell.CELL_TYPE_BOOLEAN:
            return null;
        case HSSFCell.CELL_TYPE_BLANK:
            return null;
        default:
            return null;
        }
    }

    private Boolean cell2Boolean(Cell cell)
    {
        Boolean b = null;
        switch (cell.getCellType())
        {
        case HSSFCell.CELL_TYPE_STRING:
            String str = cell.getStringCellValue();
            if ("true".equals(str))
            {
                b = true;
            }
            else if ("false".equals(str))
            {
                b = false;
            }
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            b = cell.getBooleanCellValue();
        case HSSFCell.CELL_TYPE_BLANK:
            break;
        default:
            break;
        }
        return b;
    }

    private Date cell2Date(Cell cell)
    {
        Date date = null;
        switch (cell.getCellType())
        {
        case HSSFCell.CELL_TYPE_STRING:
            String strCell = cell.getStringCellValue();
            String dataStr = cell.getStringCellValue();
            try
            {
                // yyyy-mm-dd
                if (strCell.indexOf("-") > -1 && strCell.length() == 10)
                {
                    date = getDateFormat().parse(strCell);
                    // yyyy-mm-dd HH:MM:SS
                }
                else if (strCell.indexOf("-") > -1 && strCell.length() == 19)
                {
                    date = getDateTimeFormat().parse(dataStr);
                }
                else
                {
                    String extraDateString = getExtraDatePatternString();
                    SimpleDateFormat df = new SimpleDateFormat(extraDateString);
                    date = df.parse(dataStr);
                }
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            return date;
        case HSSFCell.CELL_TYPE_NUMERIC:
            if (HSSFDateUtil.isCellDateFormatted(cell))
            {
                date = cell.getDateCellValue();
            }
            return date;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            break;
        default:
            break;
        }
        return date;
    }

    private String cell2String(Cell cell)
    {
        String strCell = "";
        switch (cell.getCellType())
        {
        case HSSFCell.CELL_TYPE_STRING:
            if (defaultChartSet.toLowerCase().indexOf("utf") > -1
                    && inputFileChartSet.toLowerCase().indexOf("gbk") > -1)
            {
                try
                {
                    strCell = new String(cell.getStringCellValue().getBytes(inputFileChartSet), defaultChartSet);
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                strCell = cell.getStringCellValue();
            }
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
            // strCell = String.valueOf(cell.getNumericCellValue());
            // String str = strCell.substring(strCell.length()-2);
            // if(str.equals(".0"))
            // strCell = strCell.substring(0, strCell.length()-2);
            strCell = new BigDecimal(cell.getNumericCellValue()).toPlainString();
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        case HSSFCell.CELL_TYPE_FORMULA:
            strCell = cell.getRichStringCellValue().getString();
            break;
        default:
            break;
        }
        return strCell.trim();
    }

}
