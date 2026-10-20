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

public class PinkyTest {

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
        levelFactory = new LevelFactory(sprites, ghostFactory, new DefaultPointCalculator());
        boardFactory = new BoardFactory(sprites);
        parser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
    }

    /**
     * test if there's no player
     */
    @Test
    void noPlayer() {
        level = parser.parseMap(
                Lists.newArrayList("#############", "#          K#", "#############"));

        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, level.getBoard());
        assert pinky != null;

        assertThat(pinky.nextAiMove()).isEqualTo(Optional.empty());
    }

    /**
     * When the ghosts are not patrolling their home corners, Pinky wants to go
     * to the place that is four grid spaces ahead of Pac-Man in the direction
     * that Pac-Man is facing. If Pac-Man is facing down, Pinky wants to go to
     * the location exactly four spaces below Pac-Man. Moving towards this place
     * uses the same logic that Blinky uses to find Pac-Man's exact location.
     * Pinky is affected by a targeting bug if Pac-Man is facing up - when he
     * moves or faces up, Pinky tries moving towards a point up, and left, four
     * spaces.*/

    // (Blinky: top-right, Pinky: top-left, Inky: bottom-right, Clyde: bottom-left)

    /**
     * when not in home corner,
     *  go to four gird spaces ahead of pac-man in direction pac-man is facing
     * */
    @Test
    void pacManFacingRight() {
        level = parser.parseMap(Lists.newArrayList(
                "####################",
                "#                  #",
                "# P               K#", // k is pinky
                "#                  #",
                "####################"));

        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.EAST);

        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, level.getBoard());

        assertThat(pinky.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }

    @Test
    void pacManFacingLeft() {
        level = parser.parseMap(Lists.newArrayList(
                "####################",
                "#                  #",
                "#K                P#", // k is pinky
                "#                  #",
                "####################"));

        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.WEST);

        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, level.getBoard());

        assertThat(pinky.nextAiMove()).isEqualTo(Optional.of(Direction.EAST));
    }

    @Test
    void pacManFacingDown() {
        level = parser.parseMap(Lists.newArrayList(
                "###########",
                "#P        #",
                "#         #",
                "#         #",
                "#         #",
                "#        K#", // k is pinky
                "###########"));

        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.SOUTH);

        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, level.getBoard());

        assertThat(pinky.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }

    @Test
    void pacManFacingUp() {
        level = parser.parseMap(Lists.newArrayList(
                "###########",
                "#        K#", // k is pinky
                "#         #",
                "#         #",
                "#         #",
                "# P       #",
                "###########"));

        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);
        pacman.setDirection(Direction.NORTH);

        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, level.getBoard());

        assertThat(pinky.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }


}