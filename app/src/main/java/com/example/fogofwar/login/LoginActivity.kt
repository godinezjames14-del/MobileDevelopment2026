package com.example.fogofwar.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import com.example.fogofwar.R
import com.example.fogofwar.app.CustomApp
import com.example.fogofwar.dashboard.DashboardActivity
import com.example.fogofwar.register.RegisterActivity
import com.example.fogofwar.utils.getEdittextVal

class LoginActivity : Activity(), LoginContract.View {
    private lateinit var presenter: LoginPresenter

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_login)

        val presenter = LoginPresenter(this, LoginModel(application as CustomApp))


        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val textviewRegisterLink = findViewById<TextView>(R.id.textviewRegisterLink)

        buttonLogin.setOnClickListener {
            presenter.processLogin(
                getEdittextVal(R.id.edittextUsername),
                getEdittextVal(R.id.edittextPassword)
            )
        }

        textviewRegisterLink.setOnClickListener {
            presenter.onRegisterClicked()
        }
    }

    override fun showLoginSuccess() {
        Toast.makeText(this, "Welcome to Fog of War!", Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToDashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}