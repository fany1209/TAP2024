
        package com.example.demo.vistas;

import java.util.*;

import javafx.animation.Animation;
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
import javafx.animation.AnimationTimer;

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
    private boolean turnoJugador2 = false;
    private Button primeraCartaVolteada;
    private Button segundaCartaVolteada;
    private Timeline timeline; // Timeline para el timer
    private int tiempoRestanteTurno;
    private final int TIEMPO_LIMITE_TURNO = 10; // Tiempo en segundos para el turno de cada jugador
    private Timer timer;
    private boolean temporizadorReiniciado = false;


    public Memorama() {
        CrearUI();
        Puntajes(0, 0);

    }

    private void CrearUI() {
        VBox vbox1 = new VBox(10);
        vbox1.setId("v1");
        Button revolverButton = new Button("Revolver");
        paresTextField = new TextField();
        timerLabel = new Label("Tiempo restante: " + tiempoRestanteTurno + " segundos");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        Label imparesLabel = new Label("Pares");
        gridPane = new GridPane();
        grid.setHgap(10);

        grid.getChildren().add(timerLabel);

        GridPane.setConstraints(timerLabel, 1, 0);

        revolverButton.setOnAction(event -> revolver());

        vbox1.getChildren().addAll(revolverButton, new HBox(10, imparesLabel, paresTextField), gridPane, grid);

        VBox vbox2 = new VBox(10);
        HBox jugador1Box = new HBox(10);
        HBox jugador2Box = new HBox(10);
        jugador1Label = new Label("Jugador 1");
        jugador2Label = new Label("Jugador 2");
        puntajeJugador1 = new Label("Puntaje: 0");
        puntajeJugador2 = new Label("Puntaje: 0");

        jugador1Box.getChildren().addAll(jugador1Label, puntajeJugador1);
        jugador2Box.getChildren().addAll(jugador2Label, puntajeJugador2);

        vbox2.getChildren().addAll(jugador1Box, jugador2Box);

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

        AnimationTimer animationTimer = new AnimationTimer() {
            long startTime = System.currentTimeMillis();
            long lastUpdate = startTime;
            long secondsPassed = 0;

            @Override
            public void handle(long now) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastUpdate >= 1000) { // Actualizar cada segundo
                    lastUpdate = currentTime;
                    secondsPassed = (currentTime - startTime) / 1000;
                    tiempoRestanteTurno = (int) (TIEMPO_LIMITE_TURNO - secondsPassed);
                    if (tiempoRestanteTurno >= 0) {
                        timerLabel.setText("Tiempo restante: " + tiempoRestanteTurno + " segundos");
                    } else {
                        // El tiempo del turno ha terminado, cambiar de turno
                        turnos();
                    }
                }
            }
        };
        animationTimer.start();

        // Establecer la bandera de reinicio del temporizador como false al iniciar el temporizador
        temporizadorReiniciado = false;
    }



    private void turnos() {
        if (timeline != null) {
            timeline.stop();
        }

        turnoJugador1 = !turnoJugador1;
        ColoresTurno();
        tiempoRestanteTurno = TIEMPO_LIMITE_TURNO;

        if (timeline == null || timeline.getStatus() != Animation.Status.RUNNING) {
            iniciarTimer();

        }
    }





    private void desactiva() {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button) {
                ((Button) node).setDisable(true);
            }
        }
    }

    private void activa() {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button) {
                ((Button) node).setDisable(false);
            }
        }
    }


    private void revolver() {
        try {
            // Detener el temporizador actual si existe
            if (timeline != null) {
                timeline.stop();
            }

            int cantidadPares = Integer.parseInt(paresTextField.getText());
            if (cantidadPares < 3 || cantidadPares > 15) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error en la cantidad de pares");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, introduce un número de pares entre 3 y 15.");
                alert.showAndWait();
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
            turnoJugador2 = false;
            ColoresTurno();

            activa();

            iniciarTimer();


        } catch (NumberFormatException e) {
            // Mostrar mensaje de error si el texto no es un número válido
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de formato");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, introduce un número válido de pares.");
            alert.showAndWait();
        }
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
        List<String> imagenes = new ArrayList<>();
        List<String> imagenesSeleccionadas = new ArrayList<>();

        imagenes.add("nezuco.jpg");
        imagenes.add("ren.jpg");
        imagenes.add("tanji.jpg");
        imagenes.add("-1.jpg");
        imagenes.add("-2.jpg");
        imagenes.add("-3.jpg");
        imagenes.add("-4.jpg");
        imagenes.add("-5.jpg");
        imagenes.add("-6.jpg");
        imagenes.add("amor.jpg");
        imagenes.add("ino.jpg");
        imagenes.add("logods.jpg");
        imagenes.add("rayo.jpg");
        imagenes.add("sol.jpg");
        imagenes.add("to.jpg");

        Collections.shuffle(imagenes);
        for (int i = 0; i < cantidadPares; i++) {
            imagenesSeleccionadas.add(imagenes.get(i));
            imagenesSeleccionadas.add(imagenes.get(i));
        }
        return imagenesSeleccionadas;
    }
    private void colocarImagenes(List<String> imagenes) {
        Button[][] arBtnCartas = new Button[5][6];
        Collections.shuffle(imagenes);
        int cont = 0;
        for (String imagen : imagenes) {
            int posx;
            int posy;
            do {
                posx = (int) (Math.random() * 5);
                posy = (int) (Math.random() * 6);
            } while (arBtnCartas[posx][posy] != null);

            Button boton = new Button();
            Image imagenFrontal = new Image(getClass().getResourceAsStream("/imagenes/" + imagen));
            Image imagenTrasera = new Image(getClass().getResourceAsStream("/imagenes/back.jpg"));

            ImageView imageView = new ImageView(imagenTrasera);
            imageView.setFitWidth(100);
            imageView.setFitHeight(150);

            boton.setGraphic(imageView);
            boton.setPrefSize(100, 150);

            // Establecer opacidad de la imagen y el botón
            imageView.setOpacity(1);
            boton.setOpacity(1);
            final int finalPosx = posx;
            final int finalPosy = posy;


            boton.setOnAction(event -> {
                Button cartaSeleccionada = (Button) event.getSource();
                ImageView imageViewCartaSeleccionada = (ImageView) cartaSeleccionada.getGraphic();
                Image imagenActual = imageViewCartaSeleccionada.getImage();
                Image imagenParaMostrar = imagenActual.equals(imagenTrasera) ? new Image(getClass().getResourceAsStream("/imagenes/" + imagen)) : imagenTrasera;

                imageViewCartaSeleccionada.setImage(imagenParaMostrar);

                if (!primeraCartaRevelada) {
                    primeraCartaRevelada = true;
                    imagenPrimeraCarta = imagen;
                    primeraCartaVolteada = cartaSeleccionada;
                    cartaSeleccionada.setDisable(true);
                } else {
                    segundaCartaVolteada = cartaSeleccionada;
                    segundaCartaVolteada.setDisable(true); //
                    if (imagen.equals(imagenPrimeraCarta)) {
                        if (turnoJugador1) {
                            paresEncontradosJugador1++;
                        } else {
                            paresEncontradosJugador2++;
                        }
                        Puntajes(paresEncontradosJugador1, paresEncontradosJugador2);
                        end();
                        primeraCartaRevelada = false;
                    } else {
                        // No forman un par, programar para voltearlas de nuevo después de un breve retraso
                        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), evt -> {
                            ImageView primeraImageView = (ImageView) primeraCartaVolteada.getGraphic();
                            ImageView segundaImageView = (ImageView) segundaCartaVolteada.getGraphic();
                            primeraImageView.setImage(imagenTrasera);
                            segundaImageView.setImage(imagenTrasera);
                            primeraCartaVolteada.setDisable(false);
                            segundaCartaVolteada.setDisable(false);
                            turnos();
                        }));
                        timeline.play();
                        primeraCartaRevelada = false;
                        desactiva();
                        timeline.setOnFinished(e -> activa());
                    }
                }
            });
            gridPane.add(boton, posy, posx);
            arBtnCartas[posx][posy] = boton;
            cont++;
            if (cont == imagenes.size()) {
                break;
            }
        }
    }






    private void Puntajes(int puntajeJugador1, int puntajeJugador2) {
        this.puntajeJugador1.setText("Puntaje: " + puntajeJugador1);
        this.puntajeJugador2.setText("Puntaje: " + puntajeJugador2);
    }
    private void end() {
        Platform.runLater(() -> {
            int totalPares = Integer.parseInt(paresTextField.getText());
            int paresTotales = totalPares;

            if (paresEncontradosJugador1 + paresEncontradosJugador2 == paresTotales) {
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
                iniciarTimer();

                if (timeline != null) {
                    timeline.stop();
                }

            } else if (tiempoRestanteTurno <= 0) {
                turnos();
            }
        });
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}