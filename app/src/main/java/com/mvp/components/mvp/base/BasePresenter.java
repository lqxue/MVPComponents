package com.mvp.components.mvp.base;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * mvp  Presenter基类  注入单个M
 *
 * @author lqx Email:herolqx@126.com
 */
public class BasePresenter<V extends IView, M extends IModel> implements IPresenter<V> {

    private V mProxyView;
    private M mModel;
    private WeakReference<V> mWeakReference;


    @SuppressWarnings("unchecked")
    @Override
    public void bind(V view) {
        try {
            mWeakReference = new WeakReference<>(view);
            mProxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    //使用动态代理  在调用每个v层接口方法 前 统一通过代理类 进行弱引用判空
                    if (mWeakReference == null || mWeakReference.get() == null) {
                        return null;
                    } else {
                        return method.invoke(mWeakReference.get(), args);
                    }
                }
            });

            //通过获得泛型类的父类，拿到泛型的接口类实例，通过反射来实例化 model
            ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
            if (type != null) {
                Type[] types = type.getActualTypeArguments();
                mModel = (M) ((Class<?>) types[1]).newInstance();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    protected M getModel() {
        return mModel;
    }

    protected V getView() {
        return mProxyView;
    }

    @Override
    public void unBind() {
        if (mWeakReference!=null){
            mWeakReference.clear();
            mWeakReference = null;
        }
        if (mModel!=null){
            mModel.unBind();
            mModel = null;
        }
    }
}
