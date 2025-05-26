package model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Evenement.class, name = "evenementConcret"),
        @JsonSubTypes.Type(value = Concert.class, name = "concertConcret"),
        @JsonSubTypes.Type(value = Conference.class, name = "conferenceConcret"),
        @JsonSubTypes.Type(value = GestionEvenements.class, name = "GestionEvenements"),
})

/**
 * Classe abstraite représentant un événement générique.
 * Implémente l'interface {@link EvenementObservable} pour permettre la notification des observateurs.
 */
public abstract class Evenement implements EvenementObservable {
    /** L'identifiant unique de l'événement. */
    private String id;
    /** Le nom de l'événement. */
    private String nom;
    /** Le lieu où se déroule l'événement. */
    private String lieu;
    /** La date et l'heure de l'événement. */
    private LocalDateTime date;
    /** La capacité maximale de participants. */
    private int capaciteMax;
    /** La liste des participants inscrits. */
    private List<Participant> participants;
    /** La liste des observateurs recevant les notifications. */
    private List<ParticipantObserver> participantsObserver;
    /** Mapper pour la sérialisation/désérialisation JSON. */
    private ObjectMapper objectMapper;

    /**
     * Constructeur par défaut.
     */
    public Evenement() {
    }

    /**
     * Construit un nouvel événement avec les détails spécifiés.
     *
     * @param id           L'identifiant unique de l'événement.
     * @param nom          Le nom de l'événement.
     * @param lieu         Le lieu de l'événement.
     * @param date         La date et l'heure de l'événement.
     * @param capaciteMax  La capacité maximale de participants.
     */
    public Evenement(String id, String nom, String lieu, LocalDateTime date, int capaciteMax) {
        this.id = id;
        this.nom = nom;
        this.lieu = lieu;
        this.date = date;
        this.capaciteMax = capaciteMax;
        this.participants = new ArrayList<>();
        this.participantsObserver = new ArrayList<>();
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Ajoute un participant à l'événement et notifie les observateurs.
     *
     * @param P Le participant à ajouter.
     * @throws CapaciteMaxAtteinteException Si la capacité maximale est atteinte.
     */
    public void ajouterParticipant(Participant P) throws CapaciteMaxAtteinteException {
        if (participants.size() >= capaciteMax) {
            throw new CapaciteMaxAtteinteException("Capacité maximale atteinte pour l'événement " + nom);
        }
        participants.add(P);
        notifyObservers("Nouveau participant ajouté à " + nom);
    }

    /**
     * Annule l'événement, notifie les observateurs et supprime tous les participants.
     */
    public void annuler() {
        notifyObservers("Événement " + nom + " annulé.");
        participants.clear();
    }

    /**
     * Affiche les détails spécifiques de l'événement.
     * Doit être implémentée par les sous-classes.
     *
     * @return Une chaîne contenant les détails de l'événement.
     */
    public abstract String afficherDetails();

    /**
     * Ajoute un observateur à la liste des observateurs.
     *
     * @param observer L'observateur à ajouter.
     */
    @Override
    public void addObserver(ParticipantObserver observer) {
        participantsObserver.add(observer);
    }

    /**
     * Supprime un observateur de la liste des observateurs.
     *
     * @param observer L'observateur à supprimer.
     */
    @Override
    public void removeObserver(ParticipantObserver observer) {
        participantsObserver.remove(observer);
    }

    /**
     * Notifie tous les observateurs avec un message donné.
     *
     * @param message Le message à envoyer aux observateurs.
     */
    @Override
    public void notifyObservers(String message) {
        for (ParticipantObserver observer : participantsObserver) {
            observer.update(message);
        }
    }

    /**
     * Sauvegarde la liste des participants dans un fichier JSON.
     *
     * @param fichier Le chemin du fichier de destination.
     * @throws IOException En cas d'erreur d'entrée/sortie.
     */
    public void sauvegarderParticipants(String fichier) throws IOException {
        objectMapper.writeValue(new File(fichier), participants);
    }

    /**
     * Charge les participants à partir d'un fichier JSON.
     *
     * @param fichier Le chemin du fichier source.
     * @throws IOException En cas d'erreur d'entrée/sortie.
     */
    public void chargerParticipants(String fichier) throws IOException {
        participants = objectMapper.readValue(new File(fichier), objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Evenement.class));
    }

    /**
     * Obtient l'identifiant de l'événement.
     *
     * @return L'identifiant.
     */
    public String getId() {
        return id;
    }

    /**
     * Définit l'identifiant de l'événement.
     *
     * @param id Le nouvel identifiant.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtient le nom de l'événement.
     *
     * @return Le nom.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom de l'événement.
     *
     * @param nom Le nouveau nom.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obtient le lieu de l'événement.
     *
     * @return Le lieu.
     */
    public String getLieu() {
        return lieu;
    }

    /**
     * Définit le lieu de l'événement.
     *
     * @param lieu Le nouveau lieu.
     */
    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    /**
     * Obtient la date et l'heure de l'événement.
     *
     * @return La date et l'heure.
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Définit la date et l'heure de l'événement.
     *
     * @param date La nouvelle date et heure.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Obtient la capacité maximale de l'événement.
     *
     * @return La capacité maximale.
     */
    public int getCapaciteMax() {
        return capaciteMax;
    }

    /**
     * Définit la capacité maximale de l'événement.
     *
     * @param capaciteMax La nouvelle capacité maximale.
     */
    public void setCapaciteMax(int capaciteMax) {
        this.capaciteMax = capaciteMax;
    }

    /**
     * Obtient la liste des participants.
     *
     * @return La liste des participants.
     */
    public List<Participant> getParticipants() {
        return participants;
    }

    /**
     * Définit la liste des participants.
     *
     * @param participants La nouvelle liste des participants.
     */
    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    /**
     * Obtient la liste des observateurs.
     *
     * @return La liste des observateurs.
     */
    public List<ParticipantObserver> getParticipantsObserver() {
        return participantsObserver;
    }

    /**
     * Définit la liste des observateurs.
     *
     * @param participantsObserver La nouvelle liste des observateurs.
     */
    public void setParticipantsObserver(List<ParticipantObserver> participantsObserver) {
        this.participantsObserver = participantsObserver;
    }
}