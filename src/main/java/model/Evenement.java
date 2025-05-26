package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Evenement implements EvenementObservable {
    private String id;
    private String nom;
    private LocalDateTime date;
    private String lieu;
    private int capaciteMax;
    private List<Participant> participants;
    private List<ParticipantObserver> observers;

    public Evenement(String id, String nom, LocalDateTime date, String lieu, int capaciteMax) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.capaciteMax = capaciteMax;
        this.participants = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public void ajouterParticipant(Participant p) throws CapaciteMaxAtteinteException {
        if (participants.size() >= capaciteMax) {
            throw new CapaciteMaxAtteinteException("Capacité maximale atteinte pour l'événement " + nom);
        }
        participants.add(p);
        notifyObservers("Nouveau participant ajouté à " + nom);
    }

    public void annuler() {
        notifyObservers("Événement " + nom + " annulé.");
        participants.clear();
    }

    public abstract String afficherDetails();

    @Override
    public void addObserver(ParticipantObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(ParticipantObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (ParticipantObserver observer : observers) {
            observer.update(message);
        }
    }

    // Getters et setters
    public String getId() { return id; }
    public String getNom() { return nom; }
    public LocalDateTime getDate() { return date; }
    public String getLieu() { return lieu; }
    public int getCapaciteMax() { return capaciteMax; }
    public List<Participant> getParticipants() { return participants; }
}