/**
 * BallRemover function.
 * a BallRemover is in charge of removing balls from the game, as well as keeping count
  of the number of balls that remain.
 */
public class BallRemover implements HitListener {
    // members
    private GameLevel game;

    /**
     * BallRemover function.
     * @param game - Game obj.
     */
    public BallRemover(GameLevel game) {
        this.game = game;
    }

    /**
     * hitEvent function.
     * this function removes the hitter ball from game.
     * @param beingHit - the being hit block.
     * @param hitter - the hitter ball.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
            hitter.removeFromGame(this.game);
    }

    /**
     * hitEvent function.
     * this function removes the hitter ball from game.
     * @param beingHit - the being hit paddle.
     * @param hitter - the hitter ball.
     */

    public void hitEvent(Paddle beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
    }
}
