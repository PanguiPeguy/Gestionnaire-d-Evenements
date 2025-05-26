package model;

import java.time.LocalDateTime;

/**
 * Représente un concert, un type spécifique d'événement avec un artiste et un genre musical.
 * Hérite de la classe abstraite {@link Evenement}.
 */
public class Concert extends Evenement {
    /** L'artiste ou le groupe performant lors du concert. */
    private String artiste;
    /** Le genre musical du concert (ex. : rock, jazz, pop). */
    private String genreMusical;

    /**
     * Constructeur par défaut.
     */
    public Concert() {
    }

    /**
     * Construit un nouveau concert avec les détails spécifiés.
     *
     * @param id            L'identifiant unique du concert.
     * @param nom           Le nom du concert.
     * @param date          La date et l'heure du concert.
     * @param lieu          Le lieu où le concert se déroule.
     * @param capaciteMax   La capacité maximale de participants.
     * @param artiste       L'artiste ou le groupe du concert.
     * @param genreMusical  Le genre musical du concert.
     */
    public Concert(String id, String nom, LocalDateTime date, String lieu, int capaciteMax, String artiste, String genreMusical) {
        super(id, nom, lieu, date, capaciteMax);
        this.artiste = artiste;
        this.genreMusical = genreMusical;
    }

    /**
     * Affiche les détails du concert, incluant le nom, l'artiste, le genre musical, la date et le lieu.
     *
     * @return Une chaîne de caractères contenant les détails du concert.
     */
    @Override
    public String afficherDetails() {
        return "Concert: " + getNom() + ", Artiste: " + artiste + ", Genre: " + genreMusical + ", Date: " + getDate() + ", Lieu: " + getLieu();
    }

    /**
     * Obtient le nom de l'artiste ou du groupe.
     *
     * @return Le nom de l'artiste.
     */
    public String getArtiste() {
        return artiste;
    }

    /**
     * Définit le nom de l'artiste ou du groupe.
     *
     * @param artiste Le nouveau nom de l'artiste.
     */
    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }

    /**
     * Obtient le genre musical du concert.
     *
     * @return Le genre musical.
     */
    public String getGenreMusical() {
        return genreMusical;
    }

    /**
     * Définit le genre musical du concert.
     *
     * @param genreMusical Le nouveau genre musical.
     */
    public void setGenreMusical(String genreMusical) {
        this.genreMusical = genreMusical;
    }
}