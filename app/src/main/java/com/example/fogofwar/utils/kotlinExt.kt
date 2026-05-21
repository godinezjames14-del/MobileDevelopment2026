package com.example.fogofwar.utils

import android.app.Activity
import android.widget.EditText
import android.widget.Toast

fun Activity.showToast(text: String){
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Activity.getEdittextVal(id: Int): String{
    return findViewById<EditText>(id).text.toString();
}

