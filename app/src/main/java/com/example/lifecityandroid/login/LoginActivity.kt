package com.example.lifecityandroid.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.lifecityandroid.MainActivity
import com.example.lifecityandroid.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun gotoApp(view: View) {

        val intent = Intent(this@LoginActivity, MainActivity::class.java )
        startActivity(intent)
    }
}