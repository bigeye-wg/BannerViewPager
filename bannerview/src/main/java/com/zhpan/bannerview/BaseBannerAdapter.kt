package com.zhpan.bannerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zhpan.bannerview.BannerViewPager.OnPageClickListener
import com.zhpan.bannerview.utils.BannerUtils.getRealPosition
import java.util.*

/**
 * Created by zhpan on 2017/3/28.
 */
abstract class BaseBannerAdapter<T, VH : BaseViewHolder<T>?> : RecyclerView.Adapter<VH>() {
    @JvmField
    protected var mList: MutableList<T> = ArrayList()
    private var isCanLoop = false
    private var mPageClickListener: OnPageClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflate = LayoutInflater.from(parent.context).inflate(getLayoutId(viewType), parent, false)
        return createViewHolder(inflate, viewType)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val realPosition = getRealPosition(isCanLoop, position, mList.size)
        holder?.itemView?.setOnClickListener {
            if (mPageClickListener != null) {
                mPageClickListener?.onPageClick(getRealPosition(isCanLoop, position, mList.size))
            }
        }
        onBind(holder, mList[realPosition], realPosition, mList.size)
    }

    override fun getItemViewType(position: Int): Int {
        val realPosition = getRealPosition(isCanLoop, position, mList.size)
        return getViewType(realPosition)
    }

    override fun getItemCount(): Int {
        return if (isCanLoop && mList.size > 1) {
            MAX_VALUE
        } else {
            mList.size
        }
    }

    var data: List<T>?
        get() = mList
        set(list) {
            if (null != list) {
                mList.clear()
                mList.addAll(list)
            }
        }

    fun setCanLoop(canLoop: Boolean) {
        isCanLoop = canLoop
    }

    fun setPageClickListener(pageClickListener: OnPageClickListener?) {
        mPageClickListener = pageClickListener
    }

    val listSize: Int
        get() = mList.size

    protected open fun getViewType(position: Int): Int {
        return 0
    }

    protected abstract fun onBind(holder: VH, data: T, position: Int, pageSize: Int)
    abstract fun createViewHolder(itemView: View, viewType: Int): VH
    abstract fun getLayoutId(viewType: Int): Int

    companion object {
        const val MAX_VALUE = 500
    }
}