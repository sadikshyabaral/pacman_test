package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test suite to confirm that {@link Unit}s correctly (de)occupy squares.
 *
 * @author Jeroen Roosen 
 *
 */
class OccupantTest {

    /**
     * The unit under test.
     */
    private Unit unit;

    /**
     * The target square under test.
     */
    private Square target;

    /**
     * Resets the unit under test.
     */
    @BeforeEach
    void setUp() {
        unit = new BasicUnit();
        target = new BasicSquare();
    }

    /**
     * Asserts that a unit has no square to start with.
     */
    @Test
    void noStartSquare() {
        assertThat(unit.hasSquare()).isFalse();
    }

    /**
     * Tests that the unit indeed has the target square as its base after
     * occupation.
     */
    @Test
    void testOccupy() {
        unit.occupy(target);
        assertThat(unit.hasSquare()).isTrue();
    }

    /**
     * Test that the unit indeed has the target square as its base after
     * double occupation.
     * For this test I assumed double occupation referred to 2 separate units being on the same square
     */
    @Test
    void testReoccupy() {
        Unit newUnit = new BasicUnit();
        newUnit.occupy(target);
        unit.occupy(target);
        assertEquals(target, unit.getSquare());
    }

    /**
     * Test that the unit has moved from one square to another, ensure the previous square is empty
     */

    @Test
    void testOccupyRemovesFromPreviousSquare() {
        Square previousSquare = new BasicSquare();
        unit.occupy(previousSquare);
        unit.occupy(target);
        assertFalse(previousSquare.getOccupants().contains(unit));
        assertTrue(target.getOccupants().contains(unit));
    }
}
