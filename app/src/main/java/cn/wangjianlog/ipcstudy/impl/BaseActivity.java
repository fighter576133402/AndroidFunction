package cn.wangjianlog.ipcstudy.impl;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * <pre>
 * 业务名:
 * 功能说明:
 * 编写日期: 2019/4/9
 * 作者:	 WangJian
 * 备注:
 *
 * 历史记录
 * 1、修改日期：
 *    修改人：WangJian
 *    修改内容：
 * </pre>
 */

public class BaseActivity extends AppCompatActivity {

    protected Fragment currentFragment;

    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(containerViewId, fragment, fragment.getClass().getSimpleName());
        ft.commit();
    }

    /**
     * fragment替换（保留之前的状态）
     *
     * @param to
     * @param containerViewId
     */
    public void switchFragment(int containerViewId, Fragment to) {
        if (currentFragment != to) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            if (!to.isAdded()) {
                // 隐藏当前的fragment，add下一个fragment到Activity中
                transaction.hide(currentFragment).add(containerViewId, to, to.getClass().getSimpleName()).commitAllowingStateLoss();
            } else {
                transaction.hide(currentFragment).show(to).commitAllowingStateLoss();
                // to.onResume();该命令可注释，若希望fragment切换的过程中，被显示的fragment执行onResume方法，则解注；
            }
            currentFragment = to;
        }
    }

}
