package com.example.dylan.stocks

import android.app.Activity
import android.os.Bundle
import android.widget.ListView
import org.jetbrains.anko.*
import java.util.*


/**
 * Created by Dylan Richardson on 5/11/2016.
 *
 * Integrates components of the application and serves as the main activity.
 */
class MainActivity : Activity(), QuandlAPIHandler {

    val ID_QUOTE_LIST = 1
    private val mAPIService = QuandlAPIService.instance

    private val TAG = "MainActivity"

    lateinit var quoteList: ListView
    lateinit var quoteListAdapter: StockQuoteListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {
            lparams(width=matchParent, height=matchParent)
            quoteList = listView {
                lparams(width=matchParent, height=matchParent)
                id = ID_QUOTE_LIST
            }
        }

        quoteListAdapter = StockQuoteListAdapter(quoteList.context)
        quoteList.adapter = quoteListAdapter

        mAPIService.asyncEODQuoteRequest(this, arrayOf("A", "AA", "ACM", "AAPL", "GOOGL", "NVDA", "TSLA"))

    }

    override fun handleAPIResponse(quotes: ArrayList<StockQuote>) {
        quoteListAdapter.addStocks(quotes)
    }
}