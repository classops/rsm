package com.hanter.android.rsm;

import android.support.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 类型的转换接口
 * @param <S> 实际响应数据类型
 * @param <D> 接口需要的类型（如：Observable<List<String>
 */
public interface CallAdapter<S, D> {

    Type responseType();

    D adapter(RsmCall<S> call);

    /**
     * Creates {@link CallAdapter} instances based on the return type of {@linkplain
     * Rsm#create(Class) the service interface} methods.
     */
    abstract class Factory {
        /**
         * Returns a call adapter for interface methods that return {@code returnType}, or null if it
         * cannot be handled by this factory.
         */
        public abstract @Nullable
        CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Rsm rsm);

        /**
         * Extract the upper bound of the generic parameter at {@code index} from {@code type}. For
         * example, index 1 of {@code Map<String, ? extends Runnable>} returns {@code Runnable}.
         */
        protected static Type getParameterUpperBound(int index, ParameterizedType type) {
            return Utils.getParameterUpperBound(index, type);
        }

        /**
         * Extract the raw class type from {@code type}. For example, the type representing
         * {@code List<? extends Runnable>} returns {@code List.class}.
         */
        protected static Class<?> getRawType(Type type) {
            return Utils.getRawType(type);
        }
    }


}
