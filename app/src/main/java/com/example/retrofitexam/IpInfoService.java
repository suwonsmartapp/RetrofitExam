package com.example.retrofitexam;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IpInfoService {
    String BASE_URL = "http://freegeoip.net";

    @GET("json/{domain}")
    Call<IpInfo> getIpInfo(@Path("domain") String domain);
}
