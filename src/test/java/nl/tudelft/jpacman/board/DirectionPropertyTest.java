package nl.tudelft.jpacman.board;

import net.jqwik.api.*;
import static org.assertj.core.api.Assertions.*;

public class DirectionPropertyTest {

    // deltaX and deltaY must be -1, 0, or 1
    @Property
    void deltasAreInExpectedRange(@ForAll Direction dir) {
        assertThat(dir.getDeltaX()).isIn(-1, 0, 1);
        assertThat(dir.getDeltaY()).isIn(-1, 0, 1);
    }

    // there are exactly 4 directions
    @Property
    void thereAreFourDirections() {
        assertThat(Direction.values()).hasSize(4);
    }
}
