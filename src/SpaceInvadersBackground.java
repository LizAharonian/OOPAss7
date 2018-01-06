import biuoop.DrawSurface;

import java.awt.Color;

/**
 * SpaceInvadersBackground class.
 */
public class SpaceInvadersBackground implements Sprite {

    /**
     * drawOn function.
     * draw the sprite to the screen.
     * @param d - DrawSurface obj.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        //draw rectangle
        d.fillRectangle(0, 0, 800, 600);

    }


    /**
     * timePassed function.
     * notify the sprite that time has passed.
     * @param dt - the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {

    }
}
