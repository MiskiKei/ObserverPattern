class NewsObserver implements Observer {
	private String newsTitle;

	@Override
	public void update(int teamAScore, int teamBScore) {
		if (teamAScore > teamBScore) {
			int difference = teamAScore - teamBScore;
			if (difference > 20) {
				newsTitle = "Team A beat Team B by " + (difference) + " points in a landslide";
			} else {
				newsTitle = "Team A beat Team B by " + (difference) + " points in a tightly contested game";
			}
		} else if (teamAScore < teamBScore) {
			int difference = teamBScore - teamAScore;
			if (difference > 20) {
				newsTitle = "Team B beat Team A by " + (difference) + " points in a landslide";
			} else {
				newsTitle = "Team B beat Team A by " + (difference) + " points in a tightly contested game";
			}

		} else {
			newsTitle = "The game between Team A and Team B ended in a tie";
		}
	}

	public void displayNews() {
		System.out.println(newsTitle);
	}
}