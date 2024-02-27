import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

class Scoring implements Subject {
    private int teamAScore;
    private int teamBScore;
    private int currentQuarter;
    private int currentGame;
    private boolean gameOngoing;
    private ArrayList<Observer> observerList;
    private Map<Integer, String> finishedGames = new HashMap<>();
    private String teamAName;
    private String teamBName;

    public Scoring() {
        observerList = new ArrayList<>();
        currentQuarter = 1;
        currentGame = 0;
    }

    @Override
    public void registerObserver(Observer o) {
        observerList.add(o);
    }

    @Override
    public void unregisterObserver(Observer o) {
        observerList.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observerList) {
            observer.update(teamAScore, teamBScore);
        }
    }

    public void simulateGame() {
        if (!gameOngoing) {
            startNewGame();
        }

        if (currentQuarter <= 4) {
            simulateQuarter();
            if (currentQuarter == 4) {
                // After the game ends
                String gameResult = createGameResultString();
                finishedGames.put(currentGame, gameResult);

                System.out.println("The current game has ended.");
                gameOngoing = false;

                notifyObservers();

            } else {
                currentQuarter++;
            }
        } else {
            System.out.println("The game has already ended. Start a new game.");
        }

    }

    private String createGameResultString() {
        return String.format("Game %d: %s %d - %s %d", currentGame, teamAName, teamAScore, teamBName, teamBScore);
    }

    public void startNewGame() {
        currentGame++;
        currentQuarter = 1;
        teamAScore = 0;
        teamBScore = 0;
        gameOngoing = true; // Set game status to ongoing
        teamAName = generateTeamName();
        teamBName = generateTeamName();
    }

    private String generateTeamName() {
        List<String> adjectives = List.of("Blue", "Red", "Green", "Yellow", "Swift", "Mighty", "Thunder");
        List<String> nouns = List.of("Hawks", "Lions", "Wolves", "Bears", "Rockets", "Tigers", "Eagles");
        Random random = new Random();

        return adjectives.get(random.nextInt(adjectives.size())) + " " + nouns.get(random.nextInt(nouns.size()));
    }

    public boolean isGameOngoing() {
        return gameOngoing;
    }

    private void simulateQuarter() {
        // Simulate a quarter with random scores
        Random random = new Random();
        teamAScore += random.nextInt(30) + 10;
        teamBScore += random.nextInt(30) + 10;
        notifyObservers();
        printCurrentScore();
    }

    private void printCurrentScore() {
        System.out.println("Current Score in Game " + currentGame + " during Quarter " + currentQuarter + ": " + teamAName
                + " " + teamAScore + " - " + teamBName + " " + teamBScore);
    }

    public int getTeamAScore() {
        return teamAScore;
    }

    public int getTeamBScore() {
        return teamBScore;
    }

    public Map<Integer, String> getFinishedGames() {
        return finishedGames;
    }

    public int getCurrentQuarter() {
        return currentQuarter;
    }

    public int getCurrentGame() {
        return currentGame;
    }

    public String getTeamAName() {
        return teamAName;
    }

    public String getTeamBName() {
        return teamBName;
    }
}
