package com.lunaciadev.choreographer.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.badlogic.gdx.Gdx;
import com.lunaciadev.choreographer.GdxExtension;
import com.lunaciadev.choreographer.data.ItemData;
import com.lunaciadev.choreographer.types.Priority;
import com.lunaciadev.choreographer.types.QueueType;

@ExtendWith(GdxExtension.class)
public class ResultGeneratorTest {
    private InputHandler inputHandler;
    private ItemData itemData;

    public ResultGeneratorTest() throws Exception {
        itemData = new ItemData(Gdx.files.local("../assets/itemData.csv").reader(8192));
        inputHandler = new InputHandler();
    }

    @Test
    public void testEmptyResult() {
        inputHandler.clearData();

        Choreographer choreographer = new Choreographer(itemData);
        choreographer.setData(inputHandler);

        ResultGenerator resultGenerator = new ResultGenerator(itemData);
        resultGenerator.getResult(choreographer.getResult());

        StringBuilder result = new StringBuilder();

        for (int key = 0; key < itemData.getItemDataSize(); key++) {
            result.append(0);

            if (key != itemData.getItemDataSize() - 1) {
                result.append("\t");
            }
        }

        assertEquals(result.toString(), resultGenerator.generateGoogleSheetPaste());
    }

    @Test
    public void testFullResult() {
        inputHandler.clearData();

        inputHandler.addCrate(0, Priority.HIGH_PRIORITY, 8);
        inputHandler.addCrate(1, Priority.HIGH_PRIORITY, 12);

        Choreographer choreographer = new Choreographer(itemData);
        choreographer.setData(inputHandler);

        choreographer.onQueueRequest(QueueType.LIGHT_ARMS);
        choreographer.onQueueRequest(QueueType.LIGHT_ARMS);
        choreographer.onQueueRequest(QueueType.LIGHT_ARMS);
        choreographer.onQueueRequest(QueueType.LIGHT_ARMS);
        choreographer.onQueueRequest(QueueType.LIGHT_ARMS);

        choreographer.onSubmitTruck();
        choreographer.onSubmitTruck();
        choreographer.onSubmitTruck();

        ResultGenerator resultGenerator = new ResultGenerator(itemData);
        resultGenerator.getResult(choreographer.getResult());

        StringBuilder result = new StringBuilder();

        result.append("8\t12\t");

        for (int key = 2; key < itemData.getItemDataSize(); key++) {
            result.append(0);

            if (key != itemData.getItemDataSize() - 1) {
                result.append("\t");
            }
        }

        assertEquals(result.toString(), resultGenerator.generateGoogleSheetPaste());
    }

    @Test
    public void testUncommitedResult() {
        inputHandler.clearData();

        inputHandler.addCrate(0, Priority.HIGH_PRIORITY, 8);
        inputHandler.addCrate(1, Priority.HIGH_PRIORITY, 12);

        Choreographer choreographer = new Choreographer(itemData);
        choreographer.setData(inputHandler);

        choreographer.onQueueRequest(QueueType.LIGHT_ARMS);
        choreographer.onQueueRequest(QueueType.LIGHT_ARMS);
        choreographer.onQueueRequest(QueueType.LIGHT_ARMS);
        choreographer.onQueueRequest(QueueType.LIGHT_ARMS);
        choreographer.onQueueRequest(QueueType.LIGHT_ARMS);

        ResultGenerator resultGenerator = new ResultGenerator(itemData);
        resultGenerator.getResult(choreographer.getResult());

        StringBuilder result = new StringBuilder();

        for (int key = 0; key < itemData.getItemDataSize(); key++) {
            result.append(0);

            if (key != itemData.getItemDataSize() - 1) {
                result.append("\t");
            }
        }

        assertEquals(result.toString(), resultGenerator.generateGoogleSheetPaste());
    }

    @Test
    public void testPartiallyCommitedResult() {
        inputHandler.clearData();

        inputHandler.addCrate(0, Priority.HIGH_PRIORITY, 8);
        inputHandler.addCrate(1, Priority.HIGH_PRIORITY, 12);

        Choreographer choreographer = new Choreographer(itemData);
        choreographer.setData(inputHandler);

        choreographer.onQueueRequest(QueueType.LIGHT_ARMS);
        choreographer.onQueueRequest(QueueType.LIGHT_ARMS);
        choreographer.onQueueRequest(QueueType.LIGHT_ARMS);
        choreographer.onQueueRequest(QueueType.LIGHT_ARMS);
        choreographer.onQueueRequest(QueueType.LIGHT_ARMS);

        choreographer.onSubmitTruck();
        choreographer.onSubmitTruck();

        ResultGenerator resultGenerator = new ResultGenerator(itemData);
        resultGenerator.getResult(choreographer.getResult());

        StringBuilder result = new StringBuilder();

        result.append("4\t12\t");

        for (int key = 2; key < itemData.getItemDataSize(); key++) {
            result.append(0);

            if (key != itemData.getItemDataSize() - 1) {
                result.append("\t");
            }
        }

        assertEquals(result.toString(), resultGenerator.generateGoogleSheetPaste());
    }
}
