package com.myApp.algorithmproject.hash;

import androidx.annotation.Nullable;

/**
 * author: zhouyh
 * created on: 2020-06-11 09:41
 * description:
 */
public class Key {


    private int value;

    public Key(int value) {
        this.value = value;
    }

    @Override
    public int hashCode() {

        return value / 20;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != getClass()) return false;

        return ((Key)obj).value == value;
    }

    @Override
    public String toString() {
        return "v(" + value + ")";
    }
}
