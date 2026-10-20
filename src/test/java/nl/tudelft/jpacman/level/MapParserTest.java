package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class MapParserTest {
    private MapParser map;

    private LevelFactory levelFactoryMock;
    private BoardFactory boardFactoryMock;
    private Board board;
    private Level level;

    @BeforeEach
    void setUp() {
        levelFactoryMock = mock(LevelFactory.class, withSettings().verboseLogging());
        boardFactoryMock = mock(BoardFactory.class, withSettings().verboseLogging());
        board = mock(Board.class, withSettings().verboseLogging());
        level = mock(Level.class, withSettings().verboseLogging());

        map = new MapParser(levelFactoryMock, boardFactoryMock);
    }

    @Test
    void oneWall() {
        // Ensure it's possible to add a wall sprite to a map
        when(boardFactoryMock.createBoard(any(Square[][].class))).thenReturn(board);
        when(levelFactoryMock.createLevel(eq(board), any(ArrayList.class), any(ArrayList.class))).thenReturn(level);

        Level oneWall = map.parseMap(List.of("#"));

        verify(boardFactoryMock).createWall();
        verify(boardFactoryMock).createBoard(any(Square[][].class));
        verify(levelFactoryMock).createLevel(eq(board), any(ArrayList.class), any(ArrayList.class));

        assertNotNull(oneWall);

    }
}