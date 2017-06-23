package generatePDF;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

public class GeneratePDF
{
    public static void main(String[] args) throws Exception
    {
        String pdfPathFrom = "C:\\Users\\dev003\\Desktop\\3577702831518.pdf";
    
//        Document document = new Document();
//        PdfWriter pw = PdfWriter.getInstance(document, new FileOutputStream(pdfPathTo));
        PdfReader reader = new PdfReader(pdfPathFrom);
        System.out.println(reader.getPdfObject(1));
        for (int i = 0; i < reader.getNamedDestination().size(); i++)
        {
            Random r = new Random();
            String fileName = new Date().getTime() + r.nextInt(100000) + ".pdf";
            String pdfPathTo = "C:\\Users\\dev003\\Desktop\\" + fileName;
            FileOutputStream fos = new FileOutputStream(pdfPathTo);
            byte[] b = null;
            fos.write(b);
            fos.flush();
            fos.close();
        }
        String[] values = readFilePdf(pdfPathFrom);
        for (String s : values)
        {
            System.out.println(s);
        }
        System.out.println();
    }

    public static String[] readFilePdf(String fileName)
    {
        FileInputStream is = null;
        PDDocument document = null;
        String[] values = null;
        try
        {
            is = new FileInputStream(fileName);
            PDFParser parser = new PDFParser(is);
            parser.parse();
            document = parser.getPDDocument();
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            String text = stripper.getText(document);
            values = text.split("\n");
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (document != null)
            {
                try
                {
                    document.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return values;
    }
    private static void A4()
    {
        List<String> codeList=new ArrayList<>();codeList.add("abcdefd11,abcdefd11");
        String fileName = new Date().getTime() + ".pdf";
        String pdfPath = "C:\\Users\\dev003\\Desktop\\" + fileName;
        int columns = 3;
        int rows = 8;
        float padding = 8.2f;
        float spacing = 8f;
        float width = 99f;
        float fontSize = 8.5f;
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
}
