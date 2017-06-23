package com.vod.common.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.vod.common.Constant;

public abstract class Helper
{
    private final static Logger  logger         = Logger.getLogger(Helper.class);
    private final static Pattern NUMERICPATTERN = Pattern.compile("[-+]?\\d*\\.?\\d+");
    private final static Random  r              = new Random();

    public static final String getGenericFieldType(Class<?> clazz, String fieldName)
    {
        String dateType = null;
        Field f = Helper.getTheField(clazz, fieldName);
        Type fc = f.getGenericType();
        if (fc == null)
        {
            return null;
        }

        if (fc instanceof ParameterizedType)
        {
            ParameterizedType pt = (ParameterizedType) fc;

            Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
            dateType = genericClazz.getSimpleName();
        }

        return dateType;
    }

    public static final <T> void setFieldValueList(T obj, String fieldName, Object valueList)
    {
        Class<?> clazz = obj.getClass();
        Field f = Helper.getTheField(clazz, fieldName);
        if (f == null)
        {
            return;
        }

        if (valueList == null)
        {
            return;
        }
        try
        {
            f.setAccessible(true);
            Class<?> fieldTypeClazz = f.getType();
            String fieldTypeName = fieldTypeClazz.getSimpleName();
            if ("List".equalsIgnoreCase(fieldTypeName))
            {
                f.set(obj, valueList);
            }

        }
        catch (Exception e)
        {
            logger.error("setFieldValueList exception :" + e.getMessage(), e);
        }
    }

    public static String getSubString(String source, String prefix, String suffix)
    {
        String result;
        String fromLead;
        int leadingIndex = 0;
        if (source == null)
        {
            return null;
        }

        if ((prefix == null || prefix.length() == 0))
        {
            fromLead = source;
        }
        else
        {
            leadingIndex = source.indexOf(prefix);
            if (leadingIndex == -1)
            {
                return null;
            }
            fromLead = source.substring(leadingIndex + prefix.length());
        }

        if (suffix == null || suffix.length() == 0 || fromLead.indexOf(suffix) == -1)
        {
            result = fromLead;
        }
        else
        {
            result = fromLead.substring(0, fromLead.indexOf(suffix));
        }

        return result.trim();
    }

    public static boolean isNumeric(String s)
    {
        return NUMERICPATTERN.matcher(s).matches();
    }

    public static Integer convertStringToInteger(String s)
    {
        Integer i = 0;
        try
        {
            i = Integer.parseInt(s);
        }
        catch (Exception e)
        {
            logger.error("Can not convert " + s + " to integer.");
            i = null;
        }

        return i;

    }

    public static Double convertStringToDouble(String s)
    {
        Double i = 0.0;
        try
        {
            i = Double.parseDouble(s);
        }
        catch (Exception e)
        {
            logger.error("Can not convert " + s + " to double.");
            i = null;
        }

        return i;

    }

    public static Long convertIntegerToLong(Integer i)
    {
        return Long.valueOf(i.longValue());
    }

    public static String computeContentMD5(String plainText)
    {
        ByteArrayInputStream bais = new ByteArrayInputStream(plainText.getBytes());
        DigestInputStream dis = null;
        String res = null;
        try
        {
            dis = new DigestInputStream(bais, MessageDigest.getInstance("MD5"));
            byte[] buffer = new byte[8192];
            while (dis.read(buffer) > 0)
                ;
            res = new String(Base64.encodeBase64(dis.getMessageDigest().digest()));
            bais.close();
        }
        catch (Exception e)
        {
            logger.error("computeContentMD5 plainText=" + plainText + e.getMessage(), e);
        }
        return res;
    }

    public static String computeContentMD5(FileInputStream fis)
    {
        DigestInputStream dis = null;
        try
        {
            dis = new DigestInputStream(fis, MessageDigest.getInstance("MD5"));
        }
        catch (NoSuchAlgorithmException e)
        {
            logger.error("computeContentMD5 NoSuchAlgorithmException :" + e.getMessage(), e);
        }
        if (dis == null)
        {
            return null;
        }
        byte[] buffer = new byte[8192];
        try
        {
            while (dis.read(buffer) > 0)
                ;
        }
        catch (IOException e)
        {
            logger.error("computeContentMD5 IOException :" + e.getMessage(), e);
            return null;
        }
        String md5Content = new String(Base64.encodeBase64(dis.getMessageDigest().digest()));
        try
        {
            fis.getChannel().position(0);
        }
        catch (IOException e)
        {
            logger.error("computeContentMD5 IOException :" + e.getMessage(), e);
            return null;
        }
        return md5Content;
    }

