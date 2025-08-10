package com.corona.documentmanager.File;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileManagerFactoryTest {

    @Test
    void getFileManager_selectsMatchingStrategy() {
        FileManager txt = mock(FileManager.class, withSettings().extraInterfaces(SupportsMime.class));
        when(((SupportsMime) txt).supports("text/plain")).thenReturn(true);

        FileManager pdf = mock(FileManager.class, withSettings().extraInterfaces(SupportsMime.class));
        when(((SupportsMime) pdf).supports("text/plain")).thenReturn(false);

        FileFactory factory = new FileFactory(List.of(txt, pdf));
        FileManager result = factory.getFileManager("text/plain");

        assertSame(txt, result);
    }

    @Test
    void getFileManager_unsupported_throws() {
        FileManager any = mock(FileManager.class, withSettings().extraInterfaces(SupportsMime.class));
        when(((SupportsMime) any).supports("audio/mpeg")).thenReturn(false);

        FileFactory factory = new FileFactory(List.of(any));
        assertThrows(IllegalArgumentException.class, () -> factory.getFileManager("audio/mpeg"));
    }
}