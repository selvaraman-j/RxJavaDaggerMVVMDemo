package demo.selva.com.rxjavadaggermvvmdemo.viewmodel

import demo.selva.com.rxjavadaggermvvmdemo.Repository.CurrencyAPIService
import demo.selva.com.rxjavadaggermvvmdemo.model.Currency
import io.reactivex.Single
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CurrencyConversionViewModel @Inject constructor(private val currencyAPIService: CurrencyAPIService) {

    fun getCurrentCurrencyRate(): Single<Currency> {
        return currencyAPIService.getCurrentCurrencyRate()
    }

    fun getLastUpdate(): String {
        val formatter = SimpleDateFormat("dd-MMM-yyyy hh:mm:ss", Locale.getDefault())
        return formatter.format(Date())
    }

    fun convertGbpToInr(currentIndianRupee: Double, gbp: Double): Double {
        return gbp * currentIndianRupee
    }
}
