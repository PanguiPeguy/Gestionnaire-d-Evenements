package model;

public class Participant implements ParticipantObserver {
    private String id;
    private String nom;
    private String email;

    public Participant(String id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    @Override
    public void update(String message) {
        System.out.println("Notification pour " + nom + " (" + email + "): " + message);
    }

    // Getters et setters
    public String getId() { return id; }
    public String getNom() { return nom; }
    public String getEmail() { return email; }
}