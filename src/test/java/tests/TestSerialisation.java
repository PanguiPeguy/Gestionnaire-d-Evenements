package tests;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Tests unitaires pour vérifier la sérialisation et la désérialisation des événements.
 */
public class TestSerialisation {
    /** Instance de gestion des événements utilisée pour les tests. */
    private GestionEvenements gestion;
    /** Événement utilisé pour les tests. */
    private Evenement evenement;
    /** Chemin du fichier utilisé pour les tests de sérialisation/désérialisation. */
    private String fichier;

    /**
     * Configure l'environnement de test avant chaque test.
     * Crée une nouvelle instance de {@link GestionEvenements} et un événement de type {@link Conference}.
     */
    @BeforeEach
    public void setUp() {
        gestion = GestionEvenements.getInstance();
        evenement = new Conference("p2", "Conf1", "Paris", LocalDateTime.now(), 2, "Tech", Arrays.asList("Alice", "Bob"));
        fichier = "evenements.json";
    }

    /**
     * Teste la sérialisation des événements dans un fichier JSON.
     *
     * @throws Exception En cas d'erreur d'entrée/sortie.
     */
    @Test
    public void testSerialisation() throws Exception {
        gestion.sauvegarderEvenements(fichier);
    }

    /**
     * Teste la désérialisation des événements à partir d'un fichier JSON.
     *
     * @throws Exception En cas d'erreur d'entrée/sortie.
     */
    @Test
    public void testDeSerialisation() throws Exception {
        gestion.chargerEvenements(fichier);
    }
}