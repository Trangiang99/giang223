package com.example.duoihinhbatchu;

import com.example.duoihinhbatchu.object.Caudo;

import java.util.ArrayList;

public class DATA {
    private static DATA data;
    static {
        data = new DATA();
    }

    public static DATA getData() {
        return data;
    }
    public  ArrayList<Caudo> arrCauDo = new ArrayList<>();
}
