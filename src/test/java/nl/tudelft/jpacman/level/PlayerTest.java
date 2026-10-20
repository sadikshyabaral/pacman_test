package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.sprite.AnimatedSprite;
import nl.tudelft.jpacman.sprite.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerTest {

    private Player player;
    private AnimatedSprite deathSprite;
    private Map<Direction, Sprite> spriteMap;

    @BeforeEach
    void setUp() {
        deathSprite = mock(AnimatedSprite.class);

        spriteMap = new EnumMap<>(Direction.class);
        for (Direction direction : Direction.values()) {
            spriteMap.put(direction, mock(Sprite.class));
        }

        player = new Player(spriteMap, deathSprite);
    }

    /**
     * Tests that a player is alive
     */
    @Test
    void isAlive() {
        assertTrue(player.isAlive());
    }

    /**
     * Tests that a player is dead, then alive using setAlive
     */
    @Test
    void setAlive() {
        player.setAlive(false);
        assertFalse(player.isAlive());
        verify(deathSprite).restart();

        // Set player back to alive
        player.setAlive(true);
        assertTrue(player.isAlive());
        verify(deathSprite, times(2)).setAnimating(false);
        assertNull(player.getKiller());
    }

    /**
     * Tests getKiller when a ghost kills the player
     */
    @Test
    void getKiller() {
        assertNull(player.getKiller());
        Unit ghost = mock(Unit.class);
        player.setKiller(ghost);
        assertEquals(ghost, player.getKiller());
    }

    /**
     * Tests setKiller for a ghost
     */
    @Test
    void setKiller() {
        Unit ghost = mock(Unit.class);
        player.setKiller(ghost);
        assertSame(ghost, player.getKiller());
    }

    /**
     * Tests get score
     */
    @Test
    void getScore() {
        assertEquals(0, player.getScore());
    }

    /**
     * Tests getSprite, should be a sprite based on direction and then a death sprite
     */
    @Test
    void getSprite() {
        Direction direction = Direction.EAST;
        Sprite mockSprite = mock(Sprite.class);
        spriteMap.put(direction, mockSprite);

        player.setDirection(direction);
        assertEquals(mockSprite, player.getSprite());

        player.setAlive(false);
        assertEquals(deathSprite, player.getSprite());
    }

    /**
     * Tests that addPoints works when getScore is used
     */
    @Test
    void addPoints() {
        assertEquals(0, player.getScore());
        player.addPoints(10);
        assertEquals(10, player.getScore());
        player.addPoints(5);
        assertEquals(15, player.getScore());
    }
}