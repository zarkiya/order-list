package com.example.tubes_pba;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Second_Main_old extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView name, email;
    Button signOutBtn, mainmenu;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_second2);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        signOutBtn = findViewById(R.id.signout);
        mainmenu = findViewById(R.id.mainmenu);
        imageView = findViewById(R.id.imageView);




        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct !=null){
            Uri personPhoto = acct.getPhotoUrl();
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String nickName = acct.getGivenName();


            Picasso.get()
                    .load(personPhoto)
                            .into(imageView);
            name.setText(personName);
            email.setText(personEmail);

            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            String url = "https://skinnie.my.id/register/google"; // Ganti dengan URL API yang sesuai

            JSONObject json = new JSONObject();
            try {
                json.put("name", personName);
                json.put("email", personEmail);
                json.put("nickname", nickName);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody requestBody = RequestBody.create(mediaType, json.toString());
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                                                @Override
                                                public void onFailure(Call call, IOException e) {
                                                    // Penanganan jika permintaan gagal
                                                }

                                                @Override
                                                public void onResponse(Call call, Response response) throws IOException {
                                                    // Penanganan jika permintaan berhasil
                                                    if (response.isSuccessful()) {
                                                        // Response sukses
                                                    } else {
                                                        // Response gagal
                                                    }
                                                }
            });
        }

        mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intet = new Intent(Second_Main_old.this, MainActivity.class);
                startActivity(intet);
            }
        });


        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

       }

       void signOut(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(Second_Main_old.this,Main.class));
            }
        });
       }


}
