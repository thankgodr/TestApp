package com.example.veriff.feature_verification.stubed

import android.graphics.Bitmap
import com.example.veriff.core.Resource
import com.example.veriff.feature_verification.di.FakeAppModule
import com.example.veriff.feature_veriification.di.DaggerUsecaseComponent
import com.example.veriff.feature_veriification.domain.model.ExtractedData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Test


class GetDetailsUsecaseTest {

    // Arrange
    val testingComponent = DaggerUsecaseComponent.builder()
        .appModule(FakeAppModule()).build()

    // Act
    val detailsUseCase = testingComponent.getUsecase()

    // Assert
    @Test
    fun `test_that_the_repository_for_geting_text_details_emits_loading_first`() {
        runBlocking {
            val res = detailsUseCase(Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8))
                .first()
            assertThat(res).isInstanceOf(Resource.Loading::class.java)
        }
    }

    // Assert
    @Test
    fun `test_that_the_repository_for_geting_text_details_does_not_returns_null`() {
        runBlocking {
            val res = detailsUseCase(Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8))
                .last()
            assertThat(res.data).isNotNull()
        }
    }

    // Assert
    @Test
    fun `test_that_repository_for_getting_text_details_returns_class_of_Extracted_data`() {
        runBlocking {
            val res = detailsUseCase(Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8))
                .last()
            assertThat(res.data).isInstanceOf(ExtractedData::class.java)
        }
    }

    // Assert
    @Test
    fun `test_that_repository_for_getting_text_details_returns_non_empty_String`() {
        runBlocking {
            val res = detailsUseCase(Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8))
                .last()
            val response = res.data?.data
            assertThat(response?.last()?.values).isNotEmpty()
        }
    }
}
