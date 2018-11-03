package com.hanter.android.rsm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类名：AssetName <br/>
 * 描述：注解Asset的文件名
 * 创建时间：2018/10/30 23:13
 *
 * @author hanter
 * @version 1.0
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AssetName {

    String value();

}
