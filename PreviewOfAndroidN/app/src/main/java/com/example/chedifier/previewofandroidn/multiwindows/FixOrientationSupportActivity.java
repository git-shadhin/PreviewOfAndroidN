package com.example.chedifier.previewofandroidn.multiwindows;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chedifier.previewofandroidn.BaseActivity;
import com.example.chedifier.previewofandroidn.R;

/**
 * Created by chedifier on 2016/3/26.
 */
public class FixOrientationSupportActivity extends BaseActivity implements View.OnClickListener{

    private boolean mLocked = false;
    private boolean mScrHor = true;

    private TextView mChangeOri;
    private TextView mLockOri;
    private boolean mPortrait = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fix_orientation_activity);

        mChangeOri = (TextView)findViewById(R.id.change_ori);
        mLockOri = (TextView)findViewById(R.id.lock);
        mChangeOri.setOnClickListener(this);
        mLockOri.setOnClickListener(this);

//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        mPortrait = getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT;

    }

    @Override
    protected void onResume() {
        super.onResume();

        updateOpt();
    }

    public void onClickFixOrientation(View view){
        Log.d(TAG,"onClickFixOrientation");

        mLocked = !mLocked;

        if(mLocked){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }

        updateOpt();
    }

    private void updateOpt(){
        if(mLockOri!=null){
            mLockOri.setText(mLocked?"解锁":"锁定屏幕");
        }

        if(mChangeOri!=null){
            mChangeOri.setText(mPortrait?"转横屏":"转竖屏");
        }
    }

    public void onClickChangeScreenOrientation(View view){
        Log.d(TAG,"mPortrait " + mPortrait + " mLocked:"+mLocked);

        mLocked = true;
        if(mPortrait){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }

        updateOpt();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mPortrait = newConfig.orientation==Configuration.ORIENTATION_PORTRAIT;
        updateOpt();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_ori:

                onClickChangeScreenOrientation(v);
                break;

            case R.id.lock:
                onClickFixOrientation(v);
                break;
        }
    }
}
