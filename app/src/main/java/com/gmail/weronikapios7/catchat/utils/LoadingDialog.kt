package com.gmail.weronikapios7.catchat.utils

import android.app.Activity
import android.app.AlertDialog
import catchat.R

class LoadingDialog(private val mActivity: Activity) {

    private lateinit var  isDialog: AlertDialog

    fun startLoading(){
        //set view
        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_dialog,null)

        //set Dialog
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
    }

    fun isDismiss(){
        isDialog.dismiss()
    }
}