package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe singleton pour la gestion centralisée des événements.
 * Fournit des méthodes pour ajouter, supprimer, rechercher, sauvegarder et charger des événements.
 */
public class GestionEvenements {
    /** Instance unique de la classe (singleton). */
    private static GestionEvenements instance;
    /** Map stockant les événements avec leur ID comme clé. */
    private Map<String, Evenement> evenements;
    /** Mapper pour la sérialisation/désérialisation JSON. */
    private ObjectMapper objectMapper;
    /** Chemin du fichier de sauvegarde par défaut */
    private String fichierSauvegarde;

    /**
     * Constructeur privé pour empêcher l'instanciation externe.
     * Initialise la map des événements et le mapper JSON.
     */
    private GestionEvenements() {
        this.evenements = new HashMap<>();
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Configuration pour gérer les types polymorphes
        objectMapper.activateDefaultTyping(
                objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
        );

        this.fichierSauvegarde = "evenements.json"; // fichier par défaut

        // Charger automatiquement les événements existants au démarrage
        chargerEvenementsAutomatique();
    }

    /**
     * Obtient l'instance unique de la classe (singleton).
     *
     * @return L'instance unique de {@link GestionEvenements}.
     */
    public static GestionEvenements getInstance() {
        if (instance == null) {
            instance = new GestionEvenements();
        }
        return instance;
    }

    /**
     * Ajoute un événement à la collection et le sauvegarde automatiquement.
     *
     * @param Ev L'événement à ajouter.
     * @throws EvenementDejaExistantException Si un événement avec le même ID existe déjà.
     */
    public void ajouterEvenement(Evenement Ev) throws EvenementDejaExistantException {
        if (evenements.containsKey(Ev.getId())) {
            throw new EvenementDejaExistantException("L'événement avec l'ID " + Ev.getId() + " existe déjà.");
        }
        evenements.put(Ev.getId(), Ev);
        System.out.println("Événement ajouté : " + Ev.getNom());

        // Sauvegarde automatique après ajout
        try {
            sauvegarderEvenements();
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde automatique : " + e.getMessage());
        }
    }

    /**
     * Supprime un événement par son ID et sauvegarde automatiquement.
     *
     * @param id L'ID de l'événement à supprimer.
     */
    public void supprimerEvenement(String id) {
        Evenement E = evenements.remove(id);
        if (E != null) {
            E.annuler();
            System.out.println("Événement supprimé : " + E.getNom());

            // Sauvegarde automatique après suppression
            try {
                sauvegarderEvenements();
            } catch (IOException e) {
                System.err.println("Erreur lors de la sauvegarde automatique : " + e.getMessage());
            }
        }
    }

    /**
     * Recherche un événement par son nom (insensible à la casse).
     *
     * @param nom Le nom de l'événement à rechercher.
     * @return L'événement correspondant ou null si aucun n'est trouvé.
     */
    public Evenement rechercherEvenement(String nom) {
        return evenements.values().stream()
                .filter(e -> e.getNom().equalsIgnoreCase(nom))
                .findFirst()
                .orElse(null);
    }

    /**
     * Recherche un événement par son ID.
     *
     * @param id L'ID de l'événement à rechercher.
     * @return L'événement correspondant ou null si aucun n'est trouvé.
     */
    public Evenement rechercherEvenementParId(String id) {
        return evenements.get(id);
    }

    /**
     * Sauvegarde les événements dans le fichier par défaut.
     *
     * @throws IOException En cas d'erreur d'entrée/sortie.
     */
    public void sauvegarderEvenements() throws IOException {
        sauvegarderEvenements(this.fichierSauvegarde);
    }

    /**
     * Sauvegarde les événements dans un fichier JSON en préservant les données existantes.
     * Les événements actuels sont fusionnés avec ceux du fichier existant.
     *
     * @param fichier Le chemin du fichier de destination.
     * @throws IOException En cas d'erreur d'entrée/sortie.
     */
    public void sauvegarderEvenements(String fichier) throws IOException {
        Map<String, Evenement> evenementsToSave = new HashMap<>();

        // D'abord, charger les événements existants du fichier
        File file = new File(fichier);
        if (file.exists() && file.length() > 0) {
            try {
                Map<String, Evenement> existingEvenements = objectMapper.readValue(file,
                        objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Evenement.class));
                evenementsToSave.putAll(existingEvenements);
                System.out.println("Chargement de " + existingEvenements.size() + " événements existants du fichier");
            } catch (IOException e) {
                System.err.println("Erreur lors de la lecture du fichier JSON existant : " + e.getMessage());
                System.err.println("Création d'un nouveau fichier...");
            }
        }

        // Ensuite, ajouter/écraser avec les événements actuels
        evenementsToSave.putAll(this.evenements);

