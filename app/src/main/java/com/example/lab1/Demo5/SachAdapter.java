package com.example.lab1.Demo5;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.lab1.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SachAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<Sach> sachList;
    private List<Sach> sachListFull;
    private LayoutInflater inflater;
    private OnSachDeletedListener onSachDeletedListener;

    public SachAdapter(Context context, List<Sach> sachList, OnSachDeletedListener listener) {
        this.context = context;
        this.sachList = sachList;
        this.sachListFull = new ArrayList<>(sachList);
        this.inflater = LayoutInflater.from(context);
        this.onSachDeletedListener = listener;
    }

    @Override
    public int getCount() {
        return sachList.size();
    }

    @Override
    public Object getItem(int position) {
        return sachList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_sach, parent, false);
        }

        TextView tvTieuDe = convertView.findViewById(R.id.tvTieuDe);
        TextView tvTacGia = convertView.findViewById(R.id.tvTacGia);
        TextView tvNamXuatBan = convertView.findViewById(R.id.tvNamXuatBan);
        TextView tvSoTrang = convertView.findViewById(R.id.tvSoTrang);
        TextView tvTheLoai = convertView.findViewById(R.id.tvTheLoai);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        final Sach sach = sachList.get(position);

        tvTieuDe.setText("Tiêu đề: " + sach.getTieuDe());
        tvTacGia.setText("Tác giả: " + sach.getTacGia());
        tvNamXuatBan.setText("Năm xuất bản: " + sach.getNamXuatBan());
        tvSoTrang.setText("Số trang: " + sach.getSoTrang());
        tvTheLoai.setText("Thể loại: " + sach.getTheLoai());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Xóa sách")
                        .setMessage("Bạn có chắc chắn muốn xóa sách này?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ApiService apiService = ApiClient.getApiService();
                                Call<Void> call = apiService.deleteSach(sach.getId());
                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            sachList.remove(position);
                                            sachListFull.remove(sach);
                                            notifyDataSetChanged();
                                            if (onSachDeletedListener != null) {
                                                onSachDeletedListener.onSachDeleted();
                                            }
                                            Toast.makeText(context, "Xóa sách thành công", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "Lỗi khi xóa sách", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(context, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            }
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Sach> filteredResults = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredResults.addAll(sachListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Sach sach : sachListFull) {
                        if (sach.getTieuDe().toLowerCase().contains(filterPattern) ||
                                sach.getTacGia().toLowerCase().contains(filterPattern)) {
                            filteredResults.add(sach);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredResults;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                sachList.clear();
                sachList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public interface OnSachDeletedListener {
        void onSachDeleted();
    }
}
