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

