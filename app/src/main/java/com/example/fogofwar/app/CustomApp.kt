package com.example.fogofwar.app

import android.app.Application

public class CustomApp: Application() {


    var userList = arrayListOf<String>("admin", "user");
    var passList = arrayListOf<String>("123", "123");
    var balanceList = arrayListOf<Int>(1000, 100);
    var firstNameList  = arrayListOf<String>("Henry", "User");
    var lastNameList  = arrayListOf<String>("Godinez", "Name");
    var stockList = mutableListOf("Bitcoin (BTC)", "Ethereum (ETH)", "Solana (SOL)")

    var currentUser = "";
    var currentBalance = 0;
    var isAdmin = false;

    override fun onCreate() {
        super.onCreate()
    }

    fun newUser(firstName: String, lastName: String, user: String, pass: String){
        userList.add(user)
        passList.add(pass)
        firstNameList.add(firstName)
        lastNameList.add(lastName)
        balanceList.add(0)
    }

    fun loadUser(index: Int){
        if(index == 1) isAdmin = true;
        currentUser = firstNameList.get(index)
        currentBalance = balanceList.get(index)
    }

    fun clearUserData(){
        currentUser = "";
        currentBalance = 0;
        isAdmin = false;
    }



}