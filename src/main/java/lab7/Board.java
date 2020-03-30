package lab7;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    private List<Token> tokens = new ArrayList<Token>();
    boolean isLocked = false;

    Board(int n){
        tokens = IntStream.range(1, n).mapToObj(Token::new).collect(Collectors.toList());
    }

    /**
     * Returns an available token for the player that calls it
     * @param number The number the player wants to select
     * @return the Token with the selected number
     * @throws IllegalArgumentException if the number is not available on the board: in this case, the player should choose again
     * @throws IndexOutOfBoundsException if the board is empty and no number is available
     */
    public synchronized Token getToken(int number)
        throws IllegalArgumentException, IndexOutOfBoundsException
    {
        if (tokens.isEmpty())
            throw new IndexOutOfBoundsException("The board is empty");

        for (Token t : tokens){
            if (t.getNumber() == number)
            {
                tokens.remove(t);
                return t;
            }
        }

        throw new IllegalArgumentException("Number is not in the array, try another token");
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
