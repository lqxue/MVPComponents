package com.mvp.components.mvp.base;

/**
 * P层父接口
 *
 * @author lqx Email:herolqx@126.com
 */
public interface IPresenter<V extends IView> {

    /**
     * 绑定v
     * @param view
     */
    void bind(V view);

    /**
     * 解绑
     */
    void unBind();
}
