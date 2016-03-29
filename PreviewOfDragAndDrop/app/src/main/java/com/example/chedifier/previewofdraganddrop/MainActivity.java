package com.example.chedifier.previewofdraganddrop;

import android.content.ClipData;
import android.content.ClipDescription;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.DropPermissions;
import android.view.View;
import android.widget.ImageView;

import java.io.FileNotFoundException;

/**
 * 测试AndroidN上，多窗口时不同应用之间的拖拽
 */
public class MainActivity extends AppCompatActivity implements View.OnDragListener{

    protected final String TAG = getClass().getSimpleName();

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mImageView = new ImageView(this);
        mImageView.setPadding(50,50,50,50);
        mImageView.setBackgroundColor(0xffffdead);

        setContentView(mImageView);


        mImageView.setOnDragListener(this);

    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {

        switch (dragEvent.getAction()){
            case DragEvent.ACTION_DRAG_STARTED:
                Log.d(TAG,"onDrag: ACTION_DRAG_STARTED");

                if(dragEvent.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_INTENT)){
                    Log.d(TAG,"onDrag: hited");

                    mImageView.setBackgroundColor(0xffffdead);
                    mImageView.invalidate();

                    return true;
                }

                return false;
            case DragEvent.ACTION_DRAG_ENTERED:
                Log.d(TAG,"onDrag: ACTION_DRAG_ENTERED");

                mImageView.setBackgroundColor(0xff00ff00);

                break;

            case DragEvent.ACTION_DRAG_LOCATION:
                Log.d(TAG,"onDrag: ACTION_DRAG_LOCATION");

                break;

            case DragEvent.ACTION_DRAG_EXITED:
                Log.d(TAG,"onDrag: ACTION_DRAG_EXITED");

                mImageView.setBackgroundColor(0xffffdead);

                break;

            case DragEvent.ACTION_DROP:
                Log.d(TAG,"onDrag: ACTION_DROP");

                ClipData data = dragEvent.getClipData();
                if(data != null){

                    DropPermissions permissions = requestDropPermissions(dragEvent);

                    ClipData.Item item = data.getItemAt(0);
                    if(item != null){
                        Uri uri = item.getUri();

                        Log.d(TAG,uri.toString());
                        Log.d(TAG,"uri.getEncodedAuthority(): "+uri.getEncodedAuthority());

//                        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
//                        Log.d(TAG,"cursor.toString(): " + cursor.toString());
//                        while(cursor.moveToNext()){
//                            Log.d(TAG,"cursor.getBlob(0): " +  cursor.getBlob(0)
//                            + " cursor.getColumnName(0): " + cursor.getColumnName(0)
//                            + " cursor.getExtras(): " + cursor.getExtras());
//                        }




                        mImageView.setImageURI(uri);
                    }
                }

                break;

            case DragEvent.ACTION_DRAG_ENDED:
                Log.d(TAG,"onDrag: ACTION_DRAG_ENDED");

                mImageView.setBackgroundColor(0xffededed);

                break;

            default:
                Log.e(TAG,"Unknown action type received by OnDragListener.");
                break;
        }

        return true;
    }
}
