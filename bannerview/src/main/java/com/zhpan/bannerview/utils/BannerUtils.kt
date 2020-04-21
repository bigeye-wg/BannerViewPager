package com.zhpan.bannerview.utils

import android.content.res.Resources
import android.util.Log

/**
 * <pre>
 * Created by zhangpan on 2019-08-14.
 * Description:
</pre> *
 */
object BannerUtils {

    @JvmStatic
    var isDebugMode = false

    @JvmStatic
    fun dp2px(dpValue: Float): Int {
        return (0.5f + dpValue * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun log(tag: String?, msg: String?) {
        if (isDebugMode) {
            Log.e(tag, msg)
        }
    }

    fun log(msg: String?) {
        if (isDebugMode) {
            Log.e("BannerView", msg)
        }
    }

    @JvmStatic
    fun getRealPosition(isCanLoop: Boolean, position: Int, pageSize: Int): Int {
        if (pageSize == 0) return 0
        return if (isCanLoop) (position - 1 + pageSize) % pageSize else (position + pageSize) % pageSize
    }
}