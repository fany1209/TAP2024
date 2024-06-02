package com.example.demo.vistas;

import com.example.demo.modelos.MesasDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MesasForm extends Stage {
    private TableView<MesasDAO> tbvMesas;
    private MesasDAO objMesa;
    private String[] arPromts = {"Número de Mesa"};
    private Scene escena;
    private TextField[] arTxtCampos = new TextField[1];
    private Button btnGuardar;
    private Button btnEliminar;
    private VBox vbxPrincipal;

    public MesasForm(TableView<MesasDAO> tbvMesas, MesasDAO objMesa) {
        this.tbvMesas = tbvMesas;
        this.objMesa = (objMesa == null) ? new MesasDAO() : objMesa;
        CrearUI();
        this.setTitle("Agregar/Editar Mesa");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        vbxPrincipal = new VBox();
        vbxPrincipal.setPadding(new Insets(10));
        vbxPrincipal.setSpacing(10);
        vbxPrincipal.setAlignment(Pos.CENTER);

        for (int i = 0; i < arTxtCampos.length; i++) {
            arTxtCampos[i] = new TextField();
            arTxtCampos[i].setPromptText(arPromts[i]);
            vbxPrincipal.getChildren().add(arTxtCampos[i]);
        }

        LlenarForm();

        HBox hboxBotones = new HBox();
        hboxBotones.setSpacing(10);
        hboxBotones.setAlignment(Pos.CENTER);

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> GuardarMesa());

        btnEliminar = new Button("Eliminar");
        btnEliminar.setOnAction(event -> EliminarMesa());

        hboxBotones.getChildren().addAll(btnGuardar, btnEliminar);
        vbxPrincipal.getChildren().addAll(hboxBotones);

        escena = new Scene(vbxPrincipal, 350, 250);
    }

    private void LlenarForm() {
        arTxtCampos[0].setText(String.valueOf(objMesa.getNumero()));
    }

    private void GuardarMesa() {
        objMesa.setNumero(Integer.parseInt(arTxtCampos[0].getText()));

        if (objMesa.getIdMesa() > 0)
            objMesa.ACTUALIZAR();
        else
            objMesa.INSERTAR();

        tbvMesas.setItems(objMesa.CONSULTAR());
        tbvMesas.refresh();

        for (TextField textField : arTxtCampos) {
            textField.clear();
        }

        this.close();
    }

    private void EliminarMesa() {
        if (objMesa.getIdMesa() > 0) {
            objMesa.ELIMINAR();
            tbvMesas.setItems(objMesa.CONSULTAR());
            tbvMesas.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Eliminar Mesa");
            alert.setHeaderText(null);
            alert.setContentText("No se puede eliminar una mesa que aún no ha sido guardada.");
            alert.showAndWait();
        }

        this.close();
    }
}
