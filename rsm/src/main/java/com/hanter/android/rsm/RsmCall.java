package com.hanter.android.rsm;

import java.io.IOException;

/**
 * 任务的抽象
 * @param <T> responseType 响应的数据类型
 */
public interface RsmCall<T> {

    T execute() throws IOException;

}
