package com.example.lab1.Demo5;
// DetailSachActivity.java
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab1.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSachActivity extends AppCompatActivity {

    private TextView tvTieuDe, tvTacGia, tvNamXuatBan, tvSoTrang, tvTheLoai;
    private String sachId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.lab1.R.layout.activity_detail_sach);

        tvTieuDe = findViewById(R.id.tvTieuDe);
        tvTacGia = findViewById(R.id.tvTacGia);
        tvNamXuatBan = findViewById(R.id.tvNamXuatBan);
        tvSoTrang = findViewById(R.id.tvSoTrang);
        tvTheLoai = findViewById(R.id.tvTheLoai);

        sachId = getIntent().getStringExtra("sachId");

        ApiService apiService = ApiClient.getApiService();
        Call<Sach> call = apiService.getSachById(sachId);
        call.enqueue(new Callback<Sach>() {
            @Override
            public void onResponse(Call<Sach> call, Response<Sach> response) {
                if (response.isSuccessful()) {
                    Sach sach = response.body();
                    tvTieuDe.setText(sach.getTieuDe());
                    tvTacGia.setText(sach.getTacGia());
                    tvNamXuatBan.setText(String.valueOf(sach.getNamXuatBan()));
                    tvSoTrang.setText(String.valueOf(sach.getSoTrang()));
                    tvTheLoai.setText(sach.getTheLoai());
                } else {
                    Toast.makeText(DetailSachActivity.this, "Lỗi khi tải thông tin sách", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Sach> call, Throwable t) {
                Toast.makeText(DetailSachActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
