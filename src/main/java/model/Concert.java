package model;

import java.time.LocalDateTime;

public class Concert extends Evenement {
    private String artiste;
    private String genreMusical;

    public Concert(String id, String nom, LocalDateTime date, String lieu, int capaciteMax, String artiste, String genreMusical) {
        super(id, nom, lieu, date, capaciteMax);
        this.artiste = artiste;
        this.genreMusical = genreMusical;
    }

    @Override
    public String afficherDetails() {
        return "Concert: " + getNom() + ", Artiste: " + artiste + ", Genre: " + genreMusical + ", Date: " + getDate() + ", Lieu: " + getLieu();
    }

    // Getters et setters
    public String getArtiste() {
        return artiste;
    }
    public void setArtiste(String artiste){
        this.artiste = artiste;
    }

    public String getGenreMusical() {
        return genreMusical;
    }
    public void setGenreMusical(String genreMusical){
        this.genreMusical = genreMusical;
    }
}