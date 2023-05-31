package com.example.tubes_pba;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.api.services.storage.model.StorageObject;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Second_Main extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FileAdapter fileAdapter;

    private Storage storage;
    private static final String BUCKET_NAME = "images-skinnie"; // Ganti dengan nama bucket Anda

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_file);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Menginisialisasi koneksi ke Google Cloud Storage
        initializeStorage();

        // Mengambil daftar file dari Google Cloud Storage
        new FetchFilesTask().execute();
    }

    private void initializeStorage() {
        try {
            InputStream serviceAccount = getResources().openRawResource(R.raw.cred);
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class FetchFilesTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... voids) {
            List<String> fileList = new ArrayList<>();

            // Mengambil daftar file dari Google Cloud Storage
            if (storage != null) {
                Iterable<Blob> blobs = storage.list(BUCKET_NAME).iterateAll();
                for (Blob blob : blobs) {
                    fileList.add(blob.getName());
                }
            }

            return fileList;
        }

        @Override
        protected void onPostExecute(List<String> fileList) {
            // Menampilkan daftar file menggunakan adapter
            fileAdapter = new FileAdapter(Second_Main.this, fileList, storage);
            recyclerView.setAdapter(fileAdapter);
        }
    }
}

