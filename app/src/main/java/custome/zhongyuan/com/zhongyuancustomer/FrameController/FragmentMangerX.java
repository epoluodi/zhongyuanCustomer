package custome.zhongyuan.com.zhongyuancustomer.FrameController;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Administrator on 14-11-30.
 */
public class FragmentMangerX {

    private FragmentManager mfragmentManager;
    private FragmentTransaction mfragmentTransaction;
    private Map<String, Fragment> stringFragmentMap = new Hashtable<String, Fragment>();
    private Context context;
    private int container;

    public FragmentMangerX(FragmentManager fragmentManger, int container) {

        mfragmentManager = fragmentManger;
        mfragmentTransaction = mfragmentManager.beginTransaction();
        this.container=container;
    }


    public void AddFragment(Fragment fragment, String fragmentname) {
        mfragmentTransaction = mfragmentManager.beginTransaction();

        mfragmentTransaction.add(container, fragment, fragmentname);
        stringFragmentMap.put(fragmentname, fragment);

//        if (isShow)
//        {
//            mfragmentTransaction.show(fragment);
//            mfragmentTransaction.commit();
//        }
//        else
        mfragmentTransaction.commit();
//        mfragmentManager.executePendingTransactions();
    }


    public void RemoveFragment(String fragmentname, Boolean isDetach) {
        Fragment fragment = stringFragmentMap.get(fragmentname);
        stringFragmentMap.remove(fragmentname);

        if (isDetach) {
            mfragmentTransaction = mfragmentManager.beginTransaction();

            mfragmentTransaction.remove(fragment);
            mfragmentTransaction.detach(fragment);

            mfragmentTransaction.commit();
        }

    }

    public void RemoveFragment(Fragment fragment) {

        mfragmentTransaction.remove(fragment);
        stringFragmentMap.remove(fragment.getTag());
        mfragmentTransaction.commit();

    }


    public void ShowFragment(Fragment fragment) {
        mfragmentTransaction = mfragmentManager.beginTransaction();
        mfragmentTransaction.show(fragment);

        mfragmentTransaction.commit();

    }

    public void Detch(Fragment fragment) {
        mfragmentTransaction = mfragmentManager.beginTransaction();
        mfragmentTransaction.detach(fragment);

        mfragmentTransaction.commit();
    }

    public void ShowFragment(String fragmentname) {

        Fragment fragment = null;
        fragment = stringFragmentMap.get(fragmentname);
        if (fragment == null)
            return;
        ShowFragment(fragment);
    }

    public Fragment GetFragment(String fragmentname) {
        Fragment fragment = null;
        fragment = stringFragmentMap.get(fragmentname);
        if (fragment == null)
            return null;

        return fragment;
    }


    public void FragmentHide(String fragmentname) {
        mfragmentTransaction = mfragmentManager.beginTransaction();
        Fragment fragment = stringFragmentMap.get(fragmentname);
        mfragmentTransaction.hide(fragment);
        mfragmentTransaction.commit();


    }

    public void FragmentHide(Fragment fragment) {
        mfragmentTransaction = mfragmentManager.beginTransaction();
        mfragmentTransaction.hide(fragment);
        mfragmentTransaction.commit();

    }


    public void ReplaceFragment(Fragment fragment, Boolean isbackstack) {

        mfragmentTransaction = mfragmentManager.beginTransaction();
        mfragmentTransaction.replace(container, fragment);
        if (isbackstack)
            mfragmentTransaction.addToBackStack(null);
        mfragmentTransaction.commit();

    }
}
