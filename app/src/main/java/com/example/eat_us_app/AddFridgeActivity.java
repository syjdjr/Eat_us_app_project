package com.example.eat_us_app;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
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

public class AddFridgeActivity extends AppCompatActivity{
    Button vdate_btn;
    Button category_btn;
    Button addsave_btn;

    DatePickerDialog datePickerDialog;
    TextView textView;
    public String vdate;
    private ImageView selectedCategoryImage;
    private int selectedImageResourceId;
    int alarmYear, alarmMonth, alarmDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        // 툴바 세팅
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("냉장고 채우기");
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
                // 알람 코드 오류 존재함
                /*
                // [알람]: 알람을 설정해주는 변수
                final Calendar alarmDate = Calendar.getInstance();

                // [알람]: 캘린더 시간으로 알람 설정
                alarmDate.set(Calendar.YEAR, alarmYear);
                alarmDate.set(Calendar.MONTH, alarmMonth);
                alarmDate.set(Calendar.DAY_OF_MONTH, alarmDay);

                // [알람]: 3일 전으로 설정
                alarmDate.add(Calendar.DATE, -3);
                // [알람]: 10초 정도 뒤로 설정 ===
                alarmDate.add(Calendar.SECOND, 10);

                // [알람]: 알람을 실행해주는 메소드
                startAlarm(alarmDate); */
                saveItem();
                showSaveToast();
            }
        });
    }

    private void startAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if(c.before(Calendar.getInstance())){
            c.add(Calendar.DATE, 1);
        }
        //RTC_WAKE : 지정된 시간에 기기의 절전 모드를 해제하여 대기 중인 인텐트를 실행
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
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
        dialog.setContentView(R.layout.save_toast_layout);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = 800;
        params.height = 1000;
        dialog.getWindow().setAttributes(params);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 냉장고 가기 버튼 리스너 설정
        Button goFridge_btn = dialog.findViewById(R.id.goFridge_btn);
        goFridge_btn.setOnClickListener(new View.OnClickListener() {
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
                                alarmYear =year;
                                alarmMonth =month;
                                alarmDay =dayofMonth;
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

        // 냉장고 아이템 객체 생성 및 인텐트에 추가
        FridgeItem newItem = new FridgeItem(
                selectedImageResourceId,
                nameInput.getText().toString(),
                quantity,
                vdate,
                memoInput.getText().toString()
        );

        Intent returnIntent = new Intent(getApplicationContext(), FridgeActivity.class);
        returnIntent.putExtra("FRIDGE_ITEM", newItem);
        setResult(RESULT_OK, returnIntent); // 생성된 아이템 반환
    }
}
