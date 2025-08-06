package com.corona.documentmanager.File;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.documentType.DocumentType;
import com.corona.documentmanager.user.LoggedUser;
import com.drew.imaging.ImageMetadataReader;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

public class ImageDocument extends CommonFile implements File{
    /**
     * @param file
     * @return
     */
    @Override
    public Document createNewDocument(MultipartFile file, LoggedUser customUser, String title, String description, String mime_type, Optional<DocumentType> docType) throws IOException {
        String imageMetadata = extractImageMetadata(file);
        String enhancedDescription = description + "\n\nMetadati Immagine:\n" + imageMetadata;

        return super.prepareNewDocument(file, customUser, title, enhancedDescription, mime_type, docType);
    }

    private String extractImageMetadata(MultipartFile file) throws IOException {
        StringBuilder metadata = new StringBuilder();
        try {
            Metadata meta = ImageMetadataReader.readMetadata(file.getInputStream());

            // GPS metadata
            GpsDirectory gpsDirectory = meta.getFirstDirectoryOfType(GpsDirectory.class);
            if (gpsDirectory != null) {
                GeoLocation geoLocation = gpsDirectory.getGeoLocation();
                if (geoLocation != null) {
                    metadata.append("Posizione: ").append(geoLocation.getLatitude())
                            .append(", ").append(geoLocation.getLongitude()).append("\n");
                }
            }

            // EXIF metadata
            ExifSubIFDDirectory exifDirectory = meta.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            if (exifDirectory != null) {
                // Data scatto
                Date date = exifDirectory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                if (date != null) {
                    metadata.append("Data scatto: ").append(date).append("\n");
                }

                // Fotocamera e impostazioni
                metadata.append("Marca: ").append(exifDirectory.getString(ExifSubIFDDirectory.TAG_MAKE)).append("\n");
                metadata.append("Modello: ").append(exifDirectory.getString(ExifSubIFDDirectory.TAG_MODEL)).append("\n");
                metadata.append("ISO: ").append(exifDirectory.getString(ExifSubIFDDirectory.TAG_ISO_EQUIVALENT)).append("\n");
                metadata.append("Apertura: f/").append(exifDirectory.getString(ExifSubIFDDirectory.TAG_FNUMBER)).append("\n");
                metadata.append("Tempo di esposizione: ").append(exifDirectory.getString(ExifSubIFDDirectory.TAG_EXPOSURE_TIME)).append("\n");
            }

            // Dimensioni immagine
            JpegDirectory jpegDirectory = meta.getFirstDirectoryOfType(JpegDirectory.class);
            if (jpegDirectory != null) {
                metadata.append("Dimensioni: ").append(jpegDirectory.getString(JpegDirectory.TAG_IMAGE_WIDTH))
                        .append("x").append(jpegDirectory.getString(JpegDirectory.TAG_IMAGE_HEIGHT)).append("\n");
            }
        } catch (Exception e) {
            metadata.append("Errore nell'estrazione dei metadati immagine: ").append(e.getMessage());
        }
        return metadata.toString();
    }


    /**
     * @return
     */
    @Override
    public boolean isDocument() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public String language() {
        return null;
    }
}
