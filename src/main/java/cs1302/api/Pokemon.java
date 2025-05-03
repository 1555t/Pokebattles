package cs1302.api;

import java.util.ArrayList;

public class Pokemon {

    String name;
    int maxHp;
    ArrayList<Ability> filteredAttacks;
    int currentHp;
    String cry;
    String pokeSprite;


    /**
     * Creates a pokemon object with all the info needed for battle.
     * @param
     * @param
     */


    public Pokemon(PokeCard card, DexInfo dex) {
        this.maxHp = card.hp;
        this.filteredAttacks = new ArrayList<Ability>();
        //adds proper abilities to filteredAttacks
        for (Ability i : card.attacks) {
            if (i.isUsuable()) {
                filteredAttacks.add(i);
            }
        }
        this.cry = dex.getCry();
        this.pokeSprite = dex.getSprite();
    }

}
