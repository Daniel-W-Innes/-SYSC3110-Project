package helpers;

import org.junit.jupiter.api.Test;

class GameBuilderTest {

    @Test
    void getStartingBoard() {

        // If the game builder builds an invalid board, then an exception will be thrown from the
        // static getStartingBoard() method as that is where the board is created

        GameBuilder.getStartingBoard(1);
        GameBuilder.getStartingBoard(20);
        GameBuilder.getStartingBoard(2);
        GameBuilder.getStartingBoard(3);
        GameBuilder.getStartingBoard(60);
        GameBuilder.getStartingBoard(-1); // Default terrain
    }
}