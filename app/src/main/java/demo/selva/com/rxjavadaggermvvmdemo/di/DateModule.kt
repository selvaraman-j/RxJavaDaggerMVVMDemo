package demo.selva.com.rxjavadaggermvvmdemo.di

import dagger.Module
import dagger.Provides
import java.text.SimpleDateFormat
import java.util.*

@Module
class DateModule {

    @Provides
    fun provideSimpleDateFormat(): SimpleDateFormat {
        return SimpleDateFormat("dd-MMM-yyyy hh:mm:ss", Locale.getDefault())
    }

    @Provides
    fun provideDate(): Date {
        return Date()
    }
}