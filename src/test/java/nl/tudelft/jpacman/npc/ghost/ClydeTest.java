package nl.tudelft.jpacman.npc.ghost;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;


public class ClydeTest {

    private PlayerFactory playerfactory;
    private LevelFactory levelfactory;
    private GhostFactory ghostFactory;
    private BoardFactory boardfactory;
    private GhostMapParser parser;
    private Level level;

    @BeforeEach
    void setUp() {
        PacManSprites sprites = new PacManSprites();
        playerfactory = new PlayerFactory(sprites);
        ghostFactory = new GhostFactory(sprites);
        levelfactory = new LevelFactory(sprites, ghostFactory,  mock(PointCalculator.class));
        boardfactory = new BoardFactory(sprites);
        parser = new GhostMapParser(levelfactory, boardfactory, ghostFactory);
    }

    /**
     * test if there's no player
     */
    @Test
    void noPlayer() {
        level = parser.parseMap(
                Lists.newArrayList(
                        "#############",
                        "#          C#",
                        "#############"));

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assert clyde != null;

        assertThat(clyde.nextAiMove()).isEqualTo(Optional.empty());
    }

    /**
     * When Clyde is far away from Pac-Man (beyond eight grid spaces),
     * trying to move to Pac-Man's exact location
     */
    @Test
    void farAwayFromPacMan() {
        level = parser.parseMap(
                Lists.newArrayList(
                        "##############",
                        "#C          P#",
                        "##############"));
        Player pacman = playerfactory.createPacMan();
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.WEST);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());

        assertThat(clyde.nextAiMove()).isEqualTo(Optional.of(Direction.EAST));
    }

    /**
     * Clyde gets within eight grid spaces of Pac-Man,
     * he automatically changes his behavior and runs away
     */
    @Test
    void nearPacMan() {
        level = parser.parseMap(
                Lists.newArrayList(
                        "############",
                        "#  C      P#",
                        "############"));
        Player pacman = playerfactory.createPacMan();
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.WEST);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());

        assertThat(clyde.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }
}