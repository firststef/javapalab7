package lab7;

import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game {
    private List<Player> players;
    private List<Thread> threads;
    private Board board;
    private int n;
    private int m;
    private int k;

    public Game(int n, int m, int k, List<String> playerNames) {
        this.n = n;
        this.m = m;
        this.k = k;
        board = new Board(n);
        players = playerNames.stream().map(name -> new Player(name, board, k)).collect(Collectors.toList());
    }

    public void run(){
        threads = players.stream().map(Thread::new).collect(Collectors.toList());

        for (Thread t : threads){
            t.start();
        }

        boolean gameEnded = false;
        for (Thread t : threads) {
            try {
                if (gameEnded)
                    t.interrupt();
                else
                    t.join();
            }
            catch (InterruptedException ex){
                System.out.println("Unexpected error");
                gameEnded = true;
            }
        }

        System.out.println("Game completed. Results:");
        for(Player p : players){
            int result = p.lenghtOfLongestAP();
            if (result == k)
                System.out.println("Player " + p.getName() + " has won");
            else
                System.out.println("Player " + p.getName() + " has " + result + " score");
        }
    }
}
