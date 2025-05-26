package model;

import java.util.List;

public class Organisateur extends Participant {
    private List<Evenement> evenementsOrganises;

    public Organisateur(String id, String nom, String email, List<Evenement> evenementsOrganises) {
        super(id, nom, email);
        this.evenementsOrganises = evenementsOrganises;
    }


    public List<Evenement> getEvenementsOrganises() {
        return evenementsOrganises;
    }
    public void setEvenementsOrganises(List<Evenement> evenementsOrganises){
        this.evenementsOrganises = evenementsOrganises;
    }
}