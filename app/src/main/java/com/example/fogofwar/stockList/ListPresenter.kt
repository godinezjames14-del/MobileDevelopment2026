package com.example.fogofwar.stockList

import com.example.fogofwar.app.CustomApp

class ListPresenter(
    private val view: ListContract.View,
    val app: CustomApp

) : ListContract.Presenter {
    override fun loadItems() {
        view.displayItems(app.stockList)
    }

    override fun onAddItemClicked() {
        view.navigateToAddItem()
    }


}