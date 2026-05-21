package com.example.fogofwar.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import com.example.fogofwar.R
import com.example.fogofwar.app.CustomApp
import com.example.fogofwar.login.LoginActivity
import com.example.fogofwar.utils.getEdittextVal

class RegisterActivity : Activity(), RegisterContract.View {
    private lateinit var presenter: RegisterPresenter

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_register)

        val presenter = RegisterPresenter(this, application as CustomApp)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        buttonRegister.setOnClickListener {
            presenter.doRegister(
                getEdittextVal(R.id.edittextFirstName),
                getEdittextVal(R.id.edittextLastName),
                getEdittextVal(R.id.edittextEmail),
                getEdittextVal(R.id.edittextPassword)
                ,getEdittextVal(R.id.edittextConfirmPassword)
            )
        }
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}