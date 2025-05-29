package model;


/**
 * Représente un participant à un événement, capable de recevoir des notifications.
 * Implémente l'interface {@link ParticipantObserver}.
 */
public class Participant implements ParticipantObserver {
    /** L'identifiant unique du participant. */
    private String id;
    /** Le nom du participant. */
    private String nom;
    /** L'adresse email du participant. */
    private String email;

    public Participant(){

    }

    /**
     * Construit un nouveau participant avec les détails spécifiés.
     *
     * @param id    L'identifiant unique du participant.
     * @param nom   Le nom du participant.
     * @param email L'adresse email du participant.
     */
    public Participant(String id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    /**
     * Reçoit une notification et affiche un message à l'utilisateur.
     *
     * @param message Le message de notification.
     */
    @Override
    public void update(String message) {
        System.out.println("Notification envoyer a " + nom + " (" + email + "): " + message);
    }

    /**
     * Obtient l'identifiant du participant.
     *
     * @return L'identifiant du participant.
     */
    public String getId() {
        return id;
    }

    /**
     * Définit l'identifiant du participant.
     *
     * @param id Le nouvel identifiant.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtient le nom du participant.
     *
     * @return Le nom du participant.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom du participant.
     *
     * @param nom Le nouveau nom.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obtient l'adresse email du participant.
     *
     * @return L'adresse email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'adresse email du participant.
     *
     * @param email La nouvelle adresse email.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}