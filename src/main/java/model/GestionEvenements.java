package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GestionEvenements {
    private static GestionEvenements instance;
    private Map<String, Evenement> evenements;
    private ObjectMapper objectMapper;

    private GestionEvenements() {
        evenements = new HashMap<>();
        objectMapper = new ObjectMapper();
    }

    public static GestionEvenements getInstance() {
        if (instance == null) {
            instance = new GestionEvenements();
        }
        return instance;
    }

    public void ajouterEvenement(Evenement e) throws EvenementDejaExistantException {
        if (evenements.containsKey(e.getId())) {
            throw new EvenementDejaExistantException("L'événement avec l'ID " + e.getId() + " existe déjà.");
        }
        evenements.put(e.getId(), e);
    }

    public void supprimerEvenement(String id) {
        Evenement e = evenements.remove(id);
        if (e != null) {
            e.annuler();
        }
    }

    public Evenement rechercherEvenement(String nom) {
        return evenements.values().stream()
                .filter(e -> e.getNom().equalsIgnoreCase(nom))
                .findFirst()
                .orElse(null);
    }

    public void sauvegarderEvenements(String fichier) throws IOException {
        objectMapper.writeValue(new File(fichier), evenements);
    }

    public void chargerEvenements(String fichier) throws IOException {
        evenements = objectMapper.readValue(new File(fichier), objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Evenement.class));
    }

    public Map<String, Evenement> getEvenements() { return evenements; }
}