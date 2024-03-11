import java.util.ArrayList;
import java.util.List;

public class QLearner {

    public static void main(String[] args) {
        BlackJackEnv game = new BlackJackEnv(BlackJackEnv.NONE);
		//Init your QTable
		ArrayList<float[]> QTable = new ArrayList<float[]>(); // matrix form ?
        float[] state = new float[1];
        float[] action = new float[1]; // suppose the first play should be to hit by default..
        float[] Q = new float[1];
        QTable.add(state); // finish initializing the Q-table
        QTable.add(action);
        QTable.add(Q);

		//Variables to measure and report average performance
		double totalReward = 0.0;
        int numberOfGames = 0;
        while (notDone()) {
        	// Make sure the playOneGame method returns the end-reward of the game
            totalReward += playOneGame(game,QTable);
            numberOfGames++;
            if ((numberOfGames % 10000) == 0)
                System.out.println("Avg reward after " + numberOfGames + " games = " + 
                						(totalReward / ++numberOfGames));
        }
        // Show the learned QTable
        outputQTable(QTable);
    }

    private static double playOneGame(BlackJackEnv game, ArrayList<float[]> QTable) {
        boolean canPlay; // defaults to false
        int autoIter = 0; // this is for the Q table idx ?

        ArrayList<String> gamestate;
        gamestate = game.reset(); // starts the game

        while (!canPlay) {
            // we only get control when our cards VALUE REACHES 12 !!!!!
            List<String> playerState = game.getPlayerCards(gamestate);
            List<String> dealerState = game.getDealerCards(gamestate);

            int playerSum = 0;
            int dealerSum = 0;

            for (String s : playerState) {
                int cardVal = game.valueOf(s);
                playerSum += cardVal;
            }

            for (String s : dealerState) {
                int cardVal = game.valueOf(s);
                dealerSum += cardVal;
            }

            if (playerSum >= 12) {
                canPlay = true; // now we can play
            }
        }





        float[] QTableState = QTable.get(0);
        QTableState[0] = playerState;
        QTableState[1] = dealerState;

        float[] QTableAction =

    	You will probably require a loop
    	You will need to compute/select/find/fetch s,a,s' and r
    	Then update the right values in the QTable
    	...
    	// Don't forget to return the outcome/reward of the game
        return Double.parseDouble(finalGameState.get(1));
    }

	// Example stopping condition: fixed number of games
    private static int episodeCounter = 0;
    private static boolean notDone() {
        episodeCounter++;
        return (episodeCounter <= 1000000);
    }

    private static void outputQTable(ArrayList<float[]> QTable) {
        float[] s = QTable.get(0);
        float[] a = QTable.get(1);
        float[] q = QTable.get(2);
        float playerS = s[0];
        float dealerS = s[1];
        float action = a[0];
        float qval = q[0];

        System.out.println("PLAYER VALUE | DEALER VALUE | ACTION | Q VALUE");
        System.out.println("    " + playerS + "    " + dealerS + "    " + action + "    " + qval);
        System.out.println("----------------------------------------------");
    }
}
