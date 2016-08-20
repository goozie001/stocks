package com.example.dylan.stocks

import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by Dylan Richardson on 5/22/2016.
 *
 * A model that contains any information relevant to a single stock quote
 */

class StockQuote constructor(symbol: String, name: String, quotes: JSONArray) {

    val symbol: String
    val value: Double
    val name: String

    init
    {
        this.symbol = symbol
        this.name = name
        this.value = quotes[quotes.length() - 2] as Double
    }
}