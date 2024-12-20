import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import peopleEntity.Entity;

public class EntityTest {

    @Test
    public void testEntityInitialization() throws FileNotFoundException {
        Entity entity = new Entity();
        assertNotNull(entity);
        assertNotNull(entity.getX());
        assertNotNull(entity.Motion);
        assertEquals(0, entity.getX(), 0.01);
        assertEquals(0, entity.getY(), 0.01);
        assertEquals(0, entity.getMx(), 0.01);
        assertEquals(0, entity.getMy(), 0.01);
        assertEquals(0, entity.getW(), 0.01);
        assertEquals(0, entity.getH(), 0.01);
        assertEquals(false, entity.Rightpress);
        assertEquals(false, entity.Leftpress);
        assertEquals(false, entity.Up);
        assertEquals(false, entity.Down);
    }
    
}
