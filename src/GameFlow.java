import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import java.io.File;
import java.util.List;


/**
 * GameFlow class.
 */
public class GameFlow {
    //members
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private Counter livesCounter;
    private Counter scoreCounter;
    private Counter blocksCounter;
    private GUI gui;
    private HighScoresTable highScoresTable;
    private int once = 0;
    private int levelNumber = 1;
    private GameLevel level;

    /**
     * GameFlow function.
     * @param ar - AnimationRunner obj.
     * @param ks - KeyboardSensor obj.
     * @param gui - GUI obj.
     * @param highScoresTable - HighScoresTable obj.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, GUI gui, HighScoresTable highScoresTable) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.blocksCounter = new Counter();
        this.scoreCounter = new Counter();
        this.livesCounter = new Counter();
        this.livesCounter.increase(3);
        this.gui = gui;
        this.highScoresTable = highScoresTable;
    }

    /**
     * runLevels function.
     * @param levels - list of LevelInformation objects.
     */
    public void runLevels(List<LevelInformation> levels) {

        for (LevelInformation levelInfo : levels) {

            level = new GameLevel(levelInfo, this.keyboardSensor, this.animationRunner,
                    this.gui, this.livesCounter, this.scoreCounter, this.blocksCounter);
            level.initialize();
            while (level.getAlien().size() > 0 && livesCounter.getValue() > 0) {

                int prevLives = this.livesCounter.getValue();
                level.playOneTurn();
                if (this.blocksCounter.getValue() == 0) {
                    double newAlienVelocity = levelInfo.getBlocksVelocity() * 1.1;
                    levelInfo.setBlocksVelocity(newAlienVelocity);
                }
            }
            if (livesCounter.getValue() <= 0) {
                //lose screen
                this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
                        new EndScreen(this.keyboardSensor,
                                "Game Over. Your score is: ", this.scoreCounter)));
                presentHighScoreScreen();
                return;
            }

        }

        while (livesCounter.getValue() > 0) {
            levelNumber += 1;
            String name = levels.get(0).levelName().substring(0, levels.get(0).levelName().length() - 2)
                    + " " + String.valueOf(this.levelNumber);
            levels.get(0).setNewLevelName(name);
            levels.get(0).setOriginalVelocity(levels.get(0).getBlocksVelocity() * 2);
            levels.get(0).setBlocksVelocity(levels.get(0).getBlocksVelocity() * 2);
            runLevels(levels);
        }

    }

    /**
     * presentHighScoreScreen function.
     */
    private void presentHighScoreScreen() {
        int rank = this.highScoresTable.getRank(this.scoreCounter.getValue());
        if (rank <= this.highScoresTable.size()) {
            DialogManager dialog = gui.getDialogManager();
            String name = dialog.showQuestionDialog("Name", "What is your name?", "");
            ScoreInfo scoreInfo = new ScoreInfo(name, this.scoreCounter.getValue());
            this.highScoresTable.add(scoreInfo);
        }
        try {
            this.highScoresTable.save((new File("highscores")));
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
                new HighScoresAnimation(this.highScoresTable, this.gui)));

    }
}
