package tests;

import model.Conference;
import model.Evenement;
import model.EvenementDejaExistantException;
import model.GestionEvenements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests unitaires pour vérifier le comportement de l'exception {@link EvenementDejaExistantException}
 * lorsqu'un événement avec un ID existant est ajouté.
 */
public class TestEvenementDejaExistant {
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
     * Teste que l'ajout d'un événement avec un ID déjà existant lève une {@link EvenementDejaExistantException}.
     */
    @Test
    public void testEvenementDejaExistant() {
        assertThrows(EvenementDejaExistantException.class, () -> {
            gestion.ajouterEvenement(evenement);
            gestion.ajouterEvenement(evenement);
        });
    }
}