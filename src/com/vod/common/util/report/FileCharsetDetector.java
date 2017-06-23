package com.vod.common.util.report;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;

public class FileCharsetDetector
{

    private boolean found = false;

    private String encoding = null;

    public String guestFileEncoding(File file) throws FileNotFoundException, IOException
    {
        return geestFileEncoding(file, new nsDetector());
    }

    public String guestFileEncoding(File file, int languageHint) throws FileNotFoundException, IOException
    {
        return geestFileEncoding(file, new nsDetector(languageHint));
    }

    public String guestFileEncoding(String path) throws FileNotFoundException, IOException
    {
        return guestFileEncoding(new File(path));
    }

    public String guestFileEncoding(String path, int languageHint) throws FileNotFoundException, IOException
    {
        return guestFileEncoding(new File(path), languageHint);
    }

    private String geestFileEncoding(File file, nsDetector det) throws FileNotFoundException, IOException
    {
        // Set an observer...
        // The Notify() will be called when a matching charset is found.
        det.Init(new nsICharsetDetectionObserver()
        {
            public void Notify(String charset)
            {
                found = true;
                encoding = charset;
            }
        });

        BufferedInputStream imp = new BufferedInputStream(new FileInputStream(file));

        byte[] buf = new byte[1024];
        int len;
        boolean done = false;
        boolean isAscii = true;

        while ((len = imp.read(buf, 0, buf.length)) != -1)
        {
            // Check if the stream is only ascii.
            if (isAscii)
                isAscii = det.isAscii(buf, len);

            // DoIt if non-ascii and not done yet.
            if (!isAscii && !done)
                done = det.DoIt(buf, len, false);
        }
        det.DataEnd();

        if (isAscii)
        {
            encoding = "ASCII";
            found = true;
        }

        if (!found)
        {
            String prob[] = det.getProbableCharsets();
            if (prob.length > 0)
            {
                //
                encoding = prob[0];
            }
            else
            {
                imp.close();
                return null;
            }
        }
        imp.close();
        return encoding;
    }
}