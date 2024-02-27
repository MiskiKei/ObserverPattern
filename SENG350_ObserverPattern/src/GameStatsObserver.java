import java.util.HashMap;
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
    Map<String, Integer> teamWins = null;

    public GameStatsObserver(Scoring scoring) {
        this.scoring = scoring;
        totalGames = 0;
        teamAWins = 0;
        teamBWins = 0;
        completedGames = new HashSet<>();
        teamWins = new HashMap<>();  
        
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

                String winningTeam;
                if (teamAScore > teamBScore) {
                    teamAWins++;
                    winningTeam = teamAName;
                } else {
                    teamBWins++;
                    winningTeam = teamBName;
                }


                // Check if team name already exists in the win count map
                if (teamWins.containsKey(winningTeam)) {
                    teamWins.put(winningTeam, teamWins.get(winningTeam) + 1);
                } else {
                    teamWins.put(winningTeam, 1);
                }
            }
        }
    }

    public void displayStats() {
        System.out.println("Total Games: " + totalGames);
        for (Map.Entry<String, Integer> entry : teamWins.entrySet()) {
            System.out.println(entry.getKey() + " Wins: " + entry.getValue());
        }
    }
}
