package com.example.innocentevil.hellors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.innocentevil.hellors.filter.Conv2DFilter;
import com.example.innocentevil.hellors.filter.GreyFilter;
import com.example.innocentevil.hellors.filter.InvertFilter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Runnable {

    protected static String TAG = MainActivity.class.getCanonicalName();

    private ImageView demoView;
    private ImageView originView;
    private AsyncTask bmLoader;
    private Bitmap inBitmap;
    private Bitmap demoBitmap;

    private GreyFilter mGreyFilter;
    private InvertFilter mInvertFilter;
    private Conv2DFilter mConv2DFilter;

    private float[] blurKernel = {
            1.f, 1.f, 1.f,
            1.f, 1.f, 1.f,
            1.f, 1.f, 1.f,
    };

    private float[] edgeKernel = {
            0.f, 1.f, 0.f,
            1.f, -4.f,1.f,
            0.f, 1.f, 0.f
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button greyBtn = (Button) findViewById(R.id.main_grey_btn);
        greyBtn.setOnClickListener(this);

        Button invertBtn = (Button) findViewById(R.id.main_invert_btn);
        invertBtn.setOnClickListener(this);

        Button cancelBtn = (Button) findViewById(R.id.main_cancel_btn);
        cancelBtn.setOnClickListener(this);

        Button blurBtn = (Button) findViewById(R.id.main_blur_btn);
        blurBtn.setOnClickListener(this);

        Button edgeBtn = (Button) findViewById(R.id.main_edge_btn);
        edgeBtn.setOnClickListener(this);


        demoView = (ImageView) findViewById(R.id.main_demo_iv);
        originView = (ImageView) findViewById(R.id.main_original_iv);

        mGreyFilter = new GreyFilter(this);
        mInvertFilter = new InvertFilter(this);
        mConv2DFilter = new Conv2DFilter(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e(TAG, "Attached Window");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_cancel_btn:
                new Thread(this).start();
                break;
            case R.id.main_grey_btn:
                mGreyFilter.setInput(demoBitmap);
                mGreyFilter.setOutput(demoBitmap);
                mGreyFilter.perform();
                break;
            case R.id.main_invert_btn:
                mInvertFilter.setInput(demoBitmap);
                mInvertFilter.setOutput(demoBitmap);
                mInvertFilter.perform();
                break;
            case R.id.main_edge_btn:
                mConv2DFilter.setCoefficient(edgeKernel,false);
                mConv2DFilter.setInput(demoBitmap);
                mConv2DFilter.setOutput(demoBitmap);
                mConv2DFilter.perform();
                break;
            case R.id.main_blur_btn:
                mConv2DFilter.setCoefficient(blurKernel,true);
                mConv2DFilter.setInput(demoBitmap);
                mConv2DFilter.setOutput(demoBitmap);
                mConv2DFilter.perform();
                break;
        }
    }


    @Override
    public void run() {
        Log.e(TAG,"Run");
        if(Thread.currentThread() != getMainLooper().getThread()) {
            Log.e(TAG, "Load Start");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            inBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.daisy_pollen_flower_220533, options);
            demoBitmap = inBitmap.copy(Bitmap.Config.ARGB_8888, true);
            demoView.post(this);
            Log.e(TAG,"Load Complete");
        }else{
            Log.e(TAG, "Result Post");
            originView.setImageBitmap(inBitmap);
            demoView.setImageBitmap(demoBitmap);
        }

    }
}
