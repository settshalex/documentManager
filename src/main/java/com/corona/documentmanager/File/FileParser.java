package com.corona.documentmanager.File;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

public class FileParser {

    public static String parse(InputStream stream) throws IOException,SAXException, TikaException {

        //detecting the file type
        Tika tika = new Tika();
        return tika.detect(stream);
    }

    public static Boolean isOpenDocument(String mimeType){
        return Objects.equals(mimeType, "application/vnd.oasis.opendocument.text");
    }
    public static Boolean isPdfFile(String mimeType){
        return Objects.equals(mimeType, "application/pdf");
    }
}
