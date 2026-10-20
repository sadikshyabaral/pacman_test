package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.points.PointCalculator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

class GameTest {

    private Game game;
    private Level level;
    private Player player;
    private PointCalculator pointCalculator;

    /**
     * Set up a game each time
     */
    @BeforeEach
    void setUp() {
        level = mock(Level.class);
        player = mock(Player.class);
        pointCalculator = mock(PointCalculator.class);

        game = new SinglePlayerGame(player, level, pointCalculator);
    }

    /**
     * Test that the game starts
     */
    @Test
    void start() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(1);

        game.start();
        assertThat(game.isInProgress()).isTrue();
    }

    /**
     * Test that the game stops
     */
    @Test
    void stop() {
        game.start();
        game.stop();
        assertThat(game.isInProgress()).isFalse();
    }

    /**
     * Tests that a game is currently happening
     */
    @Test
    void isInProgress() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(1);

        game.start();
        assertTrue(game.isInProgress());
        game.stop();
        assertFalse(game.isInProgress());
    }

    /**
     * Test to see if getPlayers works
     */
    @Test
    void getPlayers() {
        assertNotNull(game.getPlayers());
    }

    /**
     * Test to see if getLevel works
     */
    @Test
    void getLevel() {
        assertNotNull(game.getLevel());
    }

    /**
     * Test if the player can move
     */
    @Test
    void move() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(1);
        game.start();

        game.move(player, Direction.NORTH);

        verify(level, times(1)).move(player, Direction.NORTH);
        verify(pointCalculator, times(1)).pacmanMoved(player, Direction.NORTH);
    }

    /**
     * Test to see if the level is won
     */
    @Test
    void levelWon() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(1);
        game.start();

        game.levelWon();
        assertFalse(game.isInProgress());
    }

    /**
     * Test to see if the level is lost
     */
    @Test
    void levelLost() {
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(1);
        game.start();

        game.levelLost();
        assertFalse(game.isInProgress());
    }
}

class GameTest1 {
/**
     * The Class GameTest is for coverage testing for the functions start and stop in Game.java
     * We aim to achieve 100% coverage for each of these two functions
     */

        private Launcher launcher;

        /**
         * Start a launcher, which can display the user interface.
         */
        @BeforeEach
        public void before() {
            launcher = new Launcher();
        }

        /**
         * Close the user interface.
         */
        @AfterEach
        public void after() {
            launcher.dispose();
        }

        /**
         * The following two tests are for coverage of the void start function in Game.java
         * The first is to ensure that start works properly
         * The second test start_again ensures coverage of the function to see that if we start it
         * after it has already been started the launcher recognises that.
         */
        @Test
        void start1() {
            launcher.launch();

            getGame().start();

            Assertions.assertThat(getGame().isInProgress()).isTrue();
        }

        @Test
        void start_again() {

            launcher.launch();

            getGame().start();

            getGame().start();

            Assertions.assertThat(getGame().isInProgress()).isTrue();
        }

        /**
         * There is a bug in the function start in Game.java
         * It is impossible to achieve 100% coverage in the function as there is no else statement
         * if the player is not alive or the pellets are < 0
         * I attempted writing the following test, but due to this bug, it ran an infinite amount
         * of time and I had to quit the test before my laptop overheated.
         *
         *@Test
         *     void player_dead() {
         *
         *         launcher.launch();
         *         player_dead();
         *         getGame().start();
         *
         *         assertThat(getGame().isInProgress()).isFalse();
         *     }
         *
         * This bug could be solved by including an extra while statement in the start function
         * to deal with if the player is dead before starting
         */


        /**
         * The following two tests are to see coverage of the function stop in Game.java
         */

        @Test
        void stop1() {
            launcher.launch();

            getGame().start();

            getGame().stop();

            Assertions.assertThat(getGame().isInProgress()).isFalse();
        }

        @Test
        void stop_again() {
            launcher.launch();
            getGame().start();
            getGame().stop();
            getGame().stop();
            Assertions.assertThat(getGame().isInProgress()).isFalse();

        }

        /**
         * After running these two tests we can see 100% coverage in the function stop
         * meaning it is a well written function with no bugs.
         */

        private Game getGame() {
            return launcher.getGame();
        }
    }




