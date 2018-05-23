package com.inspur.tax.roboat.gallery

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inspur.tax.roboat.common.obtainViewModel
import com.inspur.tax.roboat.databinding.FragmentGalleryBinding
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment() {
    private lateinit var dataBinding: FragmentGalleryBinding

    companion object {
        fun newInstance() = GalleryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentGalleryBinding.inflate(inflater, container, false)
            .also { it.viewModel = (activity as GalleryActivity).obtainViewModel() }
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dataBinding.viewModel?.let {
            it.loopEvent.observe(this, Observer {
                it?.let(galleryBanners::switchBanner)
            })
        }
        setupHints()
        backhome.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupHints() {
        val adapter = VideoAdapter(emptyList())
        hintsListView.adapter = adapter
    }
}