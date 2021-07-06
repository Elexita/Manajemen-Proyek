package com.ftiuksw.tugasmanpro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ftiuksw.tugasmanpro.tampilData.APIRequestData;
import com.ftiuksw.tugasmanpro.tampilData.DataModel;
import com.ftiuksw.tugasmanpro.tampilData.ResponseModel;
import com.ftiuksw.tugasmanpro.tampilData.RetroServer;

import java.text.BreakIterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {
    private Context ctx;
    private List<DataModel> listPengguna;
    private int idSurvey;

    public AdapterData(Context ctx, List<DataModel> listPengguna) {
        this.ctx = ctx;
        this.listPengguna = listPengguna;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_survey, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModel dm = listPengguna.get(position);
        holder.id.setText(String.valueOf(dm.getId()));
        holder.nama.setText(dm.getNama());
        holder.berat.setText(String.valueOf(dm.getBerat()));
        holder.kota.setText(dm.getKota());
        holder.saran.setText(dm.getSaran());
        holder.email.setText(dm.getEmail());
    }

    @Override
    public int getItemCount() {
        return listPengguna.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        public BreakIterator email;
        TextView id, nama, berat, kota, saran, email;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            nama = itemView.findViewById(R.id.tp_nama);
            berat = itemView.findViewById(R.id.tp_berat);
            kota = itemView.findViewById(R.id.tp_kota);
            saran = itemView.findViewById(R.id.tp_saran);
            email = itemView.findViewById((R.id.tp_email));

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
                    dialogPesan.setMessage("Apakah yakin ingin menghapus data ini ?");
                    dialogPesan.setCancelable(true);

                    idSurvey = Integer.parseInt(id.getText().toString());

                    dialogPesan.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        deleteData();
                        dialog.dismiss();
                            ((activity_history_survey) ctx).retrieveData();
                        }
                    });

                    dialogPesan.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    dialogPesan.show();

                    return false;
                }
            });

            }

            private void deleteData(){
                APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
                Call<ResponseModel> hapusData = ardData.ardDeleteData(idSurvey);

                hapusData.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        String pesan = response.body().getPesan();

                        Toast.makeText(ctx, " "+pesan, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Toast.makeText(ctx, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
