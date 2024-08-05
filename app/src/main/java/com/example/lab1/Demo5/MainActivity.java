package com.example.lab1.Demo5;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab1.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SachAdapter.OnSachDeletedListener {

    private ListView listView;
    private SachAdapter adapter;
    private List<Sach> sachList;
    private SearchView searchView;
    private Button btnAddSach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.lab1.R.layout.activity_main);

        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.searchView);
        btnAddSach = findViewById(R.id.btnAddSach);

        sachList = new ArrayList<>();
        adapter = new SachAdapter(this, sachList, this);
        listView.setAdapter(adapter);

        fetchSachList();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        btnAddSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddSachActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sach sach = sachList.get(position);
                Intent intent = new Intent(MainActivity.this, DetailSachActivity.class);
                intent.putExtra("sachId", sach.getId());
                startActivity(intent);
            }
        });
    }

    private void fetchSachList() {
        ApiService apiService = ApiClient.getApiService();
        Call<List<Sach>> call = apiService.getAllSach();
        call.enqueue(new Callback<List<Sach>>() {
            @Override
            public void onResponse(Call<List<Sach>> call, Response<List<Sach>> response) {
                if (response.isSuccessful()) {
                    sachList.clear();
                    sachList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Lỗi khi lấy danh sách sách", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Sach>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            fetchSachList(); // Reload the list to include newly added book
        }
    }

    @Override
    public void onSachDeleted() {
        fetchSachList(); // Reload the list to reflect changes after deletion
    }
}
