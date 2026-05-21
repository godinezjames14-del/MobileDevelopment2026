package com.example.fogofwar.login

import com.example.fogofwar.app.CustomApp

class LoginModel(val app: CustomApp) : LoginContract.Model {

    override fun doLogin(user: String, pass: String): Boolean {
        if(app.userList.contains(user)){
            var i = app.userList.indexOf(user)
            if(!pass.equals(app.passList.get(i), true)) return false;
            app.loadUser(i);
        }else{
            return false;
        }
        return true;
    }
}