package com.example.fogofwar.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.fogofwar.R
import com.example.fogofwar.app.CustomApp
import com.example.fogofwar.login.LoginActivity
import com.example.fogofwar.stockList.ListActivity
import com.example.fogofwar.utils.setVisibility

class DashboardActivity : Activity(), DashboardContract.View {
    private lateinit var presenter: DashboardPresenter

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_dashboard)
        val presenter = DashboardPresenter(this, application as CustomApp)
        val app = application as CustomApp

        findViewById<Button>(R.id.buttonViewList).setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }


        presenter.loadUser()
        presenter.loadMarketData()

        findViewById<Button>(R.id.buttonLogout).setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

    }

    override fun displayWelcomeMessage(name: String) {
        val textviewUser = findViewById<TextView>(R.id.textviewUser)
        textviewUser.text = "Welcome, $name"
    }



    override fun displayStockData(data: String) {
        val textviewStockData = findViewById<TextView>(R.id.textviewStockData)
        textviewStockData.text = data
    }

    override fun showAIPrediction(prediction: String) {
        val textviewAIPreview = findViewById<TextView>(R.id.textviewAIPreview)
        textviewAIPreview.text = prediction
    }
}