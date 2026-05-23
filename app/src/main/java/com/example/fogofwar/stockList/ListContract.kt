package com.example.fogofwar.stockList

interface ListContract {
    interface View {
        fun displayItems(items: List<StockModel>)
        fun navigateToAddItem()
        fun configureButton(flag: Boolean)
        fun showEmptyState(show: Boolean)
        fun showStockDetail(stock: StockModel)
        fun showDeleteConfirm(stock: StockModel)
    }
    interface Presenter {
        fun loadItems()
        fun onAddItemClicked()
        fun loadButton()
        fun onSearchQueryChanged(query: String)
        fun onSortChanged(mode: StockAdapter.SortMode)
        fun onItemClicked(stock: StockModel)
        fun onItemLongClicked(stock: StockModel): Boolean
        fun deleteItem(stock: StockModel)
    }
}
