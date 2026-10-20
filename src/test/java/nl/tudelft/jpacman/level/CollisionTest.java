package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.*;

class CollisionTest {

    private CollisionInteractionMap collisionMap;

    @BeforeEach
    void setUp() {
        collisionMap = new CollisionInteractionMap();
    }

    /**
     * Tests that when a player and ghost collide, the player dies and the ghost is the killer
     */
    @Test
    void testPlayerGhostCollision() {
        Player player = mock(Player.class);
        Ghost ghost = mock(Ghost.class);

        collisionMap.onCollision(Player.class, Ghost.class, (p, g) -> {
            p.setAlive(false);
            p.setKiller(g);
        });

        collisionMap.collide(player, ghost);

        verify(player).setAlive(false);
        verify(player).setKiller(ghost);
    }

    /**
     * Tests that when a player touches a pellet it leaves the square
     */
    @Test
    void testPlayerPelletCollision() {
        Player player = mock(Player.class);
        Pellet pellet = mock(Pellet.class);

        collisionMap.onCollision(Player.class, Pellet.class, (p, pel) -> {
            pel.leaveSquare();
        });

        collisionMap.collide(player, pellet);

        verify(pellet).leaveSquare();
    }

    /**
     * When ghost touches a player, player dies
     */
    @Test
    void ghostCollidePlayer() {

        Player player = mock(Player.class);
        Ghost ghost = mock(Ghost.class);

        collisionMap.onCollision(Player.class, Ghost.class, (p, g) -> {
            p.setAlive(false);
            p.setKiller(g);
        });

        collisionMap.collide(ghost, player);

        verify(player).setAlive(false);
        verify(player).setKiller(ghost);

    }

    /**
     * Test for when a pellet touches a player
     */
    @Test
    void testPelletPlayerCollision() {
        Player player = mock(Player.class);
        Pellet pellet = mock(Pellet.class);

        collisionMap.onCollision(Player.class, Pellet.class, (p, pel) -> {
            pel.leaveSquare();
        });

        collisionMap.collide(pellet, player);

        verify(pellet).leaveSquare();
    }



}
