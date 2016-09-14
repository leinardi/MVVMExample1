package com.leinardi.mvvm.util;

import com.leinardi.mvvm.model.Data;

import java.util.Comparator;

/**
 * Created by leinardi on 27/08/16.
 */

public class DataComparator implements Comparator<Data> {
    @Override
    public int compare(Data a, Data b) {
        if (a.getSection() < b.getSection()) {
            return -1;
        } else if (a.getSection() > b.getSection()) {
            return 1;
        } else if (a.getItem() < b.getItem()) {
            return -1;
        } else if (a.getItem() > b.getItem()) {
            return 1;
        } else {
            return 0;
        }
    }
}
