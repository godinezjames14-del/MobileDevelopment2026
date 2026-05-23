package com.example.fogofwar.stockList

import com.example.fogofwar.app.CustomApp

class ListPresenter(
    private val view: ListContract.View,
    val app: CustomApp
) : ListContract.Presenter {

    private var currentSort = StockAdapter.SortMode.NAME_ASC
    private var currentQuery = ""

    override fun loadItems() {
        updateView()
    }

    override fun onAddItemClicked() {
        view.navigateToAddItem()
    }

    override fun loadButton() {
        view.configureButton(app.isAdmin)
    }

    override fun onSearchQueryChanged(query: String) {
        currentQuery = query
        updateView()
    }

    override fun onSortChanged(mode: StockAdapter.SortMode) {
        currentSort = mode
        updateView()
    }

    private fun updateView() {
        val allStocks = app.stockList.map { parseStockEntry(it) }
        
        // 1. Filter
        val filtered = if (currentQuery.isBlank()) {
            allStocks
        } else {
            allStocks.filter {
                it.symbol.contains(currentQuery, ignoreCase = true) ||
                it.name.contains(currentQuery, ignoreCase = true)
            }
        }

        // 2. Sort
        val sorted = when (currentSort) {
            StockAdapter.SortMode.NAME_ASC  -> filtered.sortedBy { it.name }
            StockAdapter.SortMode.NAME_DESC -> filtered.sortedByDescending { it.name }
            StockAdapter.SortMode.PRICE_ASC  -> filtered.sortedBy { it.price }
            StockAdapter.SortMode.PRICE_DESC -> filtered.sortedByDescending { it.price }
            StockAdapter.SortMode.CHANGE_ASC  -> filtered.sortedBy { it.changePercent }
            StockAdapter.SortMode.CHANGE_DESC -> filtered.sortedByDescending { it.changePercent }
        }

        view.displayItems(sorted)
        view.showEmptyState(sorted.isEmpty())
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
        app.refreshParsedStocks()
        updateView()
    }

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
