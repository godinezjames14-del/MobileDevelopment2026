package com.example.fogofwar.app

import android.app.Application
import com.example.fogofwar.stockList.StockModel

public class CustomApp: Application() {

    var userList = arrayListOf<String>("admin", "user");
    var passList = arrayListOf<String>("123", "123");
    var balanceList = arrayListOf<Int>(1000, 100);
    var firstNameList  = arrayListOf<String>("Henry", "User");
    var lastNameList  = arrayListOf<String>("Godinez", "Name");
    var stockList = mutableListOf("Bitcoin (BTC)", "Ethereum (ETH)", "Solana (SOL)", "Tralalelo (Tr)", "Kiritych (KTr)", "Tesla (T)", "Biohazard (BIO)")

    var currentUser = "";
    var currentBalance = 0;
    var isAdmin = false;

    // Parsed & cached stock data — populated once on app start
    var parsedStocks: List<StockModel> = emptyList()

    override fun onCreate() {
        super.onCreate()
        parsedStocks = stockList.map { parseStockEntry(it) }
    }

    fun newUser(firstName: String, lastName: String, user: String, pass: String){
        userList.add(user)
        passList.add(pass)
        firstNameList.add(firstName)
        lastNameList.add(lastName)
        balanceList.add(0)
    }

    fun loadUser(index: Int){
        isAdmin = (index == 0)
        currentUser = firstNameList.get(index)
        currentBalance = balanceList.get(index)
    }

    fun clearUserData(){
        currentUser = "";
        currentBalance = 0;
        isAdmin = false;
    }

    /** Refresh parsedStocks — call after adding/removing from stockList */
    fun refreshParsedStocks() {
        parsedStocks = stockList.map { parseStockEntry(it) }
    }

    // ── Stock parsing (mirrors ListPresenter logic) ──────────────────────────

    fun parseStockEntry(entry: String): StockModel {
        return if (entry.contains("|")) {
            val parts = entry.split("|")
            val symbol = parts[0]
            val name = if (parts.size > 1) parts[1] else symbol
            StockModel(symbol = symbol, name = name,
                price = simulatedPrice(symbol),
                change = simulatedChange(symbol),
                changePercent = simulatedChangePct(symbol))
        } else {
            val regex = Regex("^(.+?)\\s*\\(([^)]+)\\)$")
            val match = regex.find(entry.trim())
            val name = match?.groupValues?.get(1)?.trim() ?: entry
            val symbol = match?.groupValues?.get(2)?.trim() ?: entry
            StockModel(symbol = symbol, name = name,
                price = simulatedPrice(symbol),
                change = simulatedChange(symbol),
                changePercent = simulatedChangePct(symbol))
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
