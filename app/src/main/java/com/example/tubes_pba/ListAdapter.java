package com.example.tubes_pba;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ListAdapter extends ArrayAdapter {

    private int[] Icon;
    private String[] Headline;
    private String[] Subhead;
    private String[] Est;
    private String[] Angka;
    private String[] Menit;
    private Context context;

    ListAdapter(@NonNull Context context, int[] icon, String[] headline, String[] subhead, String[] est, String[] angka, String[] menit) {
        super(context, R.layout.customlistview, headline);
        this.Icon = icon;
        this.Headline = headline;
        this.Subhead = subhead;
        this.Est = est;
        this.Angka = angka;
        this.Menit = menit;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.customlistview, null, true);
        ImageView icon = view.findViewById(R.id.imageList);
        TextView headline = view.findViewById(R.id.text_headline);
        TextView subhead = view.findViewById(R.id.text_subhead);
        TextView est = view.findViewById(R.id.text_est);
        TextView angka = view.findViewById(R.id.text_angka);
        TextView menit = view.findViewById(R.id.text_menit);

        icon.setImageResource(Icon[position]);
        headline.setText(Headline[position]);
        subhead.setText(Subhead[position]);
        est.setText(Est[position]);
        angka.setText(Angka[position]);
        menit.setText(Menit[position]);
        return view;
    }
}