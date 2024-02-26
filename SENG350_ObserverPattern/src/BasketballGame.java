import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Implemented by scoring class to communicate with observers
interface Subject {
	public void registerObserver(Observer o);

	public void unregisterObserver(Observer o);

	public void notifyObservers();
}

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

				System.out.println(
						"The game has ended. CurrentGame: " + currentGame + ", Games Size: " + finishedGames.size());
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
		return String.format("Game %d: TeamA %d - TeamB %d", currentGame, teamAScore, teamBScore);
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
		System.out.println(
				"Current Score after Quarter " + currentQuarter + ": TeamA " + teamAScore + " - TeamB " + teamBScore);
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

interface Observer {
	void update(int teamAScore, int teamBScore);
}

class PredictionObserver implements Observer {
	private int correctPredictions;
	private int currentPrediction;

	public PredictionObserver() {
		correctPredictions = 0;
		currentPrediction = 0;
	}

	@Override
	public void update(int teamAScore, int teamBScore) {
		int predictedFinalScore = teamAScore + teamBScore + 20;
		int actualFinalScore = teamAScore + teamBScore;

		currentPrediction = predictedFinalScore; // Current prediction

		if (predictedFinalScore == actualFinalScore) {
			correctPredictions++;
		}
	}

	public int getCurrentPrediction() {
		return currentPrediction;
	}

	public void displayPredictionStats() {
		System.out.println("Prediction Stats:");
		System.out.println("Current Prediction: " + currentPrediction);
		System.out.println("Correct Final Results Predictions: " + correctPredictions);
	}
}

class GameStatsObserver implements Observer {
	private Scoring scoring;
	private int totalGames;
	private int teamAWins;
	private int teamBWins;
	private Set<Integer> completedGames;

	public GameStatsObserver(Scoring scoring) {
		this.scoring = scoring;
		totalGames = 0;
		teamAWins = 0;
		teamBWins = 0;
		completedGames = new HashSet<>();
	}

	@Override
	public void update(int teamAScore, int teamBScore) {
		if (!scoring.isGameOngoing()) {
			Map<Integer, String> finishedGames = scoring.getFinishedGames();
			calculateGameResults(finishedGames);
		}
	}

	private void calculateGameResults(Map<Integer, String> finishedGames) {
		for (Map.Entry<Integer, String> entry : finishedGames.entrySet()) {
			int gameId = entry.getKey();
			int currentGame = 0; 
			int teamAScore = 0; 
			int teamBScore = 0; 

			if (!completedGames.contains(gameId)) {
				completedGames.add(gameId);

				totalGames++;
				String gameResult = entry.getValue();
				Pattern pattern = Pattern.compile("Game (\\d+): TeamA (\\d+) - TeamB (\\d+)");
				Matcher matcher = pattern.matcher(gameResult);
				if (matcher.find()) {
					currentGame = Integer.parseInt(matcher.group(1));
					teamAScore = Integer.parseInt(matcher.group(2));
					teamBScore = Integer.parseInt(matcher.group(3));
				}

				if (teamAScore > teamBScore) {
					teamAWins++;
				} else {
					teamBWins++;
				}
			}
		}
	}

	public void displayStats() {
		System.out.println("Total Games: " + totalGames);
		System.out.println("Team A Wins: " + teamAWins);
		System.out.println("Team B Wins: " + teamBWins);
	}
}

class NewsObserver implements Observer {
	private String newsTitle;

	@Override
	public void update(int teamAScore, int teamBScore) {
		if (teamAScore > teamBScore) {
			newsTitle = "TeamA beat TeamB by " + (teamAScore - teamBScore) + " points";
		} else if (teamAScore < teamBScore) {
			newsTitle = "TeamB beat TeamA by " + (teamBScore - teamAScore) + " points";
		} else {
			newsTitle = "The game between TeamA and TeamB ended in a tie";
		}
	}

	public void displayNews() {
		System.out.println(newsTitle);
	}
}

class Main {
	public static void main(String args[]) {
		Scoring scoring = new Scoring();
		PredictionObserver predictionObserver = new PredictionObserver();
		GameStatsObserver gameStatsObserver = new GameStatsObserver(scoring);
		NewsObserver newsObserver = new NewsObserver();

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\nMenu:");
			System.out.println("1. Start a new game");
			System.out.println("2. Simulate 1 quarter");
			System.out.println("3. Print current score");
			System.out.println("4. Print current prediction");
			System.out.println("5. Print prediction stats");
			System.out.println("6. Print table of scores of all finished games");
			System.out.println("7. Generate a news piece title for last finished game");
			System.out.println("8. Exit");

			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();

			switch (choice) {
			case 1:
				if (scoring.isGameOngoing()) {
					System.out.println("A game is already ongoing. Cannot start a new game.");
				} else {
					scoring.registerObserver(predictionObserver);
					scoring.registerObserver(gameStatsObserver);
					scoring.registerObserver(newsObserver);
					scoring.startNewGame();
				}
				break;
			case 2:
				if (scoring.isGameOngoing() && scoring.getCurrentQuarter() <= 4) {
					System.out.println();
					scoring.simulateGame();
				} else if (!scoring.isGameOngoing()) {
					System.out.println("The game has already ended. Start a new game.");
				} else {
					System.out.println("Start a new game first.");
				}
				break;
			case 3:
				if (scoring.isGameOngoing()) {
					System.out.println();
					System.out.println(
							"Current Score: TeamA " + scoring.getTeamAScore() + " - TeamB " + scoring.getTeamBScore());
				} else {
					System.out.println();
					System.out.println("No game in progress. Start a new game first.");
				}
				break;
			case 4:
				if (scoring.isGameOngoing()) {
					System.out.println();
					System.out.println("Current Prediction: " + predictionObserver.getCurrentPrediction());
				} else {
					System.out.println();
					System.out.println("No game in progress. Start a new game first.");
				}
				break;
			case 5:
				if (scoring.isGameOngoing()) {
					System.out.println();
					predictionObserver.displayPredictionStats();
				} else {
					System.out.println();
					System.out.println("No game in progress. Start a new game first.");
				}
				break;
			case 6:
				if (scoring.getFinishedGames().isEmpty()) {
					System.out.println();
					System.out.println("No finished games. Simulate some games first.");
				} else {
					System.out.println();
					for (Map.Entry<Integer, String> entry : scoring.getFinishedGames().entrySet()) {
						System.out.println(entry.getValue());
						System.out.println();
					}
					gameStatsObserver.displayStats();
				}
				break;

			case 7:
				if (scoring.getFinishedGames().isEmpty()) {
					System.out.println();
					System.out.println("No finished games. Simulate a game first.");
				} else {
					newsObserver.displayNews();
				}
				break;
			case 8:
				System.out.println("Exiting the program. Goodbye!");
				scanner.close();
				System.exit(0);
				break;
			default:
				System.out.println("Invalid choice. Please enter a number between 1 and 8.");
				break;

			}
		}
	}
}
