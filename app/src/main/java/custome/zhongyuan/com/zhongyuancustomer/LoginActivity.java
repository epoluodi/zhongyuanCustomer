package custome.zhongyuan.com.zhongyuancustomer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.util.HashMap;
import java.util.Map;

import custome.zhongyuan.com.zhongyuancustomer.WebService.WebThreadDo;
import custome.zhongyuan.com.zhongyuancustomer.WebService.Webservice;

public class LoginActivity extends AppCompatActivity {

    private Button buttonlogin;
    private EditText user, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Common.ServerWCF = "Http://192.168.0.10:9229/";

        buttonlogin = (Button) findViewById(R.id.buttonlogin);
        user = (EditText) findViewById(R.id.user);
        pwd = (EditText) findViewById(R.id.pwd);

        buttonlogin.setOnClickListener(onClickListenerlogin);


        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String s = getKeyShareVarForString("pwd");
                if (!s.equals("null")) {
                    pwd.setText(s);
                    onClickListenerlogin.onClick(buttonlogin);
                }
            }
        },500);

    }


    View.OnClickListener onClickListenerlogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (user.getText().toString().equals("") ||
                    pwd.getText().toString().equals("")) {
                Toast.makeText(LoginActivity.this, "请输入登录的账号和密码", Toast.LENGTH_SHORT).show();
                return;
            }


            Common.ShowPopWindow(buttonlogin, getLayoutInflater(), "登录..");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Login();
                }
            }).start();


        }
    };

    private void Login()
    {
        PropertyInfo[] propertyInfos = new PropertyInfo[2];
        PropertyInfo propertyInfo = new PropertyInfo();
        propertyInfo.setName("pxid");
        propertyInfo.setValue(user.getText().toString());
        propertyInfos[0] = propertyInfo;

        propertyInfo = new PropertyInfo();
        propertyInfo.setName("pwd");
        propertyInfo.setValue(pwd.getText().toString());
        propertyInfos[1] = propertyInfo;
        Webservice webservice=new Webservice(Common.ServerWCF,10000);
        String r = webservice.PDA_GetInterFaceForStringNew(propertyInfos,"M_Login");

        Log.i("结果", r);

        if (r.equals("-1") || r.equals("1")) {
            handler.sendEmptyMessage(-1);
            return;
        }

        try {

            JSONObject jsonObject = new JSONObject(r);

            Common.PXID = user.getText().toString();
            setKeyShareVar("user", user.getText().toString());
            setKeyShareVar("pwd", pwd.getText().toString());

            Common.ID = jsonObject.getString("id");
            Common.HTBH = jsonObject.getString("HTBH");
            Common.dwmc = jsonObject.getString("dwmc");


            JSONArray jsonArray=jsonObject.getJSONArray("data");

            for (int i=0;i<jsonArray.length();i++)
            {
                Map<String,String> map=new HashMap<>();
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                map.put("dtzch",jsonObject1.getString("dtzch"));
                map.put("dtxh",jsonObject1.getString("dtxh"));
                map.put("dtlx",jsonObject1.getString("dtlx"));
                map.put("ccbh",jsonObject1.getString("ccbh"));
                map.put("pp",jsonObject1.getString("pp"));
                map.put("wz",jsonObject1.getString("wz"));
                Common.mapList.add(map);
            }


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "登录失败，请重新尝试", Toast.LENGTH_SHORT).show();
            return;
        }


       handler.sendEmptyMessage(0);
    }

    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Common.CLosePopwindow();
            switch (msg.what)
            {
                case -1:
                    Toast.makeText(LoginActivity.this, "登录失败，请重新尝试", Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
    @Override
    protected void onResume() {
        super.onResume();

        pwd.setText("");
        user.setText("");
        String s = getKeyShareVarForString("user");
        if (!s.equals("null")) {
            user.setText(s);
            pwd.requestFocus();
        }
    }

    public String getKeyShareVarForString(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_APPEND);
        return sharedPreferences.getString(key, "null");
    }

    public void setKeyShareVar(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

}
