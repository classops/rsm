package com.hanter.rsmdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hanter.android.rsm.GsonConverterFactory;
import com.hanter.android.rsm.ObjectCallAdapterFactory;
import com.hanter.android.rsm.Rsm;
import com.hanter.android.rsm.RxJavaCallAdapterFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRsm();

        test();
    }

    private void initRsm() {
        Rsm.init(this);
        Rsm.getInstance().addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        Rsm.getInstance().addCallAdapterFactory(ObjectCallAdapterFactory.create());
        Rsm.getInstance().addConverterFactory(GsonConverterFactory.create());
    }

    private void test() {
        List<TestBean> list = Rsm.getInstance().create(AssetService.class).test();
        for (TestBean testBean : list) {
            Log.e("Test", "test name: " + testBean.getName() + ", content: " + testBean.getContent());
        }
    }

}
