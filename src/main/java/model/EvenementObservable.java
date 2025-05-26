package model;

/**
 * Interface pour les objets pouvant être observés par des observateurs de type {@link ParticipantObserver}.
 * Implémente le patron de conception Observer.
 */
public interface EvenementObservable {
    /**
     * Ajoute un observateur à la liste des observateurs.
     *
     * @param observer L'observateur à ajouter.
     */
    void addObserver(ParticipantObserver observer);

    /**
     * Supprime un observateur de la liste des observateurs.
     *
     * @param observer L'observateur à supprimer.
     */
    void removeObserver(ParticipantObserver observer);

    /**
     * Notifie tous les observateurs avec un message donné.
     *
     * @param message Le message à envoyer aux observateurs.
     */
    void notifyObservers(String message);
}