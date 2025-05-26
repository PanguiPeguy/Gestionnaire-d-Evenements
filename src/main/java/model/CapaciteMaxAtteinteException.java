package model;

/**
 * Exception levée lorsque la capacité maximale d'un événement est atteinte.
 */
public class CapaciteMaxAtteinteException extends Exception {
    /**
     * Construit une nouvelle exception avec le message spécifié.
     *
     * @param message Le message décrivant la cause de l'exception.
     */
    public CapaciteMaxAtteinteException(String message) {
        super(message);
    }
}