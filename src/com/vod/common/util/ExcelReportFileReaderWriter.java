package com.vod.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.vod.common.util.report.FieldNameAndHeaderMapping;
import com.vod.common.util.report.Reportable;

public class ExcelReportFileReaderWriter<T extends Reportable> extends ExcelReportFileReaderWriterUtil<T>
{
    private Workbook workbook;

    private String fileName;

    private String sheetName;

    private Long sheetIndex = 1L;

    private Sheet sheet;

    public ExcelReportFileReaderWriter(String fileName)
    {
        this.fileName = fileName;
        workbook = new HSSFWorkbook();
        String fileName_suffix = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
        sheetName = fileName_suffix.substring(0, fileName_suffix.lastIndexOf(".")).toLowerCase().replace("/", "-");
    }

    public void writeData(List<T> content)
    {
        try
        {
            if (null == content || content.size() == 0)
            {
                log.error("writeReportFile(List<T> content, String fileName) the content cann't be empty");
                return;
            }
            sheet = workbook.createSheet(sheetName + (sheetIndex++));
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

                            cellStyle.setDataFormat(
                                    workbook.getCreationHelper().createDataFormat().getFormat(dateformatStr));
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
        }
        catch (Exception e)
        {
            log.error("Excel create error...", e);
        }
    }

    public void close()
    {
        if (fileName == null)
        {
            workbook = null;
            sheet = null;
            return;
        }
        try
        {
            FileOutputStream out = new FileOutputStream(new File(fileName));
            workbook.write(out);
            out.close();
            log.info("Excel create successfully..");
        }
        catch (FileNotFoundException e)
        {
            log.error("File Not Found Exception", e);
        }
        catch (IOException e)
        {
            log.error("IO Exception ", e);
        }
    }
}
