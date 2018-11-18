package demo.selva.com.rxjavadaggermvvmdemo.viewmodel

import demo.selva.com.rxjavadaggermvvmdemo.Repository.CurrencyAPIService
import demo.selva.com.rxjavadaggermvvmdemo.viewmodel.helper.DateUtil
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CurrencyConversionViewModelTest {

    companion object {
        private const val INR_GBP = 95.0
        private const val NO_OF_GBP = 2.0
        private const val TOTAL = NO_OF_GBP * INR_GBP
        private const val LAST_UPDATE = "17-Nov-2018 11:04:16"
    }

    @Mock
    lateinit var currencyAPIService: CurrencyAPIService
    @Mock
    lateinit var dateUtil: DateUtil

    private lateinit var currencyConversionViewModel: CurrencyConversionViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        currencyConversionViewModel = CurrencyConversionViewModel(currencyAPIService, dateUtil)
    }

    @Test
    fun getLastUpdateTest() {
        `when`(dateUtil.getLastUpdate()).thenReturn(LAST_UPDATE)
        assertEquals(LAST_UPDATE, currencyConversionViewModel.getLastUpdate())
    }

    @Test
    fun convertGbpToInrTest() {
        val result = currencyConversionViewModel.convertGbpToInr(INR_GBP, NO_OF_GBP)
        assertEquals(result, TOTAL)
    }
}