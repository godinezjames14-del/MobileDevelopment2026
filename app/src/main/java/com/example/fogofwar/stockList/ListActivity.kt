package com.example.fogofwar.stockList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import com.example.fogofwar.R
import com.example.fogofwar.app.CustomApp

class ListActivity : Activity(), ListContract.View {
    private lateinit var presenter: ListPresenter
    private lateinit var listView: ListView

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_list)

        listView = findViewById(R.id.stockListView)
        val btnAddAsset = findViewById<Button>(R.id.btnAddAsset)

        presenter = ListPresenter(this, application as CustomApp)

        btnAddAsset.setOnClickListener {
            presenter.onAddItemClicked()
        }

        presenter.loadItems()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadItems()
    }

    override fun displayItems(items: List<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        listView.adapter = adapter
    }

    override fun navigateToAddItem() {
        val intent = Intent(this, AddListActivity::class.java)
        startActivity(intent)
    }
}