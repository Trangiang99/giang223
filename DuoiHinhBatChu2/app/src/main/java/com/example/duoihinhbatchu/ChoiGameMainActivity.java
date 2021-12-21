package com.example.duoihinhbatchu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.duoihinhbatchu.adapter.CauTraLoiAdapter;
import com.example.duoihinhbatchu.adapter.DapAnAdapter;
import com.example.duoihinhbatchu.models.ChoiGameModel;
import com.example.duoihinhbatchu.object.Caudo;

import java.util.ArrayList;
import java.util.Random;

public class ChoiGameMainActivity extends AppCompatActivity {

    ChoiGameModel models;
    Caudo caudo;


    private String dapAn= "yeuot";

    ArrayList<String> arrCautraloi;
    GridView gdvCautraloi;
    Button btngoiy;

    ArrayList<String> arrDapAn;
    ImageView anhCauDo;
    TextView txtman;
    TextView txvTienNguoiDung;
    GridView gdvDapAn;

    int index=0;

    private MusicService mServ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choi_game_main);
        init();
        anhxa();
        setOnClick();
        hiencaudo();





    }
    private void anhxa(){
        gdvCautraloi = findViewById(R.id.gdvCautraloi);
        gdvDapAn = findViewById(R.id.gdvDapAn);
        anhCauDo = findViewById(R.id.imganhcaudo);
        txtman = findViewById(R.id.txtman);
        txvTienNguoiDung = findViewById(R.id.txvTienNguoiDung);

        //btngoiy =(Button) findViewById(R.id.btn_goiy);


    }

    private void init(){
        models=new ChoiGameModel(this);
        arrCautraloi = new ArrayList<>();



        arrDapAn = new ArrayList<>();

    }
    private void hiencaudo(){
        caudo=models.layCauDo();
        dapAn=caudo.dapAn;
        banData();
        hienthiCauTraLoi();
        hienthiDapAn();
        Glide.with(this)
                .load(caudo.anh)
                .into(anhCauDo);
        models.layThonTin();
        txvTienNguoiDung.setText(models.nguoidung.tien+"");
        txtman.setText("Màn "+caudo.id);


    }

    private void hienthiCauTraLoi(){
        gdvCautraloi.setNumColumns(arrCautraloi.size());
        gdvCautraloi.setAdapter(new CauTraLoiAdapter(this,0,arrCautraloi));

    }

    private void hienthiDapAn(){
        gdvDapAn.setNumColumns(arrDapAn.size()/2);
        gdvDapAn.setAdapter(new DapAnAdapter(this,0,arrDapAn));

    }


    private void setOnClick(){
        gdvDapAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);
                if(s.length() !=0 && index <arrCautraloi.size()){

                    for (int i=0;i<arrCautraloi.size();i++){
                        if(arrCautraloi.get(i).length()==0){
                            index = i;
                            break;
                        }
                    }
                    arrDapAn.set(position,"");
                    arrCautraloi.set(index,s);
                    index++;
                    hienthiCauTraLoi();
                    hienthiDapAn();
                    checkWin();
                }
            }
        });
        gdvCautraloi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);
                if(s.length() !=0 ){
                    index = position;
                    arrCautraloi.set(position,"");
                    for(int i =0; i<arrDapAn.size();i++){
                        if(arrDapAn.get(i).length()==0){
                            arrDapAn.set(i,s);
                            break;
                        }
                    }
                    hienthiCauTraLoi();
                    hienthiDapAn();
                }
            }
        });

//        btngoiy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                moGoiYDilog();
//
//            }
//        });

    }

    private void banData(){
        index=0;
        arrCautraloi.clear();
        arrDapAn.clear();
        Random r = new Random();
        for (int i =0; i<dapAn.length();i++){
            arrCautraloi.add("");
            String s = ""+(char)(r.nextInt(26)+65);
            arrDapAn.add(s);
            String s1 = ""+(char)(r.nextInt(26)+65);
            arrDapAn.add(s1);
        }
        for (int i =0; i<dapAn.length();i++){
            String s =""+dapAn.charAt(i);
            arrDapAn.set(i,s.toUpperCase());
        }
        for (int i=0; i<arrDapAn.size();i++){
            String s = arrDapAn.get(i);
            int vt = r.nextInt(arrDapAn.size());
            arrDapAn.set(i,arrDapAn.get(vt));
            arrDapAn.set(vt,s);
        }

    }
    private void checkWin(){
        String s="";
        for (String sl :arrCautraloi){
            s=s+sl;
        }
        s=s.toUpperCase();
        if(s.equals(dapAn.toUpperCase())){
            openDialog(s);





        }
    }

    public void moGoiY(View view) {

        AlertDialog.Builder b = new AlertDialog.Builder(ChoiGameMainActivity.this);
        b.setTitle("Xác Nhận");
        b.setMessage("Bạn có muốn dùng 5 xu đổi gợi ý không?");
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                models.luuThongTin();
                if (models.nguoidung.tien < 5) {
                    Toast.makeText(getApplicationContext(),"Bạn không đủ tiền",Toast.LENGTH_LONG).show();


                    return;
                }
                int id = -1;
//        for(int i = 0;i < arrCautraloi.size();i++){
//            if(arrCautraloi.get(i).length() == 0){
//                id = i;
//                break;
//            }
//        }
                if (id == -1) {
                    for (int i = 0; i < arrCautraloi.size(); i++) {
                        String s = dapAn.toUpperCase().charAt(i) + "";
                        if (!arrCautraloi.get(i).toUpperCase().equals(s)) {
                            id = i;
                            break;
                        }
                    }
                    for (int i = 0; i < arrDapAn.size(); i++) {
                        if (arrDapAn.get(i).length() == 0) {
                            arrDapAn.set(i, arrCautraloi.get(id));
                            break;
                        }
                    }
                }
                String goiY = "" + dapAn.charAt(id);
                goiY = goiY.toUpperCase();
                for (int i = 0; i < arrCautraloi.size(); i++) {
                    if (arrCautraloi.get(i).toUpperCase().equals(goiY)) {
                        arrCautraloi.set(i, "");
                        break;
                    }
                }
                for (int i = 0; i < arrDapAn.size(); i++) {
                    if (goiY.equals(arrDapAn.get(i))) {
                        arrDapAn.set(i, "");

                        break;
                    }
                }
                arrCautraloi.set(id, goiY);
                hienthiCauTraLoi();
                hienthiDapAn();
                models.luuThongTin();
                models.nguoidung.tien = models.nguoidung.tien - 5;
                models.luuThongTin();
                txvTienNguoiDung.setText(models.nguoidung.tien + "");
                checkWin();

            }
        });
        b.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        AlertDialog al = b.create();
        al.show();


    }



    public void clear(View view) {

        banData();

        hienthiCauTraLoi();
        hienthiDapAn();

    }


    public void back(View view) {
        Intent intent = new Intent();
        intent.setClass(this,MainActivity.class);
        startActivity(intent);
    }

    public void openDialog(String s){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_goiy);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnoke = dialog.findViewById(R.id.btn_oke);
        TextView txtdapan = dialog.findViewById(R.id.txtdapan);
        txtdapan.setText(s);
        dialog.show();
        btnoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                models.luuThongTin();
                models.nguoidung.tien = models.nguoidung.tien + 2;
                models.luuThongTin();
                hiencaudo();
                dialog.dismiss();
            }
        });
    }
}