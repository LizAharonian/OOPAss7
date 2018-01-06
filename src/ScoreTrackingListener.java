/**
 * ScoreTrackingListener class.
 */
public class ScoreTrackingListener implements HitListener {
    //members
    private Counter currentScore;

    /**
     * ScoreTrackingListener constructor.
     * @param scoreCounter - initialize number for score of game.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * hitEvent function.
     * This method is called whenever the beingHit object is hit.
     * @param beingHit - the being hit obj.
     * @param hitter - the ShutBall that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        //100 point for each dead alien
        if ((beingHit.getBlockType() == CommonEnumerations.BlockType.AlienBlock)
                && (hitter.getBallType() == CommonEnumerations.BallType.PaddleBullet)) {
            this.currentScore.increase(100);
        }

    }

    /**
     * hitEvent function.
     * @param beingHit - the being hit paddle.
     * @param hitter - the hitter ball.
     */
    public void hitEvent(Paddle beingHit, Ball hitter) {

    }
}
