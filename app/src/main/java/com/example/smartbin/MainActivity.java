package com.example.smartbin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements DataAdapter.OnItemListener {
    private RecyclerView recyclerView;
    private ArrayList<Data> data;
    private DataAdapter adapter;
    FloatingActionButton btnRefresh;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerBin);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnRefresh = (FloatingActionButton) findViewById(R.id.btn_refresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                loadJSON();

                Toast.makeText(MainActivity.this, "Refreshing", Toast.LENGTH_LONG).show();
            }
        });
        loadJSON();
    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bit.ly/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<DataList> call = apiInterface.getInfo();
        call.enqueue(new Callback<DataList>() {
            @Override
            public void onResponse(Call<DataList> call, Response<DataList> response) {
                progressBar.setVisibility(View.INVISIBLE);
                DataList dataList = response.body();

                data = new ArrayList<>(Arrays.asList(dataList.getSheet1()));
                adapter = new DataAdapter(data, MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<DataList> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void OnListClick(int position) {
        String Lat=data.get(position).getLatitude();
        String Lan=data.get(position).getLongitude();
        Toast.makeText(this,Lat+" ...."+Lan,Toast.LENGTH_LONG).show();

//       // Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345")); // this will show direction between two specified points
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr="+Lat+","+Lan)); // this will show path of  bin loction from your current location.
        startActivity(intent);

    }
}
