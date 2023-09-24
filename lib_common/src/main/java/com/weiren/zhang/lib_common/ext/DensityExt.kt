package com.weiren.zhang.lib_common.ext

import android.util.TypedValue
import com.weiren.zhang.lib_common.util.AppGlobal

fun dp2px(dpVal: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dpVal, AppGlobal.get()?.resources?.displayMetrics
    ).toInt()
}
