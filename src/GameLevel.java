import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


/**
 * Game class.
 * responsible to initialize and run the game.
 */
public class GameLevel implements Animation {
    //members
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Counter blocksCounter;
    private Counter scoreCounter;
    private Counter numberOfLives;
    private GUI gui;
    private AnimationRunner runner;
    private boolean running;
    private KeyboardSensor keyboard;
    private LevelInformation levelInformation;
    private Paddle paddle;
    private int seconds = 0;
    private List<Block> origionAlienBlocks;
    private biuoop.Sleeper sleeper;
    private long prevTime = System.currentTimeMillis();
    private ScoreIndicator scoreIndicator;
    private List<Ball> balls = new ArrayList<Ball>();
    //listeners
    private HitListener blockRemover;
    private HitListener scoreTrackingListener;
    private HitListener ballRemover;
    private HitListener livesRemover;
    private AlienCollection alienCollection;


    /**
     * GameLevel function.
     *
     * @param levelInformation - levelInformation obj.
     * @param keyboard         - KeyboardSensor obj.
     * @param animationRunner  - AnimationRunner obj, runs the game animation.
     * @param gui              - GUI obj.
     * @param livesCounter     - Counter obj.
     * @param scoreCounter     - Counter obj.
     * @param blocksCounter    - Counter obj.
     */
    GameLevel(LevelInformation levelInformation, KeyboardSensor keyboard, AnimationRunner animationRunner,
              GUI gui, Counter livesCounter, Counter scoreCounter, Counter blocksCounter) {
        this.levelInformation = levelInformation;
        if (levelInformation.getBlocksVelocity() == 100) {
            this.origionAlienBlocks = levelInformation.blocks();
        } else {
            this.origionAlienBlocks = levelInformation.getCurrentBlocks();
        }
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.blocksCounter = blocksCounter;
        this.scoreCounter = scoreCounter;
        numberOfLives = livesCounter;
        this.gui = gui;
        this.runner = animationRunner;
        this.keyboard = keyboard;
        this.sprites.addSprite(this.levelInformation.getBackground());
        this.blockRemover = new BlockRemover(this, this.blocksCounter);
        this.scoreTrackingListener = new ScoreTrackingListener(this.scoreCounter);
        this.ballRemover = new BallRemover(this);
        this.alienCollection = new AlienCollection(this.origionAlienBlocks, numberOfLives,
                this, environment, this.blocksCounter);
        sleeper = new biuoop.Sleeper();
        this.livesRemover = new LivesRemover(numberOfLives);

    }

    /**
     * addCollidable function.
     * adds collidable object to game environment.
     *
     * @param c - collidable object.
     */

    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * getAlien function.
     * @return aliens.
     */

    public List<Block> getAlien() {
        return this.origionAlienBlocks;
    }

    /**
     * addSprite function.
     * adds sprite object to game.
     *
     * @param s - sprite object.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);

    }

    /**
     * initialize function.
     * Initialize a new game: create the Blocks and ShutBall
     * and add them to the game.
     */
    public void initialize() {

        for (Block block : this.origionAlienBlocks) {
            addExistBlockToGameWithListener(block, ballRemover, scoreTrackingListener);
            block.addHitListener(blockRemover);
        }
        //aliens collection
        this.sprites.addSprite(this.alienCollection);
        //shields
        for (Block block : this.levelInformation.shieldBlocks()) {
            addShieldToGame(block, blockRemover, ballRemover);
        }

        //deathRegion block:
        Point point = new Point(0, 600);
        Rectangle rectangle = new Rectangle(point, 800, 20);
        ShieldBlock deathRegion = new ShieldBlock(rectangle, Color.RED, -100);
        deathRegion.addHitListener(ballRemover);
        deathRegion.addToGame(this);
        //add bounds blocks
        this.addGenericBlockToGame(0, 0, 20, 600, Color.gray);
        this.addGenericBlockToGame(0, 20, 800, 20, Color.gray);
        this.addGenericBlockToGame(780, 0, 20, 600, Color.gray);
        //score block:
        Point pointScore = new Point(0, 0);
        Rectangle rectangleScore = new Rectangle(pointScore, 800, 20);
        ShieldBlock scoreRegion = new ShieldBlock(rectangleScore, Color.white, -100);
        scoreRegion.addToGame(this);
        scoreIndicator = new ScoreIndicator(scoreRegion, Color.black, Color.WHITE, this.scoreCounter,
                this.levelInformation.levelName());
        this.sprites.addSprite(scoreIndicator);
        LivesIndicator livesIndicator = new LivesIndicator(scoreRegion, Color.black, this.numberOfLives);
        this.sprites.addSprite(livesIndicator);


    }

