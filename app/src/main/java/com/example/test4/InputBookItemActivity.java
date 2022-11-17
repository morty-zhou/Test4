package com.example.test4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputBookItemActivity extends AppCompatActivity {

    public static final int RESULT_CODE_SUCCESS = 114514;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_book_item);

        EditText editTextTitle = findViewById(R.id.editTextBookItemTitle);
        EditText editTextPrice = findViewById(R.id.editTextBookItemPrice);

        Button buttonok = findViewById(R.id.inputButton_ok);
        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("title",editTextTitle.getText().toString());
                double price = Double.parseDouble(editTextPrice.getText().toString());
                bundle.putDouble("price",price);

                intent.putExtras(bundle);
                setResult(RESULT_CODE_SUCCESS, intent);
                InputBookItemActivity.this.finish();

            }
        });

    }
}