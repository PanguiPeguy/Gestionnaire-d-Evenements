package model;

import java.time.LocalDateTime;

public class Concert extends Evenement {
    private String artiste;
    private String genreMusical;

    public Concert(String id, String nom, LocalDateTime date, String lieu, int capaciteMax, String artiste, String genreMusical) {
        super(id, nom, date, lieu, capaciteMax);
        this.artiste = artiste;
        this.genreMusical = genreMusical;
    }

    @Override
    public String afficherDetails() {
        return "Concert: " + getNom() + ", Artiste: " + artiste + ", Genre: " + genreMusical + ", Date: " + getDate() + ", Lieu: " + getLieu();
    }

    // Getters et setters
    public String getArtiste() { return artiste; }
    public String getGenreMusical() { return genreMusical; }
}