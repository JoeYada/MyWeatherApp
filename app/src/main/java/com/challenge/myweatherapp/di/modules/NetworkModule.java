package com.challenge.myweatherapp.di.modules;

import com.challenge.myweatherapp.service.WeatherService;
import com.challenge.myweatherapp.util.Constants;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    Interceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    Interceptor provideAPIKeyInterceptor() {
        return chain -> {
            HttpUrl originalUrl = chain.request().url();
            HttpUrl modifiedUrl = originalUrl.newBuilder()
                    .addQueryParameter("appid", Constants.API_KEY)
                    .addQueryParameter("units", Constants.UNITS)
                    .build();
            Request request = chain.request().newBuilder()
                    .url(modifiedUrl)
                    .build();
            return chain.proceed(request);
        };
    }


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient( Interceptor loggingInterceptor, Interceptor apiInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(apiInterceptor)
                .connectTimeout(Constants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    WeatherService provideRetrofitService(Retrofit retrofit) {
        return retrofit.create(WeatherService.class);
    }

}
