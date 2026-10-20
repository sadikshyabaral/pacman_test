package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.sprite.AnimatedSprite;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.sprite.Sprite;
import nl.tudelft.jpacman.board.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerFactoryTest {

    private PacManSprites sprites;
    private AnimatedSprite deathSprite;
    private Map<Direction, Sprite> directionSprites;
    private PlayerFactory factory;

    @BeforeEach
    void setUp() {
        sprites = mock(PacManSprites.class);
        deathSprite = mock(AnimatedSprite.class);
        directionSprites = new EnumMap<>(Direction.class);

        for (Direction dir : Direction.values()) {
            directionSprites.put(dir, mock(Sprite.class));
        }

        when(sprites.getPacmanSprites()).thenReturn(directionSprites);
        when(sprites.getPacManDeathAnimation()).thenReturn(deathSprite);

        factory = new PlayerFactory(sprites);
    }

    /**
     * Test if the pac man was created successfully
     */
    @Test
    void createPacMan() {
        Player player = factory.createPacMan();
        assertNotNull(player);
        assertTrue(player.isAlive());
        assertEquals(0, player.getScore());
        assertNull(player.getKiller());

        Sprite expectedSprite = directionSprites.get(Direction.EAST);
        assertEquals(expectedSprite, player.getSprite());
    }

    /**
     * Test if getSprites returns the correct sprites
     */
    @Test
    void getSprites() {
        assertSame(sprites, factory.getSprites());
    }
}