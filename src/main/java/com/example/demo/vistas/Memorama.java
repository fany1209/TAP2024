package com.example.demo.vistas;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Memorama extends Stage {

    private Label puntajeJugador1;
    private Label puntajeJugador2;
    private GridPane gridPane;

    public Memorama() {
        CrearUI();
        actualizarPuntajes(10, 20);
    }

    private void CrearUI() {
        VBox vbox1 = new VBox(10);
        Button resolverButton = new Button("Resolver");
        TextField textField = new TextField();
        Label timerLabel = new Label("Timer");
        Label imparesLabel = new Label("Impares");
        gridPane = new GridPane();
        revolver();

        Thread timerThread = new Thread(() -> {
            int segundos = 0;
            try {
                while (true) {
                    Thread.sleep(1000);
                    segundos++;
                    final int finalSegundos = segundos;
                    Platform.runLater(() -> timerLabel.setText("Timer: " + finalSegundos + " segundos"));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        timerThread.setDaemon(true);
        timerThread.start();

        vbox1.getChildren().addAll(resolverButton, new HBox(10, imparesLabel, textField), new HBox(10, timerLabel), gridPane);

        VBox vbox2 = new VBox(10);
        HBox jugador1Box = new HBox(10);
        HBox jugador2Box = new HBox(10);
        Label jugador1Label = new Label("Jugador 1");
        Label jugador2Label = new Label("Jugador 2");
        puntajeJugador1 = new Label("Puntaje: 0");
        puntajeJugador2 = new Label("Puntaje: 0");

        jugador1Box.getChildren().addAll(jugador1Label, puntajeJugador1);
        jugador2Box.getChildren().addAll(jugador2Label, puntajeJugador2);

        vbox2.getChildren().addAll(jugador1Box, jugador2Box);

        HBox root = new HBox(50);
        root.getChildren().addAll(vbox1, vbox2);

        Scene scene = new Scene(root, 600, 400);

        this.setScene(scene);
        this.setTitle("Memorama");
        this.setOnCloseRequest(event -> {
            timerThread.interrupt();
            Platform.exit();
        });
        this.show();
    }

    private void revolver() {
        String[] arImagenes = {"nezuco.jpg", "nezuco.jpg", "ren.jpg", "ren.jpg", "tanji.jpg", "tanji.jpg"};
        Button[][] arBtnCartas = new Button[2][3];

        int cont = 0;
        for (String arImagen : arImagenes) {
            int posx;
            int posy;
            do {
                posx = (int) (Math.random() * 2);
                posy = (int) (Math.random() * 3);
            } while (arBtnCartas[posx][posy] != null);

            arBtnCartas[posx][posy] = new Button();
            ImageView imvCarta = new ImageView(getClass().getResource("/imagenes/" + arImagen).toString());
            imvCarta.setFitWidth(100);
            imvCarta.setFitHeight(150);
            arBtnCartas[posx][posy].setGraphic(imvCarta);
            gridPane.add(arBtnCartas[posx][posy], posy, posx);

            cont++;
            if (cont == 6) {
                cont = 0;
            }
        }
    }

    private void actualizarPuntajes(int puntajeJugador1, int puntajeJugador2) {
        this.puntajeJugador1.setText("Puntaje: " + puntajeJugador1);
        this.puntajeJugador2.setText("Puntaje: " + puntajeJugador2);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }


}
