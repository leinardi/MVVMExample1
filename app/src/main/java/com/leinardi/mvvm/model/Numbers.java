package com.leinardi.mvvm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leinardi on 07/09/16.
 */
public class Numbers {

    @SerializedName("numbers")
    @Expose
    private List<Short> numbers = new ArrayList<>();

    /**
     * @return The numbers
     */
    public List<Short> getNumbers() {
        return numbers;
    }

    /**
     * @param numbers The numbers
     */
    public void setNumbers(List<Short> numbers) {
        this.numbers = numbers;
    }

    @Override
    public String toString() {
        return "Numbers{" +
                "numbers=" + numbers +
                '}';
    }
}