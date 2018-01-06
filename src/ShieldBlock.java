import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Image;

/**
 * ShieldBlock class.
 */
public class ShieldBlock extends Block {

    //define members
    private Rectangle blockRectangle;
  //  private int hitPoints;
    private List<HitListener> hitListeners;
    private Color stroke = null;
    private Map<Integer, Color> colorMap = new HashMap<Integer, Color>();
    private Map<Integer, Image> imageMap = new HashMap<Integer, Image>();
    private CommonEnumerations.BlockType blockType = CommonEnumerations.BlockType.ShieldBlock;


    /**
     * constructor method.
     *
     * @param rectangle - block's rectangle.
     * @param color     - block's color.
     * @param hitPoints - number of hit points.
     */
    public ShieldBlock(Rectangle rectangle, java.awt.Color color, int hitPoints) {
        super(rectangle, color, hitPoints);
        blockType = CommonEnumerations.BlockType.ShieldBlock;
    }

    /**
     * constructor method.
     * this constructor support creating changes blocks.
     *
     * @param point     - blocks' upper left point
     * @param width     - block's width.
     * @param height    - block's height.
     * @param stroke    - block's border color.
     * @param colorMap  - block's colors.
     * @param imageMap  - block's images.
     * @param hitPoints - block's hit points.
     */
    public ShieldBlock(Point point, double width, double height, Color stroke, Map<Integer, Color> colorMap,
                       Map<Integer, Image> imageMap, int hitPoints) {
        super(point, width, height, stroke, colorMap, imageMap, hitPoints);
        blockType = CommonEnumerations.BlockType.ShieldBlock;

    }

    /**
     * getBlockType function.
     *
     * @return block's type.
     */
    @Override
    public CommonEnumerations.BlockType getBlockType() {
        return this.blockType;
    }


    /**
     * timePassed function.
     * currently does nothing
     * @param dt - the amount of seconds passed since the last call.
     */
    @Override
    public void timePassed(double dt) {

    }

    /**
     * setDx function.
     * @param dx - new velocity.
     * @param deceaseY - y pos.
     */
    @Override
    public void setDx(double dx, int deceaseY) {

    }

    /**
     * getVelocity function.
     *
     * @return block's velocity.
     */
    @Override
    public double getVelocity() {
        return 0;
    }

    /**
     * getOriginalVelocity function.
     *
     * @return original velocity of block.
     */
    @Override
    public double getOriginalVelocity() {
        return 0;
    }

    /**
     * getShootBall function.
     *
     * @param g - GameEnvironment obj.
     * @return new ball.
     */
    @Override
    public AlienBall getShootBall(GameEnvironment g) {
        return null;
    }

    /**
     * changeOriginalPos function.
     * changes block's position.
     */
    @Override
    public void changeOriginalPos() {

    }
    /**
     * hit function.
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     *
     * @param hitter          - hitter ball.
     * @param collisionPoint  - the triangle collision point with rectangle.
     * @param currentVelocity - current ball velocity.
     * @return the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {

        super.decreaseHitPoints();


        Velocity newVelocity = currentVelocity;
        //calculate new velocity
        if (super.isOnLeft(collisionPoint) || isOnRight(collisionPoint)) {
            newVelocity = new Velocity(-(currentVelocity.getDx()), currentVelocity.getDy());
        }
        if ((isOnTop(collisionPoint) || isOnDown(collisionPoint))) {
            newVelocity = new Velocity(newVelocity.getDx(), (-currentVelocity.getDy()));
        }
        //notify listener
        super.notifyHit(hitter);
        return newVelocity;
    }



}
