package controller;

import model.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class EventController {
    private GestionEvenements gestionEvenements;
    private NotificationService notificationService;

    public EventController() {
        this.gestionEvenements = GestionEvenements.getInstance();
        this.notificationService = message -> CompletableFuture.runAsync(() -> System.out.println("Envoi asynchrone: " + message));
    }

    public void ajouterEvenement(String type, String id, String nom, LocalDateTime date, String lieu, int capaciteMax, String extra1, String extra2) throws EvenementDejaExistantException {
        Evenement evenement;
        if (type.equals("Conference")) {
            evenement = new Conference(id, nom, lieu, date, capaciteMax, extra1, Arrays.asList(extra2.split(",")));
        } else {
            evenement = new Concert(id, nom, date, lieu, capaciteMax, extra1, extra2);
        }
        gestionEvenements.ajouterEvenement(evenement);
    }

    public void ajouterParticipant(String evenementId, Participant participant) throws CapaciteMaxAtteinteException {
        Evenement evenement = gestionEvenements.getEvenements().get(evenementId);
        if (evenement != null) {
            evenement.ajouterParticipant(participant);
            evenement.addObserver(participant);
            notificationService.envoyerNotification("Participant " + participant.getNom() + " inscrit à " + evenement.getNom());
        }
    }

    public void supprimerEvenement(String id) {
        gestionEvenements.supprimerEvenement(id);
    }

    public List<Evenement> getAllEvenements() {
        return gestionEvenements.getEvenements().values().stream().collect(Collectors.toList());
    }

    public void sauvegarder(String fichier) throws Exception {
        gestionEvenements.sauvegarderEvenements(fichier);
    }

    public void charger(String fichier) throws Exception {
        gestionEvenements.chargerEvenements(fichier);
    }
}