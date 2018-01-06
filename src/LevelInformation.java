import java.util.List;

/**
 * LevelInformation interface.
 */
public interface LevelInformation {
    /**
     * paddleSpeed function.
     * @return paddle speed.
     */
    int paddleSpeed();
    /**
     * paddleWidth function.
     * @return paddle width.
     */
    int paddleWidth();
    /**
     * levelName function.
     * @return the level name will be displayed at the top of the screen.
     */
    String levelName();
    /**
     * getBackground function.
     * @return a sprite with the background of the level.
     */
    Sprite getBackground();
    /**
     * blocks function.
     * @return the Blocks that make up this level, each block contains
       its size, color and location.
     */
    List<Block> blocks();
    /**
     * numberOfBlocksToRemove function.
     * @return the number of blocks that should be removed
       before the level is considered to be "cleared".
     */
    int numberOfBlocksToRemove();

    /**
     * shieldBlocks function.
     * @return shield blocks list.
     */
    List<ShieldBlock> shieldBlocks();

    /**
     * setNewLevelName function.
     * @param levelName - new level name.
     */
    void setNewLevelName(String levelName);

    /**
     * setBlocksVelocity function.
     * @param dx - new alien velocity.
     */

    void setBlocksVelocity(double dx);

    /**
     * setOriginalVelocity function.
     * @param dx - new original velocity.
     */
    void setOriginalVelocity(double dx);

    /**
     * getBlocksVelocity function.
     * @return new blocks velocity.
     */

    double getBlocksVelocity();

    /**
     * getCurrentBlocks function.
     * @return current aliens of game.
     */

    List<Block> getCurrentBlocks();


}