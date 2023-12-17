package com.example.eat_us_app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class AddFreezerActivity extends AppCompatActivity {
    Button vdate_btn;
    Button category_btn;
    Button addsave_btn;

    DatePickerDialog datePickerDialog;
    TextView textView;
    public String vdate;
    private ImageView selectedCategoryImage;
    private int selectedImageResourceId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        // 툴바 세팅
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("냉동고 채우기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로 가기 버튼 활성화
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // 뷰 컴포넌트 연결
        vdate_btn = findViewById(R.id.vdate_btn);
        category_btn = findViewById(R.id.category_btn);
        addsave_btn = findViewById(R.id.addsave_btn);
        selectedCategoryImage = findViewById(R.id.selectedCategoryImage);

        // 카테고리 버튼 클릭 리스너
        category_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();
            }
        });

        // 저장 버튼 클릭 리스너
        addsave_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
                showSaveToast();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 뒤로 가기 버튼 처리
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 저장 완료 토스트 메시지 표시
    public void showSaveToast(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.save_toast2_layout);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = 800;
        params.height = 1000;
        dialog.getWindow().setAttributes(params);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 냉동고 가기 버튼 리스너 설정
        Button goFreezer_btn = dialog.findViewById(R.id.goFreezer_btn);
        goFreezer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        // 뒤로 가기 버튼 리스너 설정
        Button goBack_btn = dialog.findViewById(R.id.goBack_btn);
        goBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.layout_toast);
    }

    // 선택된 카테고리 이미지 업로드
    private void updateSelectedCategoryImage(int drawableId){
        selectedCategoryImage.setImageResource(drawableId);
        selectedCategoryImage.setVisibility(View.VISIBLE);
        selectedImageResourceId = drawableId;
    }

    // 유통기한 버튼 클릭 리스너
    public void onClick(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                        vdate_btn.setText(year + "-" + (month + 1) + "-" + dayofMonth);
                        vdate = year + "-" + (month + 1) + "-" + dayofMonth;
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    // 카테고리 선택 다이얼로그 표시
    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.category_bottomsheet);

        LinearLayout fish = dialog.findViewById(R.id.fish);
        LinearLayout nuts = dialog.findViewById(R.id.nuts);
        LinearLayout fruit = dialog.findViewById(R.id.fruit);
        LinearLayout sauce = dialog.findViewById(R.id.sauce);
        LinearLayout egg = dialog.findViewById(R.id.egg);
        LinearLayout grain = dialog.findViewById(R.id.grain);
        LinearLayout drink = dialog.findViewById(R.id.drink);
        LinearLayout veggie = dialog.findViewById(R.id.veggie);
        LinearLayout meat = dialog.findViewById(R.id.meat);
        LinearLayout alcohol = dialog.findViewById(R.id.alcohol);
        LinearLayout milk = dialog.findViewById(R.id.milk);
        LinearLayout cheese = dialog.findViewById(R.id.cheese);

        fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelectedCategoryImage(R.drawable.fish);
                dialog.dismiss();
            }
        });
        nuts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelectedCategoryImage(R.drawable.nuts);
                dialog.dismiss();
            }
        });

        fruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelectedCategoryImage(R.drawable.fruit);
                dialog.dismiss();
            }
        });

        sauce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelectedCategoryImage(R.drawable.sauce);
                dialog.dismiss();
            }
        });

        egg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelectedCategoryImage(R.drawable.egg);
                dialog.dismiss();
            }
        });

        grain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelectedCategoryImage(R.drawable.grain);
                dialog.dismiss();
            }
        });

        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelectedCategoryImage(R.drawable.drink);
                dialog.dismiss();
            }
        });

        veggie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelectedCategoryImage(R.drawable.veggie);
                dialog.dismiss();
            }
        });

        meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelectedCategoryImage(R.drawable.meat);
                dialog.dismiss();
            }
        });

        alcohol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelectedCategoryImage(R.drawable.alcohol);
                dialog.dismiss();
            }
        });

        milk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelectedCategoryImage(R.drawable.milk);
                dialog.dismiss();
            }
        });
        cheese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelectedCategoryImage(R.drawable.cheese);
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    // 아이템 저장 로직
    private void saveItem(){
        // 입력된 데이터 가져오기
        EditText nameInput = findViewById(R.id.nameinput);
        EditText numInput = findViewById(R.id.numinput);
        EditText memoInput = findViewById(R.id.memoinput);
        int quantity;
        try {
            quantity = Integer.parseInt(numInput.getText().toString());
        } catch (NumberFormatException e) {
            quantity = 0;
        }

        // 냉동고 아이템 객체 생성 및 인텐트에 추가
        FreezerItem newItem = new FreezerItem(
                selectedImageResourceId,
                nameInput.getText().toString(),
                quantity,
                vdate,
                memoInput.getText().toString()
        );

        Intent returnIntent = new Intent(getApplicationContext(), FreezerActivity.class);
        returnIntent.putExtra("FREEZER_ITEM", newItem);
        setResult(RESULT_OK, returnIntent); // 생성된 아이템 반환
    }
}
