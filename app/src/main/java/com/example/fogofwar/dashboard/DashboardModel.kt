package com.example.fogofwar.dashboard

import com.example.fogofwar.app.CustomApp
import com.example.fogofwar.stockList.StockModel

class DashboardModel(private val app: CustomApp) : DashboardContract.Model {

    override fun refreshStocks() {
        app.refreshParsedStocks()
    }

    override fun getStocks(): List<StockModel> {
        return app.parsedStocks
    }

    override fun formatMarketOverview(stocks: List<StockModel>): String {
        return stocks.take(3).joinToString("\n") { stock ->
            "${stock.symbol}: ${stock.formattedPrice}  ${stock.formattedChange}"
        }
    }

    override fun sortTopStocks(stocks: List<StockModel>): List<StockModel> {
        return stocks.sortedByDescending { it.changePercent }.take(3)
    }

    override fun generateAIInsight(leader: StockModel?): String {
        return if (leader != null) {
            val sentiment = if (leader.changePercent >= 0) "bullish 📈" else "bearish 📉"
            "AI Signal: ${leader.name} leads today at ${leader.formattedChange}. Market sentiment is $sentiment."
        } else {
            "AI Signal: No market data available."
        }
    }

    override fun getCurrentUserName(): String {
        return app.currentUser
    }
}
