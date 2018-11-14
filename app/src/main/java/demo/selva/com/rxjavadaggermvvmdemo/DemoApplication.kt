package demo.selva.com.rxjavadaggermvvmdemo

import android.app.Application
import demo.selva.com.rxjavadaggermvvmdemo.di.AppComponent
import demo.selva.com.rxjavadaggermvvmdemo.di.AppModule
import demo.selva.com.rxjavadaggermvvmdemo.di.DaggerAppComponent
import demo.selva.com.rxjavadaggermvvmdemo.di.WebServiceModule

class DemoApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .webServiceModule(WebServiceModule())
                .build()
    }

    fun getApplicationComponent(): AppComponent {
        return appComponent
    }

}