package com.example.eat_us_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;

public class Put_Recipe extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private Bitmap selectedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.put_recipe);

        // 툴바 세팅
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("레시피 추가하기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imageView = findViewById(R.id.plus);
        Button finishButton = findViewById(R.id.finish);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 선택한 비트맵과 텍스트 데이터를 다른 액티비티로 전달
                Intent resultIntent = new Intent();
                resultIntent.putExtra("bitmap", selectedBitmap);
                resultIntent.putExtra("recipe", "Recipe 데이터"); // 실제 데이터를 가져와야 함
                resultIntent.putExtra("recipe_topic", "Recipe Topic 데이터"); // 실제 데이터를 가져와야 함
                setResult(RESULT_OK, resultIntent);

                // Navigate back to Recipe_list
                Intent recipeListIntent = new Intent(Put_Recipe.this, Recipe_list.class);
                startActivity(recipeListIntent);

                finish();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // 이미지 크기를 조절하도록 수정
                Bitmap resizeBitmap = resizeBitmap(bitmap, 1000, 600);
                imageView.setImageBitmap(resizeBitmap);
                selectedBitmap = resizeBitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap resizeBitmap(Bitmap originalBitmap, int newWidth, int newHeight) {
        return Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
    }

    public interface RecipeDataCallback {
        void onRecipeDataReceived(Bitmap selectedBitmap, String recipeData, String recipeTopicData);
    }

    public void sendDataToRecipe(RecipeDataCallback callback) {
        String recipeData = "Recipe 데이터"; // 실제 데이터를 가져와야 함
        String recipeTopicData = "Recipe Topic 데이터"; // 실제 데이터를 가져와야 함

        callback.onRecipeDataReceived(selectedBitmap, recipeData, recipeTopicData);
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