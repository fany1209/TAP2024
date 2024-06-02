package com.example.demo.componentes;

import com.example.demo.modelos.ServicioDomicilioDAO;
import com.example.demo.vistas.ServicioDomicilioForm;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ButtonCell4 extends TableCell<ServicioDomicilioDAO, String> {
    private Button button;
    private int opc;
    private ServicioDomicilioDAO objServicio;

    public ButtonCell4(int opc) {
        this.opc = opc;
        String buttonText = (opc == 1) ? "Editar" : "Eliminar";
        button = new Button(buttonText);
        button.setOnAction(event -> handleButtonAction(opc));
    }

    private void handleButtonAction(int opc) {
        TableView<ServicioDomicilioDAO> tbvServicio = ButtonCell4.this.getTableView();
        objServicio = tbvServicio.getItems().get(ButtonCell4.this.getIndex());
        if (opc == 1) {
            new ServicioDomicilioForm(tbvServicio, objServicio);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Eliminar Servicio a Domicilio");
            alert.setContentText("¿Estás seguro de eliminar el servicio a domicilio?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                objServicio.ELIMINAR();
                tbvServicio.setItems(objServicio.CONSULTAR());
            }
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setGraphic(button);
        } else {
            setGraphic(null);
        }
    }
}
