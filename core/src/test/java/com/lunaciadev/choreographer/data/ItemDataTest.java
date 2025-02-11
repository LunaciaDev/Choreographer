package com.lunaciadev.choreographer.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.badlogic.gdx.Gdx;
import com.lunaciadev.choreographer.GdxExtension;
import com.lunaciadev.choreographer.data.ItemData.QueueType;

public class ItemDataTest {

    /**
     * Test if it parse correctly.
     */
    @Test
    public void testValidParse() throws Exception {
        ItemData itemData = new ItemData(new BufferedReader(new StringReader("Cascadier,0,60,0,0,0")));

        assertEquals("Cascadier", itemData.getItemName(0));
        assertEquals(QueueType.SMALL_ARMS, itemData.getQueueType(0));
        assertEquals(60, itemData.getCost(0).getBmatCost());
        assertEquals(0, itemData.getCost(0).getEmatCost());
        assertEquals(0, itemData.getCost(0).getRmatCost());
        assertEquals(0, itemData.getCost(0).getHematCost());
        assertThrows(IndexOutOfBoundsException.class, () -> {
            itemData.getItemName(1);
        });
    }

    @Test
    public void testInvalidQueueTypeParse() {
        String input = "Brasa Shotgun,99,80,0,0,0";

        Exception exception = assertThrows(RuntimeException.class, () -> {

            // Supressing unused warning is justified here as we do not intend to use this.
            @SuppressWarnings("unused")
            ItemData itemData = new ItemData(new BufferedReader(new StringReader(input)));
        });

        assertEquals("Invalid queue type in database, row data: " + input, exception.getMessage());
    }

    @Test
    public void testInvalidRowDataParse() {
        String input = "Brasa Shotgun,0,80,0,0";

        Exception exception = assertThrows(RuntimeException.class, () -> {

            // Supressing unused warning is justified here as we do not intend to use this.
            @SuppressWarnings("unused")
            ItemData itemData = new ItemData(new BufferedReader(new StringReader(input)));
        });

        assertEquals("Invalid CSV row, expected 6 items, found " + input.split(",").length, exception.getMessage());
    }

    /**
     * Test if we prepared itemData.csv correctly. Happens when the data is updated,
     * always nice to see if nothing go wrong immediately via a test!
     */
    @Test
    @ExtendWith(GdxExtension.class)
    public void testCorrectItemDataCsv() throws Exception {
        @SuppressWarnings("unused")
        ItemData itemData = new ItemData(Gdx.files.local("../assets/itemData.csv").reader(8192));
    }
}
