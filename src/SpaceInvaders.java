import javax.imageio.ImageIO;
import java.awt.Color;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Image;

/**
 * SpaceInvaders class.
 */
public class SpaceInvaders implements LevelInformation {
    private List<Block> blocks = this.blocks();
    private double blocksVelocity = 100;
    private String levelName =  "Battle no. 1";
    /**
     * paddleSpeed function.
     * @return paddle speed.
     */
    public int paddleSpeed() {
        return 180;
    }

    /**
     * paddleWidth function.
     * @return paddle width.
     */
    public int paddleWidth() {
        return 50;
    }

    /**
     * levelName function.
     * @return the level name will be displayed at the top of the screen.
     */
    public String levelName() {
        return this.levelName;
    }

    /**
     * getBackground function.
     * @return a sprite with the background of the level.
     */
    public Sprite getBackground() {
        Sprite background = new SpaceInvadersBackground();
        return background;
    }

    /**
     * blocks function.
     * @return the Blocks that make up this level, each block contains
       its size, color and location.
     */
    public List<Block> blocks() {
        //image
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("block_images/enemy.png");
        try {
            Image image = ImageIO.read(is);
            List<Block> blocks1 = new ArrayList<Block>();
            Map<Integer, Color> colorMap = new HashMap<Integer, Color>();
            Map<Integer, Image> imageMap = new HashMap<Integer, Image>();
            imageMap.put(0, image);
            int x = 80;
            int y = 80;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 10; j++) {
                    Point point = new Point(x, y);
                    Rectangle rectangle = new Rectangle(point, 40, 30);
                    Block block = new Block(point, 40, 30, null, colorMap, imageMap, 1);
                    blocks1.add(block);
                    x += 50; //40 + 10 pixel space
                }
                x = 80;
                y += 40; //30 + 10 pixel space
            }
            return blocks1;
        } catch (Exception ex) {
            System.out.println("failed to create image");
            System.exit(1);
            return null;
        }

    }

    /**
     * getCurrentBlocks function.
     * @return blocks.
     */
    public  List<Block> getCurrentBlocks() {
        return this.blocks;
    }

    /**
     * shieldBlocks function.
     * @return blocks.
     */
    public List<ShieldBlock> shieldBlocks() {

        List<ShieldBlock> shieldBlocks = new ArrayList<ShieldBlock>();
        int x = 150;
        int y = 500;
            for (int i = 0; i < 3; i++) {
                for (int k = 0; k < 3; k++) {
                    if (i == 0) {
                        x = 150;
                    } else if (i == 1) {
                        x = 350;
                    } else {
                        x = 550;
                    }
                    for (int j = 0; j < 20; j++) {
                        Point point = new Point(x, y);
                        Rectangle rectangle = new Rectangle(point, 5, 5);
                        ShieldBlock shieldBlock = new ShieldBlock(rectangle, Color.cyan.brighter(), 1);
                        shieldBlocks.add(shieldBlock);
                        x += 5;
                    }
                     y += 5;


                }
                y = 500;

            }
        return shieldBlocks;
    }


    /**
     * numberOfBlocksToRemove function.
     * @return the number of blocks that should be removed
      before the level is considered to be "cleared".
     */
    public int numberOfBlocksToRemove() {
        return 50;
    }

    /**
     * setBlocksVelocity function.
     * @param dx - new alien velocity.
     */
    public void setBlocksVelocity(double dx) {
        this.blocksVelocity = dx;
        for (Block item: this.blocks) {
            item.setDx(dx, 0);
        }
    }

    /**
     * setOriginalVelocity function.
     * @param orgDx - double origin velocity.
     */
    public void setOriginalVelocity(double orgDx) {
        this.blocks = this.blocks();
        for (Block item: this.blocks) {
            item.updateOriginalDx(orgDx);
        }
    }

    /**
     * getBlocksVelocity function.
     * @return block's velocity.
     */
    public double getBlocksVelocity() {
        return this.blocksVelocity;
    }

    /**
     * setNewLevelName function.
     * @param levelName1 - new level name.
     */
    public void setNewLevelName(String levelName1) {
        this.levelName = levelName1;

    }

}
