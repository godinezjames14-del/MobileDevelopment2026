package com.example.fogofwar.dashboard

import com.example.fogofwar.app.CustomApp

class DashboardPresenter(
    private var view: DashboardContract.View?,
    val app: CustomApp
) : DashboardContract.Presenter {


    override fun loadMarketData() {
        val stockUpdate = "BTC: $64,000 (+2.5%)\nAAPL: $185.00 (-0.4%)"
        val aiInsight = "AI Signal: Market is bullish. Consider holding."

        view?.displayStockData(stockUpdate)
        view?.showAIPrediction(aiInsight)
    }

    override fun loadUser() {
        val displayName = app.currentUser
        view?.displayWelcomeMessage(displayName)
    }

    override fun onRefreshClicked() {
        loadMarketData()
    }




}