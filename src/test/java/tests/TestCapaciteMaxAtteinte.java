package tests;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestCapaciteMaxAtteinte {
    private GestionEvenements gestion;
    private Evenement evenement;

    @BeforeEach
    public void setUp() {
        gestion = GestionEvenements.getInstance();
        evenement = new Conference("p2", "Conf1", "Paris", LocalDateTime.now(),2, "Tech", Arrays.asList("Alice", "Bob"));
    }
    @Test
    public void testCapaciteMaxAtteinte() throws Exception {
        gestion.ajouterEvenement(evenement);
        evenement.ajouterParticipant(new Participant("p1", "John", "john@example.com"));
        evenement.ajouterParticipant(new Participant("p2", "Jane", "jane@example.com"));
        assertThrows(CapaciteMaxAtteinteException.class, () ->
                evenement.ajouterParticipant(new Participant("p3", "Bob", "bob@example.com")));
    }
}
