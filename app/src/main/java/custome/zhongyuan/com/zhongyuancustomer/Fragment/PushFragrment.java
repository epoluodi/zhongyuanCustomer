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
import android.widget.BaseAdapter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import custome.zhongyuan.com.zhongyuancustomer.Common;
import custome.zhongyuan.com.zhongyuancustomer.FrameController.FragmentName;
import custome.zhongyuan.com.zhongyuancustomer.LoginActivity;
import custome.zhongyuan.com.zhongyuancustomer.MainActivity;
import custome.zhongyuan.com.zhongyuancustomer.PJActivity;
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


    private List<Map<String, String>> list;

    private ListView listView;
    private TextView tip;
    private MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_push, container, false);

        listView = (ListView) rootView.findViewById(R.id.list);
        tip = (TextView) rootView.findViewById(R.id.tip);

        list = new ArrayList<>();
        myAdapter=new MyAdapter();
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(onItemClickListener);
        return rootView;

    }

    AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Map<String, String> map = list.get(i);


            Intent intent=new Intent(getActivity(), PJActivity.class);
            intent.putExtra("pushid",map.get("pushid"));
            startActivity(intent);
        }
    };


    @Override
    public void onResume() {
        super.onResume();

        list.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    Thread.sleep(800);
                    handler.sendEmptyMessage(1);
                    PropertyInfo[] propertyInfos = new PropertyInfo[1];
                    PropertyInfo propertyInfo = new PropertyInfo();
                    propertyInfo.setName("pxid");
                    propertyInfo.setValue(Common.PXID);
                    propertyInfos[0] = propertyInfo;
                    Webservice webservice = new Webservice(Common.ServerWCF, 10000);
                    String r = webservice.PDA_GetInterFaceForStringNew(propertyInfos, "M_getPushInfo");

                    if (r.equals("-1") || r.equals("1")) {
                        handler.sendEmptyMessage(-1);
                        return;
                    }

                    try {
                        JSONArray jsonArray = new JSONArray(r);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("oper", jsonObject.getString("oper"));
                            map.put("liftname", jsonObject.getString("liftname"));

                            if (jsonObject.getString("tabletype").equals("1"))
                            {
                                map.put("tabletype","乘客及载货电梯行了维护保养");
                            }else if (jsonObject.getString("tabletype").equals("2"))
                            {
                                map.put("tabletype","杂物电梯行了维护保养");
                            }else if (jsonObject.getString("tabletype").equals("3"))
                            {
                                map.put("tabletype","扶梯行了维护保养");
                            }else if (jsonObject.getString("tabletype").equals("4"))
                            {
                                map.put("tabletype","液压电梯行了维护保养");
                            }else if (jsonObject.getString("tabletype").equals("5"))
                            {
                                map.put("tabletype","电梯进行了紧急维护保养");
                            }
                            map.put("tabletype", jsonObject.getString("tabletype"));
                            map.put("pushid", jsonObject.getString("pushid"));
                            list.add(map);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    handler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            switch (msg.what) {
                case 1:
                    Common.ShowPopWindow(listView, getActivity().getLayoutInflater(), "读取...");
                    break;
                case -1:
                    Common.CLosePopwindow();
                    Toast.makeText(getActivity(), "加载数据失败,请退出重新尝试", Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Common.CLosePopwindow();

                    myAdapter.notifyDataSetChanged();
                    if (list.size() > 0) {
                        tip.setVisibility(View.GONE);
                    }
                    else
                    {
                        tip.setVisibility(View.VISIBLE);
                    }
                    break;
            }

        }
    };


    private class MyAdapter extends BaseAdapter {
        private TextView info;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = getActivity().getLayoutInflater().inflate(R.layout.list_push, null);
            info = (TextView) view.findViewById(R.id.info);


            Map<String, String> map = list.get(i);
            String strinfo = String.format("尊敬的客户您好!\n     我公司 %1$s " +
                    "对贵单位电梯进行了维护保养，请您对他们此次服务进行评价！！ \n   " +
                    "谢谢", map.get("oper"));
            info.setText(strinfo);


            if (i % 2 == 0) {
                view.setBackgroundColor(getActivity().getResources().getColor(R.color.blackTransparent1));
            }
            return view;
        }
    }


}
