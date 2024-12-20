import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;

import peopleEntity.Player;

public class PeopleEntityTest {
    @Test
    public void testHelloWorld() {
        assertEquals("Hello, World!", "Hello, World!");
    }

    @Test
    public void testPlayerInitializationRole1() throws FileNotFoundException {
        Player player = new Player(100, 200, 2);
        assertNotNull(player);
        assertEquals(100, player.getX(), 0.01);
        assertEquals(200, player.getY(), 0.01);
    }

    @Test
    public void testPlayerInitializationRole2() throws FileNotFoundException {
        Player player = new Player(300, 400, 2);
        assertNotNull(player);
        assertEquals(300, player.getX(), 0.01);
        assertEquals(400, player.getY(), 0.01);
    }

}

