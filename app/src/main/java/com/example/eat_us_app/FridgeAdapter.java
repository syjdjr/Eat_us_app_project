package com.example.eat_us_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FridgeAdapter extends RecyclerView.Adapter<FridgeAdapter.ViewHolder>{
    private List<FridgeItem> fridgeItemList; // 냉장고 아이템을 저장할 리스트
    private List<FridgeItem> fridgeItemListFull; // 전체 아이템 리스트

    public FridgeAdapter(List<FridgeItem> fridgeItemList) {
        this.fridgeItemList = fridgeItemList; // 아이템 리스트 초기화
        this.fridgeItemListFull = new ArrayList<>(fridgeItemList);
    }

    // 전체 아이템 리스트 업데이트 메서드
    public void updateFridgeItemListFull(List<FridgeItem> itemList) {
        fridgeItemListFull.clear(); // 기존 리스트 비움
        fridgeItemListFull.addAll(itemList); // 새로운 리스트 업데이트
    }

    // 텍스트 필터링 수행 메서드
    public void filter(String text){
        fridgeItemList.clear();
        if (text.isEmpty()){
        fridgeItemList.addAll(fridgeItemListFull);
        } else {
        text = text.toLowerCase();
        for (FridgeItem item : fridgeItemListFull) {
            // 이름에 검색 텍스트 포함된 경우 추가
            if (item.getName().toLowerCase().contains(text)) {
                fridgeItemList.add(item);
            }
        }
        }
        notifyDataSetChanged(); // 데이터 변경 알림
    }

    // ViewHolder 생성
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view); // ViewHolder 인스턴스 반환
    }

    // ViewHolder에 데이터 바인딩
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FridgeItem item = fridgeItemList.get(position);
        // 가져온 데이터 설정
        holder.nameTextView.setText(item.getName());
        holder.numTextView.setText(String.valueOf(item.getQuantity()));
        holder.vdateTextView.setText(item.getVdate());
        holder.memoTextView.setText(item.getMemo());
        holder.categoryImage.setImageResource(item.getCategoryimage());
    }

    // 아이템 개수 반환
    @Override
    public int getItemCount() {
        return fridgeItemList != null ? fridgeItemList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, numTextView, vdateTextView, memoTextView;
        ImageView categoryImage;

        public ViewHolder(View itemView) {
            super(itemView);
            // 뷰 컴포넌트 연결
            nameTextView = itemView.findViewById(R.id.nameTextView);
            numTextView = itemView.findViewById(R.id.numTextView);
            vdateTextView = itemView.findViewById(R.id.vdateTextView);
            memoTextView = itemView.findViewById(R.id.memoTextView);
            categoryImage = itemView.findViewById(R.id.categoryImage);
        }
    }
}
