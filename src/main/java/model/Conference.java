package model;

import java.time.LocalDateTime;
import java.util.List;

public class Conference extends Evenement {
    private String theme;
    private List<String> intervenants;

    public Conference(String id, String nom, LocalDateTime date, String lieu, int capaciteMax, String theme, List<String> intervenants) {
        super(id, nom, date, lieu, capaciteMax);
        this.theme = theme;
        this.intervenants = intervenants;
    }

    @Override
    public String afficherDetails() {
        return "Conférence: " + getNom() + ", Thème: " + theme + ", Date: " + getDate() + ", Lieu: " + getLieu() +
                ", Intervenants: " + intervenants;
    }

    // Getters et setters
    public String getTheme() { return theme; }
    public List<String> getIntervenants() { return intervenants; }
}