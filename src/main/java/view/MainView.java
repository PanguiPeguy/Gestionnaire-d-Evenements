package view;

import controller.EventController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.CapaciteMaxAtteinteException;
import model.Evenement;
import model.EvenementDejaExistantException;
import model.Participant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainView extends Application {
    private final EventController controller = new EventController();
    private VBox eventCardsContainer;
    private Stage primaryStage;

    // Couleurs modernes avec vert principal
    private static final String PRIMARY_GREEN = "#2E7D32";
    private static final String LIGHT_GREEN = "#4CAF50";
    private static final String ACCENT_GREEN = "#66BB6A";
    private static final String BACKGROUND_COLOR = "#F8F9FA";
    private static final String CARD_COLOR = "#FFFFFF";
    private static final String TEXT_DARK = "#2C3E50";
    private static final String TEXT_LIGHT = "#7F8C8D";

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        // Chargement automatique des données
        chargerDonnees();

        VBox mainLayout = createMainLayout();

        Scene scene = new Scene(mainLayout, 1200, 800);
        scene.getStylesheets().add(createModernCSS());

        stage.setTitle("Gestion d'Événements - Interface Moderne");
        stage.setScene(scene);
        stage.show();

    }

    private VBox createMainLayout() {
        VBox mainLayout = new VBox();
        mainLayout.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");

        // Header moderne
        HBox header = createModernHeader();

        // Contenu principal avec scroll
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        VBox content = new VBox(20);
        content.setPadding(new Insets(30));

        // Section événements avec titre moderne
        Label titleLabel = new Label("Événements");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_DARK + ";");

        // Container pour les cartes d'événements
        eventCardsContainer = new VBox(15);
        updateEventCards();

        content.getChildren().addAll(titleLabel, eventCardsContainer);
        scrollPane.setContent(content);

        mainLayout.getChildren().addAll(header, scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        return mainLayout;
    }

    private HBox createModernHeader() {
        HBox header = new HBox(20);
        header.setPadding(new Insets(20, 30, 20, 30));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: " + PRIMARY_GREEN + "; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2);");

        Label appTitle = new Label("🎉 Gestionnaire d'Événements");
        appTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button nouvelEventBtn = createModernButton("+ Nouvel Événement", LIGHT_GREEN, "white");
        nouvelEventBtn.setOnAction(e -> ouvrirFormulaireEvenement());

        Button nouvelParticipantBtn = createModernButton("+ Ajouter Participant", ACCENT_GREEN, "white");
        nouvelParticipantBtn.setOnAction(e -> ouvrirFormulaireParticipant());

        Button sauvegarderBtn = createModernButton("💾 Sauvegarder", "#34495E", "white");
        sauvegarderBtn.setOnAction(e -> sauvegarderManuellement());

        header.getChildren().addAll(appTitle, spacer, nouvelEventBtn, nouvelParticipantBtn, sauvegarderBtn);
        return header;
    }

    private void updateEventCards() {
        eventCardsContainer.getChildren().clear();

        List<Evenement> evenements = controller.getAllEvenements();

        if (evenements.isEmpty()) {
            Label emptyLabel = new Label("Aucun événement créé pour le moment");
            emptyLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: " + TEXT_LIGHT + "; -fx-padding: 40px;");
            emptyLabel.setAlignment(Pos.CENTER);
            eventCardsContainer.getChildren().add(emptyLabel);
            return;
        }

        for (Evenement event : evenements) {
            VBox eventCard = createEventCard(event);
            eventCardsContainer.getChildren().add(eventCard);
        }
    }

    private VBox createEventCard(Evenement event) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(25));
        card.setStyle(
                "-fx-background-color: " + CARD_COLOR + "; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 2); " +
                        "-fx-cursor: hand;"
        );

        // Header de la carte
        HBox cardHeader = new HBox(15);
        cardHeader.setAlignment(Pos.CENTER_LEFT);

        Label eventName = new Label(event.getNom());
        eventName.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_DARK + ";");

        Label eventType = new Label(event.getClass().getSimpleName());
        eventType.setStyle(
                "-fx-background-color: " + LIGHT_GREEN + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-padding: 4px 12px; " +
                        "-fx-background-radius: 20px; " +
                        "-fx-font-size: 12px;"
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button deleteBtn = new Button("🗑");
        deleteBtn.setStyle(
                "-fx-background-color: #E74C3C; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 50%; " +
                        "-fx-min-width: 35px; " +
                        "-fx-min-height: 35px;"
        );
        deleteBtn.setOnAction(e -> {
            e.consume(); // Empêche le clic sur la carte
            supprimerEvenement(event);
        });

        cardHeader.getChildren().addAll(eventName, eventType, spacer, deleteBtn);

        // Détails de l'événement
        VBox eventDetails = new VBox(8);

        Label dateLabel = new Label("📅 " + event.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm")));
        dateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: " + TEXT_LIGHT + ";");

        Label lieuLabel = new Label("📍 " + event.getLieu());
        lieuLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: " + TEXT_LIGHT + ";");

        // Affichage des informations spécifiques selon le type
        String typeSpecificInfo = getTypeSpecificInfo(event);
        if (!typeSpecificInfo.isEmpty()) {
            Label infoLabel = new Label(typeSpecificInfo);
            infoLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: " + TEXT_LIGHT + "; -fx-font-style: italic;");
            eventDetails.getChildren().add(infoLabel);
        }

        int nbParticipants = event.getParticipants().size();
        Label participantsLabel = new Label("👥 " + nbParticipants + "/" + event.getCapaciteMax() + " participants");
        participantsLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: " + TEXT_LIGHT + ";");

        // Barre de progression pour la capacité
        ProgressBar progressBar = new ProgressBar((double) nbParticipants / event.getCapaciteMax());
        progressBar.setStyle("-fx-accent: " + LIGHT_GREEN + ";");
        progressBar.setPrefHeight(8);

        eventDetails.getChildren().addAll(dateLabel, lieuLabel, participantsLabel, progressBar);

        card.getChildren().addAll(cardHeader, eventDetails);

        // Clic sur la carte pour voir les détails
        card.setOnMouseClicked(e -> ouvrirDetailsEvenement(event));

        // Effet hover
        card.setOnMouseEntered(e ->
                card.setStyle(card.getStyle() + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 12, 0, 0, 4);")
        );
        card.setOnMouseExited(e ->
                card.setStyle(card.getStyle().replace("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 12, 0, 0, 4);",
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 2);"))
        );

        return card;
    }

    private String getTypeSpecificInfo(Evenement event) {
        String className = event.getClass().getSimpleName();
        String details = event.afficherDetails();

        if ("Conference".equals(className)) {
            // Extraire le thème depuis les détails
            if (details.contains("Thème:")) {
                String theme = details.substring(details.indexOf("Thème:") + 6).trim();
                if (theme.contains(",")) {
                    theme = theme.substring(0, theme.indexOf(",")).trim();
                }
                return "🎯 Thème: " + theme;
            }
        } else if ("Concert".equals(className)) {
            // Extraire l'artiste et le genre depuis les détails
            String info = "";
            if (details.contains("Artiste:")) {
                String artiste = details.substring(details.indexOf("Artiste:") + 8).trim();
                if (artiste.contains(",")) {
                    artiste = artiste.substring(0, artiste.indexOf(",")).trim();
                }
                info += "🎤 " + artiste;
            }
            if (details.contains("Genre:")) {
                String genre = details.substring(details.indexOf("Genre:") + 6).trim();
                if (genre.contains(",")) {
                    genre = genre.substring(0, genre.indexOf(",")).trim();
                }
                if (!info.isEmpty()) info += " • ";
                info += "🎵 " + genre;
            }
            return info;
        }
        return "";
    }

    private void ouvrirDetailsEvenement(Evenement event) {
        Stage detailStage = new Stage();
        detailStage.initModality(Modality.APPLICATION_MODAL);
        detailStage.setTitle("Détails - " + event.getNom());

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");

        // Titre
        Label title = new Label(event.getNom());
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_DARK + ";");

        // Détails de l'événement
        VBox eventInfo = new VBox(10);
        eventInfo.setStyle("-fx-background-color: " + CARD_COLOR + "; -fx-padding: 20px; -fx-background-radius: 8px;");

        String eventType = event.getClass().getSimpleName();

        eventInfo.getChildren().addAll(
                new Label("ID: " + event.getId()),
                new Label("Type: " + eventType),
                new Label("Date: " + event.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm"))),
                new Label("Lieu: " + event.getLieu()),
                new Label("Capacité: " + event.getParticipants().size() + "/" + event.getCapaciteMax())
        );

        // Ajouter les informations spécifiques selon le type
        String details = event.afficherDetails();
        if ("Conference".equals(eventType)) {
            if (details.contains("Thème:")) {
                String theme = details.substring(details.indexOf("Thème:") + 6).trim();
                if (theme.contains(",")) {
                    theme = theme.substring(0, theme.indexOf(",")).trim();
                }
                Label themeLabel = new Label("Thème: " + theme);
                themeLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: " + PRIMARY_GREEN + ";");
                eventInfo.getChildren().add(themeLabel);
            }
        } else if ("Concert".equals(eventType)) {
            if (details.contains("Artiste:")) {
                String artiste = details.substring(details.indexOf("Artiste:") + 8).trim();
                if (artiste.contains(",")) {
                    artiste = artiste.substring(0, artiste.indexOf(",")).trim();
                }
                Label artisteLabel = new Label("Artiste: " + artiste);
                artisteLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: " + PRIMARY_GREEN + ";");
                eventInfo.getChildren().add(artisteLabel);
            }
            if (details.contains("Genre:")) {
                String genre = details.substring(details.indexOf("Genre:") + 6).trim();
                if (genre.contains(",")) {
                    genre = genre.substring(0, genre.indexOf(",")).trim();
                }
                Label genreLabel = new Label("Genre Musical: " + genre);
                genreLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: " + PRIMARY_GREEN + ";");
                eventInfo.getChildren().add(genreLabel);
            }
        }

        // Liste des participants
        Label participantsTitle = new Label("Participants (" + event.getParticipants().size() + ")");
        participantsTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_DARK + ";");

        ListView<String> participantsList = new ListView<>();
        participantsList.setPrefHeight(200);

        for (Participant p : event.getParticipants()) {
            participantsList.getItems().add(p.getNom() + " (" + p.getEmail() + ")");
        }

        if (event.getParticipants().isEmpty()) {
            participantsList.getItems().add("Aucun participant inscrit");
        }

        Button fermerBtn = createModernButton("Fermer", PRIMARY_GREEN, "white");
        fermerBtn.setOnAction(e -> detailStage.close());

        layout.getChildren().addAll(title, eventInfo, participantsTitle, participantsList, fermerBtn);

        Scene scene = new Scene(layout, 500, 600);
        detailStage.setScene(scene);
        detailStage.show();
    }

    private void ouvrirFormulaireEvenement() {
        Stage formStage = new Stage();
        formStage.initModality(Modality.APPLICATION_MODAL);
        formStage.setTitle("Créer un Nouvel Événement");

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");

        Label title = new Label("Créer un Nouvel Événement");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_DARK + ";");

        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(15);
        form.setStyle("-fx-background-color: " + CARD_COLOR + "; -fx-padding: 25px; -fx-background-radius: 8px;");

        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Conference", "Concert");
        typeCombo.setValue("Conference");

        TextField idField = new TextField();
        TextField nomField = new TextField();
        TextField dateField = new TextField();
        dateField.setPromptText("yyyy-MM-dd HH:mm");
        TextField lieuField = new TextField();
        TextField capaciteField = new TextField();
        TextField extra1 = new TextField();
        TextField extra2 = new TextField();

        // Labels dynamiques pour les champs extras
        Label extra1Label = new Label("Thème:");
        Label extra2Label = new Label("Autre:");

        // Style des champs
        String fieldStyle = "-fx-background-color: white; -fx-border-color: #E0E0E0; -fx-border-radius: 4px; -fx-padding: 8px;";
        idField.setStyle(fieldStyle);
        nomField.setStyle(fieldStyle);
        dateField.setStyle(fieldStyle);
        lieuField.setStyle(fieldStyle);
        capaciteField.setStyle(fieldStyle);
        extra1.setStyle(fieldStyle);
        extra2.setStyle(fieldStyle);

        // Configuration initiale pour Conférence
        extra1.setPromptText("Thème de la conférence");
        extra2.setVisible(false);
        extra2Label.setVisible(false);

        // Listener pour changer les labels selon le type
        typeCombo.setOnAction(e -> {
            String selectedType = typeCombo.getValue();
            form.getChildren().clear(); // Effacer le formulaire

            if ("Conference".equals(selectedType)) {
                extra1Label.setText("Thème:");
                extra1.setPromptText("Thème de la conférence");
                extra2.setVisible(false);
                extra2Label.setVisible(false);

                form.addRow(0, new Label("Type:"), typeCombo);
                form.addRow(1, new Label("ID:"), idField);
                form.addRow(2, new Label("Nom:"), nomField);
                form.addRow(3, new Label("Date:"), dateField);
                form.addRow(4, new Label("Lieu:"), lieuField);
                form.addRow(5, new Label("Capacité:"), capaciteField);
                form.addRow(6, extra1Label, extra1);

            } else if ("Concert".equals(selectedType)) {
                extra1Label.setText("Artiste:");
                extra1.setPromptText("Nom de l'artiste");
                extra2Label.setText("Genre Musical:");
                extra2.setPromptText("Genre musical");
                extra2.setVisible(true);
                extra2Label.setVisible(true);

                form.addRow(0, new Label("Type:"), typeCombo);
                form.addRow(1, new Label("ID:"), idField);
                form.addRow(2, new Label("Nom:"), nomField);
                form.addRow(3, new Label("Date:"), dateField);
                form.addRow(4, new Label("Lieu:"), lieuField);
                form.addRow(5, new Label("Capacité:"), capaciteField);
                form.addRow(6, extra1Label, extra1);
                form.addRow(7, extra2Label, extra2);
            }
        });

        // Configuration initiale du formulaire
        form.addRow(0, new Label("Type:"), typeCombo);
        form.addRow(1, new Label("ID:"), idField);
        form.addRow(2, new Label("Nom:"), nomField);
        form.addRow(3, new Label("Date:"), dateField);
        form.addRow(4, new Label("Lieu:"), lieuField);
        form.addRow(5, new Label("Capacité:"), capaciteField);
        form.addRow(6, extra1Label, extra1);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);

        Button creerBtn = createModernButton("Créer Événement", PRIMARY_GREEN, "white");
        Button annulerBtn = createModernButton("Annuler", "#95A5A6", "white");

        creerBtn.setOnAction(e -> {
            try {
                LocalDateTime date = LocalDateTime.parse(dateField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                String extra1Value = extra1.getText();
                String extra2Value = "Concert".equals(typeCombo.getValue()) ? extra2.getText() : "";

                controller.ajouterEvenement(
                        typeCombo.getValue(),
                        idField.getText(),
                        nomField.getText(),
                        date,
                        lieuField.getText(),
                        Integer.parseInt(capaciteField.getText()),
                        extra1Value,
                        extra2Value
                );

                updateEventCards();
                formStage.close();
                showModernAlert(Alert.AlertType.INFORMATION, "Succès", "Événement créé avec succès!");

            } catch (EvenementDejaExistantException ex) {
                showModernAlert(Alert.AlertType.ERROR, "Erreur", ex.getMessage());
            } catch (Exception ex) {
                showModernAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez vérifier vos données.");
            }
        });

        annulerBtn.setOnAction(e -> formStage.close());

        buttons.getChildren().addAll(creerBtn, annulerBtn);
        layout.getChildren().addAll(title, form, buttons);

        Scene scene = new Scene(layout, 450, 550);
        formStage.setScene(scene);
        formStage.show();
    }

    private void ouvrirFormulaireParticipant() {
        Stage formStage = new Stage();
        formStage.initModality(Modality.APPLICATION_MODAL);
        formStage.setTitle("Ajouter un Participant");

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");

        Label title = new Label("Ajouter un Participant");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_DARK + ";");

        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(15);
        form.setStyle("-fx-background-color: " + CARD_COLOR + "; -fx-padding: 25px; -fx-background-radius: 8px;");

        TextField idField = new TextField();
        TextField nomField = new TextField();
        TextField emailField = new TextField();
        ComboBox<String> eventCombo = new ComboBox<>();

        // Remplir la liste des événements
        for (Evenement event : controller.getAllEvenements()) {
            eventCombo.getItems().add(event.getId() + " - " + event.getNom());
        }

        String fieldStyle = "-fx-background-color: white; -fx-border-color: #E0E0E0; -fx-border-radius: 4px; -fx-padding: 8px;";
        idField.setStyle(fieldStyle);
        nomField.setStyle(fieldStyle);
        emailField.setStyle(fieldStyle);

        form.addRow(0, new Label("ID Participant:"), idField);
        form.addRow(1, new Label("Nom:"), nomField);
        form.addRow(2, new Label("Email:"), emailField);
        form.addRow(3, new Label("Événement:"), eventCombo);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);

        Button ajouterBtn = createModernButton("Ajouter Participant", PRIMARY_GREEN, "white");
        Button annulerBtn = createModernButton("Annuler", "#95A5A6", "white");

        ajouterBtn.setOnAction(e -> {
            try {
                if (eventCombo.getValue() != null) {
                    String eventId = eventCombo.getValue().split(" - ")[0];
                    Participant participant = new Participant(idField.getText(), nomField.getText(), emailField.getText());
                    controller.ajouterParticipant(eventId, participant);

                    updateEventCards();
                    formStage.close();
                    showModernAlert(Alert.AlertType.INFORMATION, "Succès", "Participant ajouté avec succès!");
                }
            } catch (CapaciteMaxAtteinteException ex) {
                showModernAlert(Alert.AlertType.ERROR, "Erreur", ex.getMessage());
            } catch (Exception ex) {
                showModernAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout du participant.");
            }
        });

        annulerBtn.setOnAction(e -> formStage.close());

        buttons.getChildren().addAll(ajouterBtn, annulerBtn);
        layout.getChildren().addAll(title, form, buttons);

        Scene scene = new Scene(layout, 400, 300);
        formStage.setScene(scene);
        formStage.show();
    }

    private void supprimerEvenement(Evenement event) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmer la suppression");
        confirmation.setHeaderText("Supprimer l'événement");
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer l'événement \"" + event.getNom() + "\" ?");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                controller.supprimerEvenement(event.getId());
                sauvegarderAutomatiquement();
                updateEventCards();
                showModernAlert(Alert.AlertType.INFORMATION, "Suppression", "Événement supprimé avec succès.");
            }
        });
    }

    private Button createModernButton(String text, String backgroundColor, String textColor) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: " + backgroundColor + "; " +
                        "-fx-text-fill: " + textColor + "; " +
                        "-fx-background-radius: 6px; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-cursor: hand;"
        );

        // Effet hover
        button.setOnMouseEntered(e ->
                button.setStyle(button.getStyle() + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 2);")
        );
        button.setOnMouseExited(e ->
                button.setStyle(button.getStyle().replace("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 2);", ""))
        );

        return button;
    }

    private void showModernAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Style moderne pour l'alerte
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: " + CARD_COLOR + ";");

        alert.showAndWait();
    }

    private void sauvegarderAutomatiquement() {
        try {
            controller.sauvegarder("evenements.json");
            for (Evenement event : controller.getAllEvenements()) {
                event.sauvegarderParticipants("participants_" + event.getId() + ".json", controller.getObjectMapper());
            }
            System.out.println("✅ Sauvegarde automatique effectuée");
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la sauvegarde automatique: " + e.getMessage());
            showModernAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la sauvegarde automatique : " + e.getMessage());
        }
    }

    private void sauvegarderManuellement() {
        try {
            controller.sauvegarder("evenements.json");
            for (Evenement event : controller.getAllEvenements()) {
                event.sauvegarderParticipants("participants_" + event.getId() + ".json", controller.getObjectMapper());
            }
            showModernAlert(Alert.AlertType.INFORMATION, "Sauvegarde", "Données sauvegardées avec succès!");
        } catch (Exception e) {
            showModernAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la sauvegarde.");
        }
    }

    private void chargerDonnees() {
        try {
            controller.charger("evenements.json");
            for (Evenement event : controller.getAllEvenements()) {
                event.chargerParticipants("participants_" + event.getId() + ".json", controller.getObjectMapper());
            }
            System.out.println("✅ Données chargées au démarrage");
        } catch (Exception e) {
            System.out.println("ℹ️ Aucune donnée précédente trouvée - nouveau démarrage");
        }
    }

    private String createModernCSS() {
        return "data:text/css," +
                ".scroll-pane { -fx-background-color: transparent; }" +
                ".scroll-pane .viewport { -fx-background-color: transparent; }" +
                ".scroll-pane .content { -fx-background-color: transparent; }";
    }

    public static void main(String[] args) {
        launch(args);
    }
}