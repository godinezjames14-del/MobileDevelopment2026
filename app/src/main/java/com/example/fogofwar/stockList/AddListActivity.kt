package com.example.fogofwar.stockList

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import com.example.fogofwar.*
import com.example.fogofwar.app.CustomApp
import com.example.fogofwar.utils.*

class AddListActivity : Activity() {

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_addlist)

        val app = application as CustomApp
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnBack = findViewById<Button>(R.id.btnBack)

        btnSave.setOnClickListener {
            val name = getEdittextVal(R.id.editStockName).trim()
            if (name.isNotEmpty()) {
                app.stockList.add(name)
                showToast("$name added!")
                finish()
            } else {
                showToast("Please enter an asset name")
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}