package tests;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Arrays;

public class TestSerialisation {
    private GestionEvenements gestion;
    private Evenement evenement;
    private String fichier;

    @BeforeEach
    public void setUp() {
        gestion = GestionEvenements.getInstance();
        evenement = new Conference("p2", "Conf1", "Paris", LocalDateTime.now(),2, "Tech", Arrays.asList("Alice", "Bob"));
    }

    @Test
    public void testSerialisation() throws Exception{
        gestion.sauvegarderEvenements("evenements.json");
    }

    @Test
    public void testDeSerialisation() throws Exception{
        gestion.chargerEvenements("evenements.json");
    }



}