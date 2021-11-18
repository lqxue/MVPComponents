package com.mvp.components.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * mvp Activity基类
 *
 * @author lqx Email:herolqx@126.com
 */
public abstract class BaseMVPMultipleActivity extends AppCompatActivity implements IView {

    /**
     * 保存使用注解的 Presenter 实例，销毁的时候用于解绑
     */
    private List<IPresenter> mInjectPresenters;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            mInjectPresenters = new ArrayList<>();
            //获得已经申明的变量，包括私有的   此处的this.getClass() 代表的是子类
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                //获取变量上面的注解类型
                InjectPresenter injectPresenter = field.getAnnotation(InjectPresenter.class);
                if (injectPresenter != null) {
                    Class<? extends IPresenter> type = (Class<? extends IPresenter>) field.getType();
                    IPresenter mInjectPresenter = type.newInstance();
                    mInjectPresenter.bind(this);
                    field.setAccessible(true);
                    //给Presenter成员变量赋值
                    field.set(this, mInjectPresenter);
                    mInjectPresenters.add(mInjectPresenter);
                }
            }
            super.onCreate(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 替换findViewById
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T $(int viewId) {
        return findViewById(viewId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (IPresenter presenter : mInjectPresenters) {
            presenter.unBind();
        }
        mInjectPresenters.clear();
        mInjectPresenters = null;
    }

    public Context getContext() {
        return this;
    }
}
