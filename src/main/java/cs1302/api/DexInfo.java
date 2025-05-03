package cs1302.api;

/** fills out pokeDex info from json string */
public class DexInfo {
    String name;
    cryInfo cries;
    spriteInfo sprites;

    /** another class to be parsed, for the sprites of the pokemon.*/
    private class spriteInfo {
        String front_default;

    }


    /** another class to be parsed, for the cries of the pokemon.*/
    private class cryInfo {
        public String latest;
    }

    /**
     * gets url of pokemon's cry.
     * @return the link to the pokemon's cry file
     */
    public String getCry() {
        return cries.latest;
    }

    /**
         * gets url of pokemon's sprite.
         * @return the link to the pokemon's sprite file
         */
        public String getSprite() {
            return sprites.front_default;
        }
}
