package custome.zhongyuan.com.zhongyuancustomer;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import custome.zhongyuan.com.zhongyuancustomer.Fragment.AboutFragrment;
import custome.zhongyuan.com.zhongyuancustomer.Fragment.CustomerFragrment;
import custome.zhongyuan.com.zhongyuancustomer.Fragment.PushFragrment;
import custome.zhongyuan.com.zhongyuancustomer.FrameController.FragmentMangerX;
import custome.zhongyuan.com.zhongyuancustomer.FrameController.FragmentName;

public class MainActivity extends AppCompatActivity {


    private Fragment fragmentnow;
    private PushFragrment pushFragrment;
    private CustomerFragrment customerFragrment;
    private AboutFragrment aboutFragrment;

    public static FragmentMangerX fragmentMangerX; //fragment框架


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentMangerX.FragmentHide(fragmentnow);
                    fragmentMangerX.ShowFragment(pushFragrment);
                    fragmentnow = pushFragrment;
                    return true;
                case R.id.navigation_customer:
                    fragmentMangerX.FragmentHide(fragmentnow);
                    fragmentMangerX.ShowFragment(customerFragrment);
                    fragmentnow = customerFragrment;
                    return true;
                case R.id.navigation_about:
                    fragmentMangerX.FragmentHide(fragmentnow);
                    fragmentMangerX.ShowFragment(aboutFragrment);
                    fragmentnow = aboutFragrment;
                    return true;

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentMangerX = new FragmentMangerX(getFragmentManager(), R.id.content);
        customerFragrment = new CustomerFragrment();
        pushFragrment = new PushFragrment();
        aboutFragrment = new AboutFragrment();

        ((FragmentName) pushFragrment).SetFragmentName("pushFragrment");
        fragmentMangerX.AddFragment(pushFragrment, "pushFragrment");
        fragmentMangerX.FragmentHide("pushFragrment");

        ((FragmentName) customerFragrment).SetFragmentName("customerFragrment");
        fragmentMangerX.AddFragment(customerFragrment, "customerFragrment");
        fragmentMangerX.FragmentHide("customerFragrment");

        ((FragmentName) aboutFragrment).SetFragmentName("aboutFragrment");
        fragmentMangerX.AddFragment(aboutFragrment, "aboutFragrment");
        fragmentMangerX.FragmentHide("aboutFragrment");


        fragmentMangerX.ShowFragment("pushFragrment");
        fragmentnow = pushFragrment;


        // 开启logcat输出，方便debug，发布时请关闭
        XGPushConfig.enableDebug(this, true);
        // 如果需要知道注册是否成功，请使用registerPush(getApplicationContext(), XGIOperateCallback)带callback版本
// 如果需要绑定账号，请使用registerPush(getApplicationContext(),account)版本
// 具体可参考详细的开发指南
// 传递的参数为ApplicationContext
        Context context = getApplicationContext();
        XGPushManager.registerPush(context, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object o, int i) {
                Log.i("token",String.valueOf(o));

            }

            @Override
            public void onFail(Object o, int i, String s) {

            }
        });



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
