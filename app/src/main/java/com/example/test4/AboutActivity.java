package com.example.test4;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Button buttonAddress = (Button) findViewById(R.id.address);
        buttonAddress.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri uri = Uri.parse("https://github.com/morty-zhou/Test4");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        Button buttonEmail = (Button) findViewById(R.id.email);
        buttonEmail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri uri_1 = Uri.parse("https://mail.qq.com/");
                Intent intent_1 = new Intent(Intent.ACTION_VIEW, uri_1);
                startActivity(intent_1);
            }
        });
    }
}
