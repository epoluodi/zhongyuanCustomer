package custome.zhongyuan.com.zhongyuancustomer;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import org.ksoap2.serialization.PropertyInfo;

import custome.zhongyuan.com.zhongyuancustomer.Fragment.SignFragrment;
import custome.zhongyuan.com.zhongyuancustomer.Fragment.CustomerFragrment;
import custome.zhongyuan.com.zhongyuancustomer.Fragment.PushFragrment;
import custome.zhongyuan.com.zhongyuancustomer.FrameController.FragmentMangerX;
import custome.zhongyuan.com.zhongyuancustomer.FrameController.FragmentName;
import custome.zhongyuan.com.zhongyuancustomer.WebService.WebThreadDo;

public class MainActivity extends AppCompatActivity {


    private Fragment fragmentnow;
    private PushFragrment pushFragrment;
    private CustomerFragrment customerFragrment;
    private SignFragrment signFragrment;

    private FragmentMangerX fragmentMangerX; //fragment框架
    private BottomNavigationView navigation;

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
                case R.id.navigation_sign:
                case R.id.navigation_chat:
                    fragmentMangerX.FragmentHide(fragmentnow);
                    fragmentMangerX.ShowFragment(signFragrment);
                    fragmentnow = signFragrment;
                    return true;
                case R.id.navigation_webdownload:
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse("http://vc1818.88ip.org:666/");
                    intent.setData(content_url);
                    startActivity(intent);
                    return true;

            }
            return false;
        }

    };


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == 4)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("提示");
            builder.setMessage("确定退出应用吗");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Common.Logout();
                    dialogInterface.dismiss();
                    finish();
                }
            });
            builder.setNegativeButton("取消",null);
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
            return false;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragmentMangerX = new FragmentMangerX(getFragmentManager(), R.id.content);
        customerFragrment = new CustomerFragrment();
        pushFragrment = new PushFragrment();
        signFragrment = new SignFragrment();

        ((FragmentName) pushFragrment).SetFragmentName("pushFragrment");
        fragmentMangerX.AddFragment(pushFragrment, "pushFragrment");
        fragmentMangerX.FragmentHide("pushFragrment");

        ((FragmentName) customerFragrment).SetFragmentName("customerFragrment");
        fragmentMangerX.AddFragment(customerFragrment, "customerFragrment");
        fragmentMangerX.FragmentHide("customerFragrment");

        ((FragmentName) signFragrment).SetFragmentName("signFragrment");
        fragmentMangerX.AddFragment(signFragrment, "signFragrment");
        fragmentMangerX.FragmentHide("signFragrment");


        fragmentMangerX.ShowFragment("pushFragrment");
        fragmentnow = pushFragrment;


        // 开启logcat输出，方便debug，发布时请关闭
        XGPushConfig.enableDebug(this, false);
        // 如果需要知道注册是否成功，请使用registerPush(getApplicationContext(), XGIOperateCallback)带callback版本
// 如果需要绑定账号，请使用registerPush(getApplicationContext(),account)版本
// 具体可参考详细的开发指南
// 传递的参数为ApplicationContext
        Context context = getApplicationContext();

        XGPushManager.registerPush(context, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object o, int i) {
                Log.i("token1",String.valueOf(o));

                PropertyInfo[] propertyInfos = new PropertyInfo[4];
                PropertyInfo propertyInfo = new PropertyInfo();
                propertyInfo.setName("token");
                propertyInfo.setValue(String.valueOf(o));
                propertyInfos[0] = propertyInfo;

                propertyInfo = new PropertyInfo();
                propertyInfo.setName("devicetype");
                propertyInfo.setValue("1");
                propertyInfos[1] = propertyInfo;

                propertyInfo = new PropertyInfo();
                propertyInfo.setName("pxid");
                propertyInfo.setValue(Common.PXID);
                propertyInfos[2] = propertyInfo;


                TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
                String szImei = TelephonyMgr.getDeviceId();


                propertyInfo = new PropertyInfo();
                propertyInfo.setName("deviceid");
                propertyInfo.setValue(szImei);
                propertyInfos[3] = propertyInfo;


                WebThreadDo webThreadDo=new WebThreadDo(propertyInfos,"M_UploadToken");
                webThreadDo.requstWebinterfaceForString(true);

                Log.i("结果",webThreadDo.amessage.obj.toString());


            }

            @Override
            public void onFail(Object o, int i, String s) {

            }
        });
    }


}
