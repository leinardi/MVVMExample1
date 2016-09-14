package com.leinardi.mvvm.util;

import android.support.annotation.VisibleForTesting;

import com.leinardi.mvvm.model.Data;

import java.util.BitSet;

/**
 * Created by leinardi on 27/08/16.
 */

public class DataUtils {

    private static final int MAX_BIT_SIZE = 8;
    private static final int MAX_NUMBER_SIZE = (int) Math.pow(2, MAX_BIT_SIZE);
    private static final int SECTION_START_INDEX = 0;
    private static final int SECTION_END_INDEX = 1;
    private static final int ITEM_START_INDEX = 2;
    private static final int ITEM_END_INDEX = 6;
    private static final int CHECKED_INDEX = 7;

    private DataUtils() {
    }

    public static Data toData(short number) {
        if (number >= MAX_NUMBER_SIZE) {
            throw new IndexOutOfBoundsException("Number bigger than " + MAX_NUMBER_SIZE);
        }

        BitSet bitSet = new BitSet(MAX_BIT_SIZE);
        for (int index = 0; index < Short.SIZE; index++) {
            if (((number >> index) & 1) == 1) {
                bitSet.set(index);
            }
        }

        short section = getShortFromBitSet(bitSet.get(SECTION_START_INDEX, SECTION_END_INDEX + 1));
        short item = getShortFromBitSet(bitSet.get(ITEM_START_INDEX, ITEM_END_INDEX + 1));
        boolean checked = bitSet.get(CHECKED_INDEX);
        return new Data(number, section, item, checked);
    }

    @VisibleForTesting
    public static short getShortFromBitSet(BitSet bitSet) {
        if (bitSet.length() > Short.SIZE) {
            throw new IndexOutOfBoundsException("BitSet size bigger than Short.SIZE");
        }

        short value = 0;
        for (int i = 0; i < bitSet.length(); ++i) {
            value += bitSet.get(i) ? (1 << i) : 0;
        }
        return value;
    }
}
