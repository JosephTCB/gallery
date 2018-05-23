package com.inspur.tax.roboat.common.banner

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet

class BannerViewPager : ViewPager {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun switchBanner(isNext: Boolean) {
        val current = if (isNext) currentItem + 1 else currentItem - 1
        setCurrentItem(current, true)
    }

    override fun setAdapter(adapter: PagerAdapter?) {
        super.setAdapter(adapter)
        adapter?.takeIf { it is BannerPagerAdapter }?.let {
            offscreenPageLimit = it.count
            addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    if (it.count > 3) {
                        // 检测是否达到首页或者尾页
                        if (positionOffset == 0f && position == 0) {
                            setCurrentItem(it.count - 2, false)
                        }
                        if (positionOffset == 0f && position == it.count - 1) {
                            setCurrentItem(1, false)
                        }
                    }
                }
            })
        }
    }
}