package com.example.eat_us_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FreezerActivity extends AppCompatActivity {
    private RecyclerView freezerRecyclerView; // 냉동고 아이템 표시할 RecyclerView
    private FreezerAdapter freezerAdapter; // RecyclerView에 사용될 어댑터
    private ArrayList<FreezerItem> freezerItemList; // 냉동고 아이템을 저장할 리스트
    private static final int ADD_FREEZER_REQUEST = 1; // 액티비티 결과 요청 코드

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freezer_main);

        // 툴바 세팅
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("나의 냉동고");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // 리스트, 어댑터 초기화
        freezerItemList = new ArrayList<>();
        freezerAdapter = new FreezerAdapter(freezerItemList);

        // RecyclerView 세팅
        freezerRecyclerView = findViewById(R.id.freezerRecyclerView);
        freezerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        freezerRecyclerView.setAdapter(freezerAdapter);

        // 서치뷰 설정
        SearchView searchItem = findViewById(R.id.searchItem);
        searchItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                freezerAdapter.filter(query); // 서치뷰의 텍스트 필터링
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                freezerAdapter.filter(newText); // 텍스트 변경시 필터링
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 냉동고 아이템 추가 요청에 따른 최종 결과 처리
        if (requestCode == ADD_FREEZER_REQUEST && resultCode == RESULT_OK) {
            FreezerItem newItem = data.getParcelableExtra("FREEZER_ITEM"); // 새 아이템 데이터 받기
            if (newItem != null) {
                freezerItemList.add(newItem); // 리스트에 아이템 추가
                freezerAdapter.notifyDataSetChanged(); // 어댑터에 변경 알림
                freezerAdapter.updateFreezerItemListFull(freezerItemList); // 필터링 리스트 업데이트
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // 툴바 뒤로 가기 처리
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (item.getItemId() == R.id.add_btn) {
            // 툴바 새 아이템 추가 버튼 클릭 처리
            Intent intent = new Intent(getApplicationContext(), AddFreezerActivity.class);
            startActivityForResult(intent, ADD_FREEZER_REQUEST);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    // 툴바 메뉴 세팅
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }
}
