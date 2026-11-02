package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.ui.DatingViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Spark", appName)
    }

    @Test
    fun `test initial deck and swiping right triggers match`() {
        val viewModel = DatingViewModel()
        val initialDeck = viewModel.uiState.value.deck
        assertTrue("Initial deck should not be empty", initialDeck.isNotEmpty())

        val firstProfile = initialDeck.first()
        viewModel.swipeRight(firstProfile)

        val updatedState = viewModel.uiState.value
        assertEquals(initialDeck.size - 1, updatedState.deck.size)
        assertNotNull("Should create celebratory match", updatedState.newMatchProfile)
        assertEquals(firstProfile.id, updatedState.newMatchProfile?.id)
    }
}
