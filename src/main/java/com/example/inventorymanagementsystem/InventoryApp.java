package com.example.inventorymanagementsystem;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InventoryApp extends Application {

    private final ProductDAO dao = new ProductDAO();

    private final TableView<Product> table =
            new TableView<>();

    @Override
    public void start(Stage stage) {

        TextField nameField = new TextField();
        nameField.setPromptText("Product Name");

        TextField qtyField = new TextField();
        qtyField.setPromptText("Quantity");

        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        Button addBtn = new Button("Add");

        TableColumn<Product, Long> idCol =
                new TableColumn<>("ID");

        idCol.setCellValueFactory(
                cell -> new javafx.beans.property.SimpleLongProperty(
                        cell.getValue().getId()
                ).asObject()
        );

        TableColumn<Product, String> nameCol =
                new TableColumn<>("Product");

        nameCol.setCellValueFactory(
                cell -> new javafx.beans.property.SimpleStringProperty(
                        cell.getValue().getProductName()
                )
        );

        TableColumn<Product, Integer> qtyCol =
                new TableColumn<>("Quantity");

        qtyCol.setCellValueFactory(
                cell -> new javafx.beans.property.SimpleIntegerProperty(
                        cell.getValue().getQuantity()
                ).asObject()
        );

        TableColumn<Product, Double> priceCol =
                new TableColumn<>("Price");

        priceCol.setCellValueFactory(
                cell -> new javafx.beans.property.SimpleDoubleProperty(
                        cell.getValue().getPrice()
                ).asObject()
        );

        table.getColumns().addAll(
                idCol,
                nameCol,
                qtyCol,
                priceCol
        );

        loadData();

        addBtn.setOnAction(e -> {
            // Validate fields first
            if (nameField.getText().isEmpty() ||
                    qtyField.getText().isEmpty() ||
                    priceField.getText().isEmpty()) {
                System.out.println("Please fill in all fields.");
                return;
            }

            try {
                Product product = new Product(
                        0,
                        nameField.getText(),
                        Integer.parseInt(qtyField.getText()),
                        Double.parseDouble(priceField.getText())
                );
                dao.addProduct(product);
                loadData();
                nameField.clear();
                qtyField.clear();
                priceField.clear();
            } catch (NumberFormatException ex) {
                System.out.println("Quantity must be a whole number and Price must be a number.");
            }
        });

        Button deleteBtn =
                new Button("Delete Selected");

        deleteBtn.setOnAction(e -> {

            Product selected =
                    table.getSelectionModel().getSelectedItem();

            if (selected != null) {

                dao.deleteProduct(selected.getId());

                loadData();
            }
        });

        VBox root =
                new VBox(
                        10,
                        nameField,
                        qtyField,
                        priceField,
                        addBtn,
                        deleteBtn,
                        table
                );

        root.setStyle("-fx-padding:20;");

        Scene scene =
                new Scene(root, 700, 500);

        stage.setTitle("Inventory System");
        stage.setScene(scene);
        stage.show();
    }

    private void loadData() {

        table.setItems(
                FXCollections.observableArrayList(
                        dao.getProducts()
                )
        );
    }

    public static void main(String[] args) {
        launch();
    }
}