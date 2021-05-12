package com.ericktijerou.ffmpeg_samples

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.ericktijerou.ffmpeg_samples.util.FileManager
import java.io.File

class VideoDialog : DialogFragment() {

    companion object {
        val TAG = VideoDialog::javaClass.name

        lateinit var file: File

        fun show(fragmentManager: FragmentManager, file: File) {
            this.file = file
            VideoDialog().show(fragmentManager, TAG)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_video_preview, null)

        val videoView = view.findViewById<VideoView>(R.id.videoView)
        val videoInfo = view.findViewById<TextView>(R.id.tvInfo)

        videoView.setVideoURI(Uri.fromFile(file))

        val mediaController = MediaController(activity)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        videoView.setOnCompletionListener {
            dismiss()
        }

        videoView.setOnPreparedListener {
            @SuppressLint("SetTextI18n")
            videoInfo.text = "Duration: ${FileManager(requireContext()).milliSecondsToTimer(videoView.duration.toLong())}\n"
        }

        videoView.start()

        return AlertDialog.Builder(activity)
            .setView(view)
            .setTitle("Preview")
            .setPositiveButton("Cancel") { dialog, which ->
                dismiss()
            }
            .create()
    }
}
