import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            String teamAName = scoring.getTeamAName();
            String teamBName = scoring.getTeamBName();

            if (!completedGames.contains(gameId)) {
                completedGames.add(gameId);

                totalGames++;
                String gameResult = entry.getValue();
                Pattern pattern = Pattern.compile("Game (\\d+): " + teamAName + " (\\d+) - " + teamBName + " (\\d+)");
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
        System.out.println(scoring.getTeamAName() + " Wins: " + teamAWins);
        System.out.println(scoring.getTeamBName() + " Wins: " + teamBWins);
    }
}
