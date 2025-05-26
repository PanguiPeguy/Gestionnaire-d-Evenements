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

public class TestAjouterParticipant {
    private GestionEvenements gestion;
    private Evenement evenement;

    @BeforeEach
    public void setUp() {
        gestion = GestionEvenements.getInstance();
        evenement = new Conference("p2", "Conf1", "Paris", LocalDateTime.now(),2, "Tech", Arrays.asList("Alice", "Bob"));
    }
    @Test
    public void testAjouterParticipant() throws Exception {
        gestion.ajouterEvenement(evenement);
        Participant p = new Participant("p1", "John", "john@example.com");
        evenement.ajouterParticipant(p);
        assertTrue(evenement.getParticipants().contains(p));
    }
}
