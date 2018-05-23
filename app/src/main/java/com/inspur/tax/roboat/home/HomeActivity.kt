package com.inspur.tax.roboat.home

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import com.inspur.tax.roboat.R
import com.inspur.tax.roboat.common.obtainViewModel
import com.inspur.tax.roboat.common.replaceFragmentInActivity
import com.inspur.tax.roboat.common.ui.FullScreenActivity
import com.inspur.tax.roboat.gallery.GalleryActivity
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.File

class HomeActivity : FullScreenActivity() {
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        supportFragmentManager.findFragmentById(R.id.contentFrame)
                as HomeFragment? ?: HomeFragment.newInstance().let {
            replaceFragmentInActivity(R.id.contentFrame, it)
        }
        viewModel = obtainViewModel<HomeViewModel>().also(this::subscribe)
    }

    override fun onStart() {
        super.onStart()
        swdj.setOnClickListener{
            val intent = Intent(this, GalleryActivity::class.java)
            intent.putExtra("category", swdj.text.toString())
            startActivity(intent)
        }
        swrd.setOnClickListener{
            val intent = Intent(this, GalleryActivity::class.java)
            intent.putExtra("category", swrd.text.toString())
            startActivity(intent)
        }
        fpbl.setOnClickListener{
            val intent = Intent(this, GalleryActivity::class.java)
            intent.putExtra("category", fpbl.text.toString())
            startActivity(intent)
        }
        sbns.setOnClickListener{
            val intent = Intent(this, GalleryActivity::class.java)
            intent.putExtra("category", sbns.text.toString())
            startActivity(intent)
        }
        yhbl.setOnClickListener{
            val intent = Intent(this, GalleryActivity::class.java)
            intent.putExtra("category", yhbl.text.toString())
            startActivity(intent)
        }
        zmbl.setOnClickListener{
            val intent = Intent(this, GalleryActivity::class.java)
            intent.putExtra("category", zmbl.text.toString())
            startActivity(intent)
        }
        xczx.setOnClickListener{
            val intent = Intent(this, GalleryActivity::class.java)
            intent.putExtra("category", xczx.text.toString())
            startActivity(intent)
        }
        qywh.setOnClickListener{
            val intent = Intent(this, GalleryActivity::class.java)
            intent.putExtra("category", qywh.text.toString())
            startActivity(intent)
        }
        bsfw.setOnClickListener{
            val intent = Intent(this, GalleryActivity::class.java)
            intent.putExtra("category", bsfw.text.toString())
            startActivity(intent)
        }
    }

    private fun subscribe(viewModel: HomeViewModel){
        val dirArray = resources.getStringArray(R.array.dirs).asList()
        for(dir in dirArray){
            viewModel.createSDCardDir(dir)
        }
    }
}