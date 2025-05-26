package tests;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests unitaires pour vérifier le comportement de l'exception {@link CapaciteMaxAtteinteException}
 * lorsqu'un événement atteint sa capacité maximale.
 */
public class TestCapaciteMaxAtteinte {
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
     * Teste que l'ajout d'un participant au-delà de la capacité maximale lève une {@link CapaciteMaxAtteinteException}.
     *
     * @throws Exception Si une erreur inattendue se produit.
     */
    @Test
    public void testCapaciteMaxAtteinte() throws Exception {
        gestion.ajouterEvenement(evenement);
        evenement.ajouterParticipant(new Participant("p1", "John", "john@example.com"));
        evenement.ajouterParticipant(new Participant("p2", "Jane", "jane@example.com"));
        assertThrows(CapaciteMaxAtteinteException.class, () ->
                evenement.ajouterParticipant(new Participant("p3", "Bob", "bob@example.com")));
    }
}