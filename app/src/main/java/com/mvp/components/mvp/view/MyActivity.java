package com.mvp.components.mvp.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.mvp.components.mvp.base.BaseMVPActivity;
import com.mvp.components.mvp.contract.MyContract;
import com.mvp.components.mvp.presenter.MyPresenter;

/**
 * 实现了BaseMVPActivity
 *
 * @author lqx Email:herolqx@126.com
 */
public class MyActivity extends BaseMVPActivity<MyPresenter> implements MyContract.IMyView {

    public static void launch(Context context) {
        context.startActivity(getLauncher(context));
    }

    public static Intent getLauncher(Context context) {
        return new Intent(context, MyActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().getUserName();
    }

    @Override
    protected MyPresenter setPresenter() {
        return new MyPresenter();
    }

    @Override
    public void showInfo(final String info) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(),info,Toast.LENGTH_LONG).show();
            }
        });
    }
}
