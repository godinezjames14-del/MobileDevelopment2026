package com.example.fogofwar.stockList

data class StockModel(
    val symbol: String,
    val name: String,
    val price: Double = 0.0,
    val change: Double = 0.0,
    val changePercent: Double = 0.0
) {
    val isPositive: Boolean get() = change >= 0
    val formattedPrice: String get() = "$${"%.2f".format(price)}"
    val formattedChange: String get() {
        val sign = if (isPositive) "+" else ""
        return "$sign${"%.2f".format(change)} ($sign${"%.2f".format(changePercent)}%)"
    }
}
