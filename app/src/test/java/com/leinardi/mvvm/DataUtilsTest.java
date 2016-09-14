package com.leinardi.mvvm;

import com.leinardi.mvvm.model.Data;
import com.leinardi.mvvm.util.DataUtils;

import org.junit.Test;

import java.util.BitSet;

import static org.junit.Assert.assertEquals;

public class DataUtilsTest {

    private static final short TEST_NUMBER = 140;
    private static final short TEST_SECTION = 0;
    private static final short TEST_ITEM = 3;
    private static final boolean TEST_CHECKED = true;
    private static final short TEST_NUMBER_INDEX_OUT_OF_BOUND = 1 << 9;

    @Test
    public void fromNumberToData_isCorrect() throws Exception {
        Data data = DataUtils.toData(TEST_NUMBER);

        assertEquals(TEST_NUMBER, data.getValue());
        assertEquals(TEST_SECTION, data.getSection());
        assertEquals(TEST_ITEM, data.getItem());
        assertEquals(TEST_CHECKED, data.isChecked());

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void fromNumberToData_IndexOutOfBound() throws Exception {
        DataUtils.toData(TEST_NUMBER_INDEX_OUT_OF_BOUND);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getShortFromBitSet_IndexOutOfBound() throws Exception {
        BitSet bitSet = new BitSet();
        bitSet.set(Short.SIZE + 1);

        DataUtils.getShortFromBitSet(bitSet);
    }
}