package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

//Classe java pour gerer les evenements
public class GestionEvenements {
    //instance static de la classe GestionEvenement
    private static GestionEvenements instance;
    private Map<String, Evenement> evenements;
    //
    private ObjectMapper objectMapper;


    //constructeur privee de la classe GestionEvenement pour eviter qu'une classe exterieur ne l'instance
    private GestionEvenements() {
        this.evenements = new HashMap<>();
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    //Methode static qui va gerer l'intatiation unique de la classe
    public static GestionEvenements getInstance(){
        if (instance == null){
            instance = new GestionEvenements();
        }
        return instance;
    }



    //Methode pour ajouter un Evenement
    public void ajouterEvenement(Evenement Ev) throws EvenementDejaExistantException{
        if (evenements.containsKey(Ev.getId())){
            throw new EvenementDejaExistantException("L'événement avec l'ID " + Ev.getId() + " existe déjà.");
        }
        evenements.put(Ev.getId(), Ev);
        System.out.println(evenements);
    }

    //Methode pour supprimer un Evenement
    public void supprimerEvenement(String id) {
        Evenement E = evenements.remove(id);
        if (E != null) {
            E.annuler();
        }
    }

    //Methode pour rechercher un Evenement
    public Evenement rechercherEvenement(String nom) {
        return evenements.values().stream() // recupre tous les objets Evenement(pas les cles)
                .filter(e -> e.getNom().equalsIgnoreCase(nom)) // filtre la liste des evenements pour garder uniquement ceux dont le nom correspondent a <nom>
                .findFirst() //retourne le premier evenement
                .orElse(null); //retourne null si aucun evenement ne correspond
    }

    //Methode pour sauvegarder un Evenement dans un fichier JSON
    public void sauvegarderEvenements(String fichier) throws IOException {
        objectMapper.writeValue(new File(fichier), evenements);
    }

    //Methode pour charger un Evenement
    public void chargerEvenements(String fichier) throws IOException {
        evenements = objectMapper.readValue(new File(fichier), objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Evenement.class));
    }

    //Getter et Setter
    public static void setInstance(GestionEvenements instance) {
        GestionEvenements.instance = instance;
    }

    public Map<String, Evenement> getEvenements() {
        return evenements;
    }

    public void setEvenements(Map<String, Evenement> evenements) {
        this.evenements = evenements;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}