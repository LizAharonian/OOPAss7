/**
 * AlienBall class.
 */
public class AlienBall extends Ball {
    //members
    private  CommonEnumerations.BallType ballType = CommonEnumerations.BallType.AlienBullet;

    /**
     * constructor method.
     * @param center - center point of the ball.
     * @param r      - radius of the ball.
     * @param color  - color of the ball.
     * @throws RuntimeException if radius is not valid.
     */
    public AlienBall(Point center, int r, java.awt.Color color) throws RuntimeException {
        super(center, r, color);
        this.ballType =  CommonEnumerations.BallType.AlienBullet;
    }

    /**
     * constructor method.
     * @param x     - x value of center point.
     * @param y     - y value of center point.
     * @param r     - radius of the ball.
     * @param color - color of the ball.
     * @throws RuntimeException if radius is not valid.
     */
    public AlienBall(int x, int y, int r, java.awt.Color color) throws RuntimeException {
        super(x, y , r, color);
        this.ballType =  CommonEnumerations.BallType.AlienBullet;
    }

    /**
     * constructor method.
     * @param x     - x value of center point.
     * @param y     - y value of center point.
     * @param r     - radius of the ball.
     * @param color - color of the ball.
     * @param gameEnvironment - game area.
     */

    public AlienBall(int x, int y, int r, java.awt.Color color, GameEnvironment gameEnvironment) {
        super(x, y, r, color, gameEnvironment);
        this.ballType =  CommonEnumerations.BallType.AlienBullet;
    }

    /**
     * constructor method.
     * @param x      - x value of center point.
     * @param y      - y value of center point.
     * @param r      - radius of the ball.
     * @param color  - color of the ball.
     * @param bounds - - range of motion of the ball.
     * @throws RuntimeException if radius is not valid.
     */
    public AlienBall(int x, int y, int r, java.awt.Color color, Bounds bounds) throws RuntimeException {
       super(x, y, r, color, bounds);
        this.ballType =  CommonEnumerations.BallType.AlienBullet;
    }
    /**
     * getBallType function.
     * @return bal's type.
     */
    @Override
    public CommonEnumerations.BallType getBallType() {
        return this.ballType;
    }

}
