package com.example.fogofwar.login

import com.example.fogofwar.app.CustomApp

class LoginPresenter(
    private var view: LoginContract.View?,
    val app: CustomApp

) : LoginContract.Presenter {

    override fun doLogin(user: String, pass: String) {
        if (user.trim().isEmpty() || pass.trim().isEmpty()) {
            view?.showErrorMessage("Please fill in all fields")
            return
        }

        if (user == app.user && pass == app.pass) {
            view?.showLoginSuccess()
            view?.navigateToDashboard()
        } else {
            view?.showErrorMessage("Invalid email or password")
        }
    }

    override fun onRegisterClicked() {
        // The Brain tells the Face to move screens
        view?.navigateToRegister()
    }
}