package tests;

import model.Conference;
import model.Evenement;
import model.EvenementDejaExistantException;
import model.GestionEvenements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAjouterEvenement {
    private GestionEvenements gestion;
    private Evenement evenement;

    @BeforeEach
    public void setUp() {
        gestion = GestionEvenements.getInstance();
        evenement = new Conference("p2", "Conf1", "Paris", LocalDateTime.now(),2, "Tech", Arrays.asList("Alice", "Bob"));
    }

    @Test
    public void testAjouterEvenement() throws EvenementDejaExistantException {
        gestion.ajouterEvenement(evenement);
        assertEquals(evenement, gestion.rechercherEvenement("Conf1"));
    }
}
