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

/** Class to manage the pokemon battle*/
public class Battle {
    PokePanel side1;
    PokePanel side2;
    Text battleBanner;
    int turnCount = 1;


    /**
     * creator for Battle.
     * @param p1 panel for player 1
     * @param p2 panel for player 2
     */
    public Battle (PokePanel p1, PokePanel p2, Text b) {
        this.side1 = p1;
        this.side2 = p2;
        this.battleBanner = b;
    }

    /**
     * sets up battle if it's ready, returns whether or not battle is ready.
     *@return Whether or not the battle is ready
     */
    public boolean readyBattle() {
        if (side1.isLoaded() && side2.isLoaded()) {
            side1.setTarget(side2);
            side2.setTarget(side1);


        }
        return false;
    }

    /**
     * resets game.
     *
     */
    public void gameOver () {
        side1.setTarget(null);
        side2.setTarget(null);

        this.turnCount = 1;
    }

}
