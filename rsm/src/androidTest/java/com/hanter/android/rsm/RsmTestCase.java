package com.hanter.android.rsm;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RsmTestCase {

    @Before
    public void onBefore() {
        Context appContext = InstrumentationRegistry.getContext();
        Rsm.init(appContext);
        Rsm.getInstance().addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        Rsm.getInstance().addCallAdapterFactory(ObjectCallAdapterFactory.create());
        Rsm.getInstance().addConverterFactory(GsonConverterFactory.create());
    }

    @Test
    public void testObject() {
        List<TestBean> list = Rsm.getInstance().create(TestService.class).test();
        assertNotNull(list);

        for (TestBean testBean : list) {
            System.out.println("testBean name: " + testBean.getName() + ", content: "
                    + testBean.getContent());
        }
    }

    @Test
    public void testObjectTask() {
        Observable<List<TestBean>> listTask = Rsm.getInstance().create(TestService.class)
                .testTask();
        assertNotNull(listTask);

        listTask.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TestBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<TestBean> list) {
                        for (TestBean testBean : list) {
                            System.out.println("testBean name: " + testBean.getName()
                                    + ", content: "
                                    + testBean.getContent());
                        }
                    }
                });

    }

}
