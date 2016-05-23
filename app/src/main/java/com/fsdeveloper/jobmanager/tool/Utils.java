package com.fsdeveloper.jobmanager.tool;

import android.content.Context;
import android.content.res.TypedArray;

import com.fsdeveloper.jobmanager.R;

/**
 * @author Created by Douglas Rafael on 22/05/2016.
 * @version 1.0
 */


public class Utils {

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    public static int getTabsHeight(Context context) {
        return (int) context.getResources().getDimension(R.dimen.fab_margin);
    }
}