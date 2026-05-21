package com.example.fogofwar.stockList

interface ListContract {
    interface View {
        fun displayItems(items: List<String>)
        fun navigateToAddItem()
    }
    interface Presenter {
        fun loadItems()
        fun onAddItemClicked()
    }
}