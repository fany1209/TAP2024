package com.example.demo.vistas;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EncuentraLaPareja extends Stage {

    private GridPane gridPane;
    private int paresEncontrados;
    private String imagenPrimeraCarta;
    private boolean primeraCartaRevelada;
    private Button primeraCartaVolteada;
    private Button segundaCartaVolteada;
    private final int TIEMPO_LIMITE_TURNO = 10; // Tiempo en segundos para el turno de cada jugador
    private Label timerLabel;
    private Timeline timeline;



    public EncuentraLaPareja() {
        CrearUI();
        iniciarJuego();
    }



    private void CrearUI() {
        VBox vbox = new VBox(10);
        gridPane = new GridPane();
        timerLabel = new Label("Tiempo restante: " + TIEMPO_LIMITE_TURNO + " segundos");

        vbox.getChildren().addAll(timerLabel, gridPane);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vbox);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 600, 400);
        this.setScene(scene);
        this.setTitle("Encuentra la Pareja");
        this.show();
    }

    private void iniciarJuego() {
        List<String> imagenes = generarImagenes();
        colocarImagenes(imagenes);
        iniciarTimer();
    }

    private List<String> generarImagenes() {
        List<String> imagenes = new ArrayList<>();

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

        return imagenes;
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

            imageView.setOpacity(1);
            boton.setOpacity(1);

            boton.setOnAction(event -> {
                Button cartaSeleccionada = (Button) event.getSource();
                if (cartaSeleccionada.isDisable()) {
                    return;
                }

                ImageView imageViewCartaSeleccionada = (ImageView) cartaSeleccionada.getGraphic();
                Image imagenActual = imageViewCartaSeleccionada.getImage();
                Image imagenParaMostrar = imagenActual.equals(imagenTrasera) ? imagenFrontal : imagenTrasera;

                imageViewCartaSeleccionada.setImage(imagenParaMostrar);

                if (!primeraCartaRevelada) {
                    primeraCartaRevelada = true;
                    imagenPrimeraCarta = imagen;
                    primeraCartaVolteada = cartaSeleccionada;
                    cartaSeleccionada.setDisable(true);
                } else {
                    segundaCartaVolteada = cartaSeleccionada;
                    segundaCartaVolteada.setDisable(true);
                    if (imagen.equals(imagenPrimeraCarta)) {
                        paresEncontrados++;
                        if (paresEncontrados == 15) {
                            terminarJuego();
                        }
                        primeraCartaRevelada = false;
                    } else {
                        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), evt -> {
                            ImageView primeraImageView = (ImageView) primeraCartaVolteada.getGraphic();
                            ImageView segundaImageView = (ImageView) segundaCartaVolteada.getGraphic();
                            primeraImageView.setImage(imagenTrasera);
                            segundaImageView.setImage(imagenTrasera);
                            primeraCartaVolteada.setDisable(false);
                            segundaCartaVolteada.setDisable(false);
                        }));

                        timeline.setOnFinished(e -> {
                            primeraCartaRevelada = false;
                        });
                        timeline.play();
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

    private void iniciarTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), evt -> {
            int tiempoRestante = Integer.parseInt(timerLabel.getText().split(": ")[1]) - 1;
            if (tiempoRestante >= 0) {
                timerLabel.setText("Tiempo restante: " + tiempoRestante + " segundos");
            } else {
                terminarJuego();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void terminarJuego() {
        timeline.stop();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fin del juego");
        alert.setHeaderText(null);
        alert.setContentText("¡Felicidades, has encontrado todas las parejas!");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
        Platform.exit();
    }
}
