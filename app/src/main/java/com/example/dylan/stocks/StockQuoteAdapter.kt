package com.example.dylan.stocks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.stock_item.view.*
import java.util.*


/**
 * Created by Dylan Richardson on 6/19/2016.
 *
 * Adapter for listing a group of stocks in the main view of application
 */

class StockViewHolder constructor(symbol: TextView,  value: TextView) {
    val symbol: TextView
    val value: TextView

    init {
        this.symbol = symbol
        this.value = value
    }

}

class StockQuoteListAdapter() : BaseAdapter() {
    private var quoteList = ArrayList<StockQuote>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        val mHolder: StockViewHolder
        val mView: View

        if (convertView == null) {
            mView = LayoutInflater.from(parent!!.context).inflate(R.layout.stock_item, parent, false)
            val symText = mView.stock_sym
            val valText = mView.stock_val
            mHolder = StockViewHolder(symText, valText)
            mView.tag = mHolder
        }
        else {
            mHolder = convertView.tag as StockViewHolder
            mView = convertView
        }
        val quote: StockQuote = quoteList[position]

        mHolder.symbol.text = quote.symbol
        mHolder.value.text = quote.value.toString()

        return mView
    }

    override fun getItem(position: Int): Any? {
        return quoteList[position]
    }

    override fun getItemId(position: Int): Long {
        return quoteList[position].hashCode().toLong()
    }

    override fun getCount(): Int {
        return quoteList.size
    }

    fun addStocks(quotes: ArrayList<StockQuote>): Unit {
        quoteList.addAll(quotes)
        notifyDataSetChanged()
    }
}