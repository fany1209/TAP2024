package com.example.demo.vistas;

import com.example.demo.componentes.ButtonCell2;
import com.example.demo.modelos.CategoriasDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class CategoriasTaqueria extends Stage {
    private TableView<CategoriasDAO> tbvCategorias;

    public CategoriasTaqueria() {
        CrearUI();
        this.setTitle("Categorías de Taqueria Los Inges");
    }

    public TableView<CategoriasDAO> getTableView() {
        return tbvCategorias;
    }

    private void CrearUI() {
        ImageView imvEmp = new ImageView(
                getClass().getResource("/imagenes/cat.jpg").toString()
        );
        tbvCategorias = new TableView<>();

        imvEmp.setFitHeight(50);
        imvEmp.setFitWidth(50);
        Button btnAgregarCategoria = new Button();
        btnAgregarCategoria.setOnAction(event -> new CategoriasForm(tbvCategorias, null));
        btnAgregarCategoria.setPrefSize(30, 30);
        btnAgregarCategoria.setGraphic(imvEmp);

        CrearTabla();

        BorderPane bpnPrincipal = new BorderPane();
        bpnPrincipal.setTop(btnAgregarCategoria);
        bpnPrincipal.setCenter(tbvCategorias);

        Panel pnlPrincipal = new Panel("Categorías de Taquería");
        pnlPrincipal.getStyleClass().add("panel-info");
        pnlPrincipal.setBody(bpnPrincipal);

        Scene escena = new Scene(pnlPrincipal, 600, 400); // Ajusta el tamaño según sea necesario
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        this.setScene(escena);
        this.show();
    }

    private void CrearTabla() {
        CategoriasDAO objCategoria = new CategoriasDAO();

        TableColumn<CategoriasDAO, Integer> tbcIdCategoria = new TableColumn<>("ID");
        tbcIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));

        TableColumn<CategoriasDAO, String> tbcNombreCategoria = new TableColumn<>("Nombre");
        tbcNombreCategoria.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<CategoriasDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<CategoriasDAO, String>, TableCell<CategoriasDAO, String>>() {
                    @Override
                    public TableCell<CategoriasDAO, String> call(TableColumn<CategoriasDAO, String> param) {
                        return new ButtonCell2(1);
                    }
                }
        );

        TableColumn<CategoriasDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<CategoriasDAO, String>, TableCell<CategoriasDAO, String>>() {
                    @Override
                    public TableCell<CategoriasDAO, String> call(TableColumn<CategoriasDAO, String> param) {
                        return new ButtonCell2(2);
                    }
                }
        );

        tbvCategorias.getColumns().addAll(tbcIdCategoria, tbcNombreCategoria, tbcEditar, tbcEliminar);
        tbvCategorias.setItems(objCategoria.CONSULTAR());
    }
}
