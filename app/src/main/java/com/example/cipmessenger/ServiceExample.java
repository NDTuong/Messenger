package com.example.cipmessenger;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class ServiceExample extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        Log.e("DuyTuong","Service được khởi tạo");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        Log.e("DuyTuong","Service đã hủy");
    }

}
