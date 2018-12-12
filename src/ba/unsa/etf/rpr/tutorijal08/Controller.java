package ba.unsa.etf.rpr.tutorijal08;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Controller  {

    private   ObservableList<String> listaFile = FXCollections.observableArrayList();
    NewWindowController noviController;
    public Thread thread;
    public ListView<String> list;
    public TextField Uzorak;
    public Button dugme;
    public Button dugmePrekini;
    boolean prviPut=true;
    public String samoZaPretragu="";
    private boolean trebaPrekinuti=false;

    private  void getFilesRecursive(File pFile){
        if(pFile==null)return;
        if(!pFile.isDirectory())return;
        File[] niz = pFile.listFiles();
        if ( niz == null ) {
            return;
        }
        for(File files : niz)
        {
            if(trebaPrekinuti)return;
           else if(files.isDirectory())
            {
                getFilesRecursive(files);
            }
            else
            {
                if(files.getName().contains(samoZaPretragu)) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            list.getItems().add(list.getItems().size(), files.getAbsolutePath());
                        }
                    });
                }
            }
        }
    }
    public void prodjiKrozListu() {
        String putanja = System.getProperty("user.home");
        File f = new File(putanja);
        getFilesRecursive(f);
    }

    public Controller() {}

    public void ListaKliknuta(MouseEvent actionEvent) {
            if(list.getItems().size()==0)return;
            Parent root = null;
            try {
                Stage myStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("novifxml.fxml"));
                loader.load();
                noviController = loader.getController();
                myStage.setTitle("Novi prozor");
                myStage.setScene(new Scene(loader.getRoot(),  USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                myStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    public void dugmeKliknuto(ActionEvent actionEvent) {

        Runnable runnable = (() -> {
            try {
                Thread.sleep(1000);
                Scene novaScena = dugme.getScene();
                novaScena.setCursor(Cursor.WAIT);
                trebaPrekinuti = false;
                if (prviPut) prviPut = false;
                dugme.setDisable(true);
                dugmePrekini.setDisable(false);
                samoZaPretragu = Uzorak.getText();
                prodjiKrozListu();
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }finally {
                dugme.setDisable(false);
                dugmePrekini.setDisable(true);
                dugme.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        thread =new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
    }
    public void dugmePrekinuto(ActionEvent actionEvent) {
                trebaPrekinuti=true;
                samoZaPretragu = "";
                if(thread.isAlive())thread.interrupt();
    }

    @FXML
    public void initialize() {
        this.list.setItems(this.listaFile);
        dugmePrekini.setDisable(true);
        dugme.setDisable(false);
    }
}