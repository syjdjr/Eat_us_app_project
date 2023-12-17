package com.example.eat_us_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FreezerAdapter extends RecyclerView.Adapter<FreezerAdapter.ViewHolder>{
    private List<FreezerItem> freezerItemList; // 냉동고 아이템을 저장할 리스트
    private List<FreezerItem> freezerItemListFull; // 전체 아이템 리스트

    public FreezerAdapter(List<FreezerItem> freezerItemList) {
        this.freezerItemList = freezerItemList; // 아이템 리스트 초기화
        this.freezerItemListFull = new ArrayList<>(freezerItemList);
    }

    // 전체 아이템 리스트 업데이트 메서드
    public void updateFreezerItemListFull(List<FreezerItem> itemList) {
        freezerItemListFull.clear(); // 기존 리스트 비움
        freezerItemListFull.addAll(itemList); // 새로운 리스트 업데이트
    }

    // 텍스트 필터링 수행 메서드
    public void filter(String text){
        freezerItemList.clear();
        if (text.isEmpty()){
            freezerItemList.addAll(freezerItemListFull);
        } else {
            text = text.toLowerCase();
            for (FreezerItem item : freezerItemListFull) {
                // 이름에 검색 텍스트 포함된 경우 추가
                if (item.getName().toLowerCase().contains(text)) {
                    freezerItemList.add(item);
                }
            }
        }
        notifyDataSetChanged(); // 데이터 변경 알림
    }

    // ViewHolder 생성
    @Override
    public FreezerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new FreezerAdapter.ViewHolder(view); // ViewHolder 인스턴스 반환
    }

    // ViewHolder에 데이터 바인딩
    @Override
    public void onBindViewHolder(FreezerAdapter.ViewHolder holder, int position) {
        FreezerItem item = freezerItemList.get(position);
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
        return freezerItemList != null ? freezerItemList.size() : 0;
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
