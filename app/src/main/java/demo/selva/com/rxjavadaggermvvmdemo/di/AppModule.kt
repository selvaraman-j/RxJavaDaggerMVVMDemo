package demo.selva.com.rxjavadaggermvvmdemo.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import demo.selva.com.rxjavadaggermvvmdemo.DemoApplication

@Module
class AppModule(val application: DemoApplication) {
    
    @Provides
    fun provideContext(): Context {
        return application
    }

    @Provides
    fun provideApplication(): Application {
        return application
    }
}