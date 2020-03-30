package lab7;

import java.util.ArrayList;
import java.util.List;

public class Player implements Runnable{
    private String name = "";
    private Board gameBoard;
    private List<Token> ownTokens = new ArrayList<>();
    private int k;

    public Player(String name, Board gameBoard, int k) {
        this.name = name;
        this.gameBoard = gameBoard;
        this.k = k;
    }

    @Override
    public void run()
    {
        try {
            int i = 1;//out of b exc
            while (true) {
                Token t;

                if (gameBoard.isLocked())
                    return;

                if (lenghtOfLongestAP() == k)
                    gameBoard.setLocked(true);

                //Dumbing down the computer - without this the first player always gets all the cards
                try {
                    Thread.sleep(10);
                }
                catch (InterruptedException ignored){}

                try {
                    t = gameBoard.getToken(i);
                } catch (IllegalArgumentException ex) {
                    continue;
                } finally {
                    i++;
                }
                ownTokens.add(t);
            }
        }
        catch (IndexOutOfBoundsException ignored){ // the game stops when the board is clear
        }
    }

    public String getName() {
        return name;
    }

    // Returns length of the longest AP subset in a given set
    //https://www.geeksforgeeks.org/longest-arithmetic-progression-dp-35/
    int lenghtOfLongestAP()
    {
        int n = ownTokens.size();
        if (n <= 2)  return n;

        // Create a table and initialize all values as 2. The value of
        // L[i][j] stores LLAP with set[i] and set[j] as first two
        // elements of AP. Only valid entries are the entries where j>i
        int[][] L = new int[n][n];
        int llap = 2;  // Initialize the result

        // Fill entries in last column as 2. There will always be
        // two elements in AP with last number of set as second
        // element in AP
        for (int i = 0; i < n; i++)
            L[i][n-1] = 2;

        // Consider every element as second element of AP
        for (int j=n-2; j>=1; j--)
        {
            // Search for i and k for j
            int i = j-1, k = j+1;
            while (i >= 0 && k <= n-1)
            {
                if (ownTokens.get(i).getNumber() + ownTokens.get(k).getNumber() < 2*ownTokens.get(j).getNumber())
                    k++;

                    // Before changing i, set L[i][j] as 2
                else if (ownTokens.get(i).getNumber() + ownTokens.get(k).getNumber() > 2*ownTokens.get(j).getNumber())
                {
                    L[i][j] = 2;
                    i--;
                }
                else
                {
                    // Found i and k for j, LLAP with i and j as first two
                    // elements is equal to LLAP with j and k as first two
                    // elements plus 1. L[j][k] must have been filled
                    // before as we run the loop from right side
                    L[i][j] = L[j][k] + 1;

                    // Update overall LLAP, if needed
                    llap = Math.max(llap, L[i][j]);

                    // Change i and k to fill more L[i][j] values for
                    // current j
                    i--; k++;
                }
            }

            // If the loop was stopped due to k becoming more than
            // n-1, set the remaining entities in column j as 2
            while (i >= 0)
            {
                L[i][j] = 2;
                i--;
            }
        }
        return llap;
    }
}
