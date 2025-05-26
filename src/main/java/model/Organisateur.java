package model;

import java.util.ArrayList;
import java.util.List;

public class Organisateur extends Participant {
    private List<Evenement> evenementsOrganises;

    public Organisateur(String id, String nom, String email) {
        super(id, nom, email);
        this.evenementsOrganises = new ArrayList<>();
    }

    public void ajouterEvenementOrganise(Evenement e) {
        evenementsOrganises.add(e);
    }

    // Getters
    public List<Evenement> getEvenementsOrganises() { return evenementsOrganises; }
}