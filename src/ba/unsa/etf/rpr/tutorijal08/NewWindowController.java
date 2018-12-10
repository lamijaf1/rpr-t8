package ba.unsa.etf.rpr.tutorijal08;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NewWindowController {
    public NewWindowController(){}
    public TextField postanskiBroj;
    public Thread posaljiNit;
    public String postBr="";
    public String postBr1="";
    public boolean validPostBr(){
        String postBr=postanskiBroj.getText();
        if(!postBr.contains("postanskiBroj="))return false;
        System.out.println("OKK");
            int spasiPoz=-1;
            String wordToFind = "postanskiBroj=";
            Pattern word = Pattern.compile(wordToFind);
            Matcher match = word.matcher(postBr);
            while (match.find()) {
               spasiPoz=match.end(); //pozicija prvog karaktera postanskog broj
            }

            if(spasiPoz==-1)return false;
            while(spasiPoz<postBr.length()){
                postBr+=postBr.charAt(spasiPoz);
            }
            postBr1=postBr.substring(spasiPoz, spasiPoz+5);
            if(!postBr.equals(postBr1)  || postBr.length()!=5)return false;
            if(!(postBr.charAt(0)=='7' || postBr.charAt(1)=='8'))return false;
            if(!(postBr.charAt(1)=='7' || postBr.charAt(2)=='8'))return false;
            for(int i=2;i<=4;i++){
                if(!(postBr.charAt(i)>='0'&& postBr.charAt(i)<='9'))return false;
            }
            return true;

    }

    @FXML
    public void initialize() {
    }
    public void posalji(ActionEvent actionEvent) {
        Runnable runnable =new Runnable() {
            @Override
            public void run () {
                //System.out.println("OK");
                try {
                    posaljiNit.sleep(1000);
                    postanskiBroj.requestFocus();
                    if (!postanskiBroj.isFocused()) {
                        if (validPostBr() == true) System.out.println("OK");
                        else System.out.println("NOT OK");
                    }

                } catch (Exception e) {}
            }
        };

            posaljiNit = new Thread(runnable);
            posaljiNit.start();

    }
}
