package main;

import view.MainView;

/**
 * Point d'entrée principal de l'application de gestion d'événements.
 * Cette classe lance l'interface utilisateur principale.
 */
public class Main {
    /**
     * Méthode principale qui démarre l'application.
     * Délègue l'exécution à la classe {@link MainView}.
     *
     * @param args Les arguments de la ligne de commande (non utilisés dans cette implémentation).
     */
    public static void main(String[] args) {
        MainView.main(args);
    }
}