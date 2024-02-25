package com.example.demo.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Calculadora extends Stage {
    private GridPane gridPane;
    private TextField displayField;
    private final Button[][] botones = new Button[4][4];
    private Button botonLimpiar;
    private final char[] etiquetas = {'7', '8', '9', '/', '4', '5', '6', '*', '1', '2', '3', '-', '0', '.', '=', '+'};


    public Calculadora() {
        crearUI();
        Scene scene = new Scene(gridPane, 200, 250);
        scene.getStylesheets().add(getClass().getResource("/estilos/calculadora.css").toString());
        this.setTitle("Calculadora");
        this.setScene(scene);
        this.show();
    }

    private void crearUI() {
        gridPane = new GridPane();
        displayField = new TextField();
        displayField.setEditable(false);
        displayField.setPrefColumnCount(4);
        gridPane.add(displayField, 0, 0, 4, 1);
        botonLimpiar = new Button("C");
        botonLimpiar.setId("botonLimpiar");
        botonLimpiar.setPrefSize(100, 50);
        botonLimpiar.setOnAction((event) -> clearDisplay());
        gridPane.add(botonLimpiar, 0, 5, 2, 1);
        int posicion = 0;
        for (int i = 1; i <= 4; i++) {
            for (int j = 0; j < 4; j++) {
                final char simbolo = etiquetas[posicion];
                botones[i - 1][j] = new Button(String.valueOf(simbolo));
                botones[i - 1][j].setPrefSize(50, 50);
                botones[i - 1][j].setOnAction((event) -> handleButtonClick(String.valueOf(simbolo)));
                // Asignar clase CSS "operador" a los botones que representan operadores matemáticos
                if (esOperador(simbolo)) {
                    botones[i - 1][j].getStyleClass().add("operador");
                }
                gridPane.add(botones[i - 1][j], j, i);
                posicion++;
            }
        }
    }
    private boolean esOperador(char caracter) {
        return caracter == '+' || caracter == '-' || caracter == '*' || caracter == '/';
    }


    private void handleButtonClick(String text) {
        switch (text) {
            case "=":
                evaluateExpression();
                break;
            default:
                appendToDisplay(text);
                break;
        }
    }

    private void evaluateExpression() {
        try {
            String result = String.valueOf(eval(displayField.getText()));
            displayField.setText(result);
        } catch (Exception e) {
            displayField.setText("Error");
        }
    }

    private void clearDisplay() {
        displayField.clear();
    }

    private void appendToDisplay(String text) {
        displayField.appendText(text);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private double eval(String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Carácter inesperado: " + (char) ch);
                return x;
            }


            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm(); // Suma
                    else if (eat('-')) x -= parseTerm(); // Resta
                    else return x;
                }
            }


            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor(); // Multiplicación
                    else if (eat('/')) x /= parseFactor(); // División
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();
                double x;
                int startPos = this.pos;
                if (eat('(')) { // Parentesis
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Carácter inesperado: " + (char) ch);
                }

                return x;
            }
        }.parse();
    }
}
