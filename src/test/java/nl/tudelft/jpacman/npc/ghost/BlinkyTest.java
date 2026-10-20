package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.Level;
import static org.assertj.core.api.Assertions.assertThat;
import com.google.common.collect.Lists;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

public class BlinkyTest {

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
        levelfactory = new LevelFactory(sprites, ghostFactory, new DefaultPointCalculator());
        boardfactory = new BoardFactory(sprites);
        parser = new GhostMapParser(levelfactory, boardfactory, ghostFactory);
    }

    @Test
    void noPlayer() {
        level = parser.parseMap(
                Lists.newArrayList("#############", "#          B#", "#############"));

        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        assert blinky != null;

        assertThat(blinky.nextAiMove()).isEqualTo(Optional.empty());
    }

    /**
     * when the ghosts are not in home corner,
     *  move up, vertical distance is greater
     * */
    @Test
    void verticalUp() {
        level = parser.parseMap(Lists.newArrayList(
                "##############",
                "#            #",
                "#      P     #",
                "#            #",
                "#            #",
                "#   B        #",
                "##############"));
        Player pacman = playerfactory.createPacMan();
        level.registerPlayer(pacman);

        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());

        // should move up, Pac-Man is further vertically
        assertThat(blinky.nextAiMove()).isEqualTo(Optional.of(Direction.NORTH));
    }

    /**
     * when the ghosts are not in home corner,
     *  move down, vertical distance is greater
     * */
    @Test
    void verticalDown() {
        level = parser.parseMap(Lists.newArrayList(
                "##############",
                "#            #",
                "#  B         #",
                "#            #",
                "#            #",
                "#    P       #",
                "##############"));
        Player pacman = playerfactory.createPacMan();
        level.registerPlayer(pacman);

        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());

        // should move up, Pac-Man is further vertically
        assertThat(blinky.nextAiMove()).isEqualTo(Optional.of(Direction.SOUTH));
    }

    /**
     * when the ghosts are not in home corner,
     *  move left, horizontal distance is greater
     * */
    @Test
    void horizontalLeft() {
        level = parser.parseMap(Lists.newArrayList(
                "##############",
                "#            #",
                "#   P     B  #",
                "#            #",
                "##############"));
        Player pacman = playerfactory.createPacMan();
        level.registerPlayer(pacman);

        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());

        // should move left, Pac-Man is further horizontally
        assertThat(blinky.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }

    /**
     * when the ghosts are not in home corner,
     *  move left, horizontal distance is greater
     * */
    @Test
    void horizontalRight() {
        level = parser.parseMap(Lists.newArrayList(
                "##############",
                "#            #",
                "# B       P  #",
                "#            #",
                "##############"));
        Player pacman = playerfactory.createPacMan();
        level.registerPlayer(pacman);

        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());

        // should move right, Pac-Man is further horizontally
        assertThat(blinky.nextAiMove()).isEqualTo(Optional.of(Direction.EAST));
    }

}