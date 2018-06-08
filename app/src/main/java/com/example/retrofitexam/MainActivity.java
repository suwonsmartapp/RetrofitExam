package com.example.retrofitexam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

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

    public void getWeather(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService service = retrofit.create(WeatherService.class);

        service.getWeather(mEditText.getText().toString(), WeatherService.APP_ID)
                .enqueue(new Callback<WeatherResult>() {
                    @Override
                    public void onResponse(Call<WeatherResult> call,
                                           Response<WeatherResult> response) {
                        WeatherResult result = response.body();
                        if (result != null) {
                            Toast.makeText(MainActivity.this,
                                    result.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("날씨", "onResponse: " + result.toString());

                        } else {
                            Toast.makeText(MainActivity.this, "그런 도시는 없습니다", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResult> call, Throwable t) {
                        Log.d("날씨", "onFailure: " + t.getMessage());
                    }
                });
    }

    public void getForecast(View view) {
        ListView listView = findViewById(R.id.listView);

        final ForecastAdapter adapter = new ForecastAdapter();
        listView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService service = retrofit.create(WeatherService.class);

        service.getForecast(mEditText.getText().toString(),
                WeatherService.APP_ID)
                .enqueue(new Callback<ForecastResult>() {
                    @Override
                    public void onResponse(Call<ForecastResult> call, Response<ForecastResult> response) {
                        ForecastResult result = response.body();

                        adapter.updateData(result.getList());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ForecastResult> call, Throwable t) {
                        Log.d("날씨", "onFailure: " + t.getMessage());
                    }
                });
    }

    static class ForecastAdapter extends BaseAdapter {
        private List<Forecast> mData = new ArrayList<>();

        public void updateData(List<Forecast> data) {
            mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_forecast, parent, false);
                holder.temp = convertView.findViewById(R.id.temp);
                holder.humidity = convertView.findViewById(R.id.humidity);
                holder.icon = convertView.findViewById(R.id.iconImage);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Forecast forecast = mData.get(position);
            holder.temp.setText(forecast.getMain().getTemp());
            holder.humidity.setText(forecast.getMain().getHumidity());

            String iconUrl = "http://openweathermap.org/img/w/" + forecast.getWeather().get(0).getIcon() + ".png";

            Glide.with(parent.getContext())
                    .load(iconUrl)
                    .into(holder.icon);

            return convertView;
        }
    }

    static class ViewHolder {
        TextView temp;
        TextView humidity;
        TextView description;
        TextView time;
        ImageView icon;
    }
}
