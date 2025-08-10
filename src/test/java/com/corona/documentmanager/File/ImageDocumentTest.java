package com.corona.documentmanager.File;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.documentType.DocumentType;
import com.corona.documentmanager.user.LoggedUser;
import com.corona.documentmanager.user.User;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ImageDocumentTest {

    ImageDocument imageDocument;
    private LoggedUser principal() {
        User u = User.builder()
                .id(1L)
                .username("testuser")
                .password("pwd")
                .role("USER")
                .build();
        return new LoggedUser(u);
    }
    @BeforeEach
    void setUp() {
        imageDocument = new ImageDocument();
    }
    @Test
    void createNewDocument() throws IOException, ImageWriteException {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        byte[] imageBytes = baos.toByteArray();

        TiffOutputSet outputSet = new TiffOutputSet();
        TiffOutputDirectory exifDir = outputSet.getOrCreateExifDirectory();

        exifDir.add(ExifTagConstants.EXIF_TAG_USER_COMMENT, "Canon");
        exifDir.add(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL, "2023:08:08 12:34:56");

        ByteArrayOutputStream withExifStream = new ByteArrayOutputStream();
        try (InputStream is = new ByteArrayInputStream(imageBytes)) {
            new ExifRewriter().updateExifMetadataLossless(is, withExifStream, outputSet);
        } catch (ImageReadException e) {
            throw new RuntimeException(e);
        }

        byte[] jpegWithExif = withExifStream.toByteArray();

        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "test-image.jpg",
                "image/jpeg",
                jpegWithExif
        );
        DocumentType docType = new DocumentType();
        ImageDocument imageDocumentNew = new ImageDocument();
        Document doc = imageDocumentNew.createNewDocument(multipartFile, principal(), "title", "description", "image/jpeg", Optional.of(docType));
        String a = doc.getDescription();
        System.out.println(a);
        assertNotNull(imageDocumentNew);
        assertEquals(ImageDocument.class, imageDocumentNew.getClass());
        assertNull(imageDocumentNew.language());
    }

    @Test
    void getDocumentType() {

    }
    @Test
    void isDocument() {
        assertFalse(imageDocument.isDocument());
    }

    @Test
    void language() {
        assertNull(imageDocument.language());
    }
}