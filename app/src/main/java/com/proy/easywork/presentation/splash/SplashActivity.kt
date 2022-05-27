package com.proy.easywork.presentation.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.proy.easywork.MainActivity
import com.proy.easywork.R
import com.proy.easywork.data.datasource.preferences.MDefaultSharedPref
import com.proy.easywork.data.datasource.storage.MDataInjection
import com.proy.easywork.presentation.principal.view.activities.PrincipalActivity
import com.proy.easywork.presentation.principal.view.activities.TecnicoActivity

class SplashActivity : AppCompatActivity() {
    val sp: MDefaultSharedPref = MDataInjection.instance.providePreferences() as MDefaultSharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

    }

    fun newIntent(context: Context): Intent {
        val intent = Intent(context, SplashActivity::class.java)
        intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        return intent
    }

    override fun onStart() {
        super.onStart()
        signin()
    }

    private fun signin() {
        if (sp.isSession()) {
            if(sp.session().rol=="1"){
                val intent = Intent(this, PrincipalActivity::class.java)
                startActivity(intent)
                finish()
            }else {
                val intent = Intent(this, TecnicoActivity::class.java)
                startActivity(intent)
                finish()
            }

        }else{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}