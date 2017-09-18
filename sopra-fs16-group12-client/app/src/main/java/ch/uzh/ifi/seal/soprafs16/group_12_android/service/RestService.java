package ch.uzh.ifi.seal.soprafs16.group_12_android.service;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RestService {

    private final static String baseUrl = "https://sopra-fs16-group12.herokuapp.com/";

    public RestApiInterface restApiInterface;
    private static RestService instance;

    private RestService(Context context) {

        boolean isDebuggable = (0 != (context.getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE));
        okhttp3.OkHttpClient.Builder httpClient = new okhttp3.OkHttpClient.Builder();

        if (isDebuggable) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptor);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addConverterFactory(JacksonConverterFactory.create()).build();

        restApiInterface = retrofit.create(RestApiInterface.class);
    }

    public static RestApiInterface getInstance(Context context) {

        if (instance == null) {
            instance = new RestService(context);
        }

        return instance.restApiInterface;
    }

}
