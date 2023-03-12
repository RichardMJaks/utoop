package oop;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Module version info
 *
 * JDK 17
 * Gradle 7.2
 */

// Probably should use BigDecimal
public class MustRuut extends Application {
    String rateFile = "kursid.txt";
    int windowWidth = 250;
    int windowHeight = 100;
    int clearButtonHeight = 30;

    /**
     * Get rates from file
     * @param path file path
     * @return RateDict containing rates to each currency listed in file
     * @throws IOException
     */
    HashMap<String, Double> getRatesFromFile(Path path) throws IOException {
        HashMap<String, Double> rates = new HashMap<>(); // Courses
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()) {
            String[] l = scanner.nextLine().split(" ");
            rates.put(l[0], Double.valueOf(l[1]));
        }

        return rates;
    }

    // Custom round to 2 decimals bc Math.round doesn't have an overload to do this internally for some reason
    double myRound(double i) {
        return  (double) (Math.round(i * 100) / 100);
    }

    @Override
    public void start(Stage mainStage) throws Exception {
        AnchorPane root = new AnchorPane(); // Root

        HBox mainContainer = new HBox();

        // Separator boxes
        VBox valueEUR = new VBox();
        valueEUR.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        valueEUR.setPrefSize((double) windowWidth / 2, windowHeight);
        VBox valueForeign = new VBox();
        valueForeign.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        valueForeign.setPrefSize((double) windowWidth / 2, windowHeight);

        //region Rates dictionary
        HashMap<String, Double> rates = getRatesFromFile(Path.of(rateFile));
        // Also get currencies for the drop-down box
        ObservableList<String> currencies = FXCollections.observableArrayList(); // Must be of ObservableList type for drop-down
        for (Map.Entry<String, Double> m : rates.entrySet()) {
            currencies.add(m.getKey());
        }
        currencies = currencies.stream()
                .sorted(String::compareTo)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(), FXCollections::observableArrayList)); // Went a step further to avoid compiler errors
        //endregion

        // Drop-down boxes
        ChoiceBox<String> currency = new ChoiceBox<>();
        currency.setItems(currencies);
        currency.setValue(currency.getItems().get(0)); // Set first element as value

        // Labels
        Label labelEUR = new Label("Convert from EUR");
        Label labelForeign = new Label("to " + currency.getValue());

        //region Text fields
        TextField eurInput = new TextField();
        TextField foreignOutput = new TextField();

        // Set properties
        foreignOutput.setEditable(false);

        // Field-Field sync
        eurInput.textProperty().addListener((o, old, n) ->
                foreignOutput.setText(
                        (!Objects.equals(n, "")) ? // If empty then set empty value
                            Double.toString(myRound(
                                    Double.parseDouble(n) * rates.get(currency.getValue())
                            ))
                        : ""
                )
        );
        // Listen to drop-down menu changes and update accordingly
        currency.setOnAction(event -> {
            foreignOutput.setText(
                        (!Objects.equals(eurInput.getText(), "")) ? // If empty then set empty value
                            Double.toString(myRound(
                                    Double.parseDouble(eurInput.getText()) * rates.get(currency.getValue())
                            ))
                        : ""

            );
            labelForeign.setText("to " + currency.getValue());
        });

        //endregion

        //region Clear button
        Button clearFields = new Button("Clear");
        clearFields.setPrefHeight(clearButtonHeight);
        clearFields.isDefaultButton();
        clearFields.setOnAction(event -> {
            eurInput.clear();
            foreignOutput.clear();
        });
        //endregion

        //region logo
        Canvas logo = new Canvas(30, 30);
        GraphicsContext gc = logo.getGraphicsContext2D();

        gc.setFill(Paint.valueOf("5fd47e"));
        gc.fillRoundRect(5, 2, 25, 26, 10, 10);

        gc.setFill(Paint.valueOf("224d2e"));
        gc.fillRect(7, 20, 21, 5);

        gc.setFill(Paint.valueOf("dcf2e2"));
        gc.fillOval(7, 5, 21, 14);
        //endregion

        //region Positioning & styling
        root.getChildren().addAll(mainContainer, clearFields);
        // Clear button
        AnchorPane.setBottomAnchor(clearFields, 0.0);
        AnchorPane.setLeftAnchor(clearFields, 0.0);
        AnchorPane.setRightAnchor(clearFields, 0.0);
        // mainContainer
        AnchorPane.setLeftAnchor(mainContainer, 0.0);
        AnchorPane.setRightAnchor(mainContainer, 0.0);
        AnchorPane.setTopAnchor(mainContainer, 0.0);
        AnchorPane.setBottomAnchor(mainContainer, (double) clearButtonHeight);

        // EUR and foreign boxes into mainContainer
        mainContainer.getChildren().addAll(valueEUR, valueForeign);

        // EUR box styling
        valueEUR.getChildren().addAll(labelEUR, eurInput, logo);

        // Foreign box styling
        valueForeign.getChildren().addAll(labelForeign, foreignOutput, currency);

        //endregion

        // Start the whole thing
        Scene scene = new Scene(root, windowWidth, windowHeight, Color.ANTIQUEWHITE);
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

