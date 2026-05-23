package com.example.fogofwar.dashboard

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.fogofwar.R
import com.example.fogofwar.app.CustomApp
import com.example.fogofwar.login.LoginActivity
import com.example.fogofwar.stockList.ListActivity
import com.example.fogofwar.stockList.StockModel

class DashboardActivity : Activity(), DashboardContract.View {
    private lateinit var presenter: DashboardPresenter
    private lateinit var topStocksContainer: LinearLayout

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_dashboard)

        topStocksContainer = findViewById(R.id.topStocksContainer)

        val model = DashboardModel(application as CustomApp)
        val app = application as CustomApp
        presenter = DashboardPresenter(this, model)

        presenter.loadUser()
        presenter.loadMarketData()

        findViewById<Button>(R.id.buttonViewList).setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }

        findViewById<Button>(R.id.buttonLogout).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    override fun displayWelcomeMessage(name: String) {
        findViewById<TextView>(R.id.textviewUser).text = "Welcome, $name"
    }

    override fun displayStockData(data: String) {
        findViewById<TextView>(R.id.textviewStockData).text = data
    }

    override fun showAIPrediction(prediction: String) {
        findViewById<TextView>(R.id.textviewAIPreview).text = prediction
    }

    override fun displayTopStocks(stocks: List<StockModel>) {
        topStocksContainer.removeAllViews()

        stocks.forEachIndexed { index, stock ->
            val medal = when (index) {
                0 -> "🥇"
                1 -> "🥈"
                else -> "🥉"
            }
            val color = if (stock.isPositive) Color.parseColor("#2E7D32")
                        else Color.parseColor("#C62828")

            val row = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                setPadding(0, 12, 0, 12)
            }

            val labelView = TextView(this).apply {
                text = "$medal  ${stock.symbol}"
                textSize = 15f
                setTextColor(Color.parseColor("#333333"))
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }

            val nameView = TextView(this).apply {
                text = stock.name
                textSize = 13f
                setTextColor(Color.parseColor("#666666"))
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.5f)
            }

            val changeView = TextView(this).apply {
                text = stock.formattedChange
                textSize = 13f
                setTextColor(color)
                gravity = Gravity.END
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.2f)
            }

            row.addView(labelView)
            row.addView(nameView)
            row.addView(changeView)
            topStocksContainer.addView(row)


            if (index < stocks.size - 1) {
                val divider = android.view.View(this).apply {
                    setBackgroundColor(Color.parseColor("#E0E0E0"))
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 1)
                }
                topStocksContainer.addView(divider)
            }
        }
    }
}
