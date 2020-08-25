package com.mvp.components.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.mvp.components.mvp.base.BaseMVPMultipleActivity;
import com.mvp.components.mvp.base.InjectPresenter;
import com.mvp.components.mvp.contract.MyContract;
import com.mvp.components.mvp.presenter.MyMultiplePresenter;
import com.mvp.components.mvp.presenter.MyPresenter;

/**
 * 实现了MyMultipleActivity
 *
 * @author lqx Email:herolqx@126.com
 */
public class MyMultipleActivity extends BaseMVPMultipleActivity implements MyContract.IMyView {

    @InjectPresenter
    MyPresenter myPresenter;

    @InjectPresenter
    MyMultiplePresenter myMultiplePresenter;

    public static void launch(Context context) {
        context.startActivity(getLauncher(context));
    }

    public static Intent getLauncher(Context context) {
        return new Intent(context, MyMultipleActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPresenter.getUserName();
        myMultiplePresenter.getUserName();

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
