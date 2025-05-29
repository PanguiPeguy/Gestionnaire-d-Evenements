package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Contrôleur pour la gestion des événements et des participants.
 * Sert d'intermédiaire entre les modèles et les opérations de l'application.
 */
public class EventController {
    /** Instance de gestion des événements. */
    private GestionEvenements gestionEvenements;
    /** Service de notification pour l'envoi de messages asynchrones. */
    private NotificationService notificationService;
    private ObjectMapper ObjectMapper;

    /**
     * Construit un nouveau contrôleur avec une instance singleton de {@link GestionEvenements}
     * et un service de notification par défaut (affichage console).
     */
    public EventController() {
        this.gestionEvenements = GestionEvenements.getInstance();
        if (gestionEvenements.getObjectMapper() == null) {
            throw new IllegalStateException("ObjectMapper n'est pas ete initialiser dans GestionEvenements");
        }
        this.notificationService = message -> CompletableFuture.runAsync(() -> System.out.println("Envoi asynchrone: " + message));
    }

    /**
     * Ajoute un nouvel événement (concert ou conférence) à la gestion des événements.
     *
     * @param type        Le type d'événement ("Concert" ou "Conference").
     * @param id          L'identifiant unique de l'événement.
     * @param nom         Le nom de l'événement.
     * @param date        La date et l'heure de l'événement.
     * @param lieu        Le lieu de l'événement.
     * @param capaciteMax La capacité maximale de participants.
     * @param extra1      Information supplémentaire (artiste pour Concert, thème pour Conference).
     * @param extra2      Information supplémentaire (genre musical pour Concert, liste d'intervenants pour Conference).
     * @throws EvenementDejaExistantException Si un événement avec le même ID existe déjà.
     */
    public void ajouterEvenement(String type, String id, String nom, LocalDateTime date, String lieu, int capaciteMax, String extra1, String extra2) throws EvenementDejaExistantException {
        Evenement evenement;
        if (type.equals("Conference")) {
            evenement = new Conference(id, nom, lieu, date, capaciteMax, extra1, Arrays.asList(extra2.split(",")));
        } else {
            evenement = new Concert(id, nom, date, lieu, capaciteMax, extra1, extra2);
        }
        gestionEvenements.ajouterEvenement(evenement);
    }

    /**
     * Ajoute un participant à un événement et envoie une notification.
     *
     * @param evenementId L'ID de l'événement.
     * @param participant Le participant à ajouter.
     * @throws CapaciteMaxAtteinteException Si la capacité maximale de l'événement est atteinte.
     */
    public void ajouterParticipant(String evenementId, Participant participant) throws CapaciteMaxAtteinteException, ParticipantDejeExistantException {
        Evenement evenement = gestionEvenements.getEvenements().get(evenementId);
        if (evenement != null) {
            evenement.ajouterParticipant(participant);
            evenement.addObserver(participant);
            notificationService.envoyerNotification("Participant " + participant.getNom() + " inscrit à " + evenement.getNom());
        }
    }

    /**
     * Supprime un événement par son ID.
     *
     * @param id L'ID de l'événement à supprimer.
     */
    public void supprimerEvenement(String id) {
        gestionEvenements.supprimerEvenement(id);
    }

    /**
     * Obtient la liste de tous les événements.
     *
     * @return Une liste contenant tous les événements.
     */
    public List<Evenement> getAllEvenements() {
        return gestionEvenements.getEvenements().values().stream().collect(Collectors.toList());
    }

    /**
     * Sauvegarde les événements dans un fichier JSON.
     *
     * @param fichier Le chemin du fichier de destination.
     * @throws Exception En cas d'erreur d'entrée/sortie.
     */
    public void sauvegarder(String fichier) throws Exception {
        gestionEvenements.sauvegarderEvenements(fichier);
    }

    /**
     * Sauvegarde les participants de l'événement courant dans un fichier JSON.
     * <p>
     * <strong>Attention</strong>: La variable {@code evenement} est actuellement inutilisée et peut être null.
     *
     * @param fichier Le chemin du fichier de destination.
     * @throws Exception En cas d'erreur d'entrée/sortie ou si l'événement est null.
     */


    /**
     * Charge les événements à partir d'un fichier JSON.
     *
     * @param fichier Le chemin du fichier source.
     * @throws Exception En cas d'erreur d'entrée/sortie.
     */
    public void charger(String fichier) throws Exception {
        gestionEvenements.chargerEvenements(fichier);
    }

    public ObjectMapper getObjectMapper() {
        return gestionEvenements.getObjectMapper();
    }
}