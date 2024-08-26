package org.om2324.heroesmarvel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MarvelController implements Initializable {
    @FXML
    private TabPane pantallaBuscar;
    @FXML
    private AnchorPane pantallaAnadir;
    @FXML
    private AnchorPane pantallaEditar;
    @FXML
    private AnchorPane pantallaBorrar;
    @FXML
    private TextField fieldBuscarId;
    @FXML
    private TextField fieldBuscarNombre;
    @FXML
    private TextField fieldBuscarAlias;
    @FXML
    private TextField fieldAnadirAlias;
    @FXML
    private TextField fieldAnadirNombre;
    @FXML
    private TextField fieldAnadirPoder;
    @FXML
    private TextField fieldEditarId;
    @FXML
    private TextField fieldEditarAlias;
    @FXML
    private TextField fieldEditarNombre;
    @FXML
    private TextField fieldEditarPoder;
    @FXML
    private TextField fieldBorrarId;
    @FXML
    private Label lblBuscarIdResult;
    @FXML
    private Label lblBuscarResult;
    @FXML
    private ListView<HeroeMarvel> listView;


    @FXML
    protected void onClickBtnBuscar(){
        paneVisible("buscar");
    }
    @FXML
    protected void onClickBtnAnadir(){
        paneVisible("añadir");
    }
    @FXML
    protected void onClickBtnEditar(){
        paneVisible("editar");
    }
    @FXML
    protected void onClickBtnBorrar(){
        paneVisible("borrar");
    }

    @FXML
    protected void onClickBuscarId(){
        if(fieldBuscarId.getText().isEmpty()){
            setStyle("red", fieldBuscarId);
        }else{
            try{
                HeroeMarvel heroe = MarvelAPIModelo.GetHeroById(fieldBuscarId.getText());
                lblBuscarIdResult.setText("NOMBRE -> " + heroe.getNombre() + "\nPODER -> " + heroe.getPoder() + "\nALIAS -> " + heroe.getAlias());
            }catch(RuntimeException e){
                messageAlert("Ha habido un error\n" + e.getMessage(), "error", "Error");
            }
        }
    }
    @FXML
    protected void onClickBuscarCoincidencia(){
        if(fieldBuscarAlias.getText().isEmpty()){
            setStyle("red", fieldBuscarAlias);
        }
        if(fieldBuscarNombre.getText().isEmpty()){
            setStyle("red", fieldBuscarNombre);
        }
        else{
            try {
                List<HeroeMarvel> listaHeroes = MarvelAPIModelo.GetHeroByNombreAlias(fieldBuscarNombre.getText(), fieldBuscarAlias.getText());
                StringBuilder result = new StringBuilder();
                for (HeroeMarvel heroe : listaHeroes) {
                    result.append("NOMBRE -> ")
                            .append(heroe.getNombre())
                            .append("\nPODER -> ")
                            .append(heroe.getPoder())
                            .append("\nALIAS -> ")
                            .append(heroe.getAlias());
                }
                lblBuscarResult.setText(result.toString());
            }catch(RuntimeException e){
                messageAlert("Ha habido un error\n" + e.getMessage(), "error", "Error");
            }
        }
    }
    @FXML
    protected void onClickAnadir(){
        if(fieldAnadirAlias.getText().isEmpty()){
            setStyle("red", fieldAnadirAlias);
        }
        if(fieldAnadirNombre.getText().isEmpty()){
            setStyle("red", fieldAnadirNombre);
        }
        if(fieldAnadirPoder.getText().isEmpty()){
            setStyle("red", fieldAnadirPoder);
        }
        else{
            try {
                HeroeMarvel heroe = new HeroeMarvel(fieldAnadirNombre.getText(), fieldAnadirPoder.getText(), fieldAnadirAlias.getText());
                boolean added = MarvelAPIModelo.PostRequest(heroe);
                if (added) {
                    messageAlert("Nuevo héroe MARVEL se ha añadido correctamente", "info", "Añadido");
                    cargarHeroes();
                    vaciarAnadir();
                }
            }catch(RuntimeException e){
                messageAlert("Ha habido un error\n" + e.getMessage(), "error", "Error");
            }
        }
    }
    @FXML
    protected void onClickEditar(){
        if(fieldEditarId.getText().isEmpty()){
            setStyle("red", fieldEditarId);
        }
        if(fieldEditarAlias.getText().isEmpty()){
            setStyle("red", fieldEditarAlias);
        }
        if(fieldEditarNombre.getText().isEmpty()){
            setStyle("red", fieldEditarNombre);
        }
        if(fieldEditarPoder.getText().isEmpty()){
            setStyle("red", fieldEditarPoder);
        }
        else{
            try{
                HeroeMarvel heroe = new HeroeMarvel(fieldEditarNombre.getText(), fieldEditarPoder.getText(), fieldEditarAlias.getText());
                boolean edited = MarvelAPIModelo.PutRequest(fieldEditarId.getText(), heroe);
                if(edited){
                    messageAlert("Héroe MARVEL se ha editado correctamente", "info", "Editado");
                    cargarHeroes();
                    vaciarEditar();
                }
            }catch(RuntimeException e){
                messageAlert("Ha habido un error\n" + e.getMessage(), "error", "Error");
            }
        }
    }
    @FXML
    protected void onClickBorrar(){
        if(fieldBorrarId.getText().isEmpty()){
            setStyle("red", fieldBorrarId);
        }
        else{
            try{
                boolean deleted = MarvelAPIModelo.DeleteRequest(fieldBorrarId.getText());
                if(deleted){
                    messageAlert("Héroe MARVEL se ha borrado correctamente", "info", "Borrado");
                    cargarHeroes();
                    vaciarBorrar();
                }
            }catch(RuntimeException e){
                messageAlert("Ha habido un error\n" + e.getMessage(), "error", "Error");
            }
        }
    }
    //region ONTYPED
    @FXML
    protected void onTypedBuscarId(){
        fieldBuscarId.setStyle("");
    }
    @FXML
    protected void onTypedBuscarNombre(){
        fieldBuscarNombre.setStyle("");
    }
    @FXML
    protected void onTypedBuscarAlias(){
        fieldBuscarAlias.setStyle("");
    }
    @FXML
    protected void onTypedAnadirNombre(){
        fieldAnadirNombre.setStyle("");
    }
    @FXML
    protected void onTypedAnadirPoder(){
        fieldAnadirPoder.setStyle("");
    }
    @FXML
    protected void onTypedAnadirAlias(){
        fieldAnadirAlias.setStyle("");
    }
    @FXML
    protected void onTypedAnadirID(){
        fieldAnadirAlias.setStyle("");
    }
    @FXML
    protected void onTypedEditarNombre(){
        fieldEditarNombre.setStyle("");
    }
    @FXML
    protected void onTypedEditarAlias(){
        fieldEditarAlias.setStyle("");
    }
    @FXML
    protected void onTypedEditarPoder(){
        fieldEditarPoder.setStyle("");
    }
    @FXML
    protected void onTypedBorrarId(){
        fieldBorrarId.setStyle("");
    }
    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarHeroes();
    }
    protected void cargarHeroes(){
        List<HeroeMarvel>  listaHeroes = MarvelAPIModelo.GetRequest();
        ObservableList<HeroeMarvel> observableList = FXCollections.observableArrayList(listaHeroes);
        listView.setItems(observableList);
    }

    protected void paneVisible(String pane){
        setVisibleNode(false, pantallaAnadir, pantallaBorrar, pantallaBuscar, pantallaEditar);
        switch (pane){
            case "buscar" -> {
                pantallaBuscar.setVisible(true);
                vaciarBuscar();
            }case "añadir" -> {
                pantallaAnadir.setVisible(true);
                vaciarAnadir();
            }case "editar" -> {
                pantallaEditar.setVisible(true);
                vaciarEditar();
            }case "borrar" -> {
                pantallaBorrar.setVisible(true);
                vaciarBorrar();
            }
        }
    }
    protected void setVisibleNode(boolean toDo, Node... nodes){
        for (Node node  : nodes)
            node.setVisible(toDo);
    }
    protected void setStyle(String style, Node... nodes){
        for (Node node : nodes){
            if(style.equals("style"))
                node.setStyle("-fx-scale-x: 1.2; -fx-scale-y: 1.2;");
            else if(style.equals("red"))
                node.setStyle("-fx-border-color: red;");
            else
                node.setStyle("");
        }
    }
    protected void vaciarCampos(TextField...TextFields){
        for (TextField textField : TextFields)
            textField.setText("");
    }

    protected void vaciarBuscar(){
        vaciarCampos(fieldBuscarId, fieldBuscarAlias, fieldBuscarNombre);
        lblBuscarIdResult.setText("");
        lblBuscarResult.setText("");
        setStyle("", fieldBuscarId, fieldBuscarAlias, fieldBuscarNombre);
    }
    protected void vaciarAnadir(){
        vaciarCampos(fieldAnadirAlias, fieldAnadirNombre, fieldAnadirPoder);
        setStyle("", fieldAnadirAlias, fieldAnadirNombre, fieldAnadirPoder);
    }
    protected void vaciarEditar(){
        vaciarCampos(fieldEditarNombre, fieldEditarAlias, fieldEditarPoder, fieldEditarId);
        setStyle("", fieldEditarNombre, fieldEditarAlias, fieldEditarPoder, fieldEditarId);
    }
    protected void vaciarBorrar(){
        vaciarCampos(fieldBorrarId);
        setStyle("", fieldBorrarId);
    }
    protected void messageAlert(String message, String type, String header){
        Alert alert;
        if(!message.isEmpty()) {
            if (type.equals("error")) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(header);
                alert.setContentText(message);
                alert.showAndWait();

                System.out.println(message);
            } else if (type.equals("info")) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(header);
                alert.setContentText(message);
                alert.showAndWait();
            }
        }
    }
}