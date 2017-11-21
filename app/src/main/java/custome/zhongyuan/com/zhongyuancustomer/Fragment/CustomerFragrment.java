package custome.zhongyuan.com.zhongyuancustomer.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Map;

import custome.zhongyuan.com.zhongyuancustomer.Common;
import custome.zhongyuan.com.zhongyuancustomer.FrameController.FragmentName;
import custome.zhongyuan.com.zhongyuancustomer.R;


/**
 * Created by Stereo on 16/7/12.
 */
public class CustomerFragrment extends Fragment implements FragmentName {


    private String Fragment_Name = "";


    @Override
    public void SetFragmentName(String name) {
        Fragment_Name = name;
    }

    @Override
    public String GetFragmentName() {
        return Fragment_Name;
    }




    public CustomerFragrment() {


    }


    private TextView cname,htbh,jssj;

    private ListView listView;
    private MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer, container, false);
        cname = (TextView)rootView.findViewById(R.id.cname);
        htbh = (TextView)rootView.findViewById(R.id.htbh);
        listView = (ListView)rootView.findViewById(R.id.list);
        jssj=(TextView)rootView.findViewById(R.id.jssj);
        cname.setText("(" + Common.PXID + ")" + Common.dwmc);
        htbh.setText(Common.HTBH);
        jssj.setText(Common.jssj);

        myAdapter=new MyAdapter();
        listView.setAdapter(myAdapter);

        return rootView;

    }




    private class MyAdapter extends BaseAdapter
    {
        private TextView dtxh,dtpp,dtwz,dtlx,dtzcbh,dtccbh,nextwbdt;

        @Override
        public int getCount() {
            return Common.mapList.size();
        }

        @Override
        public Object getItem(int i) {
            return Common.mapList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = getActivity().getLayoutInflater().inflate(R.layout.list_liftinfo,null);
            dtxh = (TextView)view.findViewById(R.id.dtxh);
            dtpp = (TextView)view.findViewById(R.id.dtpp);
            dtwz = (TextView)view.findViewById(R.id.dtwz);
            dtlx = (TextView)view.findViewById(R.id.dtlx);
            dtzcbh = (TextView)view.findViewById(R.id.dtzcbh);
            dtccbh = (TextView)view.findViewById(R.id.dtccbh);
            nextwbdt = (TextView)view.findViewById(R.id.nextwbdt);

            Map<String,String> map=Common.mapList.get(i);
            dtxh.setText("电梯编号:" + map.get("dtbh"));
            dtpp.setText("救援识别号:" + map.get("jyNum"));
            dtwz.setText("位置:" + map.get("wz"));
            dtlx.setText("电梯类型:" + map.get("dtlx"));
            dtzcbh.setText("电梯注册编号:" + map.get("dtzch"));
            dtccbh.setText("电梯检验有效期:" +  map.get("jcrq"));
            nextwbdt.setText("下次保养时间:" +  map.get("nextwbdt"));

            if (i % 2 ==0)
            {
                view.setBackgroundColor(getActivity().getResources().getColor(R.color.blackTransparent1));
            }
            return view;
        }
    }




}
