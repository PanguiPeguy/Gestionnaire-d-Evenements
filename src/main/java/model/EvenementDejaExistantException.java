package model;

/**
 * Exception levée lorsqu'un événement avec un identifiant existant est ajouté.
 */
public class EvenementDejaExistantException extends Exception {
    /**
     * Construit une nouvelle exception avec le message spécifié.
     *
     * @param message Le message décrivant la cause de l'exception.
     */
    public EvenementDejaExistantException(String message) {
        super(message);
    }
}