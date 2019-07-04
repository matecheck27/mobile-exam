package com.sample.com.exam.Shared

import java.math.BigDecimal
import java.text.NumberFormat

class CurrencyFormatter {

    companion object {

        fun formatCurrency(double: Double?) : String {
            if(double == null) return ""
            return formatCurrency(double.toBigDecimal())
        }

        fun formatCurrency(decimal: BigDecimal) : String {
            val currencyFormat = NumberFormat.getInstance()
            currencyFormat.minimumFractionDigits = 2
            currencyFormat.maximumFractionDigits = 2
            return currencyFormat.format(decimal)
        }

    }
}