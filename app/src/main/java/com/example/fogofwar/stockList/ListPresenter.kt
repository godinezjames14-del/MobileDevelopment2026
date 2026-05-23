package com.example.fogofwar.stockList

import com.example.fogofwar.app.CustomApp

class ListPresenter(
    private val view: ListContract.View,
    val app: CustomApp
) : ListContract.Presenter {

    private var currentSort = StockAdapter.SortMode.NAME_ASC

    override fun loadItems() {
        val stocks = app.stockList.map { parseStockEntry(it) }
        view.displayItems(stocks)
        view.showEmptyState(stocks.isEmpty())
    }

    override fun onAddItemClicked() {
        view.navigateToAddItem()
    }

    override fun loadButton() {
        view.configureButton(app.isAdmin)
    }

    override fun onSearchQueryChanged(query: String) {
        // Delegated to adapter via view; presenter notifies so view can pass to adapter
        val stocks = app.stockList.map { parseStockEntry(it) }
            .filter {
                it.symbol.contains(query, ignoreCase = true) ||
                it.name.contains(query, ignoreCase = true)
            }
        view.displayItems(stocks)
        view.showEmptyState(stocks.isEmpty())
    }

    override fun onSortChanged(mode: StockAdapter.SortMode) {
        currentSort = mode
        loadItems()
    }

    override fun onItemClicked(stock: StockModel) {
        view.showStockDetail(stock)
    }

    override fun onItemLongClicked(stock: StockModel): Boolean {
        if (app.isAdmin) {
            view.showDeleteConfirm(stock)
        }
        return true
    }

    override fun deleteItem(stock: StockModel) {
        app.stockList.removeIf {
            it.startsWith(stock.symbol) || it == stock.name
        }
        loadItems()
    }

    /**
     * Parse stored string into a StockModel.
     * Stored format: "SYMBOL|Full Name" OR legacy "Full Name (SYMBOL)"
     * Simulated price/change data — swap in a real API call here.
     */
    private fun parseStockEntry(entry: String): StockModel {
        return if (entry.contains("|")) {
            val parts = entry.split("|")
            val symbol = parts[0]
            val name = if (parts.size > 1) parts[1] else symbol
            StockModel(
                symbol = symbol,
                name = name,
                price = simulatedPrice(symbol),
                change = simulatedChange(symbol),
                changePercent = simulatedChangePct(symbol)
            )
        } else {
            // Legacy format: "Bitcoin (BTC)"
            val regex = Regex("^(.+?)\\s*\\(([^)]+)\\)$")
            val match = regex.find(entry.trim())
            val name = match?.groupValues?.get(1)?.trim() ?: entry
            val symbol = match?.groupValues?.get(2)?.trim() ?: entry
            StockModel(
                symbol = symbol,
                name = name,
                price = simulatedPrice(symbol),
                change = simulatedChange(symbol),
                changePercent = simulatedChangePct(symbol)
            )
        }
    }

    // Deterministic simulated data based on symbol hash — replace with real API
    private fun simulatedPrice(symbol: String): Double {
        val seed = symbol.hashCode().toLong()
        return 10.0 + (Math.abs(seed) % 99000) / 100.0
    }
    private fun simulatedChange(symbol: String): Double {
        val seed = (symbol + "chg").hashCode().toLong()
        return (Math.abs(seed) % 2000 - 1000) / 100.0
    }
    private fun simulatedChangePct(symbol: String): Double {
        val seed = (symbol + "pct").hashCode().toLong()
        return (Math.abs(seed) % 1000 - 500) / 100.0
    }
}
