package com.mvp.components.mvp.contract;


import com.mvp.components.mvp.base.IPresenter;
import com.mvp.components.mvp.base.IView;

/**
 * 契约接口
 *
 * @author lqx Email:herolqx@126.com
 */
public interface MyContract {

    interface IMyView extends IView {

        void showInfo(String info);
    }

    interface IMyPresenter extends IPresenter<IMyView> {

        String getUserName();
    }
}
