package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameFactoryTest {

    private GameFactory gameFactory;
    private PlayerFactory playerFactory;
    private Level level;
    private PointCalculator pointCalculator;

    @BeforeEach
    void setUp() {
        playerFactory = mock(PlayerFactory.class); // Mock PlayerFactory
        level = mock(Level.class); // Mock Level
        pointCalculator = mock(PointCalculator.class); // Mock PointCalculator
        gameFactory = new GameFactory(playerFactory);
    }

    /**
     * Test that a single player game is made
     */
    @Test
    void createSinglePlayerGame() {
        Player mockPlayer = mock(Player.class);
        when(playerFactory.createPacMan()).thenReturn(mockPlayer);

        Game game = gameFactory.createSinglePlayerGame(level, pointCalculator);

        assertThat(game).isInstanceOf(SinglePlayerGame.class);
        assertThat(((SinglePlayerGame) game).getLevel()).isEqualTo(level);
    }

    /**
     * Test that getPlayerFactory returns correctly
     */
    @Test
    void getPlayerFactory() {
        assertThat(gameFactory.getPlayerFactory()).isEqualTo(playerFactory);
    }
}