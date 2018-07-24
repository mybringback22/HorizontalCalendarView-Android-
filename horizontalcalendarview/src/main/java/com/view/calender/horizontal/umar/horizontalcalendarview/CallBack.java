package com.view.calender.horizontal.umar.horizontalcalendarview;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by UManzoor on 2/8/2018.
 */

public class CallBack {
    private String methodName;
    private Object scope;
    /*
        Sending Third Parameter. For Internet Check.
     */
    public CallBack(Object scope, String methodName) {
        this.methodName = methodName;
        this.scope = scope;
    }
    public Object invoke(Object... parameters) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = scope.getClass().getMethod(methodName , getParameterClasses(parameters));
        return method.invoke(scope , parameters);
    }

    private Class[] getParameterClasses(Object... parameters) {
        Class[] classes = new Class[parameters.length];
        for (int i=0; i < classes.length; i++) {
            classes[i] = parameters[i].getClass();
        }
        return classes;
    }


}
