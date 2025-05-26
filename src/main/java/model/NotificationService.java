package model;

import java.util.concurrent.CompletableFuture;

public interface NotificationService {
    CompletableFuture<Void> envoyerNotification(String message);
}