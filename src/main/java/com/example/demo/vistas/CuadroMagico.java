package com.example.demo.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.RandomAccessFile;

public class CuadroMagico extends Stage {
    private GridPane gridPane;
    private TextField sizeInput;

    public CuadroMagico() {
        crearUI();
        Scene scene = new Scene(crearUI(), 400, 400);
        scene.getStylesheets().add(getClass().getResource("/estilos/cm.css").toString());

        this.setTitle("Cuadro Mágico");
        this.setScene(scene);
        this.show();
    }

    public VBox crearUI() {
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setId("gridPane");


        sizeInput = new TextField();
        sizeInput.setPromptText("Tamaño del cuadro (impar mayor o igual a 3)");
        sizeInput.setMaxWidth(200);
        sizeInput.setAlignment(Pos.CENTER);

        Button generarButton = new Button("Generar");
        generarButton.setId("generarButton");
        generarButton.setPrefSize(100, 50);
        generarButton.setOnAction(e -> generar());

        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.getChildren().addAll(sizeInput, generarButton);
        inputBox.setId("inputBox");


        VBox root = new VBox(10);
        root.getChildren().addAll(inputBox, gridPane);
        root.setAlignment(Pos.CENTER);
        root.setId("root");


        return root;
    }

    private void generar() {
        int size;
        try {
            size = Integer.parseInt(sizeInput.getText());
        } catch (NumberFormatException e) {
            Error("Por favor, introduce un número válido para el tamaño del cuadro.");
            return;
        }

        if (size % 2 == 0) {
            Error("El tamaño del cuadro debe ser impar.");
            return;
        }

        int[][] cuadroMagico = Determina(size);
        mostrar(cuadroMagico);
        guardar(cuadroMagico);
    }

    private int[][] Determina(int size) {
        int[][] cuadroMagico = new int[size][size];

        int num = 1;
        int row = 0;
        int col = size / 2;

        while (num <= size * size) {
            cuadroMagico[row][col] = num;
            num++;

            int nextRow = (row - 1 + size) % size;
            int nextCol = (col + 1) % size;

            if (cuadroMagico[nextRow][nextCol] != 0) {
                row = (row + 1) % size;
            } else {
                row = nextRow;
                col = nextCol;
            }
        }

        return cuadroMagico;
    }

    private void mostrar(int[][] cuadroMagico) {
        gridPane.getChildren().clear();

        for (int i = 0; i < cuadroMagico.length; i++) {
            for (int j = 0; j < cuadroMagico[i].length; j++) {
                Label label = new Label(String.valueOf(cuadroMagico[i][j]));
                label.setPrefWidth(50);
                label.setPrefHeight(50);
                label.setAlignment(Pos.CENTER);
                gridPane.add(label, j, i);
            }
        }
    }

    private void guardar(int[][] cuadroMagico) {
        try (RandomAccessFile file = new RandomAccessFile("cuadro_magico.txt", "rw")) {
            // Escribir el cuadro mágico en el archivo de texto
            for (int i = 0; i < cuadroMagico.length; i++) {
                for (int j = 0; j < cuadroMagico[i].length; j++) {
                    file.writeUTF(String.valueOf(cuadroMagico[i][j]));
                    if (j < cuadroMagico[i].length - 1) {
                        file.writeUTF("\t");
                    }
                }
                file.writeUTF(System.lineSeparator());
            }

            Mensaje("Cuadro mágico generado y guardado en cuadro_magico.txt.");
        } catch (IOException e) {
            Error("Error al escribir en el archivo.");
            e.printStackTrace();
        }
    }

    private void Error(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void Mensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
