package org.xlsx.serializer.annotation;

import com.sun.istack.internal.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface XLSXProperty {
    String name();
}
