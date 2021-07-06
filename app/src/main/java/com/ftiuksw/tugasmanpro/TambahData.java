package com.ftiuksw.tugasmanpro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ftiuksw.tugasmanpro.tampilData.APIRequestData;
import com.ftiuksw.tugasmanpro.tampilData.ResponseModel;
import com.ftiuksw.tugasmanpro.tampilData.RetroServer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahData extends AppCompatActivity {

    private EditText td_nama, td_berat, td_kota, td_saran, td_email;
    private Button btnSimpan;
    private String nama, berat, kota, saran, email;

@Override
    protected void onCreate(Bundle saveInstanceState){
    super.onCreate(saveInstanceState);
    setContentView(R.layout.activity_tambah);

    td_nama = findViewById(R.id.td_nama);
    td_berat = findViewById(R.id.td_berat);
    td_kota = findViewById(R.id.td_kota);
    td_saran = findViewById(R.id.td_saran);
    td_email = findViewById(R.id.td_email);
    btnSimpan = findViewById(R.id.btn_simpan);

    btnSimpan.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        nama = td_nama.getText().toString();
        berat = td_berat.getText().toString();
        kota = td_kota.getText().toString();
        saran = td_saran.getText().toString();
        email = td_email.getText().toString();

        if(nama.trim().equals("")){
            td_nama.setError("Nama tidak boleh kosong");
        }
            else if(berat.trim().equals("")){
                td_berat.setError("Berat tidak boleh kosong");
            }
            else if(kota.trim().equals("")){
                td_kota.setError("Kota tidak boleh kosong");
            }
            else if(saran.trim().equals("")){
                td_saran.setError("Saran tidak boleh kosong");
            }
            else if(email.trim().equals("")){
                td_email.setError("Email tidak boleh kosong");
            }
            else{
            createData();
        }
        }
    });
    }

    private void createData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> simpanData = ardData.ardCreateData(nama, berat, kota, saran, email);

        simpanData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahData.this, "Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(TambahData.this, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
