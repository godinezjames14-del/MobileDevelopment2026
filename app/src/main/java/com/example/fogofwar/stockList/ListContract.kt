package com.example.fogofwar.stockList

interface ListContract {
    interface View {
        fun displayItems(items: List<String>)
        fun navigateToAddItem()

        fun configureButton(flag: Boolean)
    }
    interface Presenter {
        fun loadItems()
        fun onAddItemClicked()

        fun loadButton()
    }
}