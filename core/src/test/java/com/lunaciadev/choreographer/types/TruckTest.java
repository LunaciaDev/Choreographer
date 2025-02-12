package com.lunaciadev.choreographer.types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.badlogic.gdx.Gdx;
import com.lunaciadev.choreographer.GdxExtension;
import com.lunaciadev.choreographer.data.ItemData;

@ExtendWith(GdxExtension.class)
public class TruckTest {
    private ItemData itemData;

    public TruckTest() throws Exception {
        itemData = new ItemData(Gdx.files.local("../assets/itemData.csv").reader(8192));
    }

    @Test
    public void testAddNonFull() {
        Truck testTruck = new Truck(itemData);

        assertTrue(testTruck.addItem(0));
        assertTrue(testTruck.addItem(1));
    }

    @Test
    public void testAddFullTruck() {
        Truck testTruck = new Truck(itemData);

        assertTrue(testTruck.addItem(25));
        assertTrue(testTruck.addItem(25));
        assertFalse(testTruck.addItem(25));
    }

    @Test
    public void testTruckContent() {
        Truck testTruck = new Truck(itemData);
        int[] testList = {7, 12, 21};

        testTruck.addItem(testList[0]);
        testTruck.addItem(testList[2]);
        testTruck.addItem(testList[1]);

        Integer[] result = testTruck.getTruckContent();

        assertEquals(result[0], testList[0]);
        assertEquals(result[1], testList[2]);
        assertEquals(result[2], testList[1]);
    }
}
