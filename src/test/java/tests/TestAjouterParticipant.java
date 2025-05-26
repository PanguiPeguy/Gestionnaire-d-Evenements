package tests;

import model.Conference;
import model.Evenement;
import model.GestionEvenements;
import model.Participant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests unitaires pour vérifier l'ajout d'un participant à un événement.
 */
public class TestAjouterParticipant {
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
     * Teste que l'ajout d'un participant à un événement fonctionne correctement.
     *
     * @throws Exception Si une erreur inattendue se produit.
     */
    @Test
    public void testAjouterParticipant() throws Exception {
        gestion.ajouterEvenement(evenement);
        Participant p = new Participant("p1", "John", "john@example.com");
        evenement.ajouterParticipant(p);
        assertTrue(evenement.getParticipants().contains(p));
    }
}