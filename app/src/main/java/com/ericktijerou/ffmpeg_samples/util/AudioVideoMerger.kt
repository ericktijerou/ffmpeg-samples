package com.ericktijerou.ffmpeg_samples.util

import android.content.Context
import android.util.Log
import com.arthenica.ffmpegkit.FFmpegKit
import com.ericktijerou.ffmpeg_samples.FFmpegCallback
import java.io.File
import java.io.IOException


class AudioVideoMerger private constructor(private val context: Context) {

    private var audio: File? = null
    private var video: File? = null
    private var callback: FFmpegCallback? = null
    private var outputPath = ""
    private var outputFileName = ""

    fun audioFile(originalFiles: File) = apply {
        this.audio = originalFiles
    }

    fun videoFile(originalFiles: File) = apply {
        this.video = originalFiles
    }

    fun callback(callback: FFmpegCallback) = apply {
        this.callback = callback
    }

    fun outputPath(output: String) = apply {
        this.outputPath = output
    }

    fun outputFileName(output: String) = apply {
        this.outputFileName = output
    }

    fun merge() {
        if (audio == null || !audio!!.exists() || video == null || !video!!.exists()) {
            callback!!.onFailure(IOException("File not exists"))
            return
        }
        if (!audio!!.canRead() || !video!!.canRead()) {
            callback!!.onFailure(IOException("Can't read the file. Missing permission?"))
            return
        }

        val outputLocation = FileManager(context).getConvertedFile(outputPath, outputFileName)

        val cmd = arrayOf(
            "-i",
            video!!.path,
            "-i",
            audio!!.path,
            "-c:v",
            "copy",
            "-c:a",
            "aac",
            "-strict",
            "experimental",
            "-map",
            "0:v:0",
            "-map",
            "1:a:0",
            "-shortest",
            outputLocation.path
        )
        FFmpegKit.executeAsync(cmd,
            { session ->
                val state = session.state
                val returnCode = session.returnCode

                // CALLED WHEN SESSION IS EXECUTED
                Log.d(
                    TAG,
                    java.lang.String.format(
                        "FFmpeg process exited with state %s and rc %s.%s",
                        state,
                        returnCode,
                        session.failStackTrace
                    )
                )
                FileManager(context).refreshGallery(outputLocation.path, context)
                callback?.onSuccess(outputLocation, "")
            }, {
                Log.d(TAG, "LOOOOOOOG")
                // CALLED WHEN SESSION PRINTS LOGS
            }) {
            Log.d(TAG, "statistics")
            // CALLED WHEN SESSION GENERATES STATISTICS
        }
    }

    companion object {

        val TAG = "AudioVideoMerger"

        fun with(context: Context): AudioVideoMerger {
            return AudioVideoMerger(context)
        }
    }
}
