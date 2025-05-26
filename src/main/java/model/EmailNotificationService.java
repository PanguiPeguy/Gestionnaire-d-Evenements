package model;

import java.util.concurrent.CompletableFuture;

/**
 * Implémentation du service de notification qui envoie des emails de manière asynchrone.
 * Implémente l'interface {@link NotificationService}.
 */
public class EmailNotificationService implements NotificationService {
    /**
     * Envoie une notification par email de manière asynchrone.
     * Affiche actuellement le message dans la console à des fins de démonstration.
     *
     * @param message Le message à envoyer dans la notification.
     * @return Une {@link CompletableFuture} représentant l'achèvement de l'envoi.
     */
    @Override
    public CompletableFuture<Void> envoyerNotification(String message) {
        return CompletableFuture.runAsync(() -> {
            // Logique d'envoi d'email
            System.out.println("Email envoyé: " + message);
        });
    }
}