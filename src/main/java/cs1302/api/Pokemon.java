package cs1302.api;

import java.util.ArrayList;

/** class of a Pokemon that will be battling. */
public class Pokemon {

    String name;
    int maxHp;
    ArrayList<Ability> filteredAttacks;
    int currentHp;
    String pokeSprite;


    /**
     * Creates a pokemon object with all the info needed for battle.
     * @param card PokeCard info on pokemon
     * @param dex PokeDex info on pokemon
     */
    public Pokemon(PokeCard card, DexInfo dex) {
        this.maxHp = card.getHp();
        this.name = dex.name;
        this.currentHp = maxHp;

        this.filteredAttacks = new ArrayList<Ability>();
        //adds proper abilities to filteredAttacks
        for (Ability i : card.attacks) {
            if (i.isUsuable()) {
                filteredAttacks.add(i);
            }
        }

        if (filteredAttacks.size() < 1) {
            throw new IllegalArgumentException();
        }


        this.pokeSprite = dex.getSprite();
    }

}
