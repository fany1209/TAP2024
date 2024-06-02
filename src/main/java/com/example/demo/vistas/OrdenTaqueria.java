package com.example.demo.vistas;

import com.example.demo.modelos.OrdenesDAO;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrdenTaqueria extends Stage {
    private TableView<OrdenesDAO> tableView;

    public OrdenTaqueria() {
        setTitle("Ordenes");
        tableView = new TableView<>();

        TableColumn<OrdenesDAO, Integer> idOrdenCol = new TableColumn<>("ID Orden");
        idOrdenCol.setCellValueFactory(new PropertyValueFactory<>("idOrden"));

        TableColumn<OrdenesDAO, Integer> idEmpleadoCol = new TableColumn<>("ID Empleado");
        idEmpleadoCol.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));

        TableColumn<OrdenesDAO, String> fechaCol = new TableColumn<>("Fecha");
        fechaCol.setCellValueFactory(new PropertyValueFactory<>("fecha"));


        TableColumn<OrdenesDAO, Integer> idMesaCol = new TableColumn<>("ID Mesa");
        idMesaCol.setCellValueFactory(new PropertyValueFactory<>("idMesa"));

        tableView.getColumns().addAll(idOrdenCol, idEmpleadoCol, fechaCol, idMesaCol);

        VBox root = new VBox(tableView);
        Scene scene = new Scene(root, 600, 400);
        setScene(scene);

        refreshTable();
    }

    public void refreshTable() {
        tableView.getItems().clear();
        tableView.getItems().addAll(new OrdenesDAO().CONSULTAR());
    }
}
