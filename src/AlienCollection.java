import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * AlienCollection class.
 */
public class AlienCollection implements Sprite {
    //members
    private List<Block> aliens;
    private Counter numberOfLives;
    private GameLevel game;
    private GameEnvironment gameEnvironment;
    private long prevTime = System.currentTimeMillis();
    private Map<Integer, List<Block>> alienMap;
    private Counter numberOfAliens;

    /**
     * constructor method.
     * @param aliens - list of aliens.
     * @param numberOfLives - counter of player's lives.
     * @param game - GameLevel obj.
     * @param gameEnvironment - GameEnvironment obj.
     * @param numberOfAliens - counter of aliens.
     */

    AlienCollection(List<Block> aliens, Counter numberOfLives, GameLevel game, GameEnvironment gameEnvironment,
                    Counter numberOfAliens) {
        this.aliens = aliens;
        this.numberOfLives = numberOfLives;
        this.game = game;
        this.gameEnvironment = gameEnvironment;
        this.alienMap = getBlocksByArenas();
        this.numberOfAliens = numberOfAliens;
    }

    /**
     * getBlocksByArenas function.
     * @return aliens list splited by columns.
     */
    private Map<Integer, List<Block>> getBlocksByArenas() {
        Map<Integer, List<Block>> blocksByArenas = new HashMap<Integer, List<Block>>();
        List<Block> blocks1 = new ArrayList<Block>();
        List<Block> blocks2 = new ArrayList<Block>();
        List<Block> blocks3 = new ArrayList<Block>();
        List<Block> blocks4 = new ArrayList<Block>();
        List<Block> blocks5 = new ArrayList<Block>();
        List<Block> blocks6 = new ArrayList<Block>();
        List<Block> blocks7 = new ArrayList<Block>();
        List<Block> blocks8 = new ArrayList<Block>();
        List<Block> blocks9 = new ArrayList<Block>();
        List<Block> blocks10 = new ArrayList<Block>();
        for (Block item : this.aliens) {

            double x = item.getCollisionRectangle().getUpperLeft().getX();
            if (x == 80) {
                blocks1.add(item);
            } else if (x == 130) {
                blocks2.add(item);
            } else if (x == 180) {
                blocks3.add(item);
            } else if (x == 230) {
                blocks4.add(item);
            } else if (x == 280) {
                blocks5.add(item);
            } else if (x == 330) {
                blocks6.add(item);
            } else if (x == 380) {
                blocks7.add(item);
            } else if (x == 430) {
                blocks8.add(item);
            } else if (x == 480) {
                blocks9.add(item);
            } else if (x == 530) {
                blocks10.add(item);
            }

        }
        blocksByArenas.put(1, blocks1);
        blocksByArenas.put(2, blocks2);
        blocksByArenas.put(3, blocks3);
        blocksByArenas.put(4, blocks4);
        blocksByArenas.put(5, blocks5);
        blocksByArenas.put(6, blocks6);
        blocksByArenas.put(7, blocks7);
        blocksByArenas.put(8, blocks8);
        blocksByArenas.put(9, blocks9);
        blocksByArenas.put(10, blocks10);
        return blocksByArenas;
    }

    /**
     * getAliens method.
     * @return list of aliens.
     */
    public List<Block> getAliens() {
        return this.aliens;
    }

    /**
     * removeAlien function.
     * @param b - Block obj.
     */
    public void removeAlien(Block b) {
        for (Map.Entry<Integer, List<Block>> entry : this.alienMap.entrySet()) {
            if (entry.getValue().contains(b)) {
                this.alienMap.get(entry.getKey()).remove(b);
            }
        }
        this.aliens.remove(b);
        this.numberOfAliens.decrease(1);
    }

    /**
     * drawOn function.
     * draw the sprite to the screen.
     *
     * @param d - DrawSurface obj.
     */
    public void drawOn(DrawSurface d) {

        for (Block alien : this.aliens) {
            alien.drawOn(d);

        }
    }

    /**
     * addSprite function.
     *
     * @param b - Sprite obj.
     */
    public void addSprite(Block b) {
        this.aliens.add(b);
    }

    /**
     * timePassed function.
     * notify the sprite that time has passed.
     *
     * @param dt - the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
        if (!this.aliens.isEmpty()) {
            List<Block> copy = this.aliens;
            double blocksVelocity = this.aliens.get(0).getVelocity();
            double newVelocity;
            double mostLeftX = this.aliens.get(0).getCollisionRectangle().getUpperLeft().getX();
            double mostRightX = this.aliens.get(0).getCollisionRectangle().getUpperLeft().getX();
            double topY = this.aliens.get(0).getCollisionRectangle().getUpperLeft().getY();
            for (Block item : copy) {
                double x = item.getCollisionRectangle().getUpperLeft().getX();
                double y = item.getCollisionRectangle().getUpperLeft().getY();
                if (y > topY) {
                    topY = y;
                } else if (x < mostLeftX) {
                    mostLeftX = x;

                } else if (x > mostRightX) {
                    mostRightX = x;
                }
            }
            if (topY >= 470) {
                this.numberOfLives.decrease(1);

            } else if (mostLeftX <= 20 && blocksVelocity < 0) {
                newVelocity = -blocksVelocity * 1.1;
                for (Block item : this.aliens) {
                    item.setDx(newVelocity, 30);
                }
            } else if (mostRightX >= 780 - 40 && blocksVelocity > 0) {
                newVelocity = -blocksVelocity * 1.1;
                for (Block item : this.aliens) {
                    item.setDx(newVelocity, 30);
                }
            }
            Random rand = new Random();
            int value = rand.nextInt(11);
            if (value == 0) {
                value = 1;
            }
            if (this.alienMap.containsKey(value) && !this.alienMap.get(value).isEmpty()) {
                //find maxY
                Block shutBlock = this.alienMap.get(value).get(0);
                double maxY = this.alienMap.get(value).get(0).getCollisionRectangle().getUpperLeft().getY();
                List<Block> blocks = this.alienMap.get(value);
                for (Block item : blocks) {
                    double y = item.getCollisionRectangle().getUpperLeft().getY();
                    if (y >= maxY) {
                        shutBlock = item;
                    }
                }
                long usedTime = System.currentTimeMillis() - prevTime;
                long milliSecondLeftToSleep = 500 - usedTime;
                if (milliSecondLeftToSleep <= 0) {
                    shutBlock.getShootBall(this.gameEnvironment).addToGame(this.game);
                    prevTime = System.currentTimeMillis();
                }
            }
        }
    }

    /**
     * backToOriginalPos function.
     * moves alienCollection to original pos.
     */

    public void backToOriginalPos() {
        for (Block item: this.aliens) {
            item.changeOriginalPos();
        }
    }
}

