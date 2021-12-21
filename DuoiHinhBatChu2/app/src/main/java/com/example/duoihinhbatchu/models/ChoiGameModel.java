package com.example.duoihinhbatchu.models;

import com.example.duoihinhbatchu.ChoiGameMainActivity;
import com.example.duoihinhbatchu.DATA;
import com.example.duoihinhbatchu.object.Caudo;
import com.example.duoihinhbatchu.object.Nguoidung;

import java.util.ArrayList;

public class ChoiGameModel {
    ChoiGameMainActivity c;
    ArrayList<Caudo> arr;
    int cauSo=-1;
    public Nguoidung nguoidung;


    public ChoiGameModel(ChoiGameMainActivity c) {

        this.c = c;
        nguoidung = new Nguoidung();
        taoData();
    }
    private void taoData(){
        arr = new ArrayList<>(DATA.getData().arrCauDo);

//        arr.add(new Caudo("man1","soctrang","https://i.ytimg.com/vi/mQxjJpr8Gvg/maxresdefault.jpg"));
//        arr.add(new Caudo("man2","caheo","https://i.ytimg.com/vi/b7D1n0gxnRQ/hqdefault.jpg"));
//        arr.add(new Caudo("man3","kienlua","https://i.ytimg.com/vi/1sGZnGD9p60/hqdefault.jpg"));


    }
    public Caudo layCauDo(){
        cauSo++;
        if (cauSo>=arr.size()){
            cauSo=arr.size()-1;
        }

        return arr.get(cauSo);
    }
    public void layThonTin(){
        nguoidung.getTT(c);
    }
    public void luuThongTin(){
        nguoidung.saveTT(c);
    }

}
