package com.mvp.components.mvp.presenter;

import com.mvp.components.mvp.base.BaseMultiplePresenter;
import com.mvp.components.mvp.base.InjectModel;
import com.mvp.components.mvp.contract.MyContract;
import com.mvp.components.mvp.model.MyModel;

public class MyMultiplePresenter extends BaseMultiplePresenter<MyContract.IMyView> implements MyContract.IMyPresenter {

    @InjectModel
    MyModel myModel;

    @InjectModel
    MyModel myModel1;

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
