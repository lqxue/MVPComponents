package com.mvp.components.mvp.base;


import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * mvp  Presenter基类  能注入多个M
 *
 * @author lqx Email:herolqx@126.com
 */
public class BaseMultiplePresenter<V extends IView> implements IPresenter<V> {

    private V mProxyView;
    private WeakReference<V> mWeakReference;
    private List<IModel> mInjectModels;


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

            try {
                mInjectModels = new ArrayList<>();
                //获得已经申明的变量，包括私有的   此处的this.getClass() 代表的是子类
                Field[] fields = this.getClass().getDeclaredFields();
                for (Field field : fields) {
                    //获取变量上面的注解类型
                    InjectModel injectModel = field.getAnnotation(InjectModel.class);
                    if (injectModel != null) {
                        Class<? extends IModel> type = (Class<? extends IModel>) field.getType();
                        IModel mInjectModel = type.newInstance();
                        field.setAccessible(true);
                        //给Presenter成员变量赋值
                        field.set(this, mInjectModel);
                        mInjectModels.add(mInjectModel);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    protected V getView() {
        return mProxyView;
    }

    @Override
    public void unBind() {
        mWeakReference.clear();
        mWeakReference = null;
        if (mInjectModels != null) {
            for (IModel iModel : mInjectModels) {
                iModel.unBind();
            }
            mInjectModels = null;
        }
    }
}
