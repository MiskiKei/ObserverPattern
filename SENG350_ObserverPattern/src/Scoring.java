import java.util.ArrayList;
import java.util.HashMap;
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
		return String.format("Game %d: Team A %d - Team B %d", currentGame, teamAScore, teamBScore);
	}

	public void startNewGame() {
		currentGame++;
		currentQuarter = 1;
		teamAScore = 0;
		teamBScore = 0;
		gameOngoing = true; // Set game status to ongoing
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
		System.out.println("Current Score in Game " + currentGame + " during Quarter " + currentQuarter + ": Team A "
				+ teamAScore + " - Team B " + teamBScore);
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
}