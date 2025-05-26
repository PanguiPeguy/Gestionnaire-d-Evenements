package model;

import java.util.List;

/**
 * Représente un organisateur d'événements, héritant de {@link Participant}.
 * Un organisateur gère une liste d'événements organisés.
 */
public class Organisateur extends Participant {
    /** Liste des événements organisés par cet organisateur. */
    private List<Evenement> evenementsOrganises;

    /**
     * Construit un nouvel organisateur avec les détails spécifiés.
     *
     * @param id                   L'identifiant unique de l'organisateur.
     * @param nom                  Le nom de l'organisateur.
     * @param email                L'adresse email de l'organisateur.
     * @param evenementsOrganises  La liste des événements organisés.
     */
    public Organisateur(String id, String nom, String email, List<Evenement> evenementsOrganises) {
        super(id, nom, email);
        this.evenementsOrganises = evenementsOrganises;
    }

    /**
     * Obtient la liste des événements organisés.
     *
     * @return La liste des événements organisés.
     */
    public List<Evenement> getEvenementsOrganises() {
        return evenementsOrganises;
    }

    /**
     * Définit la liste des événements organisés.
     *
     * @param evenementsOrganises La nouvelle liste des événements organisés.
     */
    public void setEvenementsOrganises(List<Evenement> evenementsOrganises) {
        this.evenementsOrganises = evenementsOrganises;
    }
}