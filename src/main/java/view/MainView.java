package view;

import controller.EventController;
import model.CapaciteMaxAtteinteException;
import model.Evenement;
import model.EvenementDejaExistantException;
import model.Participant;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainView extends Application {
    private EventController controller = new EventController();

    @Override
    public void start(Stage primaryStage) {
        // Root layout
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f4f0;");

        // Top: Title
        Label titleLabel = new Label("Système de Gestion d'Événements");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #2e7d32; -fx-font-weight: bold;");
        VBox topBox = new VBox(titleLabel);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(20));
        root.setTop(topBox);

        // Center: Form and List
        VBox centerBox = new VBox(20);
        centerBox.setPadding(new Insets(20));
        centerBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #4caf50; -fx-border-radius: 5px;");

        // Event Form
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Conference", "Concert");
        typeCombo.setValue("Conference");
        TextField idField = new TextField();
        TextField nomField = new TextField();
        TextField dateField = new TextField();
        dateField.setPromptText("yyyy-MM-dd HH:mm");
        TextField lieuField = new TextField();
        TextField capaciteField = new TextField();
        TextField extra1Field = new TextField();
        extra1Field.setPromptText("Thème (Conférence) ou Artiste (Concert)");
        TextField extra2Field = new TextField();
        extra2Field.setPromptText("Intervenants (Conférence, séparés par des virgules) ou Genre (Concert)");

        formGrid.add(new Label("Type:"), 0, 0);
        formGrid.add(typeCombo, 1, 0);
        formGrid.add(new Label("ID:"), 0, 1);
        formGrid.add(idField, 1, 1);
        formGrid.add(new Label("Nom:"), 0, 2);
        formGrid.add(nomField, 1, 2);
        formGrid.add(new Label("Date (yyyy-MM-dd HH:mm):"), 0, 3);
        formGrid.add(dateField, 1, 3);
        formGrid.add(new Label("Lieu:"), 0, 4);
        formGrid.add(lieuField, 1, 4);
        formGrid.add(new Label("Capacité max:"), 0, 5);
        formGrid.add(capaciteField, 1, 5);
        formGrid.add(new Label("Extra 1:"), 0, 6);
        formGrid.add(extra1Field, 1, 6);
        formGrid.add(new Label("Extra 2:"), 0, 7);
        formGrid.add(extra2Field, 1, 7);

        Button addEventButton = new Button("Ajouter Événement");
        addEventButton.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold;");
        addEventButton.setOnAction(e -> {
            try {
                LocalDateTime date = LocalDateTime.parse(dateField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                controller.ajouterEvenement(
                        typeCombo.getValue(),
                        idField.getText(),
                        nomField.getText(),
                        date,
                        lieuField.getText(),
                        Integer.parseInt(capaciteField.getText()),
                        extra1Field.getText(),
                        extra2Field.getText()
                );
                updateEventList(centerBox);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Événement ajouté avec succès.");
            } catch (EvenementDejaExistantException ex) {
                showAlert(Alert.AlertType.ERROR, "Erreur", ex.getMessage());
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Vérifiez les champs saisis.");
            }
        });

        // Participant Form
        TextField partIdField = new TextField();
        TextField partNomField = new TextField();
        TextField partEmailField = new TextField();
        TextField partEventIdField = new TextField();

        GridPane partGrid = new GridPane();
        partGrid.setHgap(10);
        partGrid.setVgap(10);
        partGrid.add(new Label("ID Participant:"), 0, 0);
        partGrid.add(partIdField, 1, 0);
        partGrid.add(new Label("Nom Participant:"), 0, 1);
        partGrid.add(partNomField, 1, 1);
        partGrid.add(new Label("Email:"), 0, 2);
        partGrid.add(partEmailField, 1, 2);
        partGrid.add(new Label("ID Événement:"), 0, 3);
        partGrid.add(partEventIdField, 1, 3);

        Button addParticipantButton = new Button("Inscrire Participant");
        addParticipantButton.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold;");
        addParticipantButton.setOnAction(e -> {
            try {
                Participant participant = new Participant(partIdField.getText(), partNomField.getText(), partEmailField.getText());
                controller.ajouterParticipant(partEventIdField.getText(), participant);
                updateEventList(centerBox);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Participant inscrit.");
            } catch (CapaciteMaxAtteinteException ex) {
                showAlert(Alert.AlertType.ERROR, "Erreur", ex.getMessage());
            }
        });

        // Event List
        ListView<String> eventListView = new ListView<>();
        updateEventList(centerBox);

        Button deleteButton = new Button("Supprimer Événement");
        deleteButton.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-weight: bold;");
        deleteButton.setOnAction(e -> {
            String selected = eventListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String id = selected.split(" - ")[0];
                controller.supprimerEvenement(id);
                updateEventList(centerBox);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Événement supprimé.");
            }
        });

        Button saveButton = new Button("Sauvegarder");
        saveButton.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold;");
        saveButton.setOnAction(e -> {
            try {
                controller.sauvegarder("evenements.json");
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Événements sauvegardés.");
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la sauvegarde.");
            }
        });

        Label eventLabel = new Label("Ajouter un Événement");
        eventLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #2e7d32;");

        Label participantLabel = new Label("Inscrire un Participant");
        participantLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #2e7d32;");

        Label eventListLabel = new Label("Liste des Événements");
        eventListLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #2e7d32;");

        centerBox.getChildren().addAll(
                eventLabel,
                formGrid,
                addEventButton,
                participantLabel,
                partGrid,
                addParticipantButton,
                eventListLabel,
                eventListView,
                deleteButton,
                saveButton
        );

        root.setCenter(centerBox);

        // Scene and Stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestion d'Événements");
        primaryStage.show();
    }

    private void updateEventList(VBox centerBox) {
        ListView<String> eventListView = (ListView<String>) centerBox.getChildren().stream()
                .filter(node -> node instanceof ListView)
                .findFirst()
                .orElse(null);
        if (eventListView != null) {
            eventListView.getItems().clear();
            controller.getAllEvenements().forEach(e -> eventListView.getItems().add(e.getId() + " - " + e.afficherDetails()));
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}