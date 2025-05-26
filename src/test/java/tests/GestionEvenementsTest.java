package tests;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GestionEvenementsTest {
    private GestionEvenements gestion;
    private Evenement evenement;

    @BeforeEach
    public void setUp() {
        gestion = GestionEvenements.getInstance();
        evenement = new Conference("1", "Conf1", LocalDateTime.now(), "Paris", 2, "Tech", Arrays.asList("Alice", "Bob"));
    }

    @Test
    public void testAjouterEvenement() throws EvenementDejaExistantException {
        gestion.ajouterEvenement(evenement);
        assertEquals(evenement, gestion.rechercherEvenement("Conf1"));
    }

    @Test
    public void testEvenementDejaExistant() {
        assertThrows(EvenementDejaExistantException.class, () -> {
            gestion.ajouterEvenement(evenement);
            gestion.ajouterEvenement(evenement);
        });
    }

    @Test
    public void testAjouterParticipant() throws Exception {
        gestion.ajouterEvenement(evenement);
        Participant p = new Participant("p1", "John", "john@example.com");
        evenement.ajouterParticipant(p);
        assertTrue(evenement.getParticipants().contains(p));
    }

    @Test
    public void testCapaciteMaxAtteinte() throws Exception {
        gestion.ajouterEvenement(evenement);
        evenement.ajouterParticipant(new Participant("p1", "John", "john@example.com"));
        evenement.ajouterParticipant(new Participant("p2", "Jane", "jane@example.com"));
        assertThrows(CapaciteMaxAtteinteException.class, () ->
                evenement.ajouterParticipant(new Participant("p3", "Bob", "bob@example.com")));
    }

    @Test
    public void testAnnulerEvenement() throws Exception {
        gestion.ajouterEvenement(evenement);
        Participant p = new Participant("p1", "John", "john@example.com");
        evenement.ajouterParticipant(p);
        evenement.annuler();
        assertTrue(evenement.getParticipants().isEmpty());
    }
}