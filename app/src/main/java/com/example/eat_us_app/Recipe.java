package com.example.eat_us_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Recipe extends AppCompatActivity {
    private String recipeText;
    private String textName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);

        // 툴바 세팅
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("마이 레시피");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String recipeName = getIntent().getStringExtra("recipeName");

        int imageResource = 0;

        if (recipeName != null) {
            recipeName = recipeName.toLowerCase();

            // 레시피 이름에 따라 이미지 및 텍스트 설정
            if (recipeName.equals("chicken salad")) {
                imageResource = R.drawable.recipe2;
                recipeText = getString(R.string.chicken_salad);
            } else if (recipeName.equals("salmon salad")) {
                imageResource = R.drawable.recipe3;
                recipeText = getString(R.string.salmon_salad);
            } else if (recipeName.equals("kimchi fried rice")) {
                imageResource = R.drawable.recipe4;
                recipeText = getString(R.string.kimchi_fried_rice);
            }
        }

        // 이미지 및 텍스트 설정
        ImageView imageView = findViewById(R.id.photo);
        TextView textView = findViewById(R.id.topic);
        TextView recipeTextView = findViewById(R.id.recipeTextView);

        imageView.setImageResource(imageResource);
        textView.setText(recipeText);
        recipeTextView.setText(recipeName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 툴바 뒤로 가기 처리
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}