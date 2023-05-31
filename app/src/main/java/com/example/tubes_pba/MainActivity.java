package com.example.tubes_pba;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;
//import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    int[] iconList = new int[]{
            R.drawable.now, R.drawable.next, R.drawable.next, R.drawable.next,
            R.drawable.next, R.drawable.next, R.drawable.next, R.drawable.next,
            R.drawable.next,  R.drawable.next

    };

    String[] Subhead = {"Menit", "Menit", "Menit", "Menit", "Menit", "Menit", "Menit", "Menit",
            "Menit", "Menit", "Menit", "Menit", "Menit", "Menit", "Menit", "Menit",
            "Menit", "Menit", "Menit", "Menit", "Menit", "Menit", "Menit", "Menit",
            "Menit", "Menit", "Menit", "Menit", "Menit", "Menit", "Menit", "Menit",
            "Menit", "Menit", "Menit", "Menit", "Menit", "Menit", "Menit", "Menit",
            "Menit", "Menit", "Menit", "Menit", "Menit", "Menit", "Menit", "Menit",
            "Menit", "Menit", "Menit", "Menit", "Menit", "Menit", "Menit", "Menit",
            "Menit", "Menit", "Menit", "Menit", "Menit", "Menit", "Menit", "Menit"
    };

    String[] nama;
    String[] nomor;
    String[] pesanan;
    String[] estimasi;
    ListView ListView01;
    Menu menu;
    protected Cursor cursor;
    DataHelper dbcenter;
    public static MainActivity ma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn=(Button)findViewById(R.id.button2);
        Button btn2=(Button)findViewById(R.id.button3);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent inte = new Intent(MainActivity.this, BuatBiodata.class);
                startActivity(inte);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intet = new Intent(MainActivity.this, Catatan.class);
                startActivity(intet);
            }
        });


        ma = this;
        dbcenter = new DataHelper(this);
        RefreshList();
    }

    public void RefreshList(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM biodata",null);
        nama = new String[cursor.getCount()];
        nomor = new String[cursor.getCount()];
        pesanan = new String[cursor.getCount()];
        estimasi = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            nama[cc] = cursor.getString(1).toString();
            nomor[cc] = cursor.getString(0).toString();
            pesanan[cc] = cursor.getString(2).toString();
            estimasi[cc] = cursor.getString(4).toString();
        }
        ListView01 = (ListView)findViewById(R.id.listView1);
        ListAdapter listAdapter = new ListAdapter(this, iconList, nama, nomor, pesanan, estimasi, Subhead);
        ListView01.setAdapter(listAdapter);
        ListView01.setSelected(true);
        ListView01.setOnItemClickListener(new OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = nama[arg2]; //.getItemAtPosition(arg2).toString();
                final CharSequence[] dialogitem = {"Lihat Orderan", "Update Orderan", "Hapus Orderan"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item){
                            case 0 :
                                Intent i = new Intent(getApplicationContext(), LihatBiodata.class);
                                i.putExtra("nama", selection);
                                startActivity(i);
                                break;
                            case 1 :
                                Intent in = new Intent(getApplicationContext(), UpdateBiodata.class);
                                in.putExtra("nama", selection);
                                startActivity(in);
                                break;
                            case 2 :
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("delete from biodata where nama = '"+selection+"'");
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
            }});
        ((ArrayAdapter)ListView01.getAdapter()).notifyDataSetInvalidated();

    }
}