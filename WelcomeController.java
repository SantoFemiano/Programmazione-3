package com.example.grafico;
//La classe WelcomeController gestisce le schermate dall'avvio all'accesso dei vari utenti(fase di registrazione e log-in).


import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.santosalvatore.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class WelcomeController implements Initializable {


    private final List<Product> listaProdotti = new ArrayList<>();
    private final List<Utente> listaUtenti = new ArrayList<>();
    private final List<Venditore> listaVenditori = new ArrayList<>();
    @FXML
    public Button Bottone_avvio;
    public int utenteVenditoreOCliente = -1;
    AzioniCliente Azioni = new AzioniCliente();
    int accesso_negato = 0;
    int utente_esistente = 0;

    @FXML
    private Label labelField;
    @FXML
    private Label fallimento_registrazione;

    @FXML
    private MFXTextField usernameField;
    @FXML
    private MFXPasswordField passwordField;
    @FXML
    private TextField campo_username;
    @FXML
    private TextField campo_password;
    @FXML
    private TextField campo_indirizzo;
    @FXML
    private TextField campo_email;
    @FXML
    private TextField campo_numerotelefono;


    public WelcomeController() {
        GestoreDatabase database = new GestoreDatabase();
        database.getAllProductsDB(listaProdotti);
        database.getAllUtentiDB(listaUtenti);
        database.getAllVenditoriDB(listaVenditori);
        setUtenteVenditoreOCliente(this.utenteVenditoreOCliente);

    }

    public void setUtenteVenditoreOCliente(int value) {
        this.utenteVenditoreOCliente = value;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    @FXML
    public void Avvia(ActionEvent actionEvent) throws IOException {
        cambiaSchermata("schermata_scelta_tipo_account.fxml", "Scelta Account", actionEvent);

    }


    private void cambiaSchermata(String fxmlPath, String title, ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent nuovaSchermata = loader.load();
        Scene nuovaScena = new Scene(nuovaSchermata);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(nuovaScena);
    }

    private void cambiaSchermataConController(String fxmlPath, String title, int utente, ActionEvent actionEvent) throws IOException {

        FXMLLoader venditoreLog = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent vendLogin = venditoreLog.load();
        WelcomeController controller = venditoreLog.getController(); // Ottieni il controller
        controller.setUtenteVenditoreOCliente(utente);


        Scene VenditoreLogin = new Scene(vendLogin);


        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        stage.setTitle(title);
        stage.setScene(VenditoreLogin);


    }


    public void LoginVenditore(ActionEvent actionEvent) throws IOException {
        setUtenteVenditoreOCliente(0);


        cambiaSchermataConController("login.fxml", "Login Venditore", 0, actionEvent);

    }


    @FXML
    public void RegLogCliente(ActionEvent actionEvent) throws IOException {
        setUtenteVenditoreOCliente(1);


        cambiaSchermataConController("schermata_scelta_regolog_cliente.fxml", "Registrati o fai Login", 1, actionEvent);


    }

    public void Indietro(ActionEvent actionEvent) throws IOException {
        Avvia(actionEvent);
    }

    public void entraLoginCliente(ActionEvent actionEvent) throws IOException {


        cambiaSchermataConController("login.fxml", "Login Cliente", 1, actionEvent);


    }

    public void log_in(ActionEvent actionEvent) throws IOException {

        String username = usernameField.getText();
        String password = passwordField.getText();


        if (utenteVenditoreOCliente == 0) {
            LoginUtente login = new LoginUtente(listaVenditori);
            //VENDITORE
            Venditore venditore = (Venditore) login.EffettuaLogin(username, password);
            if (venditore == null) {

                accesso_negato++;
                labelField.setText("Accesso negato. " + accesso_negato);
            } else {

                cambiaSchermata("schermata_venditore.fxml", "Menu Venditore", actionEvent);
            }
        } else if (utenteVenditoreOCliente == 1) {
            LoginUtente login = new LoginUtente(listaUtenti);
            //CLIENTE
            Utente utente = (Utente) login.EffettuaLogin(username, password);
            if (utente == null) {

                accesso_negato++;
                labelField.setText("Accesso negato. " + accesso_negato);
            } else {

                Azioni.vaiSchermataPrincipaleCliente(utente, actionEvent);
            }

        } else {
            System.out.println("Errore " + utenteVenditoreOCliente);
        }
    }

    public void VaiIndietro(ActionEvent actionEvent) throws IOException {
        Indietro(actionEvent);
    }

    public void entraRegistrazioneCliente(ActionEvent actionEvent) throws IOException {
        // Caricamento della nuova schermata da un altro file FXML
        cambiaSchermata("RegistrazioneUtente.fxml", "Registrazione Cliente", actionEvent);


    }

    public void Registrati(ActionEvent actionEvent) throws IOException {
        String username = campo_username.getText();
        String password = campo_password.getText();
        String indirizzo = campo_indirizzo.getText();
        String email = campo_email.getText();
        String numero_telefono = campo_numerotelefono.getText();

        RegistrazioneUtente registrazioneUtente = new RegistrazioneUtente(listaUtenti);
        Utente utente = registrazioneUtente.datiRegistrazione(username, password, indirizzo, email, numero_telefono);
        if (utente == null) {
            utente_esistente++;
            fallimento_registrazione.setText("Utente già esistenete " + utente_esistente);
        } else {
            Azioni.vaiSchermataPrincipaleCliente(utente, actionEvent);
        }
    }

}

