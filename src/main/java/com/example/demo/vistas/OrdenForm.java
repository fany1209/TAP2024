package com.example.demo.vistas;

import com.example.demo.modelos.OrdenesDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrdenForm extends Stage {
    private TableView<OrdenesDAO> tbvOrdenes;
    private OrdenesDAO orden;
    private String[] prompts = {"ID Empleado", "Fecha", "ID Mesa"};
    private Scene scene;
    private TextField[] txtFields = new TextField[4];
    private Button btnSave;
    private Button btnDelete;
    private VBox vBox;

    public OrdenForm(TableView<OrdenesDAO> tbvOrdenes, OrdenesDAO orden) {
        this.tbvOrdenes = tbvOrdenes;
        this.orden = (orden == null) ? new OrdenesDAO() : orden;
        createUI();
        this.setTitle("Agregar/Editar Orden");
        this.setScene(scene);
        this.show();
    }

    private void createUI() {
        vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        for (int i = 0; i < txtFields.length; i++) {
            txtFields[i] = new TextField();
            txtFields[i].setPromptText(prompts[i]);
            vBox.getChildren().add(txtFields[i]);
        }

        fillForm();

        HBox hboxButtons = new HBox();
        hboxButtons.setSpacing(10);
        hboxButtons.setAlignment(Pos.CENTER);

        btnSave = new Button("Guardar");
        btnSave.setOnAction(event -> saveOrder());

        btnDelete = new Button("Eliminar");
        btnDelete.setOnAction(event -> deleteOrder());

        hboxButtons.getChildren().addAll(btnSave, btnDelete);
        vBox.getChildren().addAll(hboxButtons);

        scene = new Scene(vBox, 400, 250);
    }

    private void fillForm() {
        txtFields[0].setText(String.valueOf(orden.getIdEmpleado()));
        txtFields[1].setText(orden.getFecha());
        txtFields[2].setText(orden.getObservaciones());
        txtFields[3].setText(String.valueOf(orden.getIdMesa()));
    }

    private void saveOrder() {
        orden.setIdEmpleado(Integer.parseInt(txtFields[0].getText()));
        orden.setFecha(txtFields[1].getText());
        orden.setObservaciones(txtFields[2].getText());
        orden.setIdMesa(Integer.parseInt(txtFields[3].getText()));

        if (orden.getIdOrden() > 0)
            orden.ACTUALIZAR();
        else
            orden.INSERTAR();

        tbvOrdenes.setItems(orden.CONSULTAR());
        tbvOrdenes.refresh();

        for (TextField textField : txtFields) {
            textField.clear();
        }

        this.close();
    }

    private void deleteOrder() {
        if (orden.getIdOrden() > 0) {
            orden.ELIMINAR();
            tbvOrdenes.setItems(orden.CONSULTAR());
            tbvOrdenes.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Eliminar Orden");
            alert.setHeaderText(null);
            alert.setContentText("No se puede eliminar una orden que aún no ha sido guardada.");
            alert.showAndWait();
        }

        this.close();
    }
}
