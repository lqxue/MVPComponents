package com.mvp.components.mvp.presenter;


import com.mvp.components.mvp.base.BasePresenter;
import com.mvp.components.mvp.contract.MyContract;
import com.mvp.components.mvp.model.MyModel;

/**
 * p
 *
 * @author lqx Email:herolqx@126.com
 */
public class MyPresenter extends BasePresenter<MyContract.IMyView, MyModel> implements MyContract.IMyPresenter {

    @Override
    public String getUserName() {
        //测试是否会泄漏
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    getView().showInfo("我是请求回来的数据");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return "";
    }

}
