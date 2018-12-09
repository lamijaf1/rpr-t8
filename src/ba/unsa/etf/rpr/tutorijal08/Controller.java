package ba.unsa.etf.rpr.tutorijal08;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


import java.io.File;

public class Controller {
    private   ObservableList<String> listaFile = FXCollections.observableArrayList();
    private ObjectProperty<String> trenutniFile = new SimpleObjectProperty<>();
    public ListView<String> list;

    public TextField Uzorak;
    public Button dugme;
    private  void getFilesRecursive(File pFile)
    {
        if(pFile==null)return;
        if(!pFile.isDirectory())return;
        File[] niz = pFile.listFiles();
        if ( niz == null ) {
            return;
        }
        for(File files : niz)
        {
            if(files.isDirectory())
            {
                getFilesRecursive(files);
            }
            else
            {
                if(files.getName().equalsIgnoreCase(Uzorak.getText())) {
                    list.getItems().add(list.getItems().size(), files.getAbsolutePath());
                }
            }
        }
    }
    public void prodjiKrozListu() {
        String putanja = System.getProperty("user.home");
        File f = new File(putanja);
        getFilesRecursive(f);

    }

    public Controller() {
    }
    public void dugmeKliknuto(ActionEvent actionEvent) {
        prodjiKrozListu();
    }

    @FXML
    public void initialize() {
        this.list.setItems(this.listaFile);


    }


    public ObservableList<String> getListaFile() {
        return listaFile;
    }

    public void setListaFile(ObservableList<String> listaFile) {
        this.listaFile = listaFile;
    }


    public TextField getUzorak() {
        return Uzorak;
    }

    public void setUzorak(TextField uzorak) {
        Uzorak = uzorak;
    }
}