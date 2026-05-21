package com.example.fogofwar.app

import android.app.Application

public class CustomApp: Application() {
    var user = "1";
    var pass = "1";
    var stockList = mutableListOf("Bitcoin (BTC)", "Ethereum (ETH)", "Solana (SOL)")

    override fun onCreate() {
        super.onCreate()
    }

    fun newUser(user: String, pass: String){
        this.user = user;
        this.pass = pass;
    }

}