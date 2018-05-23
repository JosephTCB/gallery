package com.inspur.tax.roboat.data.repository

import android.net.Uri
import android.os.Environment
import com.inspur.tax.roboat.R
import com.inspur.tax.roboat.data.BannerSource
import com.inspur.tax.roboat.data.BannerSource.ImageResource
import com.inspur.tax.roboat.data.BannerSource.ImageURI
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async


object ResourceRepository {
    private val SUPPORTED_VIDEO_FORMAT = listOf("avi", "mp4")
    private val SUPPORTED_PICTURE_FORMAT = listOf("jpg", "png", "bmp", "jpeg", "gif")
    private const val PREFIX_BANNER = "banner"
    fun loadGalleryBanners(): Deferred<List<BannerSource>> {
        return async {
            (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                ?.list { _, name ->
                    SUPPORTED_PICTURE_FORMAT.any {
                        name.endsWith(suffix = it, ignoreCase = true)
                                && name.startsWith(prefix = PREFIX_BANNER, ignoreCase = true)
                    }
                }
                ?.map { ImageURI(Uri.parse(it)) }?.takeIf { it.isNotEmpty() }
                    ?: listOf(
                        ImageResource(R.drawable.gallery_banner_1),
                        ImageResource(R.drawable.gallery_banner_2)
                    ))
        }
    }

    fun loadVideo(category: String): Deferred<List<Uri>> {
        return async {
            Environment.getExternalStoragePublicDirectory(category)
                ?.listFiles { file ->
                    file.isFile && SUPPORTED_VIDEO_FORMAT.any {
                        file.name.endsWith(
                            suffix = it,
                            ignoreCase = true
                        )
                    }
                }?.map { Uri.parse(it.absolutePath) }.orEmpty()
        }
    }

    fun getVideo(category: String): Deferred<List<String>> {
        return async {
            Environment.getExternalStoragePublicDirectory(category)
                    ?.listFiles { file ->
                        file.isFile && SUPPORTED_VIDEO_FORMAT.any {
                            file.name.endsWith(
                                    suffix = it,
                                    ignoreCase = true
                            )
                        }
                    }?.map { it.name}.orEmpty()
        }
    }

}