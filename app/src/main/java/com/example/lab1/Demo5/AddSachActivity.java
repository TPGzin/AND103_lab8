package com.example.lab1.Demo5;
// AddSachActivity.java
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab1.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSachActivity extends AppCompatActivity {

    private EditText edtMaSach, edtTieuDe, edtTacGia, edtNamXuatBan, edtSoTrang, edtTheLoai;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sach);

        edtMaSach = findViewById(R.id.edtMaSach);
        edtTieuDe = findViewById(R.id.edtTieuDe);
        edtTacGia = findViewById(R.id.edtTacGia);
        edtNamXuatBan = findViewById(R.id.edtNamXuatBan);
        edtSoTrang = findViewById(R.id.edtSoTrang);
        edtTheLoai = findViewById(R.id.edtTheLoai);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maSach = edtMaSach.getText().toString();
                String tieuDe = edtTieuDe.getText().toString();
                String tacGia = edtTacGia.getText().toString();
                String namXuatBanStr = edtNamXuatBan.getText().toString();
                String soTrangStr = edtSoTrang.getText().toString();
                String theLoai = edtTheLoai.getText().toString();

                if (maSach.isEmpty() || tieuDe.isEmpty() || tacGia.isEmpty() || namXuatBanStr.isEmpty() || soTrangStr.isEmpty()) {
                    Toast.makeText(AddSachActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                int namXuatBan, soTrang;
                try {
                    namXuatBan = Integer.parseInt(namXuatBanStr);
                    soTrang = Integer.parseInt(soTrangStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(AddSachActivity.this, "Năm xuất bản và số trang phải là số", Toast.LENGTH_SHORT).show();
                    return;
                }

                Sach sach = new Sach();
                sach.setMaSach(maSach);
                sach.setTieuDe(tieuDe);
                sach.setTacGia(tacGia);
                sach.setNamXuatBan(namXuatBan);
                sach.setSoTrang(soTrang);
                sach.setTheLoai(theLoai);

                ApiService apiService = ApiClient.getApiService();
                Call<Sach> call = apiService.createSach(sach);
                call.enqueue(new Callback<Sach>() {
                    @Override
                    public void onResponse(Call<Sach> call, Response<Sach> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(AddSachActivity.this, "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddSachActivity.this, "Lỗi khi thêm sách", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Sach> call, Throwable t) {
                        Toast.makeText(AddSachActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
