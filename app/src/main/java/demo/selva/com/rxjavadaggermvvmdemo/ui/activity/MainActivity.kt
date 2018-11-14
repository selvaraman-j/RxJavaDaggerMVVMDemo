package demo.selva.com.rxjavadaggermvvmdemo.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import demo.selva.com.rxjavadaggermvvmdemo.DemoApplication
import demo.selva.com.rxjavadaggermvvmdemo.R
import demo.selva.com.rxjavadaggermvvmdemo.viewmodel.CurrencyConversionViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as DemoApplication).getApplicationComponent().inject(this)

        indianRupee = findViewById(R.id.rupee_edt)
        poundSterling = findViewById(R.id.pound_edt)
        lastUpdate = findViewById(R.id.last_update_text)

        //Todo rxjava implementation
        poundSterling.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(text: Editable) {
                if (text.isNotEmpty() && currentPrice > 0) {
                    indianRupee.setText(currencyConversionViewModel.convertGbpToInr(currentPrice, text.toString().toDouble()).toString())
                }
            }

        })

        fetchCurrentRate()

    }

    private fun fetchCurrentRate() {
        val disposable = currencyConversionViewModel.getCurrentCurrencyRate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    currentPrice = if (it?.gbpinr == null) 0.0 else it.gbpinr!!
                    indianRupee.setText(currencyConversionViewModel.convertGbpToInr(currentPrice, defaultGbp).toString())
                    poundSterling.setText(defaultGbp.toString())
                    lastUpdate.text = getString(R.string.last_update, currencyConversionViewModel.getLastUpdate())
                }, { _ -> Toast.makeText(this@MainActivity, "Please try again later!", Toast.LENGTH_SHORT).show() })

        mCompositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.clear()
    }
}