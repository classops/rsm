### Rsm
以类Retrofit的形式，获取Assets目录的数据。

#### 使用方法

添加依赖

1. 添加仓库到根build.gradle
    ``` gradle
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
    ```
2. 添加依赖
    ``` gradle
    dependencies {
        implementation 'com.github.wangmingshuo:rsm:0.1.3'
    }
    ```

##### 初始化
``` java
Rsm.init(this);
Rsm.get().addCallAdapterFactory(RxJava2CallAdapterFactory.create());
Rsm.get().addCallAdapterFactory(ObjectCallAdapterFactory.create());
Rsm.get().addConverterFactory(GsonConverterFactory.create());
```

##### 获取Assets目录数据

和Retrofit方法一样，先定义Service
``` java
public interface AssetService {

    @AssetName("test.json")
    List<TestBean> test();
}
``` 

调用方法
``` java
List<TestBean> list = Rsm.getInstance().create(AssetService.class).test();
```
支持RxJava2返回类型

``` java
public interface AssetService {

    @AssetName("test.json")
    Observable<List<TestBean>> testAsync();
}
```

