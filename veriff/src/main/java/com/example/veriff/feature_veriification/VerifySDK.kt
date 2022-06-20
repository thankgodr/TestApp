package com.example.veriff.feature_veriification

import android.app.Activity
import android.content.Intent
import com.example.veriff.core.Constants
import com.example.veriff.feature_veriification.di.DaggerUsecaseComponent
import com.example.veriff.feature_veriification.presentation.SDKActivity

object VerifySDK {
    // Create dagger component
    private val component = DaggerUsecaseComponent.create()

    /**
     * @author ThankGod Richard
     * Returns flow of Resource Object
     *The flow emit three types of Resource Object which includes Resource.Loading,
     * Resource.Error and Resource.Success
     * @param imageFile  a {@code Uri} instance
     * @param context  a {@code Context} instance
     * @return {@code Flow} of {@code Resource<ExpectedData>}
     * *
     * @param bitmap  a {@code Bitmap} instance
     * @return {@code Flow} of {@code Resource<ExpectedData>}
     **
     * @param imageFile  a  {@code File} Instance
     * @param context  a {@code Context} instance
     * @return {@code Flow} of {@code Resource<ExpectedData>}
     * *
     * @param image  an {@code Image} Instance
     * @return {@code Flow} of {@code Resource<ExpectedData>}
     */
    val getIdDetails = component.getUsecase()

    /**
     * @author ThankGod Richard
     * Returns flow of Resource Object
     *The flow emit three types of Resource Object which includes Resource.Loading,
     * Resource.Error and Resource.Success
     * @param bitmapID  a {@code Bitmap} instance
     * @return {@code Flow} of {@code Resource<Bitmap>}
     * *
     * @param fileID  a {@code File} instance
     * @param context  a {@code Context} instance
     * @return {@code Flow} of {@code Resource<Bitmap>}
     **
     * @param uriID  a  {@code Uri} Instance
     * @param context  a {@code Context} instance
     * @return {@code Flow} of {@code Resource<Bitmap>}
     */
    val verifyFaceId = component.getVerifyUseCase()

    /**
     * @author ThankGod Richard
     * This function is called with intent to recieve an activity result
     * @param context  an {@code Activity} instance
     * @return nothing
     */
    fun verifyAll(context: Activity) {
        context.startActivityForResult(Intent(context, SDKActivity::class.java), Constants.VERIFY_REQUEST_CODE)
    }
}
