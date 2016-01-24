package com.example.innocentevil.hellors.filter;

import android.graphics.Bitmap;

/**
 * Created by innocentevil on 16. 1. 24.
 */
public interface Filter {
    void setInput(Bitmap bm);
    void setOutput(Bitmap bm);
    void perform();
}
