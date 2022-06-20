package com.example.veriff.feature_veriification.presentation.viewmodels

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.veriff.feature_veriification.di.AppModule
import dagger.Component
import javax.inject.Scope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class Screen1Scope

@Component(
    modules = [AppModule::class]
)
@Screen1Scope
interface Screen1Component {

    @Component.Builder
    interface Builder {
        fun build(): Screen1Component
    }

    fun getViewModel(): SDKMainViewModel
}

@Composable
inline fun <reified T : ViewModel> daggerViewModel(
    key: String? = null,
    crossinline viewModelInstanceCreator: () -> T
): T =
    androidx.lifecycle.viewmodel.compose.viewModel(
        modelClass = T::class.java,
        key = key,
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return viewModelInstanceCreator() as T
            }
        }
    )
