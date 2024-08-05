package com.example.lab1.Demo5;
// UpdateSachActivity.java
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

public class UpdateSachActivity extends AppCompatActivity {

    private EditText edtTieuDe, edtTacGia, edtNamXuatBan, edtSoTrang, edtTheLoai;
    private Button btnUpdate;
    private String sachId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sach);

        edtTieuDe = findViewById(R.id.edtTieuDe);
        edtTacGia = findViewById(R.id.edtTacGia);
        edtNamXuatBan = findViewById(R.id.edtNamXuatBan);
        edtSoTrang = findViewById(R.id.edtSoTrang);
        edtTheLoai = findViewById(R.id.edtTheLoai);
        btnUpdate = findViewById(R.id.btnUpdate);

        sachId = getIntent().getStringExtra("sachId");

        ApiService apiService = ApiClient.getApiService();
        Call<Sach> call = apiService.getSachById(sachId);
        call.enqueue(new Callback<Sach>() {
            @Override
            public void onResponse(Call<Sach> call, Response<Sach> response) {
                if (response.isSuccessful()) {
                    Sach sach = response.body();
                    edtTieuDe.setText(sach.getTieuDe());
                    edtTacGia.setText(sach.getTacGia());
                    edtNamXuatBan.setText(String.valueOf(sach.getNamXuatBan()));
                    edtSoTrang.setText(String.valueOf(sach.getSoTrang()));
                    edtTheLoai.setText(sach.getTheLoai());
                } else {
                    Toast.makeText(UpdateSachActivity.this, "Lỗi khi tải thông tin sách", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Sach> call, Throwable t) {
                Toast.makeText(UpdateSachActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tieuDe = edtTieuDe.getText().toString();
                String tacGia = edtTacGia.getText().toString();
                String namXuatBanStr = edtNamXuatBan.getText().toString();
                String soTrangStr = edtSoTrang.getText().toString();
                String theLoai = edtTheLoai.getText().toString();

                if (tieuDe.isEmpty() || tacGia.isEmpty() || namXuatBanStr.isEmpty() || soTrangStr.isEmpty()) {
                    Toast.makeText(UpdateSachActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                int namXuatBan, soTrang;
                try {
                    namXuatBan = Integer.parseInt(namXuatBanStr);
                    soTrang = Integer.parseInt(soTrangStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(UpdateSachActivity.this, "Năm xuất bản và số trang phải là số", Toast.LENGTH_SHORT).show();
                    return;
                }

                Sach sach = new Sach();
                sach.setId(sachId);
                sach.setTieuDe(tieuDe);
                sach.setTacGia(tacGia);
                sach.setNamXuatBan(namXuatBan);
                sach.setSoTrang(soTrang);
                sach.setTheLoai(theLoai);

                Call<Sach> updateCall = apiService.updateSach(sachId, sach);
                updateCall.enqueue(new Callback<Sach>() {
                    @Override
                    public void onResponse(Call<Sach> call, Response<Sach> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(UpdateSachActivity.this, "Cập nhật sách thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(UpdateSachActivity.this, "Lỗi khi cập nhật sách", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Sach> call, Throwable t) {
                        Toast.makeText(UpdateSachActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
