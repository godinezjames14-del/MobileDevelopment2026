package com.example.fogofwar.dashboard

class DashboardPresenter(
    private var view: DashboardContract.View?,
    private val model: DashboardContract.Model
) : DashboardContract.Presenter {

    override fun loadMarketData() {
        model.refreshStocks()
        val stocks = model.getStocks()

        val stockUpdate = if (stocks.isNotEmpty()) {
            model.formatMarketOverview(stocks)
        } else {
            "No stocks available."
        }

        val topStocks = model.sortTopStocks(stocks)
        
        val aiInsight = if (topStocks.isNotEmpty()) {
            model.generateAIInsight(topStocks.first())
        } else {
            model.generateAIInsight(null)
        }

        view?.displayStockData(stockUpdate)
        view?.showAIPrediction(aiInsight)
        view?.displayTopStocks(topStocks)
    }

    override fun loadUser() {
        view?.displayWelcomeMessage(model.getCurrentUserName())
    }

    override fun onRefreshClicked() {
        loadMarketData()
    }
}
