package custome.zhongyuan.com.zhongyuancustomer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 14-6-5.
 */
public class DBManager {

    public static String DBPath;

    public String userdbpath="";
    public SQLiteDatabase db;
    private Context context;
    SQLiteOpenHelper sqLiteOpenHelper;


    public DBManager(Context context, String userDbpath) {
        sqLiteOpenHelper = new SQLiteOpenHelper(context,userDbpath,null,1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

            }
        };

        userdbpath= userDbpath;
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        this.context = context;

        db  = sqLiteOpenHelper.getWritableDatabase();
    }




    public void closeDB() {
        if (db !=null)
            db.close();
    }



















}
