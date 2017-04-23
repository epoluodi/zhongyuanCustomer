package custome.zhongyuan.com.zhongyuancustomer;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;


import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import custome.zhongyuan.com.zhongyuancustomer.db.DBManager;

/**
 * Created by Administrator on 14-10-7.
 */
public class Common {
        public final static String SCAN_ACTION = "android.intent.action.SCANRESULT";//扫描结束action


    public final static String SCAN_ACTION_DD = "scanseuic";//扫描结束action

//      android.intent.action.SCANRESULT



    public final static Object olock = new Object();

    public static String ServerWCF;
    public static Context Appcontext;
    //popwindows方法
    static PopupWindow popupWindow;
    public static View popview;

    public static String PXID="";
    public static String ID="";
    public static String HTBH="";
    public static String dwmc="";
    public static List<Map<String,String>> mapList=new ArrayList<>();

    public static DBManager mainDB;




    public static void Logout()
    {
        mapList.clear();
        PXID="";
        ID="";
        HTBH="";
        dwmc="";
    }

    //显示popwindows
    public static void ShowPopWindow(View v, LayoutInflater inflater, String text) {


        popview = inflater.inflate(R.layout.popwindows, null);

        ((TextView) popview.findViewById(R.id.poptext)).setText(text);
        popupWindow = new PopupWindow(popview, 260, 260);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.Animationpopwindows);
        popupWindow.showAtLocation(v, Gravity.CENTER_VERTICAL, 0, 0);

    }

    //设置popwindows中的文本
    public static void Setpoptext(String text) {
        ((TextView) popview.findViewById(R.id.poptext)).setText(text);
    }

    //关闭POPwindows
    public static void CLosePopwindow() {
        popupWindow.dismiss();
    }

    public static NotificationManager getNotificationManager() {
        return (NotificationManager) Appcontext.getSystemService(Context.NOTIFICATION_SERVICE);
    }


    public static void CopyDb(Context context) {
        InputStream inputStream;
        try {
            inputStream = context.getResources().openRawResource(R.raw.main);
            byte[] bytebuff = new byte[inputStream.available()];
            inputStream.read(bytebuff);
            File file = new File(Environment.getExternalStorageDirectory() + "/zhongyuan/main.db");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytebuff);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String GetSysTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    public static String GetSysTimeshort() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    public static String GetSysOnlyTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }


    //缩略图
    public static Bitmap decodeBitmap(String realbitmap, int inSampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(realbitmap, options);//此时返回bm为空


        options.inJustDecodeBounds = false;
        int w = options.outWidth;
        int h = options.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 600;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (options.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (options.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        if (inSampleSize == 0)
            options.inSampleSize = (int) (be * 0.9);
        else
            options.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeFile(realbitmap, options);
        return bitmap;
    }

}
