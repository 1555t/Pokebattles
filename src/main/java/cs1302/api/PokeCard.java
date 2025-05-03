package cs1302.api;

public class PokeCard {
    Ability[] attacks;
    int[] dexId;
    int hp;
    
    /**
     * Gives the health of the card.
     * @return the health of the card
     */
    public int getHp () {
      if (hp == 0) {
        hp = 100;
      }
      
      return hp;
    }
}
