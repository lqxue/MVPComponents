package com.mvp.components.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * mvp Activity基类
 *
 * @author lqx Email:herolqx@126.com
 */
public abstract class BaseMVPActivity<P extends IPresenter> extends AppCompatActivity implements IView {

    private P mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            mPresenter = setPresenter();
            if (mPresenter == null) {
                throw new RuntimeException("mPresenter Can not be empty");
            }
            mPresenter.bind(this);
            super.onCreate(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract P setPresenter();

    protected P getPresenter() {
        return mPresenter;
    }

    /**
     * 替换findViewById
     *
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
        if (mPresenter != null) {
            mPresenter.unBind();
            mPresenter = null;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}
