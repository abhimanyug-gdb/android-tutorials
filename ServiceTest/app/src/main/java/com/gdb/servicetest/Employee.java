package com.gdb.servicetest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by devdatta on 7/1/16.
 */

public class Employee implements Parcelable {
    private String name;
    private int age;
    private float weight;
    private boolean isOnBench;

    public Employee() {
        name = "Default";
        age = 0;
        weight = 0;
    }

    public Employee(String name, int age, float weight, boolean isOnBench) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.isOnBench = isOnBench;
    }

    public  Employee(Parcel parcel) {
        this.name = parcel.readString();
        this.age = parcel.readInt();
        this.weight = parcel.readFloat();
        this.isOnBench = (parcel.readInt() == 1);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public boolean isOnBench() {
        return isOnBench;
    }

    public void setIsOnBench(boolean isOnBench) {
        this.isOnBench = isOnBench;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeFloat(weight);
        dest.writeInt(isOnBench ? 1 : 0);
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel source) {
            return new Employee(source);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };
}
