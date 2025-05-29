package tests;

import model.Conference;
import model.Evenement;
import model.EvenementDejaExistantException;
import model.GestionEvenements;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests unitaires pour vérifier l'ajout d'un événement à {@link GestionEvenements}.
 */
public class TestAjouterEvenement {
    /** Instance de gestion des événements utilisée pour les tests. */
    private GestionEvenements gestion;
    /** Événement utilisé pour les tests. */
    private Evenement evenement;

    /**
     * Configure l'environnement de test avant chaque test.
     * Crée une nouvelle instance de {@link GestionEvenements} et un événement de type {@link Conference}.
     */
    @BeforeEach
    public void setUp() {
        gestion = GestionEvenements.getInstance();
        evenement = new Conference("p2", "Conf1", "Paris", LocalDateTime.now(), 2, "Tech", Arrays.asList("Alice", "Bob"));
    }

    /**
     * Teste que l'ajout d'un événement fonctionne correctement et qu'il peut être retrouvé par son nom.
     *
     * @throws EvenementDejaExistantException Si l'événement existe déjà (non attendu dans ce test).
     */
    @Test
    public void testAjouterEvenement() throws EvenementDejaExistantException {
        gestion.ajouterEvenement(evenement);
        assertEquals(evenement, gestion.rechercherEvenement("Conf1"));
    }

    @AfterEach
    void cleanup() {
        if (gestion.rechercherEvenement("Conf1") != null) {
            gestion.supprimerEvenement("Conf1");
        }
    }
}