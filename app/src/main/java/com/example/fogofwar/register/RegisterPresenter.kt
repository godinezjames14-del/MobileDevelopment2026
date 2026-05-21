package com.example.fogofwar.register

import com.example.fogofwar.app.CustomApp

class RegisterPresenter(
    private var view: RegisterContract.View?,
    val model: RegisterModel

) : RegisterContract.Presenter {

    override fun doRegister(firstName: String, lastName: String, email: String, pass: String, confirmPass: String) {

        if (firstName.trim().isEmpty() || lastName.trim().isEmpty() ||
            email.trim().isEmpty() || pass.trim().isEmpty()) {
            view?.showErrorMessage("All fields are required.")
            return
        }

        if (!email.contains("@") || !email.contains(".")) {
            view?.showErrorMessage("Please enter a valid email address.")
            return
        }

        if (pass.length < 6) {
            view?.showErrorMessage("Password must be at least 6 characters.")
            return
        }

        if (pass != confirmPass) {
            view?.showErrorMessage("Passwords do not match.")
            return
        }


        model.createAccount(firstName, lastName, email, pass)
        view?.navigateToLogin()
    }


}