package ba.unsa.etf.rpr.tutorijal08;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NewWindowController {

    public TextField postanskiBroj;
    public boolean naFokusu;
    public boolean NaFokusu1;
    public Thread posaljiNit;
    public String postBr = "";
    public String postBr1 = "";
    public String postBr2 = "";

    public boolean validPostBr() {
        postBr = postanskiBroj.getText();
        if (!postBr.contains("postanskiBroj=")) return false;
        String wordToFind = "postanskiBroj=";
        Pattern word = Pattern.compile(wordToFind);
        Matcher match = word.matcher(postBr);
        int spasiPoz=0;
        while (match.find()) {
            spasiPoz = match.end(); //pozicija prvog karaktera postanskog broj
        }
        int nova=spasiPoz;
        while (spasiPoz < postBr.length()) {
            postBr2 += postBr.charAt(spasiPoz);
            spasiPoz++;
        }
        if (postBr2.length() < 5) return false;
        postBr1 = postBr.substring(nova, nova + 5);
        //System.out.println(postBr1);
        if (!postBr2.equals(postBr1) || postBr1.length() < 5) return false;
        if (!(postBr1.charAt(0) == '7' || postBr1.charAt(0) == '8')) return false;
        for (int i = 1; i <= 4; i++) {
            if (!(postBr1.charAt(i) >= '0' && postBr1.charAt(i) <= '9')) return false;
        }
        return true;
    }

    public NewWindowController() {
    }

    @FXML
    public void initialize() {
        postanskiBroj.focusedProperty().addListener((obs, a, b) -> {
            if (!b) {
                naFokusu = validPostBr();
                if(naFokusu)System.out.println("OK");
                else System.out.println("NOT OK");
            }
        });


    }

    public void posalji(ActionEvent actionEvent) {
        Runnable runnable = ()  ->{
                try {
                        Thread.sleep(1000);
                        if(naFokusu)System.out.println("OK");
                        else System.out.println("NOT OK");
                } catch (Exception e) {
                    System.out.println("");
                }
            };

            posaljiNit = new Thread(runnable);
            posaljiNit.setDaemon(true);
            posaljiNit.start();


    }

    public void posalji1(ActionEvent actionEvent) {
    }
}

