package com.myApp.algorithmproject.hash;

import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * author: zhouyh
 * created on: 2020-06-09 21:46
 * description:
 */
public class Person {

    private int age;
    private float height;
    private String name;

    public Person(int age, float height, String name) {
        this.age = age;
        this.height = height;
        this.name = name;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int hashCode() {
        /**
         *充分利用所有性质算出来
         */
        int hashCode = Integer.hashCode(age);
        hashCode = hashCode * 31 + Float.floatToIntBits(height);
        hashCode = hashCode * 31 + (name != null ? name.hashCode():0);
        return hashCode;
    }

    /**
     * 用以比较2个对象是否相等
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        //内存地址
        if (this == obj) return true;
        if (obj == null || obj.getClass() !=  getClass()) return false;
//        if (obj == null || obj instanceof Person) return false;

        //比较成员变量
        Person person = (Person) obj;
        return person.age == age
                && person.height == height
                && (person.name != null?  person.name.equals(name) : name == null);
    }
}
