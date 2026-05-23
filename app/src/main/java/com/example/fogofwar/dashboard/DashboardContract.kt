package com.example.fogofwar.dashboard

import com.example.fogofwar.stockList.StockModel

interface DashboardContract {
    interface View {
        fun displayStockData(data: String)
        fun showAIPrediction(prediction: String)
        fun displayWelcomeMessage(name: String)
        fun displayTopStocks(stocks: List<StockModel>)
    }

    interface Presenter {
        fun loadMarketData()
        fun loadUser()
        fun onRefreshClicked()
    }

    interface Model {
        fun refreshStocks()
        fun getStocks(): List<StockModel>
        fun formatMarketOverview(stocks: List<StockModel>): String
        fun sortTopStocks(stocks: List<StockModel>): List<StockModel>
        fun generateAIInsight(leader: StockModel?): String
        fun getCurrentUserName(): String
    }
}
