package com.corona.documentmanager.File;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.documentType.DocumentType;
import com.corona.documentmanager.user.LoggedUser;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

@Component
public class AudioDocument extends CommonFile implements FileManager, SupportsMime{

    private static final Map<String, String> MIME_TO_EXT = Map.of(
            "audio/wav", "wav",
            "audio/mpeg", "mp3",
            "audio/x-wav", "wav",
            "audio/ogg", "ogg"
    );


    @Override
    public Document createNewDocument(MultipartFile file, LoggedUser customUser,
                                      String title, String description, String mime_type, Optional<DocumentType> docType) throws IOException {

        File tempFile = File.createTempFile(file.getName(), "." + getExtension(file));
        file.transferTo(tempFile);
        System.out.println(tempFile.getAbsolutePath());
        String audioMetadata = extractAudioMetadata(tempFile);
        String enhancedDescription = description + "\n\nMetadati Audio:\n" + audioMetadata;

        return super.prepareNewDocument(file, customUser, title, enhancedDescription, mime_type, docType);
    }

    private String extractAudioMetadata(File fileAudio) {
        StringBuilder metadata = new StringBuilder();
        try {
            AudioFile audioFile = AudioFileIO.read(fileAudio);
            AudioHeader header = audioFile.getAudioHeader();
            Tag tag = audioFile.getTag();

            metadata.append("Durata: ").append(header.getTrackLength()).append(" secondi\n");
            metadata.append("Bitrate: ").append(header.getBitRate()).append(" kbps\n");
            metadata.append("Sample Rate: ").append(header.getSampleRate()).append(" Hz\n");

            if (tag != null) {
                metadata.append("Artista: ").append(tag.getFirst(FieldKey.ARTIST)).append("\n");
                metadata.append("Album: ").append(tag.getFirst(FieldKey.ALBUM)).append("\n");
                metadata.append("Anno: ").append(tag.getFirst(FieldKey.YEAR)).append("\n");
                metadata.append("Genere: ").append(tag.getFirst(FieldKey.GENRE)).append("\n");
            }
        } catch (Exception e) {
            metadata.append("Errore nell'estrazione dei metadati audio: ").append(e.getMessage());
        }
        return metadata.toString();
    }

    public static String getExtension(MultipartFile file) {
        if (file == null) return "";

        String original = file.getOriginalFilename();
        if (original != null) {
            String filename = Paths.get(original).getFileName().toString();
            String ext = StringUtils.getFilenameExtension(filename);
            if (ext != null && !ext.isBlank()) {
                return ext.toLowerCase();
            }
        }

        String contentType = file.getContentType();
        if (contentType != null) {
            String mapped = MIME_TO_EXT.get(contentType.toLowerCase());
            if (mapped != null) {
                return mapped;
            }
        }

        return "";
    }


    @Override
    public boolean isDocument() {
        return false;
    }

    @Override
    public String language() {
        return null;
    }

    @Override
    public boolean supports(String mime) {
        return mime != null && mime.startsWith("audio/");
    }
}
