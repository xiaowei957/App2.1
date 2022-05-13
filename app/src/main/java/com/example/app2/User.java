package com.example.app2;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String pwd;
    private String name;

    public User() {
    }

    public User(Parcel in) {
        pwd = in.readString();
        name = in.readString();
    }

    //接口描述，默认为0即可
    @Override
    public int describeContents() {
        return 0;
    }

    //將数据写入Parcel容器，由Parcel容器保存，以便从parcel容器获取数据
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pwd);
        dest.writeString(name);
    }

    //静态的Parcelable.Creator接口
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        //实现从Parcel容器中读取传递数据值，封装成Parcelable对象返回逻辑层
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        //仅一句话即可（return new T[size]），供外部类反序列化本类数组使用。
        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

