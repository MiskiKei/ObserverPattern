class NewsObserver implements Observer {
    private String newsTitle;
    private Scoring scoring;

    public NewsObserver(Scoring scoring) {
        this.scoring = scoring;
    }

    @Override
    public void update(int teamAScore, int teamBScore) {
        String teamAName = scoring.getTeamAName();
        String teamBName = scoring.getTeamBName();

        if (teamAScore > teamBScore) {
            int difference = teamAScore - teamBScore;
            if (difference > 20) {
                newsTitle = teamAName + " beat " + teamBName + " by " + (difference) + " points in a landslide";
            } else {
                newsTitle = teamAName + " beat " + teamBName + " by " + (difference) + " points in a tightly contested game";
            }
        } else if (teamAScore < teamBScore) {
            int difference = teamBScore - teamAScore;
            if (difference > 20) {
                newsTitle = teamBName + " beat " + teamAName + " by " + (difference) + " points in a landslide";
            } else {
                newsTitle = teamBName + " beat " + teamAName + " by " + (difference) + " points in a tightly contested game";
            }
        } else {
            newsTitle = "The game between " + teamAName + " and " + teamBName + " ended in a tie";
        }
    }

    public void displayNews() {
        System.out.println(newsTitle);
    }
}
