package com.proy.easywork.presentation.loginTecnico.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.proy.easywork.R
import com.proy.easywork.presentation.principal.view.activities.PrincipalActivity

class LoginTecnicoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_login_tecnico)
    }
    fun newIntent(context: Context): Intent {
        val intent = Intent(context, LoginTecnicoActivity::class.java)
        intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        return intent
    }
}