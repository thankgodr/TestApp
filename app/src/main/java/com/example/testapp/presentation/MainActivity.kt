package com.example.testapp.presentation

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testapp.R
import com.example.veriff.feature_veriification.VerifySDK
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val imageUri = intent?.getParcelableExtra<Uri>("data")
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
            mainViewModel.handleEvent(
                StateEvent.VerifyImageEvent()
            )
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setContentView(R.layout.activity_main)

        btn_read_text.setOnClickListener {
            startForResult.launch(CameraActivity.start(this))
        }

        btn_detect_face.setOnClickListener {
            VerifySDK.verifyAll(this)
        }

        mainViewModel.state.observe(
            this,
            Observer {
                if (!it.isTextLoading && it.result.isNotEmpty()) {
                    txt_result.text = it.result
                }
                if (it.error && it.errorMessage.isNotEmpty()) {
                    Toast.makeText(
                        this@MainActivity,
                        it.errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
                if (!it.isFaceDetectionLoading) {
                    it.detectedImage.let { imageBitmap ->
                        image_face.setImageBitmap(imageBitmap)
                    }
                }
            }
        )
    }
}
