package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.sprite.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GhostFactoryTest {

    private PacManSprites spriteStore;
    private Map<Direction, Sprite> redSprites;
    private Map<Direction, Sprite> pinkSprites;
    private Map<Direction, Sprite> cyanSprites;
    private Map<Direction, Sprite> orangeSprites;
    private GhostFactory factory;

    @BeforeEach
    void setUp() {
        spriteStore = mock(PacManSprites.class);
        redSprites = mock(Map.class);
        pinkSprites = mock(Map.class);
        cyanSprites = mock(Map.class);
        orangeSprites = mock(Map.class);

        when(spriteStore.getGhostSprite(GhostColor.RED)).thenReturn(redSprites);
        when(spriteStore.getGhostSprite(GhostColor.PINK)).thenReturn(pinkSprites);
        when(spriteStore.getGhostSprite(GhostColor.CYAN)).thenReturn(cyanSprites);
        when(spriteStore.getGhostSprite(GhostColor.ORANGE)).thenReturn(orangeSprites);

        factory = new GhostFactory(spriteStore);
    }

    /**
     * Test that Blinky is made
     */
    @Test
    void createBlinky() {
        Ghost ghost = factory.createBlinky();
        assertNotNull(ghost);
        assertTrue(ghost instanceof Blinky);
    }

    /**
     * Test that Pinky is made
     */
    @Test
    void createPinky() {
        Ghost ghost = factory.createPinky();
        assertNotNull(ghost);
        assertTrue(ghost instanceof Pinky);
    }

    /**
     * Test that Inky is made
     */
    @Test
    void createInky() {
        Ghost ghost = factory.createInky();
        assertNotNull(ghost);
        assertTrue(ghost instanceof Inky);
    }

    /**
     * Test that Clyde is made
     */
    @Test
    void createClyde() {
        Ghost ghost = factory.createClyde();
        assertNotNull(ghost);
        assertTrue(ghost instanceof Clyde);
    }
}
