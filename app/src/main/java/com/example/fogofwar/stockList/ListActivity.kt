package com.example.fogofwar.stockList

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fogofwar.R
import com.example.fogofwar.app.CustomApp
import com.example.fogofwar.utils.setVisibility

class ListActivity : Activity(), ListContract.View {

    private lateinit var presenter: ListPresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StockAdapter
    private lateinit var tvEmptyState: TextView
    private lateinit var searchBar: EditText
    private lateinit var btnAddAsset: Button
    private lateinit var llBottomAction: LinearLayout
    private lateinit var spinnerSort: Spinner

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_list)

        recyclerView  = findViewById(R.id.stockRecyclerView)
        tvEmptyState  = findViewById(R.id.tvEmptyState)
        searchBar     = findViewById(R.id.searchBar)
        btnAddAsset   = findViewById(R.id.btnAddAsset)
        llBottomAction = findViewById(R.id.llBottomAction)
        spinnerSort   = findViewById(R.id.spinnerSort)

        presenter = ListPresenter(this, application as CustomApp)

        setupRecyclerView()
        setupSearchBar()
        setupSortSpinner()

        presenter.loadButton()
        presenter.loadItems()

        btnAddAsset.setOnClickListener {
            presenter.onAddItemClicked()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onSearchQueryChanged(searchBar.text.toString())
    }

    // ── RecyclerView ──────────────────────────────────────────────────────────

    private fun setupRecyclerView() {
        adapter = StockAdapter(
            context = this,
            items = mutableListOf(),
            onItemClick = { stock -> presenter.onItemClicked(stock) },
            onItemLongClick = { stock -> presenter.onItemLongClicked(stock) }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    // ── Search ────────────────────────────────────────────────────────────────

    private fun setupSearchBar() {
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                presenter.onSearchQueryChanged(s?.toString() ?: "")
            }
        })
    }

    // ── Sort spinner ──────────────────────────────────────────────────────────

    private fun setupSortSpinner() {
        val sortOptions = arrayOf(
            "Name A→Z", "Name Z→A",
            "Price ↑", "Price ↓",
            "Change ↑", "Change ↓"
        )
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sortOptions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSort.adapter = spinnerAdapter

        spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val mode = when (pos) {
                    0 -> StockAdapter.SortMode.NAME_ASC
                    1 -> StockAdapter.SortMode.NAME_DESC
                    2 -> StockAdapter.SortMode.PRICE_ASC
                    3 -> StockAdapter.SortMode.PRICE_DESC
                    4 -> StockAdapter.SortMode.CHANGE_ASC
                    else -> StockAdapter.SortMode.CHANGE_DESC
                }
                presenter.onSortChanged(mode)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    // ── ListContract.View ─────────────────────────────────────────────────────

    override fun displayItems(items: List<StockModel>) {
        adapter.updateData(items)
    }

    override fun navigateToAddItem() {
        startActivity(Intent(this, AddListActivity::class.java))
    }

    override fun configureButton(flag: Boolean) {
        llBottomAction.setVisibility(flag)
    }

    override fun showEmptyState(show: Boolean) {
        if (show) {
            val app = application as CustomApp
            if (app.isAdmin) {
                tvEmptyState.text = "No assets yet.\nTap + Add New Asset to get started."
            } else {
                tvEmptyState.text = "No assets currently available in the watchlist."
            }
            tvEmptyState.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            tvEmptyState.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    override fun showStockDetail(stock: StockModel) {
        val changeColor = if (stock.isPositive) "#2E7D32" else "#C62828"
        val arrow = if (stock.isPositive) "▲" else "▼"
        AlertDialog.Builder(this)
            .setTitle("${stock.symbol}  —  ${stock.name}")
            .setMessage(
                "Price:   ${stock.formattedPrice}\n" +
                "Change: $arrow ${stock.formattedChange}"
            )
            .setPositiveButton("OK", null)
            .show()
    }

    override fun showDeleteConfirm(stock: StockModel) {
        AlertDialog.Builder(this)
            .setTitle("Remove ${stock.symbol}?")
            .setMessage("Remove ${stock.name} from the watchlist?")
            .setPositiveButton("Remove") { _, _ ->
                presenter.deleteItem(stock)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
