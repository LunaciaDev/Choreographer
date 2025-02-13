package com.lunaciadev.choreographer.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.badlogic.gdx.Gdx;
import com.lunaciadev.choreographer.GdxExtension;
import com.lunaciadev.choreographer.data.ItemData;
import com.lunaciadev.choreographer.types.QueueType;

@ExtendWith(GdxExtension.class)
public class ChoreographerTest {
    private ItemData itemData;
    private InputHandler inputHandler;

    public ChoreographerTest() throws Exception {
        itemData = new ItemData(Gdx.files.local("../assets/itemData.csv").reader(8192));
        inputHandler = new InputHandler();
    }

    @Test
    public void testLightArmQueueSystem() {
        inputHandler.clearData();

        inputHandler.addCrate(0, 2, 8);
        inputHandler.addCrate(1, 2, 4);

        inputHandler.addCrate(2, 1, 8);
        inputHandler.addCrate(3, 1, 4);

        inputHandler.addCrate(4, 0, 8);
        inputHandler.addCrate(5, 0, 4);

        // should return 5 4 3 2 1 0. One test is enough to extrapolate..?

        Choreographer choreographer = new Choreographer(itemData);
        choreographer.setData(inputHandler);

        assertEquals(4, choreographer.onQueueRequest(QueueType.LIGHT_ARMS));
        assertEquals(4, choreographer.onQueueRequest(QueueType.LIGHT_ARMS));
        assertEquals(5, choreographer.onQueueRequest(QueueType.LIGHT_ARMS));
        assertEquals(2, choreographer.onQueueRequest(QueueType.LIGHT_ARMS));
        assertEquals(2, choreographer.onQueueRequest(QueueType.LIGHT_ARMS));
        assertEquals(3, choreographer.onQueueRequest(QueueType.LIGHT_ARMS));
        assertEquals(0, choreographer.onQueueRequest(QueueType.LIGHT_ARMS));
        assertEquals(0, choreographer.onQueueRequest(QueueType.LIGHT_ARMS));
        assertEquals(1, choreographer.onQueueRequest(QueueType.LIGHT_ARMS));
    }

    @Test
    public void testSystemFinish() {
        inputHandler.clearData();

        inputHandler.addCrate(0, 1, 4);
        inputHandler.addCrate(1, 0, 8);
        inputHandler.addCrate(2, 0, 4);

        Choreographer choreographer = new Choreographer(itemData);
        choreographer.setData(inputHandler);

        assertEquals(1, choreographer.onQueueRequest(QueueType.LIGHT_ARMS));
        assertEquals(1, choreographer.onQueueRequest(QueueType.LIGHT_ARMS));
        assertEquals(false, choreographer.onCheckFinished());
        assertEquals(2, choreographer.onQueueRequest(QueueType.LIGHT_ARMS));
        assertEquals(0, choreographer.onQueueRequest(QueueType.LIGHT_ARMS));
        assertEquals(false, choreographer.onCheckFinished());
        choreographer.onSubmitTruck();
        assertEquals(false, choreographer.onCheckFinished());
        choreographer.onSubmitTruck();
        assertEquals(true, choreographer.onCheckFinished());
    }

    @Test
    public void testUndoRequest() {
        inputHandler.clearData();

        inputHandler.addCrate(0, 1, 4);
        inputHandler.addCrate(1, 0, 8);
        inputHandler.addCrate(2, 0, 4);

        Choreographer choreographer = new Choreographer(itemData);
        choreographer.setData(inputHandler);

        assertEquals(false, choreographer.onUndoRequest());
        assertEquals(1, choreographer.onQueueRequest(QueueType.LIGHT_ARMS));
        assertEquals(true, choreographer.onUndoRequest());
        assertEquals(false, choreographer.onUndoRequest());
        assertEquals(1, choreographer.onQueueRequest(QueueType.LIGHT_ARMS));
        assertEquals(1, choreographer.onQueueRequest(QueueType.LIGHT_ARMS));
        assertEquals(2, choreographer.onQueueRequest(QueueType.LIGHT_ARMS));
        assertEquals(true, choreographer.onUndoRequest());
        assertEquals(true, choreographer.onUndoRequest());
        assertEquals(1, choreographer.onQueueRequest(QueueType.LIGHT_ARMS));
        assertEquals(2, choreographer.onQueueRequest(QueueType.LIGHT_ARMS));
    }

    @Test
    public void testTruckSubmission() {
        inputHandler.clearData();

        inputHandler.addCrate(0, 0, 12);

        Choreographer choreographer = new Choreographer(itemData);
        choreographer.setData(inputHandler);

        assertEquals(0, choreographer.onQueueRequest(QueueType.LIGHT_ARMS));
        assertEquals(0, choreographer.onQueueRequest(QueueType.LIGHT_ARMS));
        choreographer.onSubmitTruck();
        assertEquals(false, choreographer.onUndoRequest());
    }
}
