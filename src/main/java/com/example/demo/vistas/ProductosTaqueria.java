package com.example.demo.vistas;

import com.example.demo.componentes.ButtonCell2;
import com.example.demo.componentes.ButtonCell5;
import com.example.demo.modelos.ProductosDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class ProductosTaqueria extends Stage {
    private TableView<ProductosDAO> tbvProductos;

    public ProductosTaqueria() {
        CrearUI();
        this.setTitle("Productos de Taqueria Los Inges");
    }

    public TableView<ProductosDAO> getTableView() {
        return tbvProductos;
    }

    private void CrearUI() {
        ImageView imvEmp = new ImageView(
                getClass().getResource("/imagenes/Productos.jpg").toString()
        );
        tbvProductos = new TableView<>();

        imvEmp.setFitHeight(50);
        imvEmp.setFitWidth(50);
        Button btnAgregarProducto = new Button();
        btnAgregarProducto.setOnAction(event -> new ProductosForm(tbvProductos, null));
        btnAgregarProducto.setPrefSize(30, 30);
        btnAgregarProducto.setGraphic(imvEmp);

        CrearTabla();

        BorderPane bpnPrincipal = new BorderPane();
        bpnPrincipal.setTop(btnAgregarProducto);
        bpnPrincipal.setCenter(tbvProductos);

        Panel pnlPrincipal = new Panel("Productos de Taquería");
        pnlPrincipal.getStyleClass().add("panel-info");
        pnlPrincipal.setBody(bpnPrincipal);

        Scene escena = new Scene(pnlPrincipal, 800, 600); // Ajusta el tamaño según sea necesario
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        this.setScene(escena);
        this.show();
    }

    private void CrearTabla() {
        ProductosDAO objProducto = new ProductosDAO();

        TableColumn<ProductosDAO, Integer> tbcIdProducto = new TableColumn<>("ID");
        tbcIdProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));

        TableColumn<ProductosDAO, String> tbcNombreProducto = new TableColumn<>("Nombre");
        tbcNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<ProductosDAO, Double> tbcPrecioProducto = new TableColumn<>("Precio");
        tbcPrecioProducto.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<ProductosDAO, Double> tbcCostoProducto = new TableColumn<>("Costo");
        tbcCostoProducto.setCellValueFactory(new PropertyValueFactory<>("costo"));

        TableColumn<ProductosDAO, Integer> tbcIdCategoria = new TableColumn<>("ID Categoría");
        tbcIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));

        TableColumn<ProductosDAO, Integer> tbcIdPromocion = new TableColumn<>("ID Promoción");
        tbcIdPromocion.setCellValueFactory(new PropertyValueFactory<>("idPromocion"));

        TableColumn<ProductosDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<ProductosDAO, String>, TableCell<ProductosDAO, String>>() {
                    @Override
                    public TableCell<ProductosDAO, String> call(TableColumn<ProductosDAO, String> param) {
                        return new ButtonCell5(1);
                    }
                }
        );

        TableColumn<ProductosDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<ProductosDAO, String>, TableCell<ProductosDAO, String>>() {
                    @Override
                    public TableCell<ProductosDAO, String> call(TableColumn<ProductosDAO, String> param) {
                        return new ButtonCell5(2);
                    }
                }
        );

        tbvProductos.getColumns().addAll(tbcIdProducto, tbcNombreProducto, tbcPrecioProducto, tbcCostoProducto,
                tbcIdCategoria, tbcIdPromocion, tbcEditar, tbcEliminar);
        tbvProductos.setItems(objProducto.CONSULTAR());
    }
}
