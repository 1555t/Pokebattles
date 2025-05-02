package cs1302.api;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Enter a  Pokemon name on both sides, load them, fight.
 */
public class ApiApp extends Application {

    Stage stage;
    Scene scene;
    VBox root;

    PokePanel panel1;
    PokePanel panel2;

    HBox intendedRoot;
    ArrayList<Button> battleButtons;




    /** HTTP client. */
    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)           // uses HTTP protocol version 2 where possible
        .followRedirects(HttpClient.Redirect.NORMAL)  // always redirects, except from HTTPS to HTTP
        .build();                                     // builds and returns a HttpClient object

    /** Google {@code Gson} object for parsing JSON-formatted strings. */
    public static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()                          // enable nice output when printing
        .create();                                    // builds and returns a Gson object


    /**
     * Method to initialize Stage.
     */
    private void stageInit() {

        stage.setMinWidth(300);
        stage.setMaxWidth(600);
        stage.setMaxHeight(600);
    }


    private PokeSearchResults  generalPokeResults () {
        //TODO
        return null;
    }


    /**
     * Constructs an {@code ApiApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */
    public ApiApp() {
        root = new VBox();
        intendedRoot = new HBox(20);
        panel1 = new PokePanel();
        panel2 = new PokePanel();


    } // ApiApp

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stageInit();

        HBox.setHgrow(intendedRoot, Priority.ALWAYS);
        intendedRoot.setAlignment(Pos.CENTER);
        // setup scene
        intendedRoot.getChildren().addAll(panel1.root, panel2.root);

        root.getChildren().addAll(intendedRoot);
        scene = new Scene(root);
        stage.sizeToScene();

        // setup stage
        stage.setTitle("PokeBattles!");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();

    } // start

} // ApiApp
