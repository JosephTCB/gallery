package com.inspur.tax.roboat.gallery

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableMap
import android.net.Uri
import android.os.SystemClock.uptimeMillis
import android.widget.AdapterView
import com.inspur.tax.roboat.data.BannerListener
import com.inspur.tax.roboat.data.BannerSource
import com.inspur.tax.roboat.data.repository.ResourceRepository
import kotlinx.coroutines.experimental.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.fixedRateTimer

class GalleryViewModel(application: Application) : AndroidViewModel(application) {
    val isVideo = ObservableBoolean().also { it.set(false) }
    val videos = ObservableArrayList<Uri>()
    val loopEvent = MutableLiveData<Boolean>()
    val banners = ObservableArrayList<BannerSource>()
    private var timer: Timer? = null
    val hints = ObservableArrayList<Uri>()
    val clickListener = ObservableField<AdapterView.OnItemClickListener>()

    val bannerListener = object : BannerListener() {
        override fun onBannerClick() = Unit
    }

    private fun loopBanners() {
        timer?.cancel()
        timer = fixedRateTimer(
            name = "bannerTimer",
            period = 4000L,
            action = { loopEvent.postValue(true) })
    }

    private fun loadBanners() {
        if (banners.isEmpty()) {
            launch {
                val images = ResourceRepository.loadGalleryBanners().await()
                banners.clear()
                banners.addAll(images)
            }
        }
    }

    fun loadVideoByCategory(category: String) {
        launch {
            val videoUris = ResourceRepository.loadVideo(category).await()
            videoUris.takeIf { it.isNotEmpty() }?.let {
                isVideo.set(true)
                videos.clear()
                videos.addAll(videoUris)
            } ?: let {
                isVideo.set(false)
                loadBanners()
                loopBanners()
            }
        }
    }

    fun loadVideo(video: String, category: String) {
        launch {
            val videoUris = ResourceRepository.loadVideo(category).await()
            var left:ArrayList<Uri> = ArrayList()
            var right:ArrayList<Uri> = ArrayList()
            var flag = false
            videoUris.forEach{
                if(it.toString().contains(video))
                    flag = true
                if (flag)
                    right.add(it)
                else{
                    left.add(it)
                }
            }
            videoUris.takeIf { it.isNotEmpty() }?.let {
                isVideo.set(true)
                videos.clear()
                videos.addAll(right + left)
            } ?: let {
                isVideo.set(false)
                loadBanners()
                loopBanners()
            }
        }
    }

    fun loopHints(category: String) {
        launch {
            val videosName = ResourceRepository.loadVideo(category).await()
            videosName.takeIf { it.isNotEmpty() }?.let {
                var time: Long = 0L
                clickListener.set(AdapterView.OnItemClickListener { _, _, position, _ ->
                    it[position]?.apply {
                        if(uptimeMillis()-time > 1000){
                            loadVideo(splitVideoName(this.toString()), category)
                            time = uptimeMillis()
                        }
                    }
                })
                with(hints){
                    clear()
                    addAll(it)
                }
            }
        }
    }

    @Throws(Exception::class)
    public fun splitVideoName(path: String?): String {
        // 判空操作必须要有 , 处理方式不唯一 , 根据实际情况可选其一 。
        if (path == null) {
            throw Exception("路径不能为null")
        }

        val start = path.lastIndexOf("/")
        val end = path.lastIndexOf(".")
        return if (start != -1 && end != -1) {
            path.substring(start + 1, end)//包含头不包含尾 , 故:头 + 1
        } else {
            ""
        }
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}