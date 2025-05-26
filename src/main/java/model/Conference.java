package model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Représente une conférence, un type spécifique d'événement avec un thème et des intervenants.
 * Hérite de la classe abstraite {@link Evenement}.
 */
public class Conference extends Evenement {
    /** Le thème principal de la conférence. */
    private String theme;
    /** La liste des intervenants participant à la conférence. */
    private List<String> intervenant;

    /**
     * Constructeur par défaut.
     */
    public Conference() {
    }

    /**
     * Construit une nouvelle conférence avec les détails spécifiés.
     *
     * @param id             L'identifiant unique de la conférence.
     * @param nom            Le nom de la conférence.
     * @param lieu              Le lieu où se tient la conférence.
     * @param date           La date et l'heure de la conférence.
     * @param capaciteMax    La capacité maximale de participants.
     * @param theme            Le thème de la conférence.
     * @param intervenant      La liste des intervenants.
     */
    public Conference(String id, String nom, String lieu, LocalDateTime date, int capaciteMax, String theme, List<String> intervenant) {
        super(id, nom, lieu, date, capaciteMax);
        this.theme = theme;
        this.intervenant = intervenant;
    }

    public Conference(String id, String nom, String lieu, LocalDateTime date, int capaciteMax, String theme) {
        super(id, nom, lieu, date, capaciteMax);
        this.theme = theme;
    }

    /**
     * Affiche les détails de la conférence, incluant le nom, le thème, la date, le lieu et les intervenants.
     *
     * @return Une chaîne de caractères contenant les détails de la conférence.
     */
    @Override
    public String afficherDetails() {
        return "Conférence: " + getNom() + ", Thème: " + theme + ", Date: " + getDate() + ", Lieu: " + getLieu() +
                ", Intervenants: " + intervenant;
    }

    /**
     * Obtient le thème de la conférence.
     *
     * @return Le thème de la conférence.
     */
    public String getTheme() {
        return theme;
    }

    /**
     * Définit le thème de la conférence.
     *
     * @param theme Le nouveau thème de la conférence.
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * Obtient la liste des intervenants de la conférence.
     *
     * @return La liste des intervenants.
     */
    public List<String> getIntervenant() {
        return intervenant;
    }

    /**
     * Définit la liste des intervenants de la conférence.
     *
     * @param intervenant La nouvelle liste des intervenants.
     */
    public void setIntervenant(List<String> intervenant) {
        this.intervenant = intervenant;
    }
}