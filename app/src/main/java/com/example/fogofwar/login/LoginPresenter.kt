package com.example.fogofwar.login

import com.example.fogofwar.app.CustomApp

class LoginPresenter(
    private var view: LoginContract.View?,
    val model:LoginModel

) : LoginContract.Presenter {

    override fun processLogin(user: String, pass: String) {
        if (user.trim().isEmpty() || pass.trim().isEmpty()) {
            view?.showErrorMessage("Please fill in all fields")
            return
        }

        if (model.doLogin(user, pass)) {
            view?.showLoginSuccess()
            view?.navigateToDashboard()
        } else {
            view?.showErrorMessage("Invalid email or password")
        }
    }

    override fun onRegisterClicked() {
        view?.navigateToRegister()
    }
}