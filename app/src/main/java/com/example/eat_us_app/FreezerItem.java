package com.example.eat_us_app;

import android.os.Parcel;
import android.os.Parcelable;

public class FreezerItem implements Parcelable {
    private int categoryimage; // 카테고리 이미지 리소스 ID
    private String name; // 아이템 이름
    private int quantity; // 아이템 수량
    private String vdate; // 유통 기한
    private String memo; // 메모

    // 냉동고 아이템 인스턴스 생성자
    public FreezerItem(int categoryimage, String name, int quantity, String vdate, String memo) {
        this.categoryimage = categoryimage;
        this.name = name;
        this.quantity = quantity;
        this.vdate = vdate;
        this.memo = memo;
    }

    // Parcel에서 데이터를 읽어오는 생성자
    public FreezerItem(Parcel in) {
        categoryimage = in.readInt();
        name = in.readString();
        quantity = in.readInt();
        vdate = in.readString();
        memo = in.readString();
    }

    // Freezer Item 인스턴스를 Parcel에서 생성
    public static final Creator<FreezerItem> CREATOR = new Creator<FreezerItem>() {
        @Override
        public FreezerItem createFromParcel(Parcel in) {
            return new FreezerItem(in);
        }

        @Override
        public FreezerItem[] newArray(int size) {
            return new FreezerItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    // 객체의 데이터를 Parcel에 씀
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(categoryimage);
        parcel.writeString(name);
        parcel.writeInt(quantity);
        parcel.writeString(vdate);
        parcel.writeString(memo);
    }

    // 각각의 속성 값 가져옴
    public int getCategoryimage() { return categoryimage; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public String getVdate() { return vdate; }
    public String getMemo() { return memo; }
}
