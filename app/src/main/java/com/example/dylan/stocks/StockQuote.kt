package com.example.dylan.stocks

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by Dylan Richardson on 5/22/2016.
 *
 * A model that contains any information relevant to a single stock quote
 */

class StockQuote constructor(symbol: String, name: String, quotes: JSONArray) {

    val tag: String = "StockQuote"

    val symbol: String
    val value: Double
    val name: String

    init
    {
        this.symbol = symbol
        this.name = name
        Log.d(tag, quotes.toString())
        // Round to two decimal places
        this.value = Math.round(quotes[quotes.length() - 2] as Double * 100.0) / 100.0
    }
}