
package cs1302.api;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;

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
    HBox battleHBox;
    Text battleBanner;
    Button startButton;
    PokePanel panel1;
    PokePanel panel2;

    HBox intendedRoot;






    /**
     *
     *
     */
    private void runNow(Runnable e) {
        Thread t = new Thread(e);
        t.setDaemon(false);
        t.start();
    }

    /**
     * Formats a given string to utf-8 format
     * @param given the String to be formatted
     * @return  The String formatted
     */
    private static String utfFormat(String given) {
        //uses URL encoder to turn given string to UTF
        String term = URLEncoder.encode(given, StandardCharsets.UTF_8);
        return term.trim();
    };

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


    /**
     * Creates the battleHBox.
     * @return a created HBox
     */
    private HBox battleHBoxBuilder() {
        HBox temp = new HBox();

        //initializes battle start button.
        this.startButton = new Button("Start Battle!");
        startButton.setDisable(true);

        this.battleBanner = new Text();

        temp.getChildren().addAll(startButton, battleBanner);

        return temp;

    }







    /**
     * generates pokedex info for pokemon based off PokeCard.
     * @param card The PokeCard to get info from
     * @return Pokedex info for a pokemon
     */
    private DexInfo dexInfoResponse (PokeCard card) {
        DexInfo tempDexInfo = null;

        if (card == null || card.dexId == null) { throw new NullPointerException(); }

        int entryNo = card.dexId[0];

        URI searchURI = URI
            .create("https://pokeapi.co/api/v2/pokemon/"
                    + entryNo);

        HttpRequest dexRequest = HttpRequest.newBuilder()
            .uri(searchURI) // sets the HttpRequest's URI
            .build();      // builds and returns an HttpRequest

        try {
            tempDexInfo = dexResponse(dexRequest);
        } catch (IllegalArgumentException e) {
            throw e;
        }

        return tempDexInfo;
    }


    /**
     * Searches for pokedex entry
     * @param req the HTTPREQUEST used to search.
     * @return pokedex info
     */
    private DexInfo  dexResponse (HttpRequest req) throws IllegalArgumentException {
        HttpResponse<String> givenResponse = null;
        try {
            givenResponse = HTTP_CLIENT     // send request
                .send(req, BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new IllegalArgumentException();
        }

        DexInfo pokeDexInfo;

        //creates DexInfo  based off of json being parsed
        pokeDexInfo = GSON.fromJson(givenResponse.body(), DexInfo.class);

        return  pokeDexInfo;
    }








    /**
     * generates array of PokeStubs based off of card searched using search term, selects first.
     * @param term the pokemon name to be searched
     * @return a generated PokeCard
     */
    private PokeCard  pokeCardResult (String term) {
        PokeStub tempStub = null;
        PokeCard tempCard = null;
        URI searchURI = URI
            .create("https://api.tcgdex.net/v2/en/cards?category=Pokemon&name="
                    + utfFormat(term));

        HttpRequest stubRequest = HttpRequest.newBuilder()
            .uri(searchURI) // sets the HttpRequest's URI
            .build();      // builds and returns an HttpRequest

        try {
            tempStub = stubResponse(stubRequest);
        } catch (IllegalArgumentException e) {
            throw e;
        }


        URI cardURI = URI
            .create("https://api.tcgdex.net/v2/en/cards/" + utfFormat(tempStub.id));

        HttpRequest cardRequest = HttpRequest.newBuilder()
            .uri(cardURI) // sets the HttpRequest's URI
            .build();      // builds and returns an HttpRequest

        try {
            tempCard = cardResponse(cardRequest);
        } catch (IllegalArgumentException e) {
            throw e;
        }


        //TODO
        return tempCard;
    }




    /**
     * Searches for an array of pokeStubs based off request, returns the first item in that array.
     * @param req the HTTPREQUEST used to search.
     * @return the first PokeStub
     */
    private PokeStub stubResponse (HttpRequest req) throws IllegalArgumentException {
        HttpResponse<String> givenResponse = null;
        try {
            givenResponse = HTTP_CLIENT     // send request
                .send(req, BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new IllegalArgumentException();
        }

        PokeStub[] stubArray;

        //creates array of results based off of json being parsed
        stubArray = GSON.fromJson(givenResponse.body(), PokeStub[].class);


        return stubArray[0];

    }

    /**
     * Searches for an array of pokeStubs based off request, returns the first item in that array.
     * @param req the HTTPREQUEST used to search.
     * @return the first PokeStub
     */
    private PokeCard cardResponse (HttpRequest req) throws IllegalArgumentException {
        HttpResponse<String> givenResponse = null;
        try {
            givenResponse = HTTP_CLIENT     // send request
                .send(req, BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new IllegalArgumentException();
        }

        PokeCard chosenCard;

        //creates PokeCard based off of json being parsed
        chosenCard = GSON.fromJson(givenResponse.body(), PokeCard.class);

        return chosenCard;
    }





    /**
     * Creates an event handler for the button.
     * @param panel  The panel this eventHandler will serve
     * @return the created eventhandler
     */
    private EventHandler<ActionEvent> searchHandler (PokePanel panel) {

        //what happens when you click on the search button
        EventHandler<ActionEvent> temp = event  -> {
            startButton.setDisable(true);
            panel.pokeSearch.setProgress(-1);

            runNow(() -> {
                    //temporary card variable
                    PokeCard tCard = null;
                    //temporary dexInfo variable
                    DexInfo tDex = null;



                    //makes sure everything is usuable to be loaded
                    try {
                        tCard =  pokeCardResult(panel.searchBar.getText());



                    } catch (Exception e) {
                        Platform.runLater(() -> {
                                panel.pokeSearch.setProgress(0);
                                alertError(e, "No Valid Card for Term.");
                            }
                        );


                        return;
                    }

                    try {
                        tDex = dexInfoResponse(tCard);
                        panel.setPokemon(tCard, tDex);
                    } catch (Exception e) {
                        Platform.runLater(() -> {
                                panel.pokeSearch.setProgress(0);
                                alertError(e, "Term provides incompatible card.");
                            }
                        );

                        return;
                    }

                }

            );

        };

        return temp;
    }





    /**
     * Constructs an {@code ApiApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */
    public ApiApp() {
        root = new VBox();
        intendedRoot = new HBox(20);
        battleHBox = battleHBoxBuilder();
        panel1 = new PokePanel(battleBanner, startButton);
        panel2 = new PokePanel(battleBanner, startButton);



    } // ApiApp

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stageInit();

        HBox.setHgrow(intendedRoot, Priority.ALWAYS);
        intendedRoot.setAlignment(Pos.CENTER);

        //setup buttons
        panel1.searchButton.setOnAction(searchHandler(panel1));
        panel2.searchButton.setOnAction(searchHandler(panel2));

        //set panels against each other
        panel1.setTarget(panel2);
        panel2.setTarget(panel1);


        // setup scene
        intendedRoot.getChildren().addAll(panel1.root, panel2.root);

        root.getChildren().addAll(battleHBox, intendedRoot);
        scene = new Scene(root);
        stage.sizeToScene();

        // setup stage
        stage.setTitle("PokeBattles!");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();



    } // start

    /**
     * Warning pane appears based off erorr.
     * @param cause the exact thrown error
     * @param errorMessage the message to accompany the error that occured
     */
    private void alertError(Throwable cause, String errorMessage) {
        System.out.println(cause);
        cause.printStackTrace();
        TextArea text = new TextArea("Cause:" + cause.toString() + ": " + errorMessage);
        text.setEditable(false);
        Alert alert = new Alert(AlertType.ERROR);
        alert.getDialogPane().setContent(text);
        alert.setResizable(true);
        alert.showAndWait();
    }


} // ApiApp
