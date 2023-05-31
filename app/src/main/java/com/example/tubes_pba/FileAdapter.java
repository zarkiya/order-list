package com.example.tubes_pba;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {

    private Context context;
    private List<String> fileList;
    private Storage storage;

    public FileAdapter(Context context, List<String> fileList, Storage storage) {
        this.context = context;
        this.fileList = fileList;
        this.storage = storage;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_file, parent, false);
        return new FileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        String fileName = fileList.get(position);
        holder.textFileName.setText(fileName);

        // Mengambil thumbnail dari Google Cloud Storage
        new FetchThumbnailTask(holder.imageThumbnail).execute(fileName);
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public class FileViewHolder extends RecyclerView.ViewHolder {
        TextView textFileName;
        ImageView imageThumbnail;

        public FileViewHolder(@NonNull View itemView) {
            super(itemView);
            textFileName = itemView.findViewById(R.id.textFileName);
            imageThumbnail = itemView.findViewById(R.id.imageThumbnail);
        }
    }

    private class FetchThumbnailTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;

        public FetchThumbnailTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... fileNames) {
            String fileName = fileNames[0];

                // Mendapatkan thumbnail dari Google Cloud Storage
                Blob blob = storage.get("images-skinnie", fileName);
                byte[] thumbnailBytes = blob.getContent(Blob.BlobSourceOption.generationMatch());
                return BitmapFactory.decodeByteArray(thumbnailBytes, 0, thumbnailBytes.length);


        }

        @Override
        protected void onPostExecute(Bitmap thumbnail) {
            if (thumbnail != null) {
                imageView.setImageBitmap(thumbnail);
            } else {
                // Menggunakan placeholder jika thumbnail tidak tersedia
                imageView.setImageResource(R.drawable.facebook);
            }
        }
    }
}
