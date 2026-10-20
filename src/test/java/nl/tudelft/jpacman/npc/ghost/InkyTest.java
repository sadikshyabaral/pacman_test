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

public class InkyTest {

    private PlayerFactory playerFactory;
    private LevelFactory levelFactory;
    private GhostFactory ghostFactory;
    private BoardFactory boardFactory;
    private GhostMapParser parser;
    private Level level;

    @BeforeEach
    void setUp() {
        PacManSprites sprites = new PacManSprites();
        playerFactory = new PlayerFactory(sprites);
        ghostFactory = new GhostFactory(sprites);
        levelFactory = new LevelFactory(sprites, ghostFactory, mock(PointCalculator.class));
        boardFactory = new BoardFactory(sprites);
        parser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
    }

    @Test
    void noPlayer() {
        level = parser.parseMap(
                Lists.newArrayList("#############",
                        "#          I#",
                        "#############"));
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove()).isEqualTo(Optional.empty());
    }

    @Test
    void followBlinkyWhenTogetherBehindPacMan() {
        level = parser.parseMap(
                Lists.newArrayList("##############",
                        "#P   B    I  #",
                        "##############"));
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.EAST);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        assertThat(inky.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }

    @Test
    void moveAwayFromPacManAndBlinky() {
        level = parser.parseMap(
                Lists.newArrayList(
                        "###############",
                        "#      I  P  B#",
                        "###############"));
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.WEST);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        assertThat(inky.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }

    @Test
    void moveAwayFromPacMan() {
        level = parser.parseMap(
                Lists.newArrayList(
                        "##############",
                        "#        P IB#",
                        "##############"));
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.WEST);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        assertThat(inky.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }

    @Test
    void pacManGoesUp() {
        level = parser.parseMap(
                Lists.newArrayList("##############",
                        "#            #",
                        "#       P    #",
                        "#     B      #",
                        "#    I       #",
                        "##############"));
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.NORTH);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        // Should use bugged targeting (2 up and left)
        assertThat(inky.nextAiMove()).isEqualTo(Optional.of(Direction.NORTH));
    }

    @Test
    void wallBlocksPath() {
        level = parser.parseMap(
                Lists.newArrayList(
                        "##############",
                        "#P   # B    I#",
                        "##############"));
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.EAST);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        // Wall blocks path - should return empty
        assertThat(inky.nextAiMove()).isEqualTo(Optional.empty());
    }
}
