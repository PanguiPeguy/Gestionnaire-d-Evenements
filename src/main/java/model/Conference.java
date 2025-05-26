package model;

import java.time.LocalDateTime;
import java.util.List;

public class Conference extends Evenement{
    private String theme;
    private List<String> intervenant;

    public Conference(String id, String nom, String lieu, LocalDateTime date, int capaciteMax, String theme, List<String> intervenant) {
        super(id, nom, lieu, date, capaciteMax);
        this.theme = theme;
        this.intervenant = intervenant;
    }

    @Override
    public String afficherDetails() {
        return "Conférence: " + getNom() + ", Thème: " + theme + ", Date: " + getDate() + ", Lieu: " + getLieu() +
                ", Intervenants: " + intervenant;
    }

    public String getTheme(){
        return theme;
    }
    public void setTheme(String theme){
        this.theme = theme;
    }

    public List<String> getIntervenant(){
        return intervenant;
    }
    public void setIntervenant(List<String> Intervenant){
        this.intervenant = Intervenant;
    }
}