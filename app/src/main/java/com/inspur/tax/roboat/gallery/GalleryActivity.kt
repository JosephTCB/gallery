package com.inspur.tax.roboat.gallery

import android.os.Bundle
import com.inspur.tax.roboat.R
import com.inspur.tax.roboat.common.obtainViewModel
import com.inspur.tax.roboat.common.replaceFragmentInActivity
import com.inspur.tax.roboat.common.ui.FullScreenActivity

class GalleryActivity : FullScreenActivity() {
    private lateinit var viewModel: GalleryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        supportFragmentManager.findFragmentById(R.id.contentFrame)
                as GalleryFragment? ?: GalleryFragment.newInstance().let {
            replaceFragmentInActivity(R.id.contentFrame, it)
        }
        viewModel = obtainViewModel<GalleryViewModel>().also(this::subscribe)
    }
    private fun subscribe(viewModel: GalleryViewModel) {
        val category = intent.getStringExtra("category")
        viewModel.loopHints(category)
        viewModel.loadVideoByCategory(category)
    }
    override fun onPause() {
        super.onPause()
        this.finish()
    }
}