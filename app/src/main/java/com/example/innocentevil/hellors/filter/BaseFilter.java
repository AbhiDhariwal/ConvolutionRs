package com.example.innocentevil.hellors.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;

/**
 * Created by innocentevil on 16. 1. 24.
 */
public abstract class BaseFilter implements Filter, Runnable {


    private RenderScript mScript;
    private Bitmap outBitmap;

    public BaseFilter(Context context){
        mScript = RenderScript.create(context);
        initFilter(mScript);
    }

    @Override
    public void setInput(Bitmap bm) {
        Allocation allocation = Allocation.createFromBitmap(mScript, bm);
        setInputAllocation(allocation);
    }

    @Override
    public void setOutput(Bitmap bm) {
        Allocation allocation = Allocation.createFromBitmap(mScript, bm);
        outBitmap = bm;
        setOutputAllocation(allocation);
    }

    @Override
    public void perform() {
        FilterWorker.getInstance().execute(this);
    }

    @Override
    public void run() {
        doFilter(mScript).copyTo(outBitmap);
    }
    protected abstract void initFilter(RenderScript script);
    protected abstract void setInputAllocation(Allocation allocation);
    protected abstract void setOutputAllocation(Allocation allocation);
    protected abstract Allocation doFilter(RenderScript script);

}
