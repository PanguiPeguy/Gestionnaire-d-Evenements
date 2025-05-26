package model;

import java.util.concurrent.CompletableFuture;

/**
 * Interface définissant un service de notification asynchrone.
 */
public interface NotificationService {
    /**
     * Envoie une notification de manière asynchrone.
     *
     * @param message Le message à envoyer dans la notification.
     * @return Une {@link CompletableFuture} représentant l'achèvement de l'envoi.
     */
    CompletableFuture<Void> envoyerNotification(String message);
}