package com.example.dylan.stocks

import android.app.LauncherActivity
import android.content.Context
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

class StockViewHolder constructor(sym: TextView, symVal: TextView){
    val mSymbol : TextView
    val mSymVal : TextView

    init {
        mSymbol = sym
        mSymVal = symVal
    }

}

class StockQuoteListAdapter(ctx: Context) : BaseAdapter() {
    private var quoteList = ArrayList<StockQuote>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        val mHolder: StockViewHolder?
        val mView: View

        if (convertView == null) {
            mView = LayoutInflater.from(parent!!.context).inflate(R.layout.stock_item, parent, false)
            val symText = mView.stock_name
            val valText = mView.stock_val
            mHolder = StockViewHolder(symText, valText)
            mView.tag = mHolder
        }
        else {
            mHolder = convertView.tag as StockViewHolder
            mView = convertView
        }
        val quote: StockQuote = quoteList[position]

        mHolder.mSymbol.text = quote.symVal
        mHolder.mSymVal.text = quote.data.toString()

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