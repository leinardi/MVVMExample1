package com.leinardi.mvvm.model;

/**
 * Created by leinardi on 27/08/16.
 */

public class Data {

    private short value;
    private short section;
    private short item;
    private boolean checked;

    public Data(short value, short section, short item, boolean checked) {
        this.value = value;
        this.section = section;
        this.item = item;
        this.checked = checked;
    }

    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }

    public short getSection() {
        return section;
    }

    public void setSection(short section) {
        this.section = section;
    }

    public short getItem() {
        return item;
    }

    public void setItem(short item) {
        this.item = item;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
