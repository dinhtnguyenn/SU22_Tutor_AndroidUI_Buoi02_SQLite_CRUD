package com.dinhnt.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.dinhnt.myapplication.R;
import com.dinhnt.myapplication.dao.KhoanThuDAO;
import com.dinhnt.myapplication.model.KhoanThu;

import java.util.ArrayList;

public class KhoanThuAdapter extends BaseAdapter {

    private ArrayList<KhoanThu> list;
    private Context context;
    private KhoanThuDAO khoanThuDAO;

    public KhoanThuAdapter(ArrayList<KhoanThu> list, KhoanThuDAO khoanThuDAO, Context context) {
        this.list = list;
        this.context = context;
        this.khoanThuDAO = khoanThuDAO;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public static class ViewOfItem{
        TextView txtTen, txtTien;
        ImageView ivSua, ivXoa;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewOfItem viewOfItem;
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        if(view == null){
            viewOfItem = new ViewOfItem();
            view = inflater.inflate(R.layout.item_listview, null);
            viewOfItem.txtTen = view.findViewById(R.id.txtTen);
            viewOfItem.txtTien = view.findViewById(R.id.txtTien);
            viewOfItem.ivSua = view.findViewById(R.id.ivSua);
            viewOfItem.ivXoa = view.findViewById(R.id.ivDel);
            view.setTag(viewOfItem);
        }else {
            viewOfItem = (ViewOfItem) view.getTag();
        }

        viewOfItem.txtTen.setText(list.get(i).getTen());
        viewOfItem.txtTien.setText(list.get(i).getTien());

        viewOfItem.ivSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogUpdate(i);
            }
        });

        viewOfItem.ivXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(khoanThuDAO.delete(list.get(i).getId())){
                    Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                    reloadData();
                }else {
                    Toast.makeText(context, "Xoá không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void showDialogUpdate(int i){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update, null);
        EditText edtTen = view.findViewById(R.id.edtTen);
        EditText edtTien = view.findViewById(R.id.edtTien);
        edtTen.setText(list.get(i).getTen());
        edtTien.setText(list.get(i).getTien());
        int idKhoanThu = list.get(i).getId();

        builder.setView(view);
        builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String ten = edtTen.getText().toString();
                String tien = edtTien.getText().toString();
                KhoanThu khoanThu = new KhoanThu(idKhoanThu, ten, tien);
                if(khoanThuDAO.update(khoanThu)){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    reloadData();
                }else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
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

    private void reloadData(){
        list.clear();
        list = khoanThuDAO.getAll();
        notifyDataSetChanged();
    }
}
