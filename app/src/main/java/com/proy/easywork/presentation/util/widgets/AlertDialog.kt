package com.proy.easywork.presentation.util.widgets

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AlertDialog
import com.proy.easywork.R
import com.proy.easywork.data.datasource.preferences.MDefaultSharedPref
import com.proy.easywork.data.datasource.storage.MDataInjection
import com.proy.easywork.presentation.splash.SplashActivity

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

    fun showMessage(context: Context, mensaje:String, accept: String, onClickPositiveButton: () -> Unit) {
        setButtonColor(context, AlertDialog.Builder(context).setTitle("EasyWork").setMessage(mensaje)
            .setPositiveButton(accept) { _, _ -> onClickPositiveButton()}
            .setCancelable(true).show())
    }
//    fun showTokenExpires(context: Context, message:String, accept: String) {
//        setButtonColor(context, AlertDialog.Builder(context).setTitle(R.string.app_name).setMessage(message).setPositiveButton(accept,  DialogInterface.OnClickListener { _, _ ->
//            try {
//                val sp: MDefaultSharedPref = MDataInjection.instance.providePreferences() as MDefaultSharedPref
//
//                sp.clearSession()
//                context.startActivity(SplashActivity().newIntent(context))
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }).setCancelable(false).show())
//    }
}