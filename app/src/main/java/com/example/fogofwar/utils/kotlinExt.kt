package com.example.fogofwar.utils

import android.app.Activity
import android.view.View
import android.widget.EditText
import android.widget.Toast

fun Activity.showToast(text: String){
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Activity.getEdittextVal(id: Int): String{
    return findViewById<EditText>(id).text.toString();
}

fun View.setVisibility(flag: Boolean){
    if(flag){
        this.visibility = View.VISIBLE
    }else{
        this.visibility = View.GONE
    }
}

