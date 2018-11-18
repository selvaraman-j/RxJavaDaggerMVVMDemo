package demo.selva.com.rxjavadaggermvvmdemo.di

import dagger.Component
import demo.selva.com.rxjavadaggermvvmdemo.ui.activity.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, WebServiceModule::class, DateModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}