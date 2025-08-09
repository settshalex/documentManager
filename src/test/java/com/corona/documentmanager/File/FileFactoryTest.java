package com.corona.documentmanager.File;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileFactoryTest {

    @Test
    void getFileManager_selectsMatchingStrategy() {
        File txt = mock(File.class, withSettings().extraInterfaces(SupportsMime.class));
        when(((SupportsMime) txt).supports("text/plain")).thenReturn(true);

        File pdf = mock(File.class, withSettings().extraInterfaces(SupportsMime.class));
        when(((SupportsMime) pdf).supports("text/plain")).thenReturn(false);

        FileFactory factory = new FileFactory(List.of(txt, pdf));
        File result = factory.getFileManager("text/plain");

        assertSame(txt, result);
    }

    @Test
    void getFileManager_unsupported_throws() {
        File any = mock(File.class, withSettings().extraInterfaces(SupportsMime.class));
        when(((SupportsMime) any).supports("audio/mpeg")).thenReturn(false);

        FileFactory factory = new FileFactory(List.of(any));
        assertThrows(IllegalArgumentException.class, () -> factory.getFileManager("audio/mpeg"));
    }
}