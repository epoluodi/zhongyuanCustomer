package custome.zhongyuan.com.zhongyuancustomer.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
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

import custome.zhongyuan.com.zhongyuancustomer.FrameController.FragmentName;
import custome.zhongyuan.com.zhongyuancustomer.R;


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




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_push, container, false);


        return rootView;

    }





}
