package com.ericktijerou.ffmpeg_samples

interface ProgressListener {
    fun onProgress(progress: String)

    fun onDismiss()
}
