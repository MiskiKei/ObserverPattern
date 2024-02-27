import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String args[]) {
        Scoring scoring = new Scoring();
        PredictionObserver predictionObserver = new PredictionObserver();
        GameStatsObserver gameStatsObserver = new GameStatsObserver(scoring);
        NewsObserver newsObserver = new NewsObserver(scoring);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Start a new game");
            System.out.println("2. Simulate 1 quarter");
            System.out.println("3. Print current score");
            System.out.println("4. Print current prediction");
            System.out.println("5. Print prediction stats");
            System.out.println("6. Print table of scores of all finished games");
            System.out.println("7. Generate a news piece title for the last finished game");
            System.out.println("8. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    if (scoring.isGameOngoing()) {
                        System.out.println();
                        System.out.println("A game is already ongoing. Cannot start a new game.");
                    } else {
                        System.out.println();
                        System.out.println("Starting a new game.");
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
                        System.out.println();
                        System.out.println("The game has already ended. Start a new game.");
                    } else {
                        System.out.println();
                        System.out.println("Start a new game first.");
                    }
                    break;
                case 3:
                    if (scoring.isGameOngoing()) {
                        System.out.println();
                        System.out.println("Current Score: " + scoring.getTeamAName() + " " + scoring.getTeamAScore()
                                + " - " + scoring.getTeamBName() + " " + scoring.getTeamBScore());
                    } else {
                        System.out.println();
                        System.out.println("No game in progress. Start a new game first.");
                    }
                    break;
                case 4:
                    if (scoring.isGameOngoing()) {
                        System.out.println();
                        System.out.println("Current Predicted Total Game Score: " + predictionObserver.getCurrentPrediction());
                    } else {
                        System.out.println();
                        System.out.println("No game in progress. Start a new game first.");
                    }
                    break;
                case 5:
                    System.out.println();
                    predictionObserver.displayPredictionStats();
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
                        System.out.println();
                        newsObserver.displayNews();
                    }
                    break;
                case 8:
                    System.out.println();
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println();
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                    break;

            }
        }
    }
}

