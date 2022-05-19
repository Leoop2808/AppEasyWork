package com.proy.easywork

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            val info: PackageInfo? = packageManager.getPackageInfo(
                "com.proy.easywork",  //Insert your own package name.
                PackageManager.GET_SIGNATURES
            )
            if (info != null) {
                for (signature in info.signatures) {
                    val md = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}