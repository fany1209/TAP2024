package com.example.demo.componentes;

import com.example.demo.modelos.MesasDAO;
import com.example.demo.vistas.MesasForm;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.util.Callback;

import java.util.Optional;

public class ButtonCell6 extends TableCell<MesasDAO, String> {
    private final Button btnCelda;
    private final int opc;
    private MesasDAO objMesa;

    public ButtonCell6(int opc) {
        this.opc = opc;
        String txtButton = (opc == 1) ? "Editar" : "Eliminar";
        btnCelda = new Button(txtButton);
        btnCelda.setOnAction(event -> AccionBoton(opc));
    }

    private void AccionBoton(int opc) {
        TableView<MesasDAO> tbvMesas = ButtonCell6.this.getTableView();
        objMesa = tbvMesas.getItems().get(ButtonCell6.this.getIndex());

        if (opc == 1) {
            // Editar
            new MesasForm(tbvMesas, objMesa);
        } else {
            // Eliminar
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Eliminar Mesa");
            alert.setContentText("¿Está seguro de que desea eliminar la mesa número " + objMesa.getNumero() + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                objMesa.ELIMINAR();
                tbvMesas.setItems(objMesa.CONSULTAR());
                tbvMesas.refresh();
            }
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setGraphic(btnCelda);
        }
    }
}
