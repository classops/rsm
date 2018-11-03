package com.hanter.android.rsm;

import java.io.IOException;

/**
 * 类名：StringResponseConverter <br/>
 * 描述：字符串类型转换
 * 创建时间：2018/10/30 23:11
 *
 * @author hanter
 * @version 1.0
 */
public class StringResponseConverter implements RsmConverter<String, String> {

    @Override
    public String convert(String value) throws IOException {
        return value;
    }
}
