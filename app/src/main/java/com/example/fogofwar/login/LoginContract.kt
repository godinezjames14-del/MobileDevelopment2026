package com.example.fogofwar.login



interface LoginContract {
    interface View {
        fun showLoginSuccess()
        fun showErrorMessage(message: String)
        fun navigateToDashboard()
        fun navigateToRegister()
    }

    interface Presenter {
        fun processLogin(user: String, pass: String)
        fun onRegisterClicked()
    }

    interface Model {
        fun doLogin(user: String, pass: String): Boolean
    }
}