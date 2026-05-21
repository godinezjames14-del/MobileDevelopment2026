package com.example.fogofwar.dashboard

interface DashboardContract {
    interface View {
        fun displayStockData(data: String)
        fun showAIPrediction(prediction: String)
        fun displayWelcomeMessage(name: String)
    }

    interface Presenter {
        fun loadMarketData()
        fun loadUser()
        fun onRefreshClicked()
    }
}