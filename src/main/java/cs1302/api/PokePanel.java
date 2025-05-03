package cs1302.api;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ProgressBar;

import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Enter a  Pokemon name on both sides, load them, fight.
 */
public class PokePanel {

    private boolean loaded;

    Text pokeName;
    VBox root;
    HBox searchLayer;
    TextField searchBar;
    Button searchButton;
    ImageView pokeSprite;
    Image givenSprite;
    ScrollPane moveList;
    VBox moveVBox;
    ArrayList<Button> battleButtons;
    ProgressBar pokeSearch;
    ProgressBar healthBar;
    Pokemon currentPokemon;
    PokePanel target;




    /** loads the image of the currentPokemon. */
    private void loadImage () {
        if (currentPokemon != null) {
            try {
                givenSprite = new Image(currentPokemon.pokeSprite);
                pokeSprite.setImage(givenSprite);
            } catch (IllegalArgumentException e) {
                System.out.println(e);
                givenSprite = new Image("file:resources/icons/missingNo");
                pokeSprite.setImage(givenSprite);
            }
        }


    }


    /**
     * Sets pokemon of the panel.
     * @param dex the pokedex entry to get info from
     * @param card the PokeCard to get info from
     *
     */
    public void setPokemon(PokeCard card, DexInfo dex) {
        currentPokemon = new Pokemon(card, dex);
        loadCry();
        loadImage();
        Platform.runLater(() -> {
                pokeSearch.setProgress(1);
            }
        );
        playCry();



    }



    /**
     * Creates an area with move description and info.
     *
     * @returns HBox with created move info and button
     */
    private HBox moveBuilder () {
        return null;
    }

    /**
     * Tells whether or not a pokemon's info is loaded into the panel.
     * @returns value of {@code loaded}
     */
    public boolean isLoaded () {
        return loaded;
    }

    /**
     * Builds the searchLayer.
     * @returns the assembled searchLayer as an hbox.
     */
    private HBox searchLayerBuilder () {
        HBox temp = new HBox();
        this.searchBar = new TextField();
        this.searchButton = new Button("Search");
        temp.getChildren().addAll(searchBar, searchButton);

        return temp;
    }

    /**
     * Builds the moveList.
     * @return the assembled moveList
     */
    private ScrollPane moveListBuilder () {
        ScrollPane temp  = new ScrollPane();
        temp.setPrefViewportHeight(200);

        this.moveVBox = new VBox();
        moveVBox.setFillWidth(true);
        moveVBox.getChildren().addAll(new Button("MOVE"), new Button("MOVE"), new Button("MOVE"));


        temp.setContent(moveVBox);

        return temp;
    }


    /**
     * Sets the target for the panel to attack.
     * @param panel is the target panel to attack
     */
    public void setTarget (PokePanel panel) {
        this.target = panel;

    } //setTarget

    /**
     * Default constructor for PokePanel.
     */
    public PokePanel() {
        this.loaded = false;
        this.root = new VBox();
        this.searchLayer = searchLayerBuilder();

        //initializes imageview

        this.pokeName = new Text("MissingNo. (None)");
        this.pokeSprite = new ImageView("file:resources/icons/missingNo");

        pokeSprite.setFitWidth(150);
        pokeSprite.setFitHeight(150);

        this.moveList = moveListBuilder();
        this.healthBar = new ProgressBar(100);
        this.pokeSearch = new ProgressBar(0);

        VBox.setVgrow(root, Priority.ALWAYS);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(searchLayer, pokeName, pokeSprite, moveList, pokeSearch);

    }


} // PokePanel
