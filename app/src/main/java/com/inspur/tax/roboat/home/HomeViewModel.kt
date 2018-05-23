package com.inspur.tax.roboat.home

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.os.Environment
import java.io.File

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    fun createSDCardDir(dir: String) {
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            // 创建一个文件夹对象，赋值为外部存储器的目录
            val sdcardDir = Environment.getExternalStorageDirectory()
            //得到一个路径，内容是sdcard的文件夹路径和名字
            val path = sdcardDir.path + dir
            val path1 = File(path)
            if (!path1.exists()) {
                //若不存在，创建目录，可以在应用启动的时候创建
                path1.mkdirs()
            }
        }
    }
}