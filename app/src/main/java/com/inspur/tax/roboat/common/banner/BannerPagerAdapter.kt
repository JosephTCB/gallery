package com.inspur.tax.roboat.common.banner

import android.databinding.DataBindingUtil
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inspur.tax.roboat.data.BannerListener
import com.inspur.tax.roboat.data.BannerSource
import com.inspur.tax.roboat.databinding.LayoutBannerBinding

class BannerPagerAdapter(
    private val banners: List<BannerSource>,
    private val onBannerClick: BannerListener
) : PagerAdapter() {
    private val mViews = mutableListOf<View>()
    override fun isViewFromObject(view: View, `object`: Any): Boolean = `object` === view
    override fun getCount(): Int = banners.size
    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val dataBinding =
            mViews.getOrNull(position)?.let(DataBindingUtil::getBinding)
                    ?: LayoutBannerBinding.inflate(
                        LayoutInflater.from(container.context),
                        container,
                        false
                    )

        with(dataBinding) {
            banner = banners[position]
            executePendingBindings()
        }

        return dataBinding.root.also {
            it.setOnClickListener(onBannerClick)
            mViews.add(position, it)
            (it.parent as ViewGroup?)?.removeView(it)
            container.addView(it)
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) =
        container.removeView(mViews[position])

}
