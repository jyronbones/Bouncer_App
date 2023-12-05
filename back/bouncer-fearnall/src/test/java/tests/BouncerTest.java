package tests;
import cst8218.joshua.bouncer.entity.Bouncer;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class BouncerTest {

    private final Random random = new Random();
        private final int FRAME_HEIGHT = 600;


    private Bouncer createRandomBouncer() {
        Bouncer bouncer = new Bouncer();
        bouncer.setId(random.nextLong());
        bouncer.setX(random.nextInt(800)); // Assuming max width is 800
        bouncer.setY(random.nextInt(600)); // Assuming max height is 600
        bouncer.setYVelocity(random.nextInt(20) - 10); // Random velocity, allowing negative values
        return bouncer;
    }

    @Test
    public void testCreateBouncerWithRandomValues() {
        Bouncer bouncer = createRandomBouncer();
        assertNotNull(bouncer, "Bouncer object should not be null");
        assertNotNull(bouncer.getId(), "ID should not be null");
        assertNotNull(bouncer.getX(), "X position should not be null");
        assertNotNull(bouncer.getY(), "Y position should not be null");
        assertNotNull(bouncer.getYVelocity(), "Y velocity should not be null");
    }
    
    @Test
    public void testAdvanceOneFrame() {
        Bouncer bouncer = createRandomBouncer();
        Integer initialY = bouncer.getY();
        Integer initialYVelocity = bouncer.getYVelocity();

        bouncer.advanceOneFrame();

        // Test that Y velocity is adjusted due to gravity
        assertTrue(bouncer.getYVelocity().equals(initialYVelocity + 1), "Y velocity should increase by gravity");

        // Test that Y position is updated
        assertTrue(bouncer.getY().equals(initialY + initialYVelocity + 1), "Y position should be updated based on velocity");

        // Test for wall collision and reversal of velocity
        if (bouncer.getY() <= 0 || bouncer.getY() >= FRAME_HEIGHT) {
            assertTrue(bouncer.getYVelocity().equals(-(initialYVelocity + 1)), "Y velocity should be reversed upon collision with wall");
        }
    }

    // Additional test methods here, each creating a new Bouncer instance
}
