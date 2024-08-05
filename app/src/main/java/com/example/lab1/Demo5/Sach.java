package com.example.lab1.Demo5;

// Sach.java
import com.google.gson.annotations.SerializedName;

public class Sach {

    @SerializedName("_id")
    private String id;

    @SerializedName("ma_sach_ph1234")
    private String maSach;

    @SerializedName("tieu_de_ph1234")
    private String tieuDe;

    @SerializedName("tac_gia_ph1234")
    private String tacGia;

    @SerializedName("nam_xuat_ban_ph1234")
    private int namXuatBan;

    @SerializedName("so_trang_ph1234")
    private int soTrang;

    @SerializedName("the_loai_ph1234")
    private String theLoai;

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public int getNamXuatBan() {
        return namXuatBan;
    }

    public void setNamXuatBan(int namXuatBan) {
        this.namXuatBan = namXuatBan;
    }

    public int getSoTrang() {
        return soTrang;
    }

    public void setSoTrang(int soTrang) {
        this.soTrang = soTrang;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }
}
