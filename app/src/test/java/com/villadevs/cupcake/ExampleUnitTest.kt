package com.villadevs.cupcake

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.villadevs.cupcake.viewmodel.OrderViewModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun quantity_twelve_cupcakes() {
        val viewModel = OrderViewModel()
        viewModel.quantity.observeForever {}
        viewModel.setQuantity(12)
        assertEquals(12, viewModel.quantity.value)
    }

    @Test
    fun price_twelve_cupcakes() {
        val viewModel = OrderViewModel()
        viewModel.price.observeForever {}
        viewModel.setQuantity(12)
        assertEquals("27,00 €", viewModel.price.value)
    }
}