package tests;
import cst8218.joshua.bouncer.entity.Bouncer;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Bouncer class.
 * @author : Mohamed Elmursi
 * @version : 1.0
 * Description : This class is used to test the Bouncer class.
 */
public class BouncerTest {

    // Random number generator for creating random Bouncer objects
    private final Random random = new Random();
    // Frame height for testing wall collision
    private final int FRAME_HEIGHT = 600;

    /**
     * Create a Bouncer object with random values for testing.
     * @return Bouncer object with random values
     */
    private Bouncer createRandomBouncer() {
        Bouncer bouncer = new Bouncer();
        bouncer.setId(random.nextLong());
        bouncer.setX(random.nextInt(800)); // Assuming max width is 800
        bouncer.setY(random.nextInt(600)); // Assuming max height is 600
        bouncer.setYVelocity(random.nextInt(20) - 10); // Random velocity, allowing negative values
        return bouncer;
    }

    /**
     * Test that a Bouncer object is created with valid values.
     * @result Bouncer object should not be null and all values should be set
     * to non-null values.
     */
    @Test
    public void testCreateBouncerWithRandomValues() {
        // Create a Bouncer object with random valuess
        Bouncer bouncer = createRandomBouncer();
        assertNotNull(bouncer, "Bouncer object should not be null");
        assertNotNull(bouncer.getId(), "ID should not be null");
        assertNotNull(bouncer.getX(), "X position should not be null");
        assertNotNull(bouncer.getY(), "Y position should not be null");
        assertNotNull(bouncer.getYVelocity(), "Y velocity should not be null");
    }

    /**
     * Test AdvanceOneFrame method.
     * @result Y velocity should increase by gravity, Y position should be updated
     * based on velocity, and Y velocity should be reversed upon collision with wall.
     */
    @Test
    public void testAdvanceOneFrame() {
        // Create a Bouncer object with random values
        Bouncer bouncer = createRandomBouncer();
        // Save initial values
        Integer initialY = bouncer.getY();
        Integer initialYVelocity = bouncer.getYVelocity();

        // Advance one frame
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


    /**
     * Test for X position update in the AdvanceOneFrame method.
     * @result X position should remain constant as the AdvanceOneFrame method
     * only updates the Y position and Y velocity.
     */
    @Test
    public void testXPositionUnchangedInAdvanceOneFrame() {
        // Create a Bouncer object with random values
        Bouncer bouncer = createRandomBouncer();
        // Save initial X value
        Integer initialX = bouncer.getX();

        // Advance one frame
        bouncer.advanceOneFrame();

        // Test that X position remains unchanged
        assertEquals(initialX, bouncer.getX(), "X position should remain unchanged after advancing one frame");
    }


    /**
     * Test for Y position update in the AdvanceOneFrame method.
     * @result Y position should be updated based on Y velocity.
     */
    @Test
    public void testYPositionUpdatedInAdvanceOneFrame() {
        // Create a Bouncer object with random values
        Bouncer bouncer = createRandomBouncer();
        // Save initial Y value
        Integer initialY = bouncer.getY();

        // Advance one frame
        bouncer.advanceOneFrame();

        // Test that Y position is updated and does not equal the initial value
        assertNotEquals(initialY, bouncer.getY(), "Y position should be updated after advancing one frame");
    }

}// End of BouncerTest class

