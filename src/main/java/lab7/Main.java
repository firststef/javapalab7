package lab7;

import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        String[] players = new String[] {"wow", "wow2", "wow3"};
        Game g = new Game(10, 10, 4, Arrays.asList(players));
        g.run();
    }
}
