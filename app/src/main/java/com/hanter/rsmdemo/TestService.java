package com.hanter.rsmdemo;

import android.database.Observable;
import com.hanter.android.rsm.AssetName;
import java.util.List;

public interface TestService {

    @AssetName("test.json")
    List<TestBean> test();

    @AssetName("test.json")
    Observable<List<TestBean>> testTask();

}
