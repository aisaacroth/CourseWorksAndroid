package com.aisaacroth.courseworks.test;

import com.aisaacroth.courseworks.fake.RestAuthServiceFake;

import android.content.Intent;
import android.test.ServiceTestCase;

public class RestAuthServiceTest extends ServiceTestCase<RestAuthServiceFake> {
    
    public RestAuthServiceTest() {
        super(RestAuthServiceFake.class);
    }
    
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    public void testPreconditions() {
    }
    
    public void testLogin() {
        Intent startIntent = new Intent();
        startIntent.setClass(getContext(), RestAuthServiceFake.class);
        startIntent.putExtra("username", "air2112");
        startIntent.putExtra("password", "BA115hp34");
        startService(startIntent);
    }
    

    protected void tearDown() throws Exception {
        super.tearDown();
    }


}
