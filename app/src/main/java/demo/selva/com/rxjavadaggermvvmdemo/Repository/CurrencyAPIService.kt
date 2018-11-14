package demo.selva.com.rxjavadaggermvvmdemo.Repository

import demo.selva.com.rxjavadaggermvvmdemo.model.Currency
import io.reactivex.Single
import retrofit2.http.GET

interface CurrencyAPIService {
    @GET("api/v5/convert?q=GBP_INR&compact=ultra")
    fun getCurrentCurrencyRate(): Single<Currency>
}