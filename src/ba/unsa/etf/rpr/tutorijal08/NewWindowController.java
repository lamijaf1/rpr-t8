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
    public boolean naFokusu;
    public boolean NaFokusu1;
    public Thread posaljiNit;
    public String postBr = "";
    public String postBr1 = "";
    public String postBr2 = "";
    //URL url = new URL("http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj=");

    public boolean validPostBr() throws  Exception {
        URL url = new URL("http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj="+postanskiBroj.getText());
        BufferedReader ulaz = new BufferedReader(new InputStreamReader(url.openStream(),
                StandardCharsets.UTF_8));
        String json = "", line = null;
        while ((line = ulaz.readLine()) != null)
            json = json + line;
        if(json.equals("OK")) return true;
        else return false;

       /* if (!postBr.contains("postanskiBroj=")) return false;
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
        if(nova+5>postBr.length())return false;
        postBr1 = postBr.substring(nova, nova + 5);
        //System.out.println(postBr1);
        if (!postBr2.equals(postBr1) || postBr1.length() < 5) return false;*/
        /*if (!(postBr1.charAt(0) == '7' || postBr1.charAt(0) == '8')) return false;
        for (int i = 1; i <= 4; i++) {
            if (!(postBr1.charAt(i) >= '0' && postBr1.charAt(i) <= '9')) return false;
        }
        postBr2="";
        return true;*/
    }

    public NewWindowController() {}

    @FXML
    public void initialize() {
        postanskiBroj.focusedProperty().addListener((obs, a, b) -> {
            Runnable runnable = ()  ->{
                    if (!b) {
                        try {
                            Thread.sleep(1000);
                            postBr = postanskiBroj.getText();
                            if (validPostBr()) System.out.println("OK");
                            if (!validPostBr()) System.out.println("NOT OK");
                        } catch (Exception ex){}
                    }
            };
            posaljiNit = new Thread(runnable);
            //posaljiNit.setDaemon(true);
            posaljiNit.start();
        });
    }
    public void posalji(ActionEvent actionEvent){}
    public void posalji1(ActionEvent actionEvent) {}
}

