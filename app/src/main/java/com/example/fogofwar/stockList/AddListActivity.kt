package com.example.fogofwar.stockList

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.fogofwar.R
import com.example.fogofwar.app.CustomApp
import com.example.fogofwar.utils.showToast

class AddListActivity : Activity() {

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_addlist)

        val app       = application as CustomApp
        val etSymbol  = findViewById<EditText>(R.id.editStockSymbol)
        val etName    = findViewById<EditText>(R.id.editStockName)
        val btnSave   = findViewById<Button>(R.id.btnSave)
        val btnBack   = findViewById<Button>(R.id.btnBack)

        btnSave.setOnClickListener {
            val symbol = etSymbol.text.toString().trim().uppercase()
            val name   = etName.text.toString().trim()

            when {
                symbol.isEmpty() -> showToast("Please enter a ticker symbol (e.g. AAPL)")
                name.isEmpty()   -> showToast("Please enter the asset name")
                app.stockList.any { it.startsWith("$symbol|") } ->
                    showToast("$symbol is already in your watchlist")
                else -> {
                    // New format: "SYMBOL|Full Name"
                    app.stockList.add("$symbol|$name")
                    showToast("$symbol added to watchlist!")
                    finish()
                }
            }
        }

        btnBack.setOnClickListener { finish() }
    }
}
