package com.example.machinetestproject.di;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.machinetestproject.database.rooms.AppDatabase;
import com.example.machinetestproject.network.APIServices;
import com.example.machinetestproject.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    /*
    * Creating instance of apiServices
    * */
    @Provides
    @Singleton
    public static APIServices provideApiServices(Retrofit retrofit){
        return retrofit.create(APIServices.class);
    }

    /*
    * Creating instance of Retrofit
    * */
    @Provides
    @Singleton
    public static Retrofit getRetrofitInstance(){
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String s) {
                Log.d("Retrofit",s);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor)
//                .connectTimeout(60, TimeUnit.SECONDS)
//                .readTimeout(60, TimeUnit.SECONDS)
//                .writeTimeout(10, TimeUnit.SECONDS)
        ;
        OkHttpClient okHttpClient=builder.build();

        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    /*
    * This function creates instance of Room db and is provided in application scope.
    * */
    @Provides
    @Singleton
    public static synchronized AppDatabase getDB(@ApplicationContext Context context){
        AppDatabase instance= Room.databaseBuilder(context,AppDatabase.class, Constants.DB_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        return instance;
    }


}
