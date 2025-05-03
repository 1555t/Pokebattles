package cs1302.api;

/** Stores the information for a pokemon's attack in a class. */
public class Ability {
    String name;
    int damage;

    /**
     * Determines whether or not a move damages.
     * @return true or false for the check
     */
    public boolean isUsuable() {
        if (this.damage == 0) {
            return false;
        }

        return true;
    }

}
