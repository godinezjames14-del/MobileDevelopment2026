package com.example.fogofwar.register

interface RegisterContract {
    interface View {
        fun showErrorMessage(message: String)
        fun navigateToLogin()
    }
    interface Presenter {
        fun doRegister(fName: String, lName: String, email: String, pass: String, confirm: String)
    }
}