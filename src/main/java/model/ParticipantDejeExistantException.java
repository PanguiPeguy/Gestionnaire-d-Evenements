package model;

/**
 * Exception levée lorsqu'un participant avec un identifiant existant est ajouté.
 */
public class ParticipantDejeExistantException extends Exception{
    /**
     * Construit une nouvelle exception avec le message spécifié.
     *
     * @param message Le message décrivant la cause de l'exception.
     */
    public ParticipantDejeExistantException (String message){
        super(message);
    }
}
