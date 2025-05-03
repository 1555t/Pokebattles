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

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    Text healthText;
    Text battleBanner;
    VBox root;
    VBox healthLayer;
    VBox moveVBox;
    HBox searchLayer;
    TextField searchBar;
    Button searchButton;
    Button startButton;
    ImageView pokeSprite;
    Image givenSprite;
    ScrollPane moveList;
    ArrayList<Button> battleButtons;
    ProgressBar pokeSearch;
    ProgressBar healthBar;
    Pokemon currentPokemon;
    PokePanel target;


    static final Image DEFAULT_IMAGE = new Image("file:resources/icons/missingNo");


/**
     * Creates the eventhandler for the battlestarting button.
     * @return a generated eventhandler
     */
    private EventHandler<ActionEvent> battleStart () {
        EventHandler<ActionEvent> temp;

        temp = e -> {
            enableAttacks();
            target.enableAttacks();
            announce("Battle Started!");
            startButton.setText("Quit Battle");
            startButton.setOnAction(cancelBattle());
        };

        return temp;

    }


    /**
     * Creates the eventhandler for the battle quitting button.
     * @return a generated eventhandler
     */
    private EventHandler<ActionEvent> cancelBattle () {
        EventHandler<ActionEvent> temp;

        temp = e -> {
            startButton.setDisable(true);
            startButton.setText("Start Battle!");

            reset();
            target.reset();

        };

        return temp;
    }




    /** loads the image of the currentPokemon. */
    private void loadImage () {
        if (currentPokemon != null) {
            try {
                givenSprite = new Image(currentPokemon.pokeSprite);
                pokeSprite.setImage(givenSprite);
            } catch (IllegalArgumentException e) {
                System.out.println(e);
                pokeSprite.setImage(DEFAULT_IMAGE);
            }
        }


    }

    /**
     * Displays a message on the battle banner.
     * @param message the message to be displayed on battleBanner
     */
    private void announce (String message) {
        battleBanner.setText(message);
    }


    /**
     * Sets pokemon of the panel.
     * @param dex the pokedex entry to get info from
     * @param card the PokeCard to get info from
     *
     */
    public void setPokemon(PokeCard card, DexInfo dex) {
        try {
            currentPokemon = new Pokemon(card, dex);
        } catch (Exception e) {
            throw e;
        }
        loaded = true;
        loadImage();



        Platform.runLater(() -> {
                moveVBox.getChildren().clear();
                pokeSearch.setProgress(1.0);
                healthBar.setProgress(1.0);

                pokeName.setText(currentPokemon.name);
                updateHealth();
                //load moves
                for (Ability i : currentPokemon.filteredAttacks) {
                    loadMove(i);
                }

                if (target.isLoaded()) {
                    System.out.println("Ready to battle!");
                    enableBattle();
                }
            }
        );


    }





    /**
     * Enables the battling button.
     */
    public void enableBattle () {
        if (startButton != null) {
            startButton.setDisable(false);
            startButton.setOnAction(battleStart());
        }
    }

    /**
     * Enables attacking and disables searching.
     */
    public void enableAttacks () {
        disableSearch();
        for (Button i : battleButtons) {
            i.setDisable(false);
        }

    }

    /**
     * Disables attacking and enables searching.
     */
    public void disableAttacks () {
        enableSearch();
        for (Button i : battleButtons) {
            i.setDisable(true);
        }

    }


    /**
     * Updates health graphics.
     */
    private void updateHealth () {
        healthText.setText("HP: " + currentPokemon.currentHp);
        healthBar.setProgress((double) currentPokemon.currentHp / currentPokemon.maxHp);

        //loses when health is 0 or below
        if (currentPokemon.currentHp <= 0) {
            loseBattle();
        }
    }

    /**
     * Disables the searching part of the pokepanel.
     */
    public void disableSearch() {
        searchButton.setDisable(true);
        searchBar.setDisable(true);
    }

    /**
     * Enables the searching part of the pokepanel.
     */
    public void enableSearch() {
        searchButton.setDisable(false);
        searchBar.setDisable(false);
    }



    /**
     * Generates a vbox to hold a move's info.
     * @param move The ability to be loaded.
     * @return a built VBox for the move
     */
    private VBox moveBuilder (Ability move) {
        VBox temp = new VBox();
        Text header = new Text(move.name);
        Text damage = new Text ("Damage: " + move.damage);
        Button attackButton = new Button("Use");


        //Sets button's action
        EventHandler<ActionEvent> attackEvent = e -> {
            announce(currentPokemon.name + " used " + move.name);
            dealDamage(move.damage);

        };

        attackButton.setOnAction(attackEvent);

        //initializes as disabled
        attackButton.setDisable(true);

        //adds button to list of BattleButtons
        battleButtons.add(attackButton);


        //aligns to center
        temp.setAlignment(Pos.CENTER);
        temp.getChildren().addAll(header, damage, attackButton);
        return temp;
    }


    /**
     * Loads a move based off of Ability.
     * @param move
     */
    private void loadMove(Ability move) {
        VBox tempVBox = moveBuilder(move);

        //adds move to moveVBox
        moveVBox.getChildren().add(tempVBox);

    }



    /**
     * makes currentPokemon take damage.
     * @param amt the amount of damage to be taken
     */
    public void takeDamage(int amt) {
        if (currentPokemon.currentHp >= amt) {
            currentPokemon.currentHp -= amt;
            updateHealth();
        } else {
            currentPokemon.currentHp = 0;
            updateHealth();
        }

    }

    /**
     * makes currentPokemon deal damage.
     * @param amt the amount of damage to be dealt
     */
    public void dealDamage(int amt) {
        if (target != null) {
            target.takeDamage(amt);
        }

    }

    /**
     * Creates an area with move description and info.
     * @return HBox with created move info and button
     */
    private HBox moveBuilder () {
        return null;
    }

    /**
     * Tells whether or not a pokemon's info is loaded into the panel.
     * @return value of {@code loaded}
     */
    public boolean isLoaded () {
        return loaded;
    }

    /**
     * Ends battle when pokemon loses.
     */
    private void loseBattle () {
        announce(target.currentPokemon.name + " has won the Battle!");

        startButton.setDisable(true);
        startButton.setText("Start Battle!");


        target.reset();
        reset();
    }


    /**
     * Builds the searchLayer.
     * @return the assembled searchLayer as an hbox.
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

        this.moveVBox = new VBox(30);
        moveVBox.setFillWidth(true);



        temp.setContent(moveVBox);

        return temp;
    }

    /**
     * Constructs VBox containing health point info.
     * @return the constructed vbox
     */
    private VBox healthVBox () {
        VBox temp = new VBox();
        this.healthText = new Text("HP: ???");
        this.healthBar = new ProgressBar(0);

        temp.getChildren().addAll(healthText, healthBar);

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
     * @param banner the Battle Banner text
     * @param battleStarter the button to start and quit battle
     */
    public PokePanel(Text banner, Button battleStarter) {
        this.loaded = false;
        this.root = new VBox();
        this.searchLayer = searchLayerBuilder();
        this.battleButtons = new ArrayList<Button>();
        //connects the battleBanner and start button
        this.battleBanner = banner;
        this.startButton = battleStarter;

        //initializes imageview

        this.pokeName = new Text("MissingNo. (None)");
        this.pokeSprite = new ImageView(DEFAULT_IMAGE);

        pokeSprite.setFitWidth(150);
        pokeSprite.setFitHeight(150);

        this.moveList = moveListBuilder();
        this.pokeSearch = new ProgressBar(0);
        this.healthLayer = healthVBox();

        VBox.setVgrow(root, Priority.ALWAYS);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(searchLayer, pokeName, pokeSprite,
                                   healthLayer,moveList, pokeSearch);

    }

    /**
     * Resets card to default values.
     */
    public void reset() {
        this.currentPokemon = null;
        this.loaded = false;
        this.healthBar.setProgress(0);
        this.healthText.setText("HP: ???");
        this.battleButtons = new ArrayList<Button>();
        this.pokeSearch.setProgress(0);
        pokeName.setText("MissingNo. (None)");
        pokeSprite.setImage(DEFAULT_IMAGE);
        this.moveVBox.getChildren().clear();
        enableSearch();
    }

} // PokePanel
