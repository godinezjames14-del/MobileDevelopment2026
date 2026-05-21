package com.example.fogofwar.register

import com.example.fogofwar.app.CustomApp

class RegisterModel(val app: CustomApp):RegisterContract.Model {

    override fun createAccount(
        firstName: String,
        lastName: String,
        email: String,
        pass: String
    ) {
        app.newUser(firstName, lastName, email, pass)
    }
}