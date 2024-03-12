import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QLearner {

    public static void main(String[] args) {
        BlackJackEnv game = new BlackJackEnv(BlackJackEnv.NONE);
		//Init your QTable
		ArrayList<float[]> QTable = new ArrayList<float[]>(); // matrix form ?

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
        boolean GameOver; // defaults to false
        boolean exploration = true; // random moves until we get enough data
        int iteration = 0; // this is for the Q table idx ?
        int action; // action for the game

        float[] row = new float[4]; // row of Q table
        float p_state; // containing the value of player and dealer
        float d_state;
        float q_action; // action per given state
        float q_val; // Q value given state (0 until update)

        ArrayList<String> gamestate;
        gamestate = game.reset(); // starts the game

        // get this in a while loop
        while (!GameOver) {
            if (exploration) {
                Random random = new Random();
                action = random.nextInt(2);
            }
            gamestate = game.step(action);

            List<String> playerCards = BlackJackEnv.getPlayerCards(gamestate);
            int sumOfPlayerCards = BlackJackEnv.totalValue(playerCards);
            List<String> dealerCards = BlackJackEnv.getDealerCards(gamestate);
            int sumOfDealerCards = BlackJackEnv.totalValue(dealerCards);

            int reward = Integer.parseInt(gamestate.get(1)); // get the reward

            // now we update Q table
            p_state = (float) sumOfPlayerCards;
            d_state = (float) sumOfDealerCards;
            q_action = (float) action;
            q_val = (float) reward;

            row[0] = p_state;
            row[1] = d_state;
            row[2] = q_action;
            row[3] = q_val;

            QTable.add(row);
            if (gamestate.get(0)) {
                GameOver = true;
            }
        }

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
