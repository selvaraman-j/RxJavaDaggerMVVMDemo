package demo.selva.com.rxjavadaggermvvmdemo.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.jakewharton.rxbinding.widget.RxTextView
import demo.selva.com.rxjavadaggermvvmdemo.DemoApplication
import demo.selva.com.rxjavadaggermvvmdemo.R
import demo.selva.com.rxjavadaggermvvmdemo.viewmodel.CurrencyConversionViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var lastUpdate: TextView
    private lateinit var indianRupee: EditText
    private lateinit var poundSterling: EditText

    private var currentPrice: Double = 0.0
    private var defaultGbp: Double = 1.0

    @Inject
    lateinit var currencyConversionViewModel: CurrencyConversionViewModel

    private val mCompositeDisposable = CompositeDisposable()
    private val mCompositeSubscription = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as DemoApplication).getApplicationComponent().inject(this)

        indianRupee = findViewById(R.id.rupee_edt)
        poundSterling = findViewById(R.id.pound_edt)
        lastUpdate = findViewById(R.id.last_update_text)

    }

    override fun onPause() {
        super.onPause()
        mCompositeSubscription.clear()
        mCompositeDisposable.clear()
    }

    override fun onResume() {
        super.onResume()
        bindTextChanges()
        fetchCurrentRate()
    }

    private fun bindTextChanges() {
        val subscription = RxTextView.textChanges(poundSterling)
                .filter {
                    it.isNotEmpty() && currentPrice > 0
                }
                .map {
                    currencyConversionViewModel.convertGbpToInr(currentPrice, it.toString().toDouble()).toString()
                }
                .subscribe {
                    indianRupee.setText(it)
                }
        mCompositeSubscription.add(subscription)
    }

    private fun fetchCurrentRate() {
        val disposable = currencyConversionViewModel.getCurrentCurrencyRate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    currentPrice = it
                }
                .subscribe({
                    updateViews()
                }, { Toast.makeText(this@MainActivity, "Please try again later!", Toast.LENGTH_SHORT).show() })

        mCompositeDisposable.add(disposable)
    }

    private fun updateViews() {
        indianRupee.setText(currencyConversionViewModel.convertGbpToInr(currentPrice, defaultGbp).toString())
        poundSterling.setText(defaultGbp.toString())
        lastUpdate.text = getString(R.string.last_update, currencyConversionViewModel.getLastUpdate())
    }
}
