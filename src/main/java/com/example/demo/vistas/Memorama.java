package com.example.demo.vistas;

import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Memorama extends Stage {

    private Label puntajeJugador1;
    private Label puntajeJugador2;
    private Label jugador1Label;
    private Label jugador2Label;
    private Label timerLabel;

    private GridPane gridPane;
    private TextField paresTextField;
    private Image imagenTrasera;
    private int paresEncontradosJugador1;
    private int paresEncontradosJugador2;
    private String imagenPrimeraCarta;
    private boolean primeraCartaRevelada;
    private boolean turnoJugador1 = true;
    private Button primeraCartaVolteada;
    private Button segundaCartaVolteada;
    private Timeline timeline; // Timeline para el timer
    private int tiempoRestanteTurno;
    private final int TIEMPO_LIMITE_TURNO = 10; // Tiempo en segundos para el turno de cada jugador

    public Memorama() {
        CrearUI();
        Puntajes(0, 0);
    }

    private void CrearUI() {
        VBox vbox1 = new VBox(10);
        vbox1.setId("v1");
        Button revolverButton = new Button("Revolver");
        paresTextField = new TextField();
        gridPane = new GridPane();
        gridPane.setHgap(10);

        revolverButton.setOnAction(event -> revolver());

        vbox1.getChildren().addAll(revolverButton, new HBox(10, new Label("Pares"), paresTextField), gridPane);

        VBox vbox2 = new VBox(10);
        HBox jugador1Box = new HBox(10);
        HBox jugador2Box = new HBox(10);
        jugador1Label = new Label("Jugador 1");
        jugador2Label = new Label("Jugador 2");
        puntajeJugador1 = new Label("Puntaje: 0");
        puntajeJugador2 = new Label("Puntaje: 0");
        timerLabel = new Label("Tiempo restante: " + TIEMPO_LIMITE_TURNO + " segundos");

        jugador1Box.getChildren().addAll(jugador1Label, puntajeJugador1);
        jugador2Box.getChildren().addAll(jugador2Label, puntajeJugador2);

        vbox2.getChildren().addAll(jugador1Box, jugador2Box, timerLabel);

        HBox root = new HBox(50);
        root.getChildren().addAll(vbox1, vbox2);
        root.setId("ARRIBA");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/estilos/memorama.css").toString());
        this.setScene(scene);
        this.setTitle("Memorama");
        this.show();

        imagenTrasera = new Image(getClass().getResourceAsStream("/imagenes/back.jpg"));
    }

    private void iniciarTimer() {
        if (timeline != null) {
            timeline.stop();
        }

        tiempoRestanteTurno = TIEMPO_LIMITE_TURNO;
        timerLabel.setText("Tiempo restante: " + tiempoRestanteTurno + " segundos");

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), evt -> {
            tiempoRestanteTurno--;
            timerLabel.setText("Tiempo restante: " + tiempoRestanteTurno + " segundos");

            if (tiempoRestanteTurno <= 0) {
                cambiarTurno();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void cambiarTurno() {
        if (timeline != null) {
            timeline.stop();
        }

        turnoJugador1 = !turnoJugador1;
        ColoresTurno();

        // Restaurar opacidad y habilitar todas las cartas no emparejadas
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button && !node.isDisabled()) {
                node.setDisable(false);
                node.setOpacity(1.0);  // Restaurar la opacidad
            }
        }

        iniciarTimer();
    }


    private void revolver() {
        try {
            if (timeline != null) {
                timeline.stop();
            }

            int cantidadPares = Integer.parseInt(paresTextField.getText());
            if (cantidadPares < 3 || cantidadPares > 15) {
                mostrarError("Por favor, introduce un número de pares entre 3 y 15.");
                return;
            }

            paresEncontradosJugador1 = 0;
            paresEncontradosJugador2 = 0;
            Puntajes(paresEncontradosJugador1, paresEncontradosJugador2);

            limpiarGrid();

            List<String> imagenes = generarImagenes(cantidadPares);
            Collections.shuffle(imagenes);
            colocarImagenes(imagenes);

            turnoJugador1 = true;
            ColoresTurno();

            iniciarTimer();

        } catch (NumberFormatException e) {
            mostrarError("Por favor, introduce un número válido de pares.");
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void ColoresTurno() {
        if (turnoJugador1) {
            jugador1Label.setTextFill(Color.GREEN);
            jugador2Label.setTextFill(Color.RED);
        } else {
            jugador1Label.setTextFill(Color.RED);
            jugador2Label.setTextFill(Color.GREEN);
        }
    }

    private void limpiarGrid() {
        gridPane.getChildren().clear();
    }

    private List<String> generarImagenes(int cantidadPares) {
        List<String> imagenes = Arrays.asList(
                "nezuco.jpg", "ren.jpg", "tanji.jpg", "-1.jpg", "-2.jpg", "-3.jpg",
                "-4.jpg", "-5.jpg", "-6.jpg", "amor.jpg", "ino.jpg", "logods.jpg",
                "rayo.jpg", "sol.jpg", "to.jpg"
        );

        Collections.shuffle(imagenes);
        List<String> imagenesSeleccionadas = new ArrayList<>();
        for (int i = 0; i < cantidadPares; i++) {
            imagenesSeleccionadas.add(imagenes.get(i));
            imagenesSeleccionadas.add(imagenes.get(i));
        }
        return imagenesSeleccionadas;
    }

    private void colocarImagenes(List<String> imagenes) {
        // Crear una lista de todas las posiciones posibles en el grid
        List<int[]> posiciones = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                posiciones.add(new int[]{i, j});
            }
        }

        Collections.shuffle(imagenes); // Barajar las imágenes
        Collections.shuffle(posiciones); // Barajar las posiciones

        for (int i = 0; i < imagenes.size(); i++) {
            String imagen = imagenes.get(i);
            int[] posicion = posiciones.get(i); // Obtener una posición aleatoria

            Button boton = new Button();
            Image imagenFrontal = new Image(getClass().getResourceAsStream("/imagenes/" + imagen));
            Image imagenTrasera = new Image(getClass().getResourceAsStream("/imagenes/back.jpg"));

            ImageView imageView = new ImageView(imagenTrasera);
            imageView.setFitWidth(100);
            imageView.setFitHeight(150);
            imageView.setOpacity(1.0);  // Asegurar opacidad inicial

            boton.setGraphic(imageView);
            boton.setPrefSize(100, 150);

            boton.setOnAction(event -> manejarClickCarta(boton, imagenFrontal, imagen));

            gridPane.add(boton, posicion[1], posicion[0]); // Agregar el botón en la posición aleatoria
        }
    }

    private void manejarClickCarta(Button cartaSeleccionada, Image imagenFrontal, String imagen) {
        if (cartaSeleccionada.isDisabled()) {
            return;
        }

        ImageView imageViewCartaSeleccionada = (ImageView) cartaSeleccionada.getGraphic();
        imageViewCartaSeleccionada.setImage(imagenFrontal);
        imageViewCartaSeleccionada.setOpacity(1.0);  // Asegurar que la imagen es completamente visible

        if (!primeraCartaRevelada) {
            primeraCartaRevelada = true;
            imagenPrimeraCarta = imagen;
            primeraCartaVolteada = cartaSeleccionada;
            cartaSeleccionada.setDisable(true);
        } else {
            segundaCartaVolteada = cartaSeleccionada;

            if (imagen.equals(imagenPrimeraCarta)) {
                segundaCartaVolteada.setDisable(true);
                if (turnoJugador1) {
                    paresEncontradosJugador1++;
                } else {
                    paresEncontradosJugador2++;
                }
                Puntajes(paresEncontradosJugador1, paresEncontradosJugador2);
                primeraCartaVolteada.setDisable(true); // Deshabilitar permanentemente
                segundaCartaVolteada.setDisable(true); // Deshabilitar permanentemente
                primeraCartaRevelada = false;
                verificarFinJuego();
                iniciarTimer(); // Reiniciar el temporizador
            } else {
                segundaCartaVolteada.setDisable(true);
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), evt -> {
                    ((ImageView) primeraCartaVolteada.getGraphic()).setImage(imagenTrasera);
                    ((ImageView) segundaCartaVolteada.getGraphic()).setImage(imagenTrasera);
                    primeraCartaVolteada.setDisable(false);
                    segundaCartaVolteada.setDisable(false);
                }));

                timeline.setOnFinished(e -> {
                    primeraCartaRevelada = false;
                    cambiarTurno();
                });
                timeline.play();
            }
        }
    }


    private void Puntajes(int puntajeJugador1, int puntajeJugador2) {
        this.puntajeJugador1.setText("Puntaje: " + puntajeJugador1);
        this.puntajeJugador2.setText("Puntaje: " + puntajeJugador2);
    }

    private void verificarFinJuego() {
        int totalPares = Integer.parseInt(paresTextField.getText());
        if (paresEncontradosJugador1 + paresEncontradosJugador2 == totalPares) {
            String mensajeGanador;
            if (paresEncontradosJugador1 > paresEncontradosJugador2) {
                mensajeGanador = "¡Jugador 1 ha ganado!";
            } else if (paresEncontradosJugador2 > paresEncontradosJugador1) {
                mensajeGanador = "¡Jugador 2 ha ganado!";
            } else {
                mensajeGanador = "¡Empate!";
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fin del juego");
            alert.setHeaderText(null);
            alert.setContentText(mensajeGanador);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();

            reiniciarJuego();
        } else {
            iniciarTimer(); // Reiniciar el temporizador cuando se encuentra un par
        }
    }


    private void reiniciarJuego() {
        paresEncontradosJugador1 = 0;
        paresEncontradosJugador2 = 0;
        Puntajes(0, 0);
        limpiarGrid();
        timeline.stop();
        timerLabel.setText("Tiempo restante: " + TIEMPO_LIMITE_TURNO + " segundos");
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
