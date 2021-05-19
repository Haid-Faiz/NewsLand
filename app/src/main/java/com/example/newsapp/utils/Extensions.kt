package com.example.newsapp.utils

import android.text.format.DateUtils
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.libnews.models.Article
import com.example.newsapp.ui.Resource
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*



fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun Fragment.handleApiError(failure: Resource.Failure) {
    when {
        failure.errorCode == 401 -> requireView().showSnackBar("Unauthorized request")
        failure.isNetworkError == true -> requireView().showSnackBar("Please check your network connection")
        else -> requireView().showSnackBar(failure.errorBody?.toString() ?: "Something went wrong")
    }
}