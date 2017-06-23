package com.vod.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.apache.pdfbox.util.PDFMergerUtility;

public class Img2PDFUtil
{
    public static void createPDFFromImage(String Path, String outPdfFileName, List<String> fileList) throws Exception
    {
        PDDocument doc = null;
        try
        {
            doc = new PDDocument();
            BufferedImage image;
            PDPage page;
            PDXObjectImage ximage;
            PDPageContentStream contentStream;
            PDDocument docTmp;
            float imgWidth;
            float imgHeight;

            PDFMergerUtility merger = new PDFMergerUtility();
            for (String file : fileList)
            {
                file = Path + file;
                if (file.toLowerCase().endsWith(".pdf"))
                {
                    docTmp = PDDocument.load(file);
                    if (docTmp.getNumberOfPages() == 0)
                    {
                        docTmp.close();
                        continue;
                    }
                    merger.appendDocument(doc, docTmp);
                    docTmp.close();
                    continue;
                }

                image = ImageIO.read(new File(file));
                //TMD...
                if (file.indexOf("USPS") != -1)
                {
                    SimpleDateFormat DateFormatMMddyyyy = new SimpleDateFormat("MM/dd/yyyy");
                    String date = DateFormatMMddyyyy.format(new Date());
                    image = Helper.getImg(image, date, 650, 1036);
                }
                imgWidth = image.getWidth();
                imgHeight = image.getHeight();
                ximage = new PDJpeg(doc, image, 1); //ximage = new PDPixelMap(doc, tmp_image);
                page = new PDPage(new PDRectangle(imgWidth, imgHeight));
                doc.addPage(page);
                contentStream = new PDPageContentStream(doc, page, true, false);
                contentStream.drawImage(ximage, 0, 0);
                //contentStream.drawXObject(ximage, 0, 0, image.getWidth(), image.getHeight());

                contentStream.close();

            }
            doc.save(Path + outPdfFileName);
        }
        finally
        {
            if (doc != null)
            {
                doc.close();
            }
        }
    }

}
