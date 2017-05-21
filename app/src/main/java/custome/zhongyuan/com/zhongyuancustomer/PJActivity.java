package custome.zhongyuan.com.zhongyuancustomer;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.ksoap2.serialization.PropertyInfo;

import custome.zhongyuan.com.zhongyuancustomer.WebService.Webservice;

public class PJActivity extends AppCompatActivity {

    private RadioButton radioButton1, radioButton2, radioButton3;
    private EditText editTextpj;
    private Button btnsubmit;
    private String pushid;
    private int score = 0;
    private String pj = "无说明";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pj);
        radioButton1 = (RadioButton) findViewById(R.id.radio1);
        radioButton2 = (RadioButton) findViewById(R.id.radio2);
        radioButton3 = (RadioButton) findViewById(R.id.radio3);

        editTextpj = (EditText) findViewById(R.id.editpj);
        btnsubmit = (Button) findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(onClickListenerbtnsubmit);
        pushid = getIntent().getStringExtra("pushid");

    }


    View.OnClickListener onClickListenerbtnsubmit = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (!editTextpj.getText().toString().equals("")) {
                pj = editTextpj.getText().toString();

            }

            if (radioButton1.isChecked()) {
                score = 3;
            } else if (radioButton2.isChecked()) {
                score = 2;
            } else if (radioButton3.isChecked()) {
                score = 1;
            }

            Common.ShowPopWindow(btnsubmit, getLayoutInflater(), "提交...");


            new Thread(new Runnable() {
                @Override
                public void run() {
                    PropertyInfo[] propertyInfos = new PropertyInfo[3];
                    PropertyInfo propertyInfo = new PropertyInfo();
                    propertyInfo.setName("pushid");
                    propertyInfo.setValue(pushid);
                    propertyInfos[0] = propertyInfo;

                    propertyInfo = new PropertyInfo();
                    propertyInfo.setName("score");
                    propertyInfo.setValue(score);
                    propertyInfos[1] = propertyInfo;
                    propertyInfo = new PropertyInfo();
                    propertyInfo.setName("pj");
                    propertyInfo.setValue(pj);
                    propertyInfos[2] = propertyInfo;

                    Webservice webservice = new Webservice(Common.ServerWCF, 10000);
                    String r = webservice.PDA_GetInterFaceForStringNew(propertyInfos, "M_updatePushInfo");

                    Log.i("结果", r);

                    if (r.equals("-1") || r.equals("1")) {
                        handler.sendEmptyMessage(-1);
                        return;
                    }


                    handler.sendEmptyMessage(0);

                }
            }).start();

        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Common.CLosePopwindow();
            switch (msg.what) {
                case -1:
                    Toast.makeText(PJActivity.this, "提交失败，请重新尝试", Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Toast.makeText(PJActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
    };

}
