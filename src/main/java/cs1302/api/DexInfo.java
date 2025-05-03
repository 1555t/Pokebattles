package cs1302.api;

/** fills out pokeDex info from json string. */
public class DexInfo {
    String name;
    SpriteInfo sprites;



    /**
     * gets url of pokemon's sprite.
     * @return the link to the pokemon's sprite file
     */
    public String getSprite() {
        return sprites.frontDefault;
    }
}
