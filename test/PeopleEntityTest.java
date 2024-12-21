import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;

import peopleEntity.Player;
import peopleEntity.Robot;

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

    @Test
    public void testPlayerInject() throws FileNotFoundException {
        Player player = new Player(100, 200, 2);
        player.inject = true;
        player.act();
        assertEquals(1000, player.getX(), 0.01);
        assertEquals(480, player.getY(), 0.01);
    }

    @Test
    public void testPlayerActRight() throws FileNotFoundException {
        Player player = new Player(100, 200, 2);
        player.Rightpress = true;   //will move right for 8 pixels at each step
        player.act();
        player.act();
        assertEquals(116, player.getX(), 0.01);
        assertEquals(200, player.getY(), 0.01);
    }

    @Test
    public void testPlayerActLeft() throws FileNotFoundException {
        Player player = new Player(100, 200, 2);
        player.Leftpress = true;   //will move left for 8 pixels at each step
        player.act();
        player.act();
        assertEquals(84, player.getX(), 0.01);
        assertEquals(200, player.getY(), 0.01);
    }

    @Test
    public void testPlayerActUp() throws FileNotFoundException {
        Player player = new Player(100, 200, 2);
        player.Up = true;   //will move up for 8 pixels at each step
        player.act();
        player.act();
        assertEquals(100, player.getX(), 0.01);
        assertEquals(216, player.getY(), 0.01);
    }

    @Test
    public void testPlayerActDown() throws FileNotFoundException {
        Player player = new Player(100, 200, 2);
        player.Down = true;   //will move down for 8 pixels at each step
        player.act();
        player.act();
        assertEquals(100, player.getX(), 0.01);
        assertEquals(184, player.getY(), 0.01);
    }

    @Test
    public void testRobotInitializationDirection1() throws FileNotFoundException {
        Robot robot = new Robot(500, 600, "picture/character_robot_drag.png", 1);
        assertNotNull(robot);
        assertEquals(500, robot.getX(), 0.01);
        assertEquals(600, robot.getY(), 0.01);
    }

    @Test
    public void testRobotActDirection1() throws FileNotFoundException {
        Robot robot = new Robot(500, 600, "picture/character_robot_drag.png", 1);
        for (int i = 0; i < 10; i++) robot.act();
        
        assertEquals(590, robot.getY(), 0.01);
    }

    @Test
    public void testRobotActDirection2() throws FileNotFoundException {
        Robot robot = new Robot(700, 800, "picture/character_robot_drag_Left.png", 2);
        for (int i = 0; i < 10; i++) robot.act();
        
        assertEquals(810, robot.getY(), 0.01);
    }

}

