package com.proy.easywork.presentation.util.widgets

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AlertDialog
import com.proy.easywork.R

class AlertDialog {
    private fun setButtonColor(context: Context, alertDialog: AlertDialog) {
    var button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
    if (button != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            button.setTextColor(context.resources.getColor(R.color.colorAccent, context.theme))
        else
            button.setTextColor(context.resources.getColor(R.color.colorAccent))
    }

    button = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL)
    if (button != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            button.setTextColor(context.resources.getColor(R.color.colorAccent, context.theme))
        else
            button.setTextColor(context.resources.getColor(R.color.colorAccent))
    }

    button = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
    if (button != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            button.setTextColor(context.resources.getColor(R.color.cancelar, context.theme))
        else
            button.setTextColor(context.resources.getColor(R.color.cancelar))
    }
}

    fun showMessage(context: Context, mensaje:String, accept: String, decline: String, onClickPositiveButton: () -> Unit,onClickNegativeButton: () -> Unit) {
        setButtonColor(context, AlertDialog.Builder(context).setTitle("EasyWork").setMessage(mensaje)
            .setPositiveButton(accept) { _, _ -> onClickPositiveButton()}
            .setNegativeButton(decline) { _, _ -> onClickNegativeButton()}
            .setCancelable(true).show())
    }
}