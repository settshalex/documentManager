package com.corona.documentmanager.File;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.documentType.DocumentType;
import com.corona.documentmanager.user.LoggedUser;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;

public class AudioDocument extends CommonFile implements File{
    @Override
    public Document createNewDocument(MultipartFile file, LoggedUser customUser,
                                      String title, String description, String mime_type, Optional<DocumentType> docType) throws IOException {

        String audioMetadata = extractAudioMetadata(file);
        String enhancedDescription = description + "\n\nMetadati Audio:\n" + audioMetadata;

        return super.prepareNewDocument(file, customUser, title, enhancedDescription, mime_type, docType);
    }

    private String extractAudioMetadata(MultipartFile file) throws IOException {
        StringBuilder metadata = new StringBuilder();
        try {
            AudioFile audioFile = AudioFileIO.read(file.getResource().getFile());
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

    @Override
    public boolean isDocument() {
        return false;
    }

    @Override
    public String language() {
        return null;
    }

}
