import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

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
    private int predictedFinalScore;

    @Override
    public void update(int teamAScore, int teamBScore) {
        predictedFinalScore = teamAScore + teamBScore + 20; //Cumulative Scores + 20 
    }

    public void displayPrediction() {
        System.out.println("Predicted Final Score: " + predictedFinalScore);
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




/*class AverageScoreDisplay implements Observer {
	private float runRate;
	private int predictedScore;

	public void update(int runs, int wickets, float overs) {
		this.runRate = (float) runs / overs;
		this.predictedScore = (int) (this.runRate * 50);
		display();
	}

	public void display() {
		System.out.println(
				"\nAverage Score Display: \n" + "Run Rate: " + runRate + "\nPredictedScore: " + predictedScore);
	}
}

class CurrentScoreDisplay implements Observer {
	private int runs, wickets;
	private float overs;

	public void update(int runs, int wickets, float overs) {
		this.runs = runs;
		this.wickets = wickets;
		this.overs = overs;
		display();
	}

	public void display() {
		System.out
				.println("\nCurrent Score Display:\n" + "Runs: " + runs + "\nWickets:" + wickets + "\nOvers: " + overs);
	}
}

// Driver Class
class Main {
	public static void main(String args[]) {
		// create objects for testing
		AverageScoreDisplay averageScoreDisplay = new AverageScoreDisplay();
		CurrentScoreDisplay currentScoreDisplay = new CurrentScoreDisplay();

		// pass the displays to Cricket data
		CricketData cricketData = new CricketData();

		// register display elements
		cricketData.registerObserver(averageScoreDisplay);
		cricketData.registerObserver(currentScoreDisplay);

		// in real app you would have some logic to
		// call this function when data changes
		cricketData.dataChanged();

		// remove an observer
		cricketData.unregisterObserver(averageScoreDisplay);

		// now only currentScoreDisplay gets the
		// notification
		cricketData.dataChanged();
	}*/

