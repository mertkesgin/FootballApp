package com.example.mertkesgin.footballapp.utils

import android.view.View
import android.widget.ProgressBar

object ManageProgress {

    fun hideProgress(progressBar: ProgressBar){
        progressBar.visibility = View.GONE
    }

    fun showProgress(progressBar: ProgressBar){
        progressBar.visibility = View.VISIBLE
    }
}