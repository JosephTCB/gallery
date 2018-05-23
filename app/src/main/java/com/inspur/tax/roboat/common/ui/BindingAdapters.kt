package com.inspur.tax.roboat.common.ui

import android.databinding.BindingAdapter
import android.net.Uri
import android.support.v4.view.ViewPager
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListView
import android.widget.VideoView
import com.inspur.tax.roboat.common.banner.BannerPagerAdapter
import com.inspur.tax.roboat.common.banner.BannerViewPager
import com.inspur.tax.roboat.data.BannerListener
import com.inspur.tax.roboat.data.BannerSource
import com.inspur.tax.roboat.data.expand
import com.inspur.tax.roboat.gallery.VideoAdapter

@BindingAdapter("banner")
fun ImageView.adaptBanner(banner: BannerSource) {
    when (banner) {
        is BannerSource.ImageURI -> this.setImageURI(banner.source)
        is BannerSource.ImageResource -> this.setImageResource(banner.source)
    }
}

@BindingAdapter("banners", "onBannerClick")
fun BannerViewPager.adaptBanners(banners: List<BannerSource>, bannerListener: BannerListener) {
    banners.takeIf { it.isNotEmpty() }?.let {
        adapter =
                BannerPagerAdapter(it.expand(), bannerListener).apply { offscreenPageLimit = count }
        val count = adapter?.count ?: 0
        this.clearOnPageChangeListeners()
        this.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (count > 3) {      // 检测是否达到首页或者尾页
                    if (positionOffset == 0f && position == 0) {
                        setCurrentItem(count - 2, false)
                    }
                    if (positionOffset == 0f && position == count - 1) {
                        setCurrentItem(1, false)
                    }
                }
            }
        })
    }
}

@BindingAdapter("videos")
fun VideoView.adaptVideos(videos: List<Uri>) {
    videos.takeIf { it.isNotEmpty() }?.let {
        var index = 0
        setVideoURI(videos[index])
        start()
        requestFocus()
        setOnErrorListener { _, _, _ -> false }
        setOnPreparedListener { it.start() }
        setOnCompletionListener {
            index = index.inc().rem(videos.size)
            setVideoURI(videos[index])
        }
    }
}

@BindingAdapter("onItemClickListener")
fun ListView.adaptListener(listener: AdapterView.OnItemClickListener?) {
    this.onItemClickListener = listener ?: AdapterView.OnItemClickListener { _, _, _, _ -> Unit }
}

@BindingAdapter("items")
fun ListView.adaptItems(items: List<Uri>) =
        adapter?.let {
            when (it) {
                is VideoAdapter -> it.update(items)
                else -> Unit
            }
        }