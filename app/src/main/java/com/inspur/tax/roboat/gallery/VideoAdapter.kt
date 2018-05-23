package com.inspur.tax.roboat.gallery

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.inspur.tax.roboat.R
import kotlinx.android.synthetic.main.layout_video.view.*
import android.media.ThumbnailUtils
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.Bitmap



class VideoAdapter(private var videos: List<Uri>) : BaseAdapter() {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View =
        view ?: LayoutInflater.from(parent.context).inflate(
            R.layout.layout_video,
            parent,
            false
        ).also {
            it.name.text = splitVideoName(videos.getOrElse(position, { "" }).toString())
            it.image.setImageBitmap(getVideoThumbnail(
                    videos.getOrElse(position, { "" }).toString(),100,100,1))
        }

    override fun getItem(position: Int) = videos.getOrElse(position, { "" })

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = videos.size

    fun update(data: List<Uri>) {
        this.videos = data
        notifyDataSetChanged()
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

    private fun getVideoThumbnail(videoPath: String, width: Int, height: Int,
                                  kind: Int): Bitmap? {
        var bitmap: Bitmap? = null
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind)
        println("w" + bitmap!!.width)
        println("h" + bitmap.height)
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT)
        return bitmap
    }
}