    public static XMLGregorianCalendar dateToXMLGregorianCalendar(Date d)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return calendarToXMLGregorianCalendar(cal);
    }

    public static XMLGregorianCalendar calendarToXMLGregorianCalendar(Calendar cal)
    {

        try
        {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar) cal);
        }
        catch (Exception e)
        {
            logger.error("calendarToXMLGregorianCalendar cal=" + cal.getClass().getSimpleName() + e.getMessage(), e);
        }

        return null;
    }

    public static boolean createFilePath(String path)
    {
        boolean res = false;
        try
        {
            File d = new File(path);
            if (d.exists())
                return true;
            boolean mkFlag = d.mkdirs();
            if (!mkFlag)
            {
                logger.error("Can not create directory: " + path);
            }
            return mkFlag;
        }
        catch (Exception e)
        {
            logger.error("createFilePath path=" + path + e.getMessage(), e);
        }
        return res;
    }

    public static byte[] downloadFileToCache(String url)
    {
        URL website;
        byte[] result = null;
        try
        {
            website = new URL(url);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            for (;;)
            {
                ByteBuffer buffer = ByteBuffer.allocate(4096);
                int len = rbc.read(buffer);
                if (len <= 0)
                    break;
                bos.write(buffer.array(), 0, len);
            }
            result = bos.toByteArray();
        }
        catch (Exception e)
        {
            logger.error("Error while downloading url: " + url);
        }

        return result;
    }

    public static <E extends Enum<E>> List<String> listFullName(Class<E> clazz)
    {
        List<String> list = new ArrayList<String>();
        E[] arrays = clazz.getEnumConstants();
        for (int i = 0; i < arrays.length; i++)
        {
            String fullName = clazz.getSimpleName() + "." + arrays[i].name();
            list.add(fullName);
        }
        return list;
    }

    public static <E extends Enum<E>> E generateEnumFromFullName(Class<E> clazz, String fullName)
    {
        if (fullName == null || fullName.length() == 0)
        {
            return null;
        }
        E enumValue = null;
        try
        {
            String name = fullName.substring(fullName.lastIndexOf('.') + 1);
            enumValue = Enum.valueOf(clazz, name);
        }
        catch (Exception e)
        {
            logger.error("generateEnumFromFullName error :" + e.getMessage(), e);
            enumValue = null;
        }
        return enumValue;
    }

    public static <E extends Enum<E>> String getFullName(Class<E> clazz, String name)
    {
        String fullName = null;
        if (name == null || name.length() == 0)
        {
            return null;
        }
        try
        {
            E element = Enum.valueOf(clazz, name);
            fullName = element.getClass().getSimpleName() + "." + name;
        }
        catch (Exception e)
        {
            logger.error("getFullName error :" + e.getMessage(), e);
            fullName = null;
        }
        return fullName;
    }

    public static <T> void setFieldValue(T obj, String fieldName, String fieldValue) throws Exception
    {
        Class<?> clazz = obj.getClass();
        Field f = Helper.getTheField(clazz, fieldName);
        if (f == null)
        {
            return;
        }

        if (fieldValue == null)
        {
            return;
        }

        f.setAccessible(true);
        Class<?> fieldTypeClazz = f.getType();
        String fieldTypeName = fieldTypeClazz.getSimpleName();
        if ("Date".equalsIgnoreCase(fieldTypeName))
        {
            Date date = TimeFormatterUtil.convertToDate(fieldValue);
            f.set(obj, date);
        }
        else if ("String".equalsIgnoreCase(fieldTypeName))
        {
            f.set(obj, fieldValue);
        }
        else if ("boolean".equals(fieldTypeName))
        {
            if ("true".equals(fieldValue) || "1".equals(fieldValue))
            {
                f.setBoolean(obj, true);
            }
            else
            {
                f.setBoolean(obj, false);
            }
        }
        else if ("Boolean".equals(fieldTypeName))
        {
            if ("null".equals(fieldValue))
            {
                f.set(obj, null);
            }
            else if ("true".equals(fieldValue) || "1".equals(fieldValue))
            {
                f.set(obj, Boolean.TRUE);
            }
            else
            {
                f.set(obj, Boolean.FALSE);
            }
        }
        else if ("int".equalsIgnoreCase(fieldTypeName))
        {
            f.setInt(obj, Integer.parseInt(fieldValue));
        }
        else if ("Integer".equalsIgnoreCase(fieldTypeName))
        {
            if (fieldValue == "")
            {
                f.set(obj, null);
            }
            else
            {
                f.set(obj, new Integer(fieldValue));
            }
        }
        else if ("long".equals(fieldTypeName))
        {
            f.setLong(obj, Long.parseLong(fieldValue));
        }
        else if ("Long".equals(fieldTypeName))
        {
            if (fieldValue == "")
            {
                f.set(obj, null);
            }
            else
            {
                f.set(obj, new Long(fieldValue));
            }
        }
        else if ("short".equals(fieldTypeName))
        {
            f.setShort(obj, Short.parseShort(fieldValue));
        }
        else if ("Short".equals(fieldTypeName))
        {
            f.set(obj, new Short(fieldValue));
        }
        else if ("byte".equals(fieldTypeName))
        {
            f.setShort(obj, Byte.parseByte(fieldValue));
        }
        else if ("Byte".equals(fieldTypeName))
        {
            f.set(obj, new Byte(fieldValue));
        }
        else if ("float".equals(fieldTypeName))
        {
            f.setFloat(obj, Float.parseFloat(fieldValue));
        }
        else if ("Float".equals(fieldTypeName))
        {
            f.set(obj, new Float(fieldValue));
        }
        else if ("double".equals(fieldTypeName))
        {
            f.setDouble(obj, Double.parseDouble(fieldValue));
        }
        else if ("Double".equals(fieldTypeName))
        {
            f.set(obj, new Double(fieldValue));
        }
        else if ("char".equalsIgnoreCase(fieldTypeName))
        {
            f.setChar(obj, Character.valueOf(fieldValue.charAt(0)));
        }
        else if ("Character".equalsIgnoreCase(fieldTypeName))
        {
            f.set(obj, new Character(fieldValue.charAt(0)));
        }
        else if ("BigDecimal".equalsIgnoreCase(fieldTypeName))
        {
            f.set(obj, new BigDecimal(fieldValue));
        }
    }

    private static Field getTheField(Class<?> clazz, String fieldName)
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

    public static <T> String getFieldValue(T obj, String fieldName)
    {
        Class<?> clazz = obj.getClass();
        Field f = Helper.getTheField(clazz, fieldName);
        if (f == null)
        {
            return null;
        }
        Object fieldValue = null;
        try
        {
            f.setAccessible(true);
            fieldValue = f.get(obj);

        }
        catch (IllegalArgumentException e)
        {
            logger.error("getFieldValue IllegalArgumentException :" + e.getMessage(), e);
            return null;
        }
        catch (IllegalAccessException e)
        {
            logger.error("getFieldValue IllegalAccessException" + e.getMessage(), e);
            return null;
        }

        if (fieldValue == null)
            return null;

        return fieldValue.toString();
    }

    public static <T> Object getFieldObjectValue(T obj, String fieldName)
    {
        Class<?> clazz = obj.getClass();
        Field f = Helper.getTheField(clazz, fieldName);
        if (f == null)
        {
            return null;
        }
        Object fieldValue = null;
        try
        {
            f.setAccessible(true);
            fieldValue = f.get(obj);

        }
        catch (IllegalArgumentException e)
        {
            logger.error("getFieldObjectValue IllegalArgumentException" + e.getMessage(), e);
            return null;
        }
        catch (IllegalAccessException e)
        {
            logger.error("getFieldObjectValue IllegalAccessException" + e.getMessage(), e);
            return null;
        }

        if (fieldValue == null)
        {
            return null;
        }

        // formatter number
        if (f.getType().getSimpleName().equalsIgnoreCase("double")
                || f.getType().getSimpleName().equalsIgnoreCase("BigDecimal"))
        {
            BigDecimal bigDecimal = new BigDecimal(fieldValue.toString());
            bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            fieldValue = bigDecimal.toString();
        }

        // formatter date
        if (f.getType().getSimpleName().equalsIgnoreCase("Date"))
        {
            fieldValue = TimeFormatterUtil.convertToString((Date) fieldValue);
        }

        return fieldValue;
    }

    public static String mergePDF(String pathToShippingLabelFile, String[] fileNames, String mergedFileName)
    {
        PDFMergerUtility mergedPdf = new PDFMergerUtility();
        for (int i = 0; i < fileNames.length; i++)
        {
            String fileName = fileNames[i];
            if (fileName.endsWith(".pdf"))
            {
                mergedPdf.addSource(pathToShippingLabelFile + fileName);
            }
        }
        mergedPdf.setDestinationFileName(pathToShippingLabelFile + mergedFileName);
        try
        {
            mergedPdf.mergeDocuments();
        }
        catch (COSVisitorException e)
        {
            logger.error("mergePDF COSVisitorException :" + e.getMessage(), e);
        }
        catch (Exception e)
        {
            logger.error(" mergePDF exception :" + e.getMessage(), e);
        }
        File file = new File(pathToShippingLabelFile + mergedFileName);
        if (!file.exists())
        {
            logger.error("Generate Labels ERROR. " + file.getAbsolutePath() + " not exist");
            mergedFileName = null;
        }
        return mergedFileName;
    }

    public static String readFile(String path, Charset encoding)
    {
        String data = null;
        byte[] encoded = null;
        try
        {
            encoded = Files.readAllBytes(Paths.get(path));
        }
        catch (IOException e)
        {
            logger.error("ReadFile ERROR. path=" + path, e);
        }
        data = new String(encoded, encoding);
        return data;
    }

    public static void writeFile(String path, String data)
    {
        try
        {
            Files.write(Paths.get(path), data.getBytes(Constant.ENCODING));
        }
        catch (IOException e)
        {
            logger.error("writeFile ERROR. path=" + path, e);
        }
    }

    public static void writeFile(String path, String data, Charset charset)
    {
        try
        {
            Files.write(Paths.get(path), data.getBytes(charset));
        }
        catch (IOException e)
        {
            logger.error("writeFile ERROR. path=" + path, e);
        }
    }

    public static String md5(String str)
    {
        MessageDigest messageDigest = null;

        try
        {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        }
        catch (NoSuchAlgorithmException e)
        {
            logger.error(e);
        }
        catch (UnsupportedEncodingException e)
        {
            logger.error(e);
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++)
        {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }
    public static String saveShipplingLabelFile(byte[] labelData, String courierId, String shippingLabelPath,
            String trackingNumber, String businessNum)
    {
        String savedLabelFileName;
        String labelFileName = null;
        String extension = null;
        File file;
        boolean isUps = ("UPS".equals(courierId));
        boolean isUsps = ("USPS".equals(courierId));
        if (isUps)
        {
            extension = ".GIF";
        }
        else
        {
            if (labelData[1] == 'P' && labelData[2] == 'N' && labelData[3] == 'G')
            {
                extension = ".PNG";
            }
            else if (labelData[1] == 'h' && labelData[2] == 't' && labelData[3] == 'm')
            {
                extension = ".html";
            }
            else
            {
                extension = ".pdf";
            }
        }

        try
        {
            if (!shippingLabelPath.endsWith("/"))
            {
                shippingLabelPath += "/";
            }
            SimpleDateFormat dateFormatForFileName = new SimpleDateFormat("yyyyMMddHHmmss");
            for (;;)
            {
                String timeStampFileName = dateFormatForFileName.format(new Date()) + "-" + r.nextInt(10000);
                String newExtension = null;
                if (extension.equals(".PNG") || isUps)
                {
                    newExtension = ".pdf";
                }
                else
                {
                    newExtension = extension;
                }
                labelFileName = courierId + "-" + timeStampFileName + newExtension;
                savedLabelFileName = shippingLabelPath + labelFileName;
                file = new File(savedLabelFileName);
                if (!file.exists())
                {
                    break;
                }

            }
            logger.info("The file length of shipping label file:" + savedLabelFileName + " is: " + labelData.length);
            if (extension.equals(".PNG") || isUps)
            {
                ByteArrayInputStream bais = new ByteArrayInputStream(labelData);
                BufferedImage imgBuf = javax.imageio.ImageIO.read(bais);
                boolean res;
                if (isUps)
                {
                    imgBuf = Rotate(imgBuf, 90);
                    //res = ImageIO.write(imgBuf, "GIF", file);
                    res = savePdfByImg(imgBuf, savedLabelFileName, "GIF");
                }
                else
                {
                    if (isUsps)
                    {
                        BufferedImage businessNumImgBuf = generateCode128Barcode(businessNum);
                        imgBuf = ImgMerge(imgBuf, businessNumImgBuf, 20, 130);
                    }
                    //res = ImageIO.write(imgBuf, "PNG", file);
                    res = savePdfByImg(imgBuf, savedLabelFileName, "PNG");
                }

                if (!res)
                {
                    logger.error("Write " + labelFileName + " error.");
                    labelFileName = null;
                }
            }
            else
            {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(labelData);
                fos.flush();
                fos.close();
            }
        }
        catch (Exception e)
        {
            logger.error("Write " + labelFileName + " error: " + e.getMessage());
            labelFileName = null;
        }
        return labelFileName;
    }
    
    //将图片转为pdf
    public static boolean savePdfByImg(BufferedImage img,String pdfPath,String type) 
    {
        try
        {
            Document doc = new Document(null, 0, 0, 0, 0);
            FileOutputStream fos = new FileOutputStream(pdfPath);
            PdfWriter.getInstance(doc, fos);
            doc.setPageSize(new com.lowagie.text.Rectangle(img.getWidth(), img.getHeight()));
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img, type, os);
            byte[] b = os.toByteArray();
            com.lowagie.text.Image image = com.lowagie.text.Image.getInstance(b);
            doc.open();
            doc.add(image);
            doc.close();
            return true;
        }
        catch (Exception e)
        {
            logger.error("img-->>pdf " + pdfPath + " error: " + e.getMessage());
            return false;
        }
    }

    public static BigDecimal convertKgToLb(BigDecimal weight)
    {
        if (weight == null)
        {
            return null;
        }
        BigDecimal weightInLb = BigDecimal.valueOf(Constant.KG_PER_LB * weight.doubleValue());
        return weightInLb;
    }

    public static BigDecimal convertCmToIn(BigDecimal cm)
    {
        if (cm == null)
        {
            return null;
        }
        BigDecimal inch = BigDecimal.valueOf(Constant.INCH_PER_CM * cm.doubleValue());
        return inch;
    }

    public static double convertKgToLb(double weight)
    {
        double weightInLb = DoubleUtil.mul(weight, Constant.KG_PER_LB);
        return weightInLb;
    }

    public static double convertKgToLbCeiling1(double weight)
    {
        BigDecimal b = new BigDecimal(Constant.KG_PER_LB * weight);
        double result = b.setScale(1, BigDecimal.ROUND_CEILING).doubleValue();

        return result;
    }

    public static double convertLbToKg(double weightLbs)
    {
        return DoubleUtil.div(weightLbs, Constant.KG_PER_LB, 5);
    }

    public static double convertCmToInCeiling1(double cm)
    {
        BigDecimal b = new BigDecimal(Constant.INCH_PER_CM * cm);
        double result = b.setScale(1, BigDecimal.ROUND_CEILING).doubleValue();

        return result;
    }

    public static double convertCmToIn(double cm)
    {
        double inch = DoubleUtil.mul(cm, Constant.INCH_PER_CM);
        return inch;
    }

    public static double convertCmToFt(double cm)
    {
        double inch = DoubleUtil.mul(cm, Constant.FT_PER_CM);
        return inch;
    }

    public static double convertKgToOZS(double weight)
    {
        double weightInOZS = DoubleUtil.mul(weight, Constant.KG_PER_OZS);
        return weightInOZS;
    }

    public static <T> Map<String, String> getTheFieldsInfo(T obj)
    {
        Class<?> clazz = obj.getClass();
        Map<String, String> fieldsInfo = new HashMap<String, String>();
        Field[] fieldTemp = clazz.getDeclaredFields();
        if (fieldTemp != null && fieldTemp.length > 0)
        {
            for (Field f : fieldTemp)
            {
                fieldsInfo.put(f.getName(), f.getType().getSimpleName());
            }
        }
        return fieldsInfo;
    }

    public static BufferedImage Rotate(Image src, int angel)
    {
        int src_width = src.getWidth(null);
        int src_height = src.getHeight(null);
        // calculate the new image size
        Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angel);

        BufferedImage res = null;
        res = new BufferedImage(rect_des.width, rect_des.height, BufferedImage.TYPE_BYTE_INDEXED);
        Graphics2D g2 = res.createGraphics();
        // transform
        g2.translate((rect_des.width - src_width) / 2, (rect_des.height - src_height) / 2);
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);

        g2.drawImage(src, null, null);
        return res;
    }

    public static Rectangle CalcRotatedSize(Rectangle src, int angel)
    {
        // if angel is greater than 90 degree, we need to do some conversion
        if (angel >= 90)
        {
            if (angel / 90 % 2 == 1)
            {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }

        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_dalta_width = Math.atan((double) src.height / src.width);
        double angel_dalta_height = Math.atan((double) src.width / src.height);

        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_height));
        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        return new java.awt.Rectangle(new Dimension(des_width, des_height));
    }

    public static BufferedImage generateCode128Barcode(String businessNum)
    {
        Code128Bean bean = new Code128Bean();
        final int dpi = 200;
        bean.setFontName("Helvetica");// 设置字体
        bean.setModuleWidth(0.4);// 设置条码线条宽度
        bean.setHeight(12);// 设置条码高度
        bean.doQuietZone(false);// 是否设置空白区
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(dpi, BufferedImage.TYPE_3BYTE_BGR, false, 0);
        bean.generateBarcode(canvas, businessNum);
        return canvas.getBufferedImage();
    }

    public static BufferedImage generateCode39Barcode(String businessNum)
    {
        Code39Bean bean = new Code39Bean();
        final int dpi = 200;
        bean.setFontName("Helvetica");
        bean.setModuleWidth(0.25);// 设置条码线条宽度
        bean.setHeight(12);
        bean.doQuietZone(false);
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(dpi, BufferedImage.TYPE_3BYTE_BGR, false, 0);
        bean.generateBarcode(canvas, businessNum);
        return canvas.getBufferedImage();
    }

    public static BufferedImage ImgMerge(BufferedImage imgBuf, BufferedImage businessNumImgBuf, int x, int y)
    {
        y = imgBuf.getHeight() - y;
        int width = businessNumImgBuf.getWidth();// 图片宽度
        int height = businessNumImgBuf.getHeight();// 图片高度
        // 从图片中读取RGB
        int[] imageArray = new int[width * height];
        imageArray = businessNumImgBuf.getRGB(0, 0, width, height, imageArray, 0, width);
        imgBuf.setRGB(x, y, width, height, imageArray, 0, width);// 设置部分的RGB
        return imgBuf;
    }

    public static String ImagePathOf128BarCode(String baseUrl, String dir, String fileName, String code)
    {
        if (StringUtils.isEmpty(code))
        {
            return null;
        }

        if (!createFilePath(Constant.ApplicationContextPath + dir))
        {
            return null;
        }
        File imgFile = new File(Constant.ApplicationContextPath + dir + fileName);
        if (!imgFile.exists())
        {
            BufferedImage image = generateCode128Barcode(code);
            try
            {
                ImageIO.write(image, "PNG", imgFile);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return null;
            }
        }
        return baseUrl + dir + fileName;
    }

    public static List<List<String>> convertCsvToTable(String csvData, String delimiter)
    {
        List<List<String>> arrData = new ArrayList<>();
        // Check to see if the delimiter is defined. If not,
        // then default to comma.
        if (StringUtils.isEmpty(csvData))
        {
            return arrData;
        }

        delimiter = delimiter == null ? "," : delimiter;

        // Create a regular expression to parse the CSV values.
        Pattern objPattern = Pattern.compile(
                //delimiter
                "(\\" + delimiter + "|\\r?\\n|\\r|^)" +
                        // Quoted fields.
                        "(?:\"([^\"]*(?:\"\"[^\"]*)*)\"|" +
                        // Standard fields.
                        "([^\"\\" + delimiter + "\\r\\n]*))",
                Pattern.CASE_INSENSITIVE);
        // Create an array to hold our data. Give the array
        // a default empty first row.
        arrData.add(new ArrayList<String>());

        // Create an array to hold our individual pattern
        // matching groups.
        Matcher matcher = objPattern.matcher(csvData);
        // Keep looping over the regular expression matches
        // until we can no longer find a match.
        while (matcher.find())
        {

            // Get the delimiter that was found.
            String strMatchedDelimiter = matcher.group(1);

            // Check to see if the given delimiter has a length
            // (is not the start of string) and if it matches
            // field delimiter. If id does not, then we know
            // that this delimiter is a row delimiter.
            if (strMatchedDelimiter.length() > 0 && !delimiter.equals(strMatchedDelimiter))
            {
                arrData.add(new ArrayList<String>());
            }

            // Now that we have our delimiter out of the way,
            // let's check to see which kind of value we
            // captured (quoted or unquoted).
            String quoted = matcher.group(2);
            String strMatchedValue;
            if (!StringUtils.isEmpty(quoted))
            {
                // We found a quoted value. When we capture
                // this value, unescape any double quotes.
                strMatchedValue = quoted.replace("\"\"", "\"");
            }
            else
            {
                // We found a non-quoted value.
                strMatchedValue = matcher.group(3);
            }

            // Now that we have our value string, let's add
            // it to the data array.
            arrData.get(arrData.size() - 1).add(strMatchedValue);
        }
        return arrData;
    }

    public static String arrayToTableHTML(List<List<String>> arrData)
    {
        if (arrData == null)
        {
            return null;
        }
        String style = "<style type='text/css'>"
                + ".dlgtitle{color:black; font-weight:bold; height:30px;padding: 2px, 4px;align:center;background-color: #dfe4eb;}"
                + ".oddrow {background-color: #dfe4eb;}                                                                           "
                + ".even{background-color: #dfe4eb;}                                                                              "
                + ".ocenter{text-align: center;}                                                                                  "
                + "tbody>tr:hover{                                                                                                "
                + "  color: white;                                                                                                "
                + "  background-color: grey;                                                                                      "
                + "}                                                                                                              "
                + "table {                                                                                                        "
                + "  overflow: auto;                                                                                              "
                + "}                                                                                                              "
                + ".tbody>tr:hover{                                                                                               "
                + "  color: white;                                                                                                "
                + "  background-color: gray;                                                                                      "
                + "}                                                                                                              "
                + "tr td{                                                                                                         "
                + "  padding: 2px;                                                                                                "
                + "}                                                                                                              "
                + "</style>";
        String tableStr = style + "<table>";
        for (int i = 0; i < arrData.size(); i++) {
          List<String> row = arrData.get(i);
          if (i == 0) {
            tableStr += "<thead><tr class='dlgtitle ocenter'>";
          } else if (i == 1) {
            tableStr += "<thbody><tr class='event ocenter'>";
          } else if (i % 2 == 0) {
            tableStr += "<tr class='oddrow ocenter'>";
          } else {
            tableStr += "<tr class='event ocenter'>";
          }

          if (row!=null && row.size()>0) {
            for (int j = 0; j < row.size(); j++) {
              tableStr += "<td>" + row.get(j) + "</td>";
            }
          }

          if (i == 0) {
                tableStr += "</tr></thead>";
          } else {
                tableStr += "</tr>";
          }

        }
        tableStr += "</thbody>";
        tableStr += "</table>";
        return tableStr;
    }
    
    /**
     * 随机生成一位字母+四位数字
     * 
     * @return
     */
    public static String genRandomNum()
    {
        String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "M", "N", "P", "Q", "R", "S", "T", "U",
                "V", "W", "X", "Y", "Z" };//过滤 O  L  I

        int[] nums = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        int n = (int) (Math.random() * 23);
        String letter = letters[n];
        int length = letter.length();

        while (length < 5)
        {
            int m = (int) (Math.random() * 10);
            letter += nums[m];
            length++;
        }

        return letter;
    }
    
    /**
     * 生成bar code
     * 
     * @param str
     * @param businessNum
     * @return
     */
    public static File code(String codeName, String barcode, int dpi, int height)
    {
        File file = new File(codeName);
        try
        {
            // Create the barcode bean
            Code128Bean bean = new Code128Bean();
            bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi));// 设置条码线条宽度
            bean.setHeight(height);// 设置条码高度
            bean.doQuietZone(false);// 是否设置空白区
            bean.setFontName("Helvetica");// 设置字体
            bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);// 去掉条码号
            // Open output file
            OutputStream out = new FileOutputStream(file);
            try
            {
                // Set up the canvas provider for monochrome JPEG output
                BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/png", dpi,
                        BufferedImage.TYPE_3BYTE_BGR, false, 0);//TYPE_BYTE_BINARY
                // Generate the barcode
                bean.generateBarcode(canvas, barcode);
                // Signal end of generation
                canvas.finish();
            }
            finally
            {
                out.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return file;
    }
    
    /**
     * 生成货位条码
     * 
     * @param codeList
     * @param imgPath
     * @return
     */
    public static String generatePdf70M30ForLoctCode(List<String> codeList, String imgPath)
    {
        if (codeList == null || codeList.size() == 0)
        {
            return null;
        }
        Document document = new Document(PageSize.A4, 20, 20, 10, 10);
        document.setPageSize(new com.lowagie.text.Rectangle(Float.parseFloat("198.45"), Float.parseFloat("85.05")));
        String fileName = new Date().getTime() + ".pdf";
        String pdfPath = imgPath + fileName;
        try
        {
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();
            for (int i = 0; i < codeList.size(); i++)
            {
                document.newPage();

                // 图片名称
                String codeName = imgPath + new Date().getTime() + ".png";
                String barcode = codeList.get(i);
                File file = code(codeName, barcode, 152, 10);
                com.lowagie.text.Image tempImage1 = com.lowagie.text.Image.getInstance(file.toString());
                // 删除图片文件
                file.delete();
                if(barcode.length() > 12)
                {
                    tempImage1.setAbsolutePosition(10, 32);
                }else{
                    // 图片位置
                    tempImage1.setAbsolutePosition(20, 32);
                }

                // 拼条码号
                Chunk num = new Chunk(barcode);
                Font toFont1 = FontFactory.getFont(FontFactory.COURIER);
                toFont1.setSize(10);
                num.setFont(toFont1);
                num.setTextRise(-40);
                // 拼条码号
                Paragraph bunum = new Paragraph();
                bunum.setIndentationLeft(40);
                bunum.add(num);
                document.add(tempImage1);
                document.add(bunum);
            }
            document.close();
            File file = new File(pdfPath);
            file.deleteOnExit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            document.close();
        }
        return fileName;
    }

    public static String generatePdf100M60(List<String> codeList, String imgPath)
    {
        if(codeList == null || codeList.size() == 0)
        {
            return null;
        }
        Document document = new Document(PageSize.A4, 20, 20, 10, 10);
        document.setPageSize(new com.lowagie.text.Rectangle(Float.parseFloat("283.5"), Float.parseFloat("170.1")));
        String fileName = new Date().getTime() +".pdf";
        String pdfPath = imgPath + fileName;
        try
        {
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();
            for(int i = 0; i < codeList.size() ; i++)
            {
            document.newPage();

            // 图片名称
            String codeName = imgPath + new Date().getTime() + ".png";
//            String barcode = codeList.get(i);
            String barcode = codeList.get(i).split(",")[0];
            File file = code(codeName, barcode, 152, 12);
            com.lowagie.text.Image tempImage1 = com.lowagie.text.Image.getInstance(file.toString());
            tempImage1.scaleAbsolute(123, 50);//自定义大小
            // 删除图片文件
            file.delete();
            // 图片位置
            tempImage1.setAbsolutePosition(80, 80);
            String sku = codeList.get(i).split(",")[1];
            if (sku != null && sku.length() > 25)
            {
                sku = sku.substring(0, 25) + "...";
            }
            // 拼条码号
            Chunk num = new Chunk(barcode);
            Font toFont1 = FontFactory.getFont(FontFactory.COURIER);
            toFont1.setSize(10);
            num.setFont(toFont1);
            num.setTextRise(-80);
            Paragraph bunum = new Paragraph();
            bunum.setIndentationLeft(85);
            bunum.add(num);
            document.add(tempImage1);
            document.add(bunum);
            //Æ´SKU
            Chunk csku = new Chunk(sku);
            Font skufont = FontFactory.getFont(FontFactory.COURIER);
            skufont.setSize(Float.parseFloat("8.5"));
            csku.setFont(skufont);
            csku.setTextRise(-80);
            Paragraph bsku = new Paragraph();
            bsku.setIndentationLeft(60);
            bsku.add(csku);
            document.add(bsku);
            }
            document.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            document.close();
        }
        return fileName;
    }
    
    public static String generatePdf70M30(List<String> codeList, String imgPath)
    {
        if(codeList == null || codeList.size() == 0)
        {
            return null;
        }
        Document document = new Document(PageSize.A4, 20, 20, 10, 10);
        document.setPageSize(new com.lowagie.text.Rectangle(Float.parseFloat("198.45"), Float.parseFloat("85.05")));
        String fileName = new Date().getTime() +".pdf";
        String pdfPath = imgPath + fileName;
        try
        {
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();
            for(int i = 0; i < codeList.size() ; i++)
            {
                document.newPage();
                
                // 图片名称
                String codeName = imgPath + new Date().getTime() + ".png";
//                String barcode = codeList.get(i);
                String barcode = codeList.get(i).split(",")[0];
                File file = code(codeName, barcode, 152, 10);
                com.lowagie.text.Image tempImage1 = com.lowagie.text.Image.getInstance(file.toString());
                tempImage1.scaleAbsolute(123, 27);//自定义大小
                // 删除图片文件
                file.delete();
                // 图片位置
                tempImage1.setAbsolutePosition(35, 40);
                String sku = codeList.get(i).split(",")[1];
                if (sku != null && sku.length() > 25)
                {
                    sku = sku.substring(0, 25) + "...";
                }
                // 拼条码号
                Chunk num = new Chunk(barcode);
                Font toFont1 = FontFactory.getFont(FontFactory.COURIER);
                toFont1.setSize(10);
                num.setFont(toFont1);
                num.setTextRise(-30);
                Paragraph bunum = new Paragraph();
                bunum.setIndentationLeft(40);
                bunum.add(num);
                document.add(tempImage1);
                document.add(bunum);
                //拼SKU
                Chunk csku = new Chunk(sku);
                Font skufont = FontFactory.getFont(FontFactory.COURIER);
                skufont.setSize(Float.parseFloat("8.5"));
                csku.setFont(skufont);
                csku.setTextRise(-30);
                Paragraph bsku = new Paragraph();
                bsku.setIndentationLeft(15);
                bsku.add(csku);
                document.add(bsku);
            }
            document.close();
            File file = new File(pdfPath);
            file.deleteOnExit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            document.close();
        }
        return fileName;
    }
    
    public static String generatePdfA4(List<String> codeList, String imgPath)
    {
//        Document doc = new Document(PageSize.A4, 20, 20, 10, 10);
//        String fileName = new Date().getTime() +".pdf";
//        String pdfPath = imgPath + fileName;
//        try
//        {
//            PdfWriter.getInstance(doc, new FileOutputStream(pdfPath));
//            doc.open();
//            // 生成4列的表格
//            PdfPTable table = new PdfPTable(4);
//            table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
//            table.setWidthPercentage(110);
//            table.setWidthPercentage(110);
//            PdfPCell cell = new PdfPCell();
//
//            // 添加行
//            int size = 0;
//            for (int j = 0; j < codeList.size(); j++)
//            {
//                if (codeList.size() - 1 - j < 4)
//                {
//                    size = codeList.size() - 1 - j;
//
//                }
//                // 图片名称
//                String codeName = imgPath + new Date().getTime() + ".png";
//                String barcode = codeList.get(j);
//                File file = code(codeName, barcode, true);
//                com.lowagie.text.Image tempImage1 = com.lowagie.text.Image.getInstance(file.toString());
//                file.delete();
//                cell = new PdfPCell(tempImage1);
//                cell.setBorderWidth(0);
//
//                table.addCell(cell);
//
//            }
//            for (int x = 0; x < 4 - size; x++)
//            {
//                table.addCell("");
//            }
//
//            List<PdfPRow> list =(ArrayList<PdfPRow>) table.getRows();
//            for (PdfPRow row : list)
//            {
//                for (PdfPCell cells : row.getCells())
//                {
//                    if (cells != null)
//                    {
//                        cells.setPadding(10.0f);
//                    }
//                }
//            }
//
//            doc.add(table);
//            doc.newPage();
//
//        }
//        catch (Exception e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        finally
//        {
//            doc.close();
//        }
//        return fileName;
        
        if (codeList == null || codeList.size() == 0)
        {
            return null;
        }
        Document document = new Document(PageSize.A4, 20, 20, 10, 10);
        String fileName = new Date().getTime() + ".pdf";
        String pdfPath = imgPath + fileName;
        try
        {
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();

            int length = codeList.size();
            int row = 4;
            int col = 11;
            int celllen = row * col;
            // 图片之间的间隔
            int leftSpace = 21;
            // 图片宽度
            int imageWidth = 123;
            // 图片纵坐标
            int imgRight = 790;
            int highSpace = 75;
            int codeDpi = 130;
            int codeHight = 9;
            int pdfPage = length % celllen == 0 ? length / celllen : length / celllen + 1;
            int i = 0;
            for (int n = 0; n < pdfPage; n++)
            {
                document.newPage();
                for (; i < codeList.size(); i++)
                {

                    // 图片名称
                    String codeName = imgPath + new Date().getTime() + ".png";
                    String barcode = codeList.get(i);
                    File file = code(codeName, barcode, codeDpi, codeHight);
                    // BufferedImage sourceImg =ImageIO.read(new FileInputStream(file));
                    // System.out.println(sourceImg.getWidth());
                    // System.out.println(sourceImg.getHeight());
                    com.lowagie.text.Image tempImage1 = com.lowagie.text.Image.getInstance(file.toString());
                    tempImage1.scaleAbsolute(123,27);//自定义大小
//                    if(i == 0)
//                    {
//                        //获取图片宽度
//                        imageWidth = (int)tempImage1.getWidth();
//                    }
                    // 删除图片文件
                    file.delete();

                    if (i - celllen * n >= 0 && i - celllen * n < 4)
                    {
                        // 第一行每个单元格
                        int len = (i - celllen * n) % 4 == 0 ? 0 : (i - celllen * n) % 4;
                        // 图片位置
                        tempImage1.setAbsolutePosition(leftSpace + leftSpace * len + imageWidth * len, imgRight);

                        // 拼条码号
                        Chunk num = new Chunk(barcode);
                        Font toFont1 = FontFactory.getFont(FontFactory.COURIER);
                        toFont1.setSize(10);
                        num.setFont(toFont1);
                        num.setTextRise(-45);
                        if (len == 1)
                        {
                            num.setTextRise(-30);
                        }
                        else if (len == 2)
                        {
                            num.setTextRise(-15);
                        }
                        else if (len == 3)
                        {
                            num.setTextRise(0);
                        }
                        Paragraph bunum = new Paragraph();
                        bunum.setIndentationLeft(leftSpace + leftSpace * len + imageWidth * len);
                        bunum.add(num);
                        document.add(tempImage1);
                        document.add(bunum);
                    }
                    else if (i - celllen * n >= 4 && i - celllen * n < 8)
                    {
                        int len = (i - celllen * n) % 4 == 0 ? 0 : (i - celllen * n) % 4;
                        // 图片位置
                        tempImage1.setAbsolutePosition(leftSpace + leftSpace * len + imageWidth * len, imgRight - highSpace);

                        // 拼条码号
                        Chunk num = new Chunk(barcode);
                        Font toFont1 = FontFactory.getFont(FontFactory.COURIER);
                        toFont1.setSize(10);
                        num.setFont(toFont1);
                        num.setTextRise(-55);
                        if (len == 1)
                        {
                            num.setTextRise(-40);
                        }
                        else if (len == 2)
                        {
                            num.setTextRise(-25);
                        }
                        else if (len == 3)
                        {
                            num.setTextRise(-10);
                        }
                        Paragraph bunum = new Paragraph();
                        bunum.setIndentationLeft(leftSpace + leftSpace * len + imageWidth * len);
                        bunum.add(num);
                        document.add(tempImage1);
                        document.add(bunum);
                    }
                    else if (i - celllen * n >= 8 && i - celllen * n < 12)
                    {
                        int len = (i - celllen * n) % 4 == 0 ? 0 : (i - celllen * n) % 4;
                        // 图片位置
                        tempImage1.setAbsolutePosition(leftSpace + leftSpace * len + imageWidth * len, imgRight - highSpace * 2);

                        // 拼条码号
                        Chunk num = new Chunk(barcode);
                        Font toFont1 = FontFactory.getFont(FontFactory.COURIER);
                        toFont1.setSize(10);
                        num.setFont(toFont1);
                        num.setTextRise(-65);
                        if (len == 1)
                        {
                            num.setTextRise(-50);
                        }
                        else if (len == 2)
                        {
                            num.setTextRise(-35);
                        }
                        else if (len == 3)
                        {
                            num.setTextRise(-20);
                        }
                        Paragraph bunum = new Paragraph();
                        bunum.setIndentationLeft(leftSpace + leftSpace * len + imageWidth * len);
                        bunum.add(num);
                        document.add(tempImage1);
                        document.add(bunum);
                    }
                    else if (i - celllen * n >= 12 && i - celllen * n < 16)
                    {
                        int len = (i - celllen * n) % 4 == 0 ? 0 : (i - celllen * n) % 4;
                        // 图片位置
                        tempImage1.setAbsolutePosition(leftSpace + leftSpace * len + imageWidth * len, imgRight - highSpace * 3);

                        // 拼条码号
                        Chunk num = new Chunk(barcode);
                        Font toFont1 = FontFactory.getFont(FontFactory.COURIER);
                        toFont1.setSize(10);
                        num.setFont(toFont1);
                        num.setTextRise(-75);
                        if (len == 1)
                        {
                            num.setTextRise(-60);
                        }
                        else if (len == 2)
                        {
                            num.setTextRise(-45);
                        }
                        else if (len == 3)
                        {
                            num.setTextRise(-30);
                        }
                        Paragraph bunum = new Paragraph();
                        bunum.setIndentationLeft(leftSpace + leftSpace * len + imageWidth * len);
                        bunum.add(num);
                        document.add(tempImage1);
                        document.add(bunum);
                    }
                    else if (i - celllen * n >= 16 && i - celllen * n < 20)
                    {
                        int len = (i - celllen * n) % 4 == 0 ? 0 : (i - celllen * n) % 4;
                        // 图片位置
                        tempImage1.setAbsolutePosition(leftSpace + leftSpace * len + imageWidth * len, imgRight - highSpace * 4);

                        // 拼条码号
                        Chunk num = new Chunk(barcode);
                        Font toFont1 = FontFactory.getFont(FontFactory.COURIER);
                        toFont1.setSize(10);
                        num.setFont(toFont1);
                        num.setTextRise(-85);
                        if (len == 1)
                        {
                            num.setTextRise(-70);
                        }
                        else if (len == 2)
                        {
                            num.setTextRise(-55);
                        }
                        else if (len == 3)
                        {
                            num.setTextRise(-40);
                        }
                        Paragraph bunum = new Paragraph();
                        bunum.setIndentationLeft(leftSpace + leftSpace * len + imageWidth * len);
                        bunum.add(num);
                        document.add(tempImage1);
                        document.add(bunum);
                    }
                    else if (i - celllen * n >= 20 && i - celllen * n < 24)
                    {
                        int len = (i - celllen * n) % 4 == 0 ? 0 : (i - celllen * n) % 4;
                        // 图片位置
                        tempImage1.setAbsolutePosition(leftSpace + leftSpace * len + imageWidth * len, imgRight - highSpace * 5);

                        // 拼条码号
                        Chunk num = new Chunk(barcode);
                        Font toFont1 = FontFactory.getFont(FontFactory.COURIER);
                        toFont1.setSize(10);
                        num.setFont(toFont1);
                        num.setTextRise(-95);
                        if (len == 1)
                        {
                            num.setTextRise(-80);
                        }
                        else if (len == 2)
                        {
                            num.setTextRise(-65);
                        }
                        else if (len == 3)
                        {
                            num.setTextRise(-50);
                        }
                        Paragraph bunum = new Paragraph();
                        bunum.setIndentationLeft(leftSpace + leftSpace * len + imageWidth * len);
                        bunum.add(num);
                        document.add(tempImage1);
                        document.add(bunum);
                    }
                    else if (i - celllen * n >= 24 && i - celllen * n < 28)
                    {
                        int len = (i - celllen * n) % 4 == 0 ? 0 : (i - celllen * n) % 4;
                        // 图片位置
                        tempImage1.setAbsolutePosition(leftSpace + leftSpace * len + imageWidth * len, imgRight - highSpace * 6);

                        // 拼条码号
                        Chunk num = new Chunk(barcode);
                        Font toFont1 = FontFactory.getFont(FontFactory.COURIER);
                        toFont1.setSize(10);
                        num.setFont(toFont1);
                        num.setTextRise(-105);
                        if (len == 1)
                        {
                            num.setTextRise(-90);
                        }
                        else if (len == 2)
                        {
                            num.setTextRise(-75);
                        }
                        else if (len == 3)
                        {
                            num.setTextRise(-60);
                        }
                        Paragraph bunum = new Paragraph();
                        bunum.setIndentationLeft(leftSpace + leftSpace * len + imageWidth * len);
                        bunum.add(num);
                        document.add(tempImage1);
                        document.add(bunum);
                    }
                    else if (i - celllen * n >= 28 && i - celllen * n < 32)
                    {
                        int len = (i - celllen * n) % 4 == 0 ? 0 : (i - celllen * n) % 4;
                        // 图片位置
                        tempImage1.setAbsolutePosition(leftSpace + leftSpace * len + imageWidth * len, imgRight - highSpace * 7);

                        // 拼条码号
                        Chunk num = new Chunk(barcode);
                        Font toFont1 = FontFactory.getFont(FontFactory.COURIER);
                        toFont1.setSize(10);
                        num.setFont(toFont1);
                        num.setTextRise(-115);
                        if (len == 1)
                        {
                            num.setTextRise(-100);
                        }
                        else if (len == 2)
                        {
                            num.setTextRise(-85);
                        }
                        else if (len == 3)
                        {
                            num.setTextRise(-70);
                        }
                        Paragraph bunum = new Paragraph();
                        bunum.setIndentationLeft(leftSpace + leftSpace * len + imageWidth * len);
                        bunum.add(num);
                        document.add(tempImage1);
                        document.add(bunum);
                    }
                    else if (i - celllen * n >= 32 && i - celllen * n < 36)
                    {
                        int len = (i - celllen * n) % 4 == 0 ? 0 : (i - celllen * n) % 4;
                        // 图片位置
                        tempImage1.setAbsolutePosition(leftSpace + leftSpace * len + imageWidth * len, imgRight - highSpace * 8);

                        // 拼条码号
                        Chunk num = new Chunk(barcode);
                        Font toFont1 = FontFactory.getFont(FontFactory.COURIER);
                        toFont1.setSize(10);
                        num.setFont(toFont1);
                        num.setTextRise(-125);
                        if (len == 1)
                        {
                            num.setTextRise(-110);
                        }
                        else if (len == 2)
                        {
                            num.setTextRise(-95);
                        }
                        else if (len == 3)
                        {
                            num.setTextRise(-80);
                        }
                        Paragraph bunum = new Paragraph();
                        bunum.setIndentationLeft(leftSpace + leftSpace * len + imageWidth * len);
                        bunum.add(num);
                        document.add(tempImage1);
                        document.add(bunum);
                    }
                    else if (i - celllen * n >= 36 && i - celllen * n < 40)
                    {
                        int len = (i - celllen * n) % 4 == 0 ? 0 : (i - celllen * n) % 4;
                        // 图片位置
                        tempImage1.setAbsolutePosition(leftSpace + leftSpace * len + imageWidth * len, imgRight - highSpace * 9);

                        // 拼条码号
                        Chunk num = new Chunk(barcode);
                        Font toFont1 = FontFactory.getFont(FontFactory.COURIER);
                        toFont1.setSize(10);
                        num.setFont(toFont1);
                        num.setTextRise(-135);
                        if (len == 1)
                        {
                            num.setTextRise(-120);
                        }
                        else if (len == 2)
                        {
                            num.setTextRise(-105);
                        }
                        else if (len == 3)
                        {
                            num.setTextRise(-90);
                        }
                        Paragraph bunum = new Paragraph();
                        bunum.setIndentationLeft(leftSpace + leftSpace * len + imageWidth * len);
                        bunum.add(num);
                        document.add(tempImage1);
                        document.add(bunum);
                    }
                    else if (i - celllen * n >= 40 && i - celllen * n < 44)
                    {
                        int len = (i - celllen * n) % 4 == 0 ? 0 : (i - celllen * n) % 4;
                        // 图片位置
                        tempImage1.setAbsolutePosition(leftSpace + leftSpace * len + imageWidth * len, imgRight - highSpace * 10);

                        // 拼条码号
                        Chunk num = new Chunk(barcode);
                        Font toFont1 = FontFactory.getFont(FontFactory.COURIER);
                        toFont1.setSize(10);
                        num.setFont(toFont1);
                        num.setTextRise(-145);
                        if (len == 1)
                        {
                            num.setTextRise(-130);
                        }
                        else if (len == 2)
                        {
                            num.setTextRise(-115);
                        }
                        else if (len == 3)
                        {
                            num.setTextRise(-100);
                        }
                        Paragraph bunum = new Paragraph();
                        bunum.setIndentationLeft(leftSpace + leftSpace * len + imageWidth * len);
                        bunum.add(num);
                        document.add(tempImage1);
                        document.add(bunum);
                    }
                    if ((i + 1) % celllen == 0)
                    {
                        i++;
                        break;
                    }

                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            document.close();
        }
        return fileName;
    }
    
   
    
    
    public static void BarcodeGeneratePDF(List<String> codeList, String pdfPath, int columns, int rows, float padding,
            float spacing, float width, float fontSize)
    {
        Document document = new Document(PageSize.A4, 0, 0, 0, 0);
        try
        {
            PdfWriter arg = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();
            document.newPage();
            Table table = new Table(columns, rows);
            table.setWidth(width);
            //table.setBorderWidth(Element.ALIGN_CENTER);//测试用
            table.setBorderColor(new Color(255, 255, 255));
            table.setPadding(padding);
            table.setSpacing(spacing);

            for (String barcode : codeList)
            {
                String barcodes[] = barcode.split(",");
                String code = barcodes[0];
                String num1 = barcodes[0];
                String num2 = barcodes[1];

                Paragraph bunum1 = getParagraph(fontSize, num1);
                Paragraph bunum2 = getParagraph(fontSize, num2);
                com.lowagie.text.Image image = getBarcodeImage(arg, code);
                Cell cell = getCell(bunum1, bunum2, image);

                table.addCell(cell);
            }
            document.add(table);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            document.close();
        }
    }

    public static Cell getCell(Paragraph bunum1, Paragraph bunum2, com.lowagie.text.Image image)
    {
        Cell cell = new Cell();
        cell.add(image);
        cell.add(bunum1);
        cell.add(bunum2);
        cell.setBorderColor(new Color(255, 255, 255));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);//居中
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    public static Paragraph getParagraph(float fontSize, String num1)
    {
        // 拼文字
        Font toFont = FontFactory.getFont(FontFactory.COURIER);
        toFont.setSize(fontSize);
        Paragraph bunum = new Paragraph();
        Chunk chunk1 = new Chunk(num1);
        chunk1.setFont(toFont);
        bunum.add(chunk1);
        return bunum;
    }

    public static com.lowagie.text.Image getBarcodeImage(PdfWriter arg, String code)
    {
        ////////////获取条码图片
        com.lowagie.text.pdf.Barcode128 barcode128 = new Barcode128();
        PdfContentByte arg0 = new PdfContentByte(arg);
        Color arg1 = new Color(0, 0, 0);
        Color arg2 = new Color(255, 255, 255);
        barcode128.setCode(code);
        barcode128.setAltText(code);
        com.lowagie.text.Image image = barcode128.createImageWithBarcode(arg0, arg1, arg2);
        ///////////////
        return image;
    }
    
    //usps的图片改日期方法
    public static BufferedImage getImg(BufferedImage imgBuf, String date, int x, int y)
    {
        int imageWidth = 110;
        int imageHeight = 35;
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_INDEXED);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        graphics.setColor(Color.black);
        java.awt.Font font = new java.awt.Font("arial", Font.NORMAL, 20);
        graphics.setFont(font);
        graphics.drawString(date, 0, 20);
        return ImgMerge(imgBuf, image, x, y);
    }

    public static String stackTraceToString(Throwable e)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString(); // stack trace as a string
    }

    public static <E> String collectionToString(Collection<E> col)
    {
        String res = "";
        if (col == null || col.isEmpty())
        {
            return res;
        }

        StringBuilder sb = new StringBuilder();
        for (E s : col)
        {
            sb.append(s);
            sb.append(",");
        }
        res = sb.toString();
        return res.substring(0, res.length() - 1);
    }

}
