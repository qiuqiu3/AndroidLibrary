package com.android.commonlibrary.mvp_frame;

import com.android.commonlibrary.activity.SuperActivity;
import com.android.commonlibrary.interfacer.pre_interfacer.IPreActivity;
import com.android.commonlibrary.interfacer.pre_interfacer.PrePresenter;

/**
 * Activity基类(含mvp架构功能)
 **/
public abstract class AppActivity extends SuperActivity implements IPreActivity {

    protected PrePresenter mPresenter;

    @Override
    public void loadMVP() {
        super.loadMVP();

        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.attachView();
        }
    }

    /***
     * 若要使用mvp架构,需要在子类中重写此方法并返回具体的 PrePresenter
     * @return
     */
    @Override
    public PrePresenter getPresenter() {
        return null;
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

}
