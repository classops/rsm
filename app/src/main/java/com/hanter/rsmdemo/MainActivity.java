package com.hanter.rsmdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hanter.android.rsm.GsonConverterFactory;
import com.hanter.android.rsm.ObjectCallAdapterFactory;
import com.hanter.android.rsm.Rsm;
import com.hanter.android.rsm.adapter.RxJava2CallAdapterFactory;
import com.hanter.android.rsm.adapter.RxJavaCallAdapterFactory;
import java.util.List;
import io.reactivex.disposables.Disposable;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRsm();

        test2();

        testRxJava2();
    }

    private void initRsm() {
        Rsm.init(this);
        Rsm.get().addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        Rsm.get().addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Rsm.get().addCallAdapterFactory(ObjectCallAdapterFactory.create());
        Rsm.get().addConverterFactory(GsonConverterFactory.create());
    }

    private void test() {
        List<TestBean> list = Rsm.get().create(AssetService.class).test();
        StringBuilder stringBuilder = new StringBuilder();
        for (TestBean testBean : list) {
            stringBuilder.append("test name: " + testBean.getName() + ", content: " + testBean.getContent() + "\n");
        }
        ((TextView) findViewById(R.id.tvContent)).setText(stringBuilder.toString());
    }

    private void testRxJava2() {
        io.reactivex.Observable<List<TestBean>> testTask2 = Rsm.get().create(AssetService.class).testTask2();

        testTask2.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.Observer<List<TestBean>>() {

                    @Override
                    public void onNext(List<TestBean> testBeans) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (TestBean testBean : testBeans) {
                            stringBuilder.append("test name: " + testBean.getName() + ", content: " + testBean.getContent() + "\n");
                        }
                        ((TextView) findViewById(R.id.tvContent)).setText(stringBuilder.toString());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                });
    }

    private void test2() {
        rx.Observable<List<TestBean>> testTask2 = Rsm.get().create(AssetService.class).testTask();

        testTask2.subscribeOn(rx.schedulers.Schedulers.io())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new rx.Observer<List<TestBean>>() {

                    @Override
                    public void onNext(List<TestBean> testBeans) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (TestBean testBean : testBeans) {
                            stringBuilder.append("test name: " + testBean.getName() + ", content: " + testBean.getContent() + "\n");
                        }
                        ((TextView) findViewById(R.id.tvContent)).setText(stringBuilder.toString());
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                });
    }

}
