package com.example.demo.componentes;

import com.example.demo.modelos.PromocionesDAO;
import com.example.demo.vistas.PromocionesForm;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ButtonCell3 extends TableCell<PromocionesDAO, String> {
    private Button button;
    private int opc;
    private PromocionesDAO objPromocion;

    public ButtonCell3(int opc) {
        this.opc = opc;
        String buttonText = (opc == 1) ? "Editar" : "Eliminar";
        button = new Button(buttonText);
        button.setOnAction(event -> handleButtonAction(opc));
    }

    private void handleButtonAction(int opc) {
        TableView<PromocionesDAO> tbvPromociones = ButtonCell3.this.getTableView();
        objPromocion = tbvPromociones.getItems().get(ButtonCell3.this.getIndex());
        if (opc == 1) {
            new PromocionesForm(tbvPromociones, objPromocion);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Eliminar Promoción");
            alert.setContentText("¿Estás seguro de eliminar la promoción: " + objPromocion.getNombre() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                objPromocion.ELIMINAR();
                tbvPromociones.setItems(objPromocion.CONSULTAR());
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
