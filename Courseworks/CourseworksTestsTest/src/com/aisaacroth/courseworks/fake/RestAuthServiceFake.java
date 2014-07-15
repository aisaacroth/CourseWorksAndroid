package com.aisaacroth.courseworks.fake;

import android.content.Intent;

import com.aisaacroth.courseworks.services.RestAuthService;

public class RestAuthServiceFake extends RestAuthService {

    @Override
    public void onStart(Intent intent, int startId) {
        onHandleIntent(intent);
        stopSelf(startId);
    }
}
