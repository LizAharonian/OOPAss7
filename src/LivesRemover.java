/**
 * LivesRemover function.
 */
public class LivesRemover implements HitListener {

    // members
    private Counter lives;

    /**
     * constructor method.
     * @param lives - number of remaining lives of player.
     */
    public LivesRemover(Counter lives) {
        this.lives = lives;
    }

    /**
     * hitEvent function.
     * this function removes the hitter ball from game.
     * @param beingHit - the being hit block.
     * @param hitter - the hitter ball.
     */
    public void hitEvent(Block beingHit, Ball hitter) {

    }
    /**
     * hitEvent function.
     * this function removes the hitter ball from game.
     * @param beingHit - the being hit paddle.
     * @param hitter - the hitter ball.
     */
    public void hitEvent(Paddle beingHit, Ball hitter) {
        this.lives.decrease(1);
    }
}
