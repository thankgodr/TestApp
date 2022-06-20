package com.example.testapp.presentation

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {
    lateinit var mainViewModel: MainViewModel
    lateinit var state: VerificationState

    @Before
    fun setUp() {
        mainViewModel = MainViewModel()
        mainViewModel.state.observeForever {
            state = it
        }
    }

    @Test
    fun test_states_changes_when_event_is_triggered() {
        mainViewModel.handleEvent(StateEvent.VerifyImageEvent())
        assertThat(state.error).isTrue()
    }
}
