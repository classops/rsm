package com.hanter.android.rsm;

import android.support.annotation.Nullable;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 类名：RsmConverter <br/>
 * 描述：类型转换器
 * 创建时间：2018/10/30 23:00
 *
 * @author hanter
 * @version 1.0
 */
public interface RsmConverter<F, T> {

    T convert(F value) throws IOException;

    /** Creates {@link RsmConverter} instances based on a type and target usage. */
    abstract class Factory {
        /**
         * Returns a {@link RsmConverter} for converting an HTTP response body to {@code type}, or null if
         * {@code type} cannot be handled by this factory. This is used to create converters for
         * response types such as {@code SimpleResponse} from a {@code Call<SimpleResponse>}
         * declaration.
         */
        public @Nullable
        RsmConverter<String, ?> responseBodyConverter(Type type,
                                                      Annotation[] annotations, Rsm rsm) {
            return null;
        }
        
        /**
         * Returns a {@link RsmConverter} for converting {@code type} to a {@link String}, or null if
         * {@code type} cannot be handled by this factory. This is used to create converters for types
         * specified by {@link Field @Field}, {@link FieldMap @FieldMap} values,
         * {@link Header @Header}, {@link HeaderMap @HeaderMap}, {@link Path @Path},
         * {@link Query @Query}, and {@link QueryMap @QueryMap} values.
         */
        public @Nullable RsmConverter<?, String> stringConverter(Type type, Annotation[] annotations,
                                             Rsm rsm) {
            return null;
        }

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
