package com.example.veriff.feature_veriification.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.ui.theme.VerifySDKTheme
import com.example.veriff.R
import com.example.veriff.feature_veriification.presentation.composables.CameraView
import com.example.veriff.feature_veriification.presentation.composables.RequestPermissions
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

internal class SDKActivity : AppCompatActivity() {

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
        setContent {
            VerifySDKTheme {
                RequestPermissions {
                    CameraView(
                        outputDirectory = outputDirectory,
                        executor = cameraExecutor
                    )
                }
            }
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
