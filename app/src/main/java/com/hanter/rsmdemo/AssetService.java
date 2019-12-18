package com.hanter.rsmdemo;

import com.hanter.android.rsm.AssetName;

import java.util.List;


public interface AssetService {

    @AssetName("test.json")
    List<TestBean> test();


    @AssetName("test.json")
    rx.Observable<List<TestBean>> testTask();

    @AssetName("test.json")
    io.reactivex.Observable<List<TestBean>> testTask2();

}
