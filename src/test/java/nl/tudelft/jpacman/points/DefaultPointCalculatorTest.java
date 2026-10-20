package nl.tudelft.jpacman.points;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DefaultPointCalculatorTest {
    DefaultPointCalculator calculator;
    Player playerMock;

    @BeforeEach
    void setUp() {
        calculator = new DefaultPointCalculator();
        playerMock = mock(Player.class, withSettings().verboseLogging());
    }

    // testing that no points are added to user's score if PacMan collides with a ghost since that will cause PacMan to die
    @Test
    public void noPointsAddedIfCollidedWithAGhost() {
        Ghost ghostMock = mock(Ghost.class, withSettings().verboseLogging());
        calculator.collidedWithAGhost(playerMock, ghostMock);
        verify(playerMock, never()).addPoints(any(Integer.class));
    }

    // testing that points are added if PacMan consumes a pellet
    @Test
    public void pointsAddedIfConsumedAPellet() {
        Pellet pelletMock = mock(Pellet.class, withSettings().verboseLogging());
        calculator.consumedAPellet(playerMock, pelletMock);
        verify(playerMock).addPoints(any(Integer.class)); // standard pellet value is 10 apparently but no pre-conditions on pellet value in Pellet class
    }

    // testing no points are added if PacMan moves to a spot with no pellet nor ghost
    @Test
    public void noPointsAddedIfPacmanMoved() {
        // ran into issue where could not mock nor use any(Direction.class) since is an ENUM
        calculator.pacmanMoved(playerMock, Direction.NORTH);
        verify(playerMock, never()).addPoints(any(Integer.class));

        calculator.pacmanMoved(playerMock, Direction.SOUTH);
        verify(playerMock, never()).addPoints(any(Integer.class));

        calculator.pacmanMoved(playerMock, Direction.WEST);
        verify(playerMock, never()).addPoints(any(Integer.class));

        calculator.pacmanMoved(playerMock, Direction.EAST);
        verify(playerMock, never()).addPoints(any(Integer.class));
    }
}
