package com.corona.documentmanager.File;

import java.util.Collection;
import java.util.List;

public interface SupportsMime {
    boolean supports(String mime);

    default Collection<String> supportedMimes() {
        return List.of();
    }
}