    /**
     * ddBallToGame function.
     *
     * @param x        - ball center position.
     * @param y        - ball center position.
     * @param radius   - ball's radius.
     * @param color    - ball's color.
     * @param velocity - - ball's velocity.
     */
    public void addBallToGame(double x, double y, double radius, Color color, Velocity velocity) {
        Ball ball = new Ball((int) x, (int) y, (int) radius, color, environment);
        ball.setVelocity(velocity);
        ball.addToGame(this);
        balls.add(ball);
    }

    /**
     * addBallToGame function.
     * @param ball - alien ball.
     */
    public void addBallToGame(Ball ball) {
        ball.addToGame(this);
        balls.add(ball);
    }

    /**
     * addExistBlockToGameWithListener function.
     *
     * @param block        - Block obj.
     * @param hitListener  -  block listener.
     * @param hitListener2 -  block listener.
     */
    private void addExistBlockToGameWithListener(Block block, HitListener hitListener, HitListener hitListener2) {
        block.addHitListener(hitListener);
        block.addHitListener(hitListener2);
        block.addToGame(this);
        this.blocksCounter.increase(1);
    }

    /**
     * addShieldToGame function.
     * @param block - Block obj.
     * @param hitListener - HitListener obj.
     * @param hitListener2 - HitListener obj.
     */
    private void addShieldToGame(Block block, HitListener hitListener, HitListener hitListener2) {
        block.addHitListener(hitListener);
        block.addHitListener(hitListener2);
        block.addToGame(this);
    }

    /**
     * addBlockToGame function.
     *
     * @param x      - upperLeft's point x position.
     * @param y      - upperLeft's point y position.
     * @param width  - block's width.
     * @param height - block's height.
     * @param color  - block's color.
     */
    private void addGenericBlockToGame(double x, double y, double width, double height, Color color) {
        Point point = new Point(x, y);
        Rectangle rectangle = new Rectangle(point, width, height);
        ShieldBlock block = new ShieldBlock(rectangle, color, -100);
        block.addHitListener(ballRemover);
        block.addToGame(this);

    }

    /**
     * removeCollidable function.
     *
     * @param c - collidable obj to remove from game.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
        if (this.origionAlienBlocks.contains(c)) {
            this.origionAlienBlocks.remove(c);
        }
    }

    /**
     * removeSprite function.
     *
     * @param s - Sprite obj to remove from game.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * removeAlien function.
     * @param alien - Sprite obj to remove from game.
     */

    public void removeAlien(Block alien) {
        this.alienCollection.removeAlien(alien);
    }

    /**
     * shouldStop function.
     *
     * @return boolean.
     */
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * doOneFrame function.
     *
     * @param d  - DrawSurfaceObj for paint.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        //pause screen
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY,
                    new PauseScreen(this.keyboard)));
        }
        //paddle balls
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {

            long usedTime = System.currentTimeMillis() - prevTime;
            long milliSecondLeftToSleep = 350 - usedTime;
            if (milliSecondLeftToSleep <= 0) {
                double x = this.paddle.getCollisionRectangle().getUpperLeft().getX()
                        + (this.paddle.getCollisionRectangle().getWidth() / 2);
                double y = this.paddle.getCollisionRectangle().getUpperLeft().getY() - 5;
                Velocity v = Velocity.fromAngleAndSpeed(0, 500);
                this.addBallToGame(x, y, 5, Color.WHITE, v);
                prevTime = System.currentTimeMillis();
            }
        }
        int prevLives = this.numberOfLives.getValue();
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);
        if (this.numberOfLives.getValue() < prevLives || this.alienCollection.getAliens().size() <= 0) {
            this.running = false;
            //reset aliens collection
            this.alienCollection.backToOriginalPos();
            for (Ball item : this.balls) {
                item.removeFromGame(this);
            }
        }
    }


    /**
     * playOneTurn function.
     * runs one turn of game.
     */
    public void playOneTurn() {
        scoreIndicator.setLevelName(this.levelInformation.levelName());
        //create paddle
        Point pointPaddle = new Point(400 - (this.levelInformation.paddleWidth() / 2), 560);
        Rectangle rectangle5 = new Rectangle(pointPaddle, this.levelInformation.paddleWidth(), 20);
        Paddle paddle1 = new Paddle(rectangle5, Color.YELLOW, gui.getKeyboardSensor(),
                this.levelInformation.paddleSpeed());
        this.paddle = paddle1;
        paddle.addToGame(this);
        paddle.addHitListener(ballRemover);
        paddle.addHitListener(livesRemover);
        //runner
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        // use our runner to run the current animation -- which is one turn of
        // the game.
        this.runner.run(this);
        this.sprites.removeSprite(paddle);
        this.environment.removeCollidable(paddle);
        //clean useless balls
        for (Ball item : this.balls) {
            item.removeFromGame(this);
        }
    }
}
