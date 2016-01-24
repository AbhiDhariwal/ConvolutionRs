package com.example.innocentevil.hellors.filter;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;

import com.example.innocentevil.hellors.ScriptC_conv2d;

/**
 * Created by innocentevil on 16. 1. 24.
 */
public class Conv2DFilter extends BaseFilter {

    private ScriptC_conv2d cConv2d;
    private Allocation in;
    private Allocation out;
    private RenderScript script;

    public Conv2DFilter(Context context){
        super(context);
    }

    public void setCoefficient(float[] matix,boolean normalize){
        int size = (int) Math.sqrt(matix.length);
        cConv2d.set_ksize(size);
        Allocation v = Allocation.createSized(script, Element.F32(script),matix.length);
        v.copyFrom(matix);
        cConv2d.bind_kmatrix(v);
        float total = 0.f;
        for(float f : matix){
            total += f;
        }
        cConv2d.set_kdiv(total);
        cConv2d.set_normal(normalize);
    }

    @Override
    protected void initFilter(RenderScript script) {
        this.script = script;
        cConv2d = new ScriptC_conv2d(script);
    }

    @Override
    protected void setInputAllocation(Allocation allocation) {
        in = allocation;
        cConv2d.set_gIn(in);
        cConv2d.invoke_setup();

    }

    @Override
    protected void setOutputAllocation(Allocation allocation) {
        out = allocation;
    }

    @Override
    protected Allocation doFilter(RenderScript script) {
        cConv2d.forEach_root(in,out);
        return out;
    }
}
