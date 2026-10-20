package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NavigationTest {

    /**
     * Test that findNearest works correctly
     */
    @Test
    void testFindNearest() {
        Square start = mock(Square.class);
        Square next = mock(Square.class);
        Unit target = mock(Unit.class);
        when(start.getSquareAt(Direction.NORTH)).thenReturn(next);
        when(next.getOccupants()).thenReturn(List.of(target));
        when(start.getOccupants()).thenReturn(List.of());

        when(target.hasSquare()).thenReturn(true);

        Unit found = Navigation.findNearest(Unit.class, start);
        assertEquals(target, found);
    }

    /**
     * Test that the unit is found correctly in the board
     */
    @Test
    void testFindUnitInBoard() {
        Board board = mock(Board.class);
        Square s1 = mock(Square.class);
        Unit u = mock(Unit.class);

        when(board.getWidth()).thenReturn(1);
        when(board.getHeight()).thenReturn(1);
        when(board.squareAt(0, 0)).thenReturn(s1);
        when(s1.getOccupants()).thenReturn(List.of(u));
        when(u.hasSquare()).thenReturn(true);

        Unit result = Navigation.findUnitInBoard(Unit.class, board);
        assertEquals(u, result);
    }

    /**
     * Test that the unit is found correctly
     */
    @Test
    void testFindUnit() {
        Square square = mock(Square.class);
        Unit unit = mock(Unit.class);

        when(square.getOccupants()).thenReturn(List.of(unit));
        when(unit.hasSquare()).thenReturn(true);

        Unit result = Navigation.findUnit(Unit.class, square);
        assertEquals(unit, result);
    }

    /**
     * Test that shortestPath works correctly
     */
    @Test
    void testGetDirection() {
        Direction direction = Direction.NORTH;
        Square square = mock(Square.class);
        Navigation.Node node = new Navigation.Node(direction, square, null);
        assertEquals(direction, node.getDirection());
    }

    /**
     * Test that getSquare works correctly
     */
    @Test
    void testGetSquare() {
        Square square = mock(Square.class);
        Navigation.Node node = new Navigation.Node(Direction.EAST, square, null);
        assertEquals(square, node.getSquare());
    }

    /**
     * Test that getParent works correctly
     */
    @Test
    void testGetParent() {
        Square rootSquare = mock(Square.class);
        Square childSquare = mock(Square.class);
        Navigation.Node parent = new Navigation.Node(null, rootSquare, null);
        Navigation.Node child = new Navigation.Node(Direction.SOUTH, childSquare, parent);
        assertEquals(parent, child.getParent());
    }

    /**
     * Test that getPath works correctly
     */
    @Test
    void testGetPath() {
        Square start = mock(Square.class);
        Square mid = mock(Square.class);
        Square end = mock(Square.class);

        Navigation.Node root = new Navigation.Node(null, start, null);
        Navigation.Node middle = new Navigation.Node(Direction.EAST, mid, root);
        Navigation.Node leaf = new Navigation.Node(Direction.NORTH, end, middle);

        List<Direction> path = leaf.getPath();

        assertEquals(2, path.size());
        assertEquals(Direction.EAST, path.get(0));
        assertEquals(Direction.NORTH, path.get(1));
    }
}
