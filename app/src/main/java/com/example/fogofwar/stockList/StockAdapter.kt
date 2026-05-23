package com.example.fogofwar.stockList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fogofwar.R

class StockAdapter(
    private val context: Context,
    private var items: MutableList<StockModel>,
    private val onItemClick: (StockModel) -> Unit,
    private val onItemLongClick: (StockModel) -> Boolean
) : RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    private var fullList: MutableList<StockModel> = items.toMutableList()

    inner class StockViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvSymbol: TextView = view.findViewById(R.id.tvSymbol)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val tvChange: TextView = view.findViewById(R.id.tvChange)
        val tvChangeIndicator: TextView = view.findViewById(R.id.tvChangeIndicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_stock, parent, false)
        return StockViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = items[position]

        holder.tvSymbol.text = stock.symbol
        holder.tvName.text = stock.name
        holder.tvPrice.text = stock.formattedPrice
        holder.tvChange.text = stock.formattedChange

        val green = ContextCompat.getColor(context, R.color.stock_green)
        val red = ContextCompat.getColor(context, R.color.stock_red)

        if (stock.isPositive) {
            holder.tvChange.setTextColor(green)
            holder.tvChangeIndicator.text = "▲"
            holder.tvChangeIndicator.setTextColor(green)
        } else {
            holder.tvChange.setTextColor(red)
            holder.tvChangeIndicator.text = "▼"
            holder.tvChangeIndicator.setTextColor(red)
        }

        holder.itemView.setOnClickListener { onItemClick(stock) }
        holder.itemView.setOnLongClickListener { onItemLongClick(stock) }
    }

    override fun getItemCount() = items.size

    /** Filter list by query (symbol or name) */
    fun filter(query: String) {
        items = if (query.isBlank()) {
            fullList.toMutableList()
        } else {
            fullList.filter {
                it.symbol.contains(query, ignoreCase = true) ||
                it.name.contains(query, ignoreCase = true)
            }.toMutableList()
        }
        notifyDataSetChanged()
    }

    /** Sort by various criteria */
    fun sortBy(mode: SortMode) {
        items = when (mode) {
            SortMode.NAME_ASC  -> items.sortedBy { it.name }.toMutableList()
            SortMode.NAME_DESC -> items.sortedByDescending { it.name }.toMutableList()
            SortMode.PRICE_ASC  -> items.sortedBy { it.price }.toMutableList()
            SortMode.PRICE_DESC -> items.sortedByDescending { it.price }.toMutableList()
            SortMode.CHANGE_ASC  -> items.sortedBy { it.changePercent }.toMutableList()
            SortMode.CHANGE_DESC -> items.sortedByDescending { it.changePercent }.toMutableList()
        }
        notifyDataSetChanged()
    }

    /** Replace entire dataset (e.g. after a reload) */
    fun updateData(newItems: List<StockModel>) {
        fullList = newItems.toMutableList()
        items = fullList.toMutableList()
        notifyDataSetChanged()
    }

    /** Remove a single item by symbol */
    fun removeItem(stock: StockModel) {
        val idx = items.indexOfFirst { it.symbol == stock.symbol }
        if (idx >= 0) {
            items.removeAt(idx)
            notifyItemRemoved(idx)
        }
        fullList.removeIf { it.symbol == stock.symbol }
    }

    enum class SortMode {
        NAME_ASC, NAME_DESC,
        PRICE_ASC, PRICE_DESC,
        CHANGE_ASC, CHANGE_DESC
    }
}
