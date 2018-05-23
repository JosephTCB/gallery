package com.inspur.tax.roboat.data

import android.net.Uri
import android.support.annotation.DrawableRes
import android.support.annotation.RawRes
import android.view.View


sealed class BannerSource(open val source: Any) {
    class ImageURI(override val source: Uri) : BannerSource(source)
    class ImageResource(@RawRes @DrawableRes override val source: Int) : BannerSource(source)
}

fun List<BannerSource>.expand() =
    this.toMutableList().apply {
        val first = first()
        add(0, last())
        add(first)
    }

abstract class BannerListener : View.OnClickListener {
    companion object {
        private const val MIN_CLICK_DELAY_TIME = 3000L
    }

    private var lastClickTime: Long = 0L

    override fun onClick(v: View) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime
            onBannerClick()
        }
    }

    abstract fun onBannerClick()
}