import java.util.Random;

class PredictionObserver implements Observer {
	private int correctPredictions;
	private int totalPointError;
	private int currentPrediction;
	private int totalPredictions;
	private Random random;

	public PredictionObserver() {
		correctPredictions = 0;
		totalPointError = 0;
		currentPrediction = 0;
		totalPredictions = 0;
		random = new Random();
	}

	@Override
	public void update(int teamAScore, int teamBScore) {
		int basePrediction = teamAScore + teamBScore;

		int randomPointError = generateRandomPointError();

		currentPrediction = basePrediction + randomPointError;

		totalPointError += Math.abs(currentPrediction - (teamAScore + teamBScore));
		totalPredictions++;

		int predictionRange = 5;

		if (Math.abs(currentPrediction - (teamAScore + teamBScore)) <= predictionRange) {
			correctPredictions++;
		}
	}

	public double getAveragePointError() {
		return totalPredictions == 0 ? 0 : (double) totalPointError / totalPredictions;
	}

	private int generateRandomPointError() {
		// Range from -20 and 20
		return random.nextInt(41) - 20;
	}

	public int getCurrentPrediction() {
		return currentPrediction;
	}

	public void displayPredictionStats() {
		System.out.println("Prediction Stats:");
		System.out.println("Average Point Error Rate: " + getAveragePointError());
		System.out.println("Correct Final Results Predictions within 5 Point Range: " + correctPredictions);
	}
}
