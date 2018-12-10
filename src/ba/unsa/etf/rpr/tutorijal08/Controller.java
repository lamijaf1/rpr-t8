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
    private ObjectProperty<String> trenutniFile = new SimpleObjectProperty<>();
    NewWindowController noviController;
    boolean dugmeProvjeri=true;
    public Thread thread;
    public ListView<String> list;
    public TextField Uzorak;
    public Button dugme;
    public Button dugmePrekini;
    public String samoZaPretragu="";
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
                if(files.getName().equalsIgnoreCase(samoZaPretragu)) {
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
       // if(thread!=null || thread.isDaemon())thread.stop();
        dugme.getStyleClass().removeAll("poljeTrazi, poljePrekini");
        Runnable runnable = () -> {
            try{
                Thread.sleep(1000);
                samoZaPretragu=Uzorak.getText();
                prodjiKrozListu();
                dugme.getStyleClass().add("poljeTrazi");

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        };
        thread =new Thread(runnable);
        thread.start();

    }
    public void dugmePrekinuto(ActionEvent actionEvent) {
        //if(thread!=null || thread.isInterrupted())thread.stop();
        Runnable runnable = () -> {
            try {
                Thread.sleep(1000);
                samoZaPretragu = "";
                dugmePrekini.getStyleClass().removeAll("poljePrekini");
                dugmePrekini.getStyleClass().add("poljeTrazi");
                for (String s : list.getItems()) s = "";
            } catch (Exception e) {
            }
        };
        thread =new Thread(runnable);
        thread.start();
       // listaFile.remove(0, listaFile.size());
    }

    @FXML
    public void initialize() {
        dugmePrekini.getStyleClass().add("addBobOk");
        this.list.setItems(this.listaFile);
        Uzorak.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {


            }
        });



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
        Uzorak= uzorak;
    }
}