package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Evenement implements EvenementObservable{
    private String id;
    private String nom;
    private String lieu;
    private LocalDateTime date;
    private int capaciteMax;
    private List<Participant> participants;
    private List<ParticipantObserver> participantsObserver;

    public Evenement (){

    }

    public Evenement (String id, String nom, String lieu, LocalDateTime date, int capaciteMax){
        this.id = id;
        this.nom = nom;
        this.lieu = lieu;
        this.date = date;
        this.capaciteMax = capaciteMax;
        this.participants = new ArrayList<>();
        this.participantsObserver = new ArrayList<>();
    }

    public void ajouterParticipant(Participant P) throws CapaciteMaxAtteinteException{
        if (participants.size() >= capaciteMax){
            throw new CapaciteMaxAtteinteException("Capacité maximale atteinte pour l'événement " + nom);
        }
        participants.add(P);
        notifyObservers("Nouveau participant ajouté à " + nom);
    }

    public void annuler(){
        notifyObservers("Événement " + nom + " annulé.");
        participants.clear();
    }

    public abstract String afficherDetails();

    @Override
    public void addObserver(ParticipantObserver observer) {
        participantsObserver.add(observer);
    }

    @Override
    public void removeObserver(ParticipantObserver observer) {
        participantsObserver.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (ParticipantObserver observer : participantsObserver) {
            observer.update(message);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getCapaciteMax() {
        return capaciteMax;
    }

    public void setCapaciteMax(int capaciteMax) {
        this.capaciteMax = capaciteMax;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public List<ParticipantObserver> getParticipantsObserver() {
        return participantsObserver;
    }

    public void setParticipantsObserver(List<ParticipantObserver> participantsObserver) {
        this.participantsObserver = participantsObserver;
    }
}