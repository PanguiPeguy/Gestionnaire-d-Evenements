package model;

/**
 * Interface pour les observateurs recevant des notifications d'événements.
 * Utilisée dans le cadre du patron de conception Observer.
 */
public interface ParticipantObserver {
    /**
     * Met à jour l'observateur avec un message de notification.
     *
     * @param message Le message de notification reçu.
     */
    void update(String message);
}