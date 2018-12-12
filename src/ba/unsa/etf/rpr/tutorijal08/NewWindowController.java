package ba.unsa.etf.rpr.tutorijal08;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NewWindowController {
    public TextField postanskiBroj;
    public String postBr = "";
    private Thread posaljiNit;

    public boolean validPostBr() throws Exception {
        URL url = new URL("http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj=" + postanskiBroj.getText());
        BufferedReader ulaz = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        String json = "", line = null;
        while ((line = ulaz.readLine()) != null) json = json + line;
        if (json.equals("OK")) return true;
        else return false;
    }

    public NewWindowController() {
    }

    @FXML
    public void initialize() {
        postanskiBroj.focusedProperty().addListener((obs, a, b) -> {
            Runnable runnable = () -> {
                if (!b) {
                    try {
                        Thread.sleep(1000);
                        postBr = postanskiBroj.getText();
                        if (validPostBr()) System.out.println("OK");
                        if (!validPostBr()) System.out.println("NOT OK");
                    } catch (Exception ex) {
                    }
                }
            };
            posaljiNit = new Thread(runnable);
            posaljiNit.start();
        });
    }
}
