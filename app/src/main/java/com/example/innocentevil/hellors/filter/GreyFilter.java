package com.example.innocentevil.hellors.filter;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.util.Log;

import com.example.innocentevil.hellors.ScriptC_grey;


/**
 * Created by innocentevil on 16. 1. 24.
 */
public class GreyFilter extends BaseFilter {

    private ScriptC_grey mCGrey;
    private Allocation input;
    private Allocation output;
    static protected String TAG = GreyFilter.class.getCanonicalName();

    public GreyFilter(Context context){
        super(context);
    }

    @Override
    protected void initFilter(RenderScript script) {
        mCGrey = new ScriptC_grey(script);
    }

    @Override
    protected void setOutputAllocation(Allocation allocation) {
        input = allocation;
    }

    @Override
    protected void setInputAllocation(Allocation allocation) {
        output = allocation;
    }

    @Override
    protected Allocation doFilter(RenderScript script) {
        mCGrey.forEach_root(input, output);
        return output;
    }
}
