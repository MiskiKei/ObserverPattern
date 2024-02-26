import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Implemented by scoring class to communicate with observers
interface Subject {
	public void registerObserver(Observer o);

	public void unregisterObserver(Observer o);

	public void notifyObservers();
}

class Scoring implements Subject {
    private int teamAScore;
    private int teamBScore;
    private ArrayList<Observer> observerList;

    public Scoring() {
        observerList = new ArrayList<>();
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

    public void simulateQuarter() {
        // Simulate a quarter with random scores
        Random random = new Random();
        teamAScore += random.nextInt(30) + 10;
        teamBScore += random.nextInt(30) + 10;
        notifyObservers();
    }

    public int getTeamAScore() {
        return teamAScore;
    }

    public int getTeamBScore() {
        return teamBScore;
    }
}

interface Observer {
    void update(int teamAScore, int teamBScore);
}

class PredictionObserver implements Observer {
    private int correctPredictions;
    private List<Integer> pointErrorRates;
    private int currentPrediction;

    public PredictionObserver() {
        correctPredictions = 0;
        pointErrorRates = new ArrayList<>();
        currentPrediction = 0; 
    }

    @Override
    public void update(int teamAScore, int teamBScore) {
        int predictedFinalScore = teamAScore + teamBScore + 20;
        int actualFinalScore = teamAScore + teamBScore;

        currentPrediction = predictedFinalScore; //Current prediction

        if (predictedFinalScore == actualFinalScore) {
            correctPredictions++;
        }

        int pointError = Math.abs(predictedFinalScore - actualFinalScore); //Points off from predicted to actual
        pointErrorRates.add(pointError);
    }

    public int getCurrentPrediction() {
        return currentPrediction;
    }

    public void displayPredictionStats() {
        System.out.println("Prediction Stats:");
        System.out.println("Current Prediction: " + currentPrediction);
        System.out.println("Correct Final Results: " + correctPredictions);

        if (!pointErrorRates.isEmpty()) {
            double averagePointErrorRate = calculateAverage(pointErrorRates);
            System.out.println("Average Point Error Rate: " + averagePointErrorRate);
        } else {
            System.out.println("No predictions made yet.");
        }
    }
    
    private double calculateAverage(List<Integer> points) {
        int sum = 0;
        for (int value : points) {
            sum += value;
        }
        return (double) sum / points.size();
    }
}

class GameStatsObserver implements Observer {
    private int totalGames;
    private int teamAWins;
    private int teamBWins;

    @Override
    public void update(int teamAScore, int teamBScore) {
        totalGames++;
        if (teamAScore > teamBScore) {
            teamAWins++;
        } else {
            teamBWins++;
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
		PredictionObserver predictionObserver = new PredictionObserver();
		GameStatsObserver gameStatsObserver = new GameStatsObserver();
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
	            System.out.println("7. Generate a news piece title");
	            System.out.println("8. Exit");
		}

	}
}
