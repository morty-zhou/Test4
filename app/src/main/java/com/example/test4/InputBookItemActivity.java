package com.example.test4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

public class InputBookItemActivity extends AppCompatActivity {

    public static final int RESULT_CODE_SUCCESS = 114514;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_book_item);

        position = this.getIntent().getIntExtra("position",0);
        String title = this.getIntent().getStringExtra("title");
        String author = this.getIntent().getStringExtra("author");
        String shop = this.getIntent().getStringExtra("shop");
        Double price = this.getIntent().getDoubleExtra("price", 0);

        EditText editTextTitle = findViewById(R.id.editTextBookItemTitle);
        EditText editTextAuthor = findViewById(R.id.editTextBookItemAuthor);
        EditText editTextShop = findViewById(R.id.editTextBookItemShop);
        EditText editTextPrice = findViewById(R.id.editTextBookItemPrice);

        if(null!= title){
            editTextTitle.setText(title);
            editTextAuthor.setText(author);
            editTextAuthor.setText(shop);
            editTextPrice.setText(price.toString());
        }


        Button buttonok = findViewById(R.id.inputButton_ok);
        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("title",editTextTitle.getText().toString());
                bundle.putString("author",editTextAuthor.getText().toString());
                bundle.putString("shop",editTextShop.getText().toString());
                double price = Double.parseDouble(editTextPrice.getText().toString());
                bundle.putDouble("price",price);
                bundle.putInt("position", position);

                intent.putExtras(bundle);
                setResult(RESULT_CODE_SUCCESS, intent);
                InputBookItemActivity.this.finish();

            }
        });

    }
}