        // Créer le répertoire parent si nécessaire
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        // Sauvegarder la map fusionnée
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, evenementsToSave);
        System.out.println("Sauvegarde de " + evenementsToSave.size() + " événements dans " + fichier);
    }

    /**
     * Charge les événements à partir d'un fichier JSON.
     *
     * @param fichier Le chemin du fichier source.
     * @throws IOException En cas d'erreur d'entrée/sortie.
     */
    public void chargerEvenements(String fichier) throws IOException {
        File file = new File(fichier);
        if (!file.exists()) {
            System.out.println("Le fichier " + fichier + " n'existe pas. Démarrage avec une collection vide.");
            this.evenements = new HashMap<>();
            return;
        }

        if (file.length() == 0) {
            System.out.println("Le fichier " + fichier + " est vide. Démarrage avec une collection vide.");
            this.evenements = new HashMap<>();
            return;
        }

        try {
            Map<String, Evenement> chargedEvenements = objectMapper.readValue(file,
                    objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Evenement.class));
            this.evenements = chargedEvenements != null ? chargedEvenements : new HashMap<>();
            System.out.println("Chargement de " + this.evenements.size() + " événements depuis " + fichier);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier " + fichier + " : " + e.getMessage());
            this.evenements = new HashMap<>();
            throw e;
        }
    }

    /**
     * Charge automatiquement les événements depuis le fichier par défaut sans lever d'exception.
     */
    private void chargerEvenementsAutomatique() {
        try {
            chargerEvenements(this.fichierSauvegarde);
        } catch (IOException e) {
            System.out.println("Impossible de charger les événements existants. Démarrage avec une collection vide.");
            this.evenements = new HashMap<>();
        }
    }

    /**
     * Fusionne les événements d'un fichier avec ceux actuellement en mémoire.
     *
     * @param fichier Le chemin du fichier source.
     * @throws IOException En cas d'erreur d'entrée/sortie.
     */
    public void fusionnerEvenements(String fichier) throws IOException {
        Map<String, Evenement> nouveauxEvenements = new HashMap<>();

        File file = new File(fichier);
        if (file.exists() && file.length() > 0) {
            nouveauxEvenements = objectMapper.readValue(file,
                    objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Evenement.class));
        }

        // Fusionner sans écraser les événements existants
        for (Map.Entry<String, Evenement> entry : nouveauxEvenements.entrySet()) {
            if (!this.evenements.containsKey(entry.getKey())) {
                this.evenements.put(entry.getKey(), entry.getValue());
            }
        }

        System.out.println("Fusion terminée. Total : " + this.evenements.size() + " événements");
    }

    /**
     * Affiche tous les événements.
     */
    public void afficherTousLesEvenements() {
        if (evenements.isEmpty()) {
            System.out.println("Aucun événement enregistré.");
        } else {
            System.out.println("=== Liste des événements ===");
            evenements.values().forEach(e ->
                    System.out.println("ID: " + e.getId() + " - Nom: " + e.getNom())
            );
        }
    }

    /**
     * Obtient la map des événements.
     *
     * @return La map des événements.
     */
    public Map<String, Evenement> getEvenements() {
        return new HashMap<>(evenements); // Retourner une copie pour protéger l'intégrité
    }

    /**
     * Définit la map des événements.
     *
     * @param evenements La nouvelle map des événements.
     */
    public void setEvenements(Map<String, Evenement> evenements) {
        this.evenements = evenements != null ? new HashMap<>(evenements) : new HashMap<>();
    }

    /**
     * Obtient le mapper JSON.
     *
     * @return Le mapper JSON.
     */
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * Définit le mapper JSON.
     *
     * @param objectMapper Le nouveau mapper JSON.
     */
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Obtient le chemin du fichier de sauvegarde par défaut.
     *
     * @return Le chemin du fichier de sauvegarde.
     */
    public String getFichierSauvegarde() {
        return fichierSauvegarde;
    }

    /**
     * Définit le chemin du fichier de sauvegarde par défaut.
     *
     * @param fichierSauvegarde Le nouveau chemin du fichier de sauvegarde.
     */
    public void setFichierSauvegarde(String fichierSauvegarde) {
        this.fichierSauvegarde = fichierSauvegarde;
    }

    /**
     * Définit l'instance singleton (utilisé pour les tests).
     *
     * @param instance La nouvelle instance.
     */
    public static void setInstance(GestionEvenements instance) {
        GestionEvenements.instance = instance;
    }

    /**
     * Vide la collection d'événements en mémoire (sans affecter le fichier).
     */
    public void viderEvenements() {
        this.evenements.clear();
        System.out.println("Collection d'événements vidée.");
    }

    /**
     * Compte le nombre d'événements.
     *
     * @return Le nombre d'événements.
     */
    public int getNombreEvenements() {
        return evenements.size();
    }
}