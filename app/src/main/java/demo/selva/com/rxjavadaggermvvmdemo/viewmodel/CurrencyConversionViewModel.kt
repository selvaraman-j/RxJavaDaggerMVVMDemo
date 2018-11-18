package demo.selva.com.rxjavadaggermvvmdemo.viewmodel

import demo.selva.com.rxjavadaggermvvmdemo.Repository.CurrencyAPIService
import demo.selva.com.rxjavadaggermvvmdemo.viewmodel.helper.DateUtil
import io.reactivex.Single
import javax.inject.Inject

class CurrencyConversionViewModel @Inject constructor(private val currencyAPIService: CurrencyAPIService
                                                      , private val dateUtil: DateUtil) {

    fun getCurrentCurrencyRate(): Single<Double> {
        return currencyAPIService.getCurrentCurrencyRate()
                .map {
                    it.gbpinr ?: 0.0
                }
    }

    fun getLastUpdate(): String {
        return dateUtil.getLastUpdate()
    }

    fun convertGbpToInr(currentIndianRupee: Double, gbp: Double): Double {
        return gbp * currentIndianRupee
    }
}
