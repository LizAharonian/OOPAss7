/**
 * HitListener interface.
 */
public interface HitListener {

    /**
     * hitEvent function.
     * This method is called whenever the beingHit object is hit.
     * @param beingHit - the being hit obj.
     * @param hitter - the ShutBall that's doing the hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
    /**
     * hitEvent function.
     * this function removes the hitter ball from game.
     * @param beingHit - the being hit paddle.
     * @param hitter - the hitter ball.
     */
    void hitEvent(Paddle beingHit, Ball hitter);
}
