package com.ericktijerou.ffmpeg_samples

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ericktijerou.ffmpeg_samples.util.Utils
import java.io.File

class MainActivity : AppCompatActivity() {

    private val audio: File by lazy {
        Utils.copyFileToExternalStorage(
            R.raw.audio,
            "audio.mp3",
            applicationContext
        )
    }
    private val audio2: File by lazy {
        Utils.copyFileToExternalStorage(
            R.raw.audio2,
            "audio2.mp3",
            applicationContext
        )
    }
    private val audio3: File by lazy {
        Utils.copyFileToExternalStorage(
            R.raw.audio3,
            "audio3.mp3",
            applicationContext
        )
    }
    private val video: File by lazy {
        Utils.copyFileToExternalStorage(
            R.raw.video,
            "video.mp4",
            applicationContext
        )
    }
    private val video2: File by lazy {
        Utils.copyFileToExternalStorage(
            R.raw.video2,
            "video2.mp4",
            applicationContext
        )
    }
    private val videoSmall1: File by lazy {
        Utils.copyFileToExternalStorage(
            R.raw.video_small_1,
            "video_small_1.mp4",
            applicationContext
        )
    }
    private val images: Array<File> by lazy {
        arrayOf(
            Utils.copyFileToExternalStorage(R.drawable.image1, "image1.png", applicationContext),
            Utils.copyFileToExternalStorage(R.drawable.image2, "image2.png", applicationContext),
            Utils.copyFileToExternalStorage(R.drawable.image3, "image3.png", applicationContext),
            Utils.copyFileToExternalStorage(R.drawable.image4, "image4.png", applicationContext),
            Utils.copyFileToExternalStorage(R.drawable.image5, "image5.png", applicationContext)
        )
    }
    private val font: File by lazy {
        Utils.copyFileToExternalStorage(
            R.font.roboto_black,
            "myFont.ttf",
            applicationContext
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                RESULT_CODE
            )
        } else if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                RESULT_CODE
            )
        }
    }

    private fun initListeners() {

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RESULT_CODE) {
            initListeners()
        }
    }

    companion object {
        const val RESULT_CODE = 200
    }
}