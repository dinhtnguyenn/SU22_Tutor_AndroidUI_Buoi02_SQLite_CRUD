package com.dinhnt.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.dinhnt.myapplication.adapter.KhoanThuAdapter;
import com.dinhnt.myapplication.dao.KhoanThuDAO;
import com.dinhnt.myapplication.model.KhoanThu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    KhoanThuDAO khoanThuDAO;
    ListView listKhoanThu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //giao diện (danh sách, item)
        listKhoanThu = findViewById(R.id.listKhoanThu);
        FloatingActionButton floatingAdd = findViewById(R.id.floatingAdd);

        //data
        khoanThuDAO = new KhoanThuDAO(this);
        getAllData();

        floatingAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hiển thị dialog add
                showDialogAdd();
            }
        });
    }

    private void getAllData(){
        ArrayList<KhoanThu> list = khoanThuDAO.getAll();

        //adapter
        KhoanThuAdapter adapter = new KhoanThuAdapter(list, khoanThuDAO, this);
        listKhoanThu.setAdapter(adapter);
    }

    private void showDialogAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add, null);
        EditText edtTen = view.findViewById(R.id.edtTen);
        EditText edtTien = view.findViewById(R.id.edtTien);
        builder.setView(view);
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String ten = edtTen.getText().toString();
                String tien = edtTien.getText().toString();
                KhoanThu khoanThu = new KhoanThu(ten, tien);
                if(khoanThuDAO.add(khoanThu)){
                    Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    getAllData();
                }else {
                    Toast.makeText(MainActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}