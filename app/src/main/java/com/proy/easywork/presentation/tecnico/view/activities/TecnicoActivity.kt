package com.proy.easywork.presentation.tecnico.view.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.proy.easywork.R
import com.proy.easywork.presentation.principal.view.activities.PrincipalActivity

class TecnicoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tecnico)
    }
    fun newIntent(context: Context): Intent {
        val intent = Intent(context, TecnicoActivity::class.java)
        intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        return intent
    }
}