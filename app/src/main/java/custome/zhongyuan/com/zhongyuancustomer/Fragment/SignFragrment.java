package custome.zhongyuan.com.zhongyuancustomer.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import custome.zhongyuan.com.zhongyuancustomer.FrameController.FragmentName;
import custome.zhongyuan.com.zhongyuancustomer.R;


/**
 * Created by Stereo on 16/7/12.
 */
public class SignFragrment extends Fragment implements FragmentName {


    private String Fragment_Name = "";


    @Override
    public void SetFragmentName(String name) {
        Fragment_Name = name;
    }

    @Override
    public String GetFragmentName() {
        return Fragment_Name;
    }




    public SignFragrment() {


    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign, container, false);


        return rootView;

    }





}
