import biuoop.drawsurface;

/**
 * animation interface.
 */
public interface animation {
    /**
     * dooneframe function.
     * @param d - drawsurfaceobj for paint.
     * @param dt - specifies the amount of seconds passed since the last call.
     */
    void dooneframe(drawsurface d, double dt);

    /**
     * shouldstop function.
     * @return true for stop, false otherwise.
     */
    boolean shouldstop();
}
