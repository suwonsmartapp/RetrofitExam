package com.example.retrofitexam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private IpInfoService mService;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.editText);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IpInfoService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(IpInfoService.class);
    }

    public void getInfo(View view) {
        mService.getIpInfo(mEditText.getText().toString())
                .enqueue(new Callback<IpInfo>() {
                    @Override
                    public void onResponse(Call<IpInfo> call,
                                           Response<IpInfo> response) {
                        IpInfo ipInfo = response.body();
                        Toast.makeText(MainActivity.this,
                                ipInfo.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<IpInfo> call, Throwable t) {
                        Toast.makeText(MainActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
