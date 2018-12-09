package ba.unsa.etf.rpr.tutorijal08;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


import java.io.File;

public class Controller {
    private   ObservableList<String> listaFile = FXCollections.observableArrayList();
    private ObjectProperty<String> trenutniFile = new SimpleObjectProperty<>();
    ListView<String> list = new ListView<String>(listaFile);
    TextField Uzorak;

    private  void getFilesRecursive(File pFile)
    {
        if(pFile.isDirectory())return;
        if(pFile==null)return;
        for(File files : pFile.listFiles())
        {
            if(files.isDirectory())
            {
                getFilesRecursive(files);
            }
            else
            {
                if(pFile.getName().equalsIgnoreCase(Uzorak.getText())) list.getItems().add(list.getItems().size(), pFile.getAbsolutePath());
            }
        }
    }
    public void prodjiKrozListu() {
        String putanja = System.getProperty("user.home");
        File f = new File(putanja);
        getFilesRecursive(f);

    }

    public Controller() {
        prodjiKrozListu();
    }
    public void dugmeKliknuto(ActionEvent actionEvent) {
        prodjiKrozListu();
    }

    @FXML
    public void initialize() {
        prodjiKrozListu();


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