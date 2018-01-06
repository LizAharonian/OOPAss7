import biuoop.GUI;
import java.io.File;


/**
 * Ass7Game class.
 */
public class Ass7Game {
    /**
     * main function.
     * this function runs the game.
     *
     * @param args - string arr of requested levels.
     */
    public static void main(String[] args) {

        //create the game gui
        GUI gui = new GUI("Game", 800, 600);
        AnimationRunner animationRunner = new AnimationRunner(gui, 60);
        //load or create highScoreTable
        HighScoresTable highScoresTable = HighScoresTable.loadFromFile((new File("highscores")));
        if (highScoresTable.getHighScores().isEmpty()) {
            //create default score table
            highScoresTable = new HighScoresTable(4);
        }
        HighScoresAnimation highScoresAnimation = new HighScoresAnimation(highScoresTable, gui);
        //create menue
        Menu<Task<Void>> menuAnimation = new MenuAnimation<Task<Void>>(gui, animationRunner);
        menuAnimation.addSelection("h", "High Scores",
                new ShowHiScoresTask(animationRunner, highScoresAnimation, gui));
        menuAnimation.addSelection("q", "Exit",
                new ExitTask(animationRunner));
        menuAnimation.addSelection("s", "Start Game",
                new StartGameTask(animationRunner, gui, highScoresTable, "definitions/alien_level_definitions.txt"));

        while (true) {
            animationRunner.run(menuAnimation);
            Task<Void> task = menuAnimation.getStatus();
            if (task != null) {
                task.run();
            }
            menuAnimation.setStop(false);

        }

    }
}
