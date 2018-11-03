package com.hanter.android.rsm;

import java.util.List;

import rx.Observable;

public interface TestService {

    @AssetName("test.json")
    List<TestBean> test();

    @AssetName("test.json")
    Observable<List<TestBean>> testTask();


}
