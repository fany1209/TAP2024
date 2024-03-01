package com.example.demo.vistas;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CuadroMagico extends Stage {
    private GridPane gridPane;

    public CuadroMagico() {
        crearUI();
        Scene scene = new Scene(gridPane, 200, 200);
        this.setTitle("Cuadro Mágico");
        this.setScene(scene);
        this.show();
    }

    private void crearUI() {
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        int[][] cuadroMagico = generarCuadroMagico();

        for (int i = 0; i < cuadroMagico.length; i++) {
            for (int j = 0; j < cuadroMagico[i].length; j++) {
                Label label = new Label(String.valueOf(cuadroMagico[i][j]));
                gridPane.add(label, j, i);
            }
        }
    }

    private int[][] generarCuadroMagico() {
        int[][] cuadroMagico = new int[3][3];
        int num = 1;
        int row = 2;
        int col = 1;

        for (int i = 0; i < 9; i++) {
            cuadroMagico[row][col] = num++;
            row = (row + 1) % 3;
            col = (col + 1) % 3;
            if (cuadroMagico[row][col] != 0) {
                row = (row + 2) % 3;
                col = (col + 1) % 3;
            }
        }

        return cuadroMagico;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
