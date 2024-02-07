package com.example.demo.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class cuadroMagico extends Stage {

    private Scene escena;
public cuadroMagico(){
    this.setTitle("cuadro magico");
    this.setScene(new Scene(new Button("da click")));
    this.show();
   }
   private void CrearUI(){
    escena = new Scene(new Button("da click"));
   }
}
