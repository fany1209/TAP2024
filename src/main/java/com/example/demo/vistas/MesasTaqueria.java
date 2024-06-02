package com.example.demo.vistas;

import com.example.demo.componentes.ButtonCell;
import com.example.demo.componentes.ButtonCell6;
import com.example.demo.modelos.MesasDAO;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class MesasTaqueria extends Stage {
    private Panel pnlPrincipal;
    private BorderPane bpnPrincipal;
    private ToolBar tlbMenu;
    private Scene escena;
    private Button btnAgregarMesa;
    private TableView<MesasDAO> tbvMesas;

    public MesasTaqueria() {
        CrearUI();
        this.setTitle("Taquería Los Inges - Mesas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        ImageView imageView = new ImageView(
                getClass().getResource("/imagenes/mesa.jpg").toString()
        );
        tbvMesas = new TableView<>();

        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        btnAgregarMesa = new Button();
        btnAgregarMesa.setOnAction(event -> new MesasForm(tbvMesas, null));
        btnAgregarMesa.setPrefSize(30, 30);
        btnAgregarMesa.setGraphic(imageView);
        tlbMenu = new ToolBar(btnAgregarMesa);

        CrearTabla();

        bpnPrincipal = new BorderPane();
        bpnPrincipal.setTop(tlbMenu);
        bpnPrincipal.setCenter(tbvMesas);

        pnlPrincipal = new Panel("Mesas de Taquería");
        pnlPrincipal.getStyleClass().add("panel-info");
        pnlPrincipal.setBody(bpnPrincipal);

        escena = new Scene(pnlPrincipal, 600, 400);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }

    private void CrearTabla() {
        MesasDAO objMesa = new MesasDAO();

        TableColumn<MesasDAO, Integer> tbcIdMesa = new TableColumn<>("ID");
        tbcIdMesa.setCellValueFactory(new PropertyValueFactory<>("idMesa"));

        TableColumn<MesasDAO, Integer> tbcNumero = new TableColumn<>("Número");
        tbcNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));

        TableColumn<MesasDAO, String> tbcestado = new TableColumn<>("Estado"); // Columna para el estado
        tbcestado.setCellValueFactory(new PropertyValueFactory<>("estado"));


        TableColumn<MesasDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<MesasDAO, String>, javafx.scene.control.TableCell<MesasDAO, String>>() {
                    @Override
                    public javafx.scene.control.TableCell<MesasDAO, String> call(TableColumn<MesasDAO, String> param) {
                        return new ButtonCell6(1);
                    }
                }
        );

        TableColumn<MesasDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<MesasDAO, String>, javafx.scene.control.TableCell<MesasDAO, String>>() {
                    @Override
                    public javafx.scene.control.TableCell<MesasDAO, String> call(TableColumn<MesasDAO, String> param) {
                        return new ButtonCell6(2);
                    }
                }
        );

        tbvMesas.getColumns().addAll(tbcIdMesa, tbcNumero, tbcestado, tbcEditar, tbcEliminar);
        tbvMesas.setItems(objMesa.CONSULTAR());
    }

}
