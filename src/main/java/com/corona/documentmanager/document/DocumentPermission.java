package com.corona.documentmanager.document;

import com.corona.documentmanager.user.LoggedUser;
import org.springframework.stereotype.Component;

import java.util.Set;

// Java
@Component
public class DocumentPermission {
    public boolean canRead(Document d, LoggedUser u) {
        return isOwner(d, u) || hasShare(d, u, Set.of("READ","WRITE"));
    }
    public boolean canWrite(Document d, LoggedUser u) {
        return isOwner(d, u) || hasShare(d, u, Set.of("WRITE"));
    }
    private boolean isOwner(Document d, LoggedUser u) {
        return d.getCreatedBy() != null && d.getCreatedBy().getId().equals(u.getUserId());
    }
    private boolean hasShare(Document d, LoggedUser u, Set<String> allowed) {
        return d.getShares().stream()
                .anyMatch(s -> s.getSharedWithUser().getId().equals(u.getUserId())
                        && allowed.contains(s.getPermission().name()));
    }
}