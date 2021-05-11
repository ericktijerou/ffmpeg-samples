package com.ericktijerou.ffmpeg_samples

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class ProgressDialog : DialogFragment(), ProgressListener {

    var text: String = ""

    lateinit var tvProgress: TextView
    lateinit var tvName: TextView
    lateinit var btnStop: Button

    override fun onProgress(progress: String) {
        this.text = progress
        tvProgress.text = text
    }

    override fun onDismiss() {
        dismiss()
    }

    companion object {
        val TAG = ProgressDialog::javaClass.name
        var name: String = ""

        fun show(fragmentManager: FragmentManager, name: String) {
            ProgressDialog().show(fragmentManager, TAG)
            this.name = name
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        (activity as MainActivity).progressListener = this
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_progress, null)
        tvProgress = view.findViewById<AppCompatTextView>(R.id.tvProgress)
        tvName = view.findViewById<AppCompatTextView>(R.id.tvName)
        btnStop = view.findViewById<Button>(R.id.btnStop)
        tvProgress.text = text
        tvName.text = name
        btnStop.setOnClickListener {

        }
        return AlertDialog.Builder(activity)
            .setCancelable(false)
            .setView(view)
            .setTitle("Running FFMpeg Commands")
            .create()
    }
}
