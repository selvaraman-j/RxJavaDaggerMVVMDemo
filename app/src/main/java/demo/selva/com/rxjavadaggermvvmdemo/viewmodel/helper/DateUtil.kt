package demo.selva.com.rxjavadaggermvvmdemo.viewmodel.helper

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

data class DateUtil @Inject constructor(private val simpleDateFormat: SimpleDateFormat, private val date: Date) {

    fun getLastUpdate(): String {
        return simpleDateFormat.format(date)
    }

}