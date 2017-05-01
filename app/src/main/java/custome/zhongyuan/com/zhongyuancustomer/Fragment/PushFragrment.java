package custome.zhongyuan.com.zhongyuancustomer.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import custome.zhongyuan.com.zhongyuancustomer.Common;
import custome.zhongyuan.com.zhongyuancustomer.FrameController.FragmentName;
import custome.zhongyuan.com.zhongyuancustomer.LoginActivity;
import custome.zhongyuan.com.zhongyuancustomer.MainActivity;
import custome.zhongyuan.com.zhongyuancustomer.R;
import custome.zhongyuan.com.zhongyuancustomer.WebService.Webservice;


/**
 * Created by Stereo on 16/7/12.
 */
public class PushFragrment extends Fragment implements FragmentName {


    private String Fragment_Name = "";


    @Override
    public void SetFragmentName(String name) {
        Fragment_Name = name;
    }

    @Override
    public String GetFragmentName() {
        return Fragment_Name;
    }




    public PushFragrment() {


    }


    private List<Map<String,String>> list;

    private ListView listView;
    private TextView textView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_push, container, false);

        listView = (ListView)rootView.findViewById(R.id.list);
        textView = (TextView)rootView.findViewById(R.id.tip);

        list  = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    handler.sendEmptyMessage(1);
                    Thread.sleep(200);

                    PropertyInfo[] propertyInfos = new PropertyInfo[1];
                    PropertyInfo propertyInfo = new PropertyInfo();
                    propertyInfo.setName("pxid");
                    propertyInfo.setValue(Common.PXID);
                    propertyInfos[0] = propertyInfo;
                    Webservice webservice=new Webservice(Common.ServerWCF,10000);
                    String r = webservice.PDA_GetInterFaceForStringNew(propertyInfos,"M_getPushInfo");

                    if (r.equals("-1") || r.equals("1")) {
                        handler.sendEmptyMessage(-1);
                        return;
                    }

                    try{
                        JSONArray jsonArray=new JSONArray(r);
                        

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    handler.sendEmptyMessage(0);
                }catch (Exception e)
                {e.printStackTrace();}
            }
        }).start();

        return rootView;

    }


    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Common.CLosePopwindow();
            switch (msg.what)
            {
                case 1:
                    Common.ShowPopWindow(listView,getActivity().getLayoutInflater(),"读取...");
                    break;
                case -1:
                    Toast.makeText(getActivity(), "加载数据失败,请退出重新尝试", Toast.LENGTH_SHORT).show();
                    break;
                case 0:

                    break;
            }

        }
    };





}
