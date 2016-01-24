package com.example.innocentevil.hellors.filter;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;

import com.example.innocentevil.hellors.ScriptC_invert;

/**
 * Created by innocentevil on 16. 1. 24.
 */
public class InvertFilter extends BaseFilter {

    private Allocation in;
    private Allocation out;
    private ScriptC_invert mCInvert;

    public InvertFilter(Context context){
        super(context);
    }

    @Override
    protected void initFilter(RenderScript script) {
        mCInvert = new ScriptC_invert(script);
    }

    @Override
    protected void setInputAllocation(Allocation allocation) {
        in = allocation;
    }

    @Override
    protected void setOutputAllocation(Allocation allocation) {
        out = allocation;
    }

    @Override
    protected Allocation doFilter(RenderScript script) {
        mCInvert.forEach_root(in,out);
        return out;
    }
}
