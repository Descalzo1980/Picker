package com.stas.picker.bottom_sheet.media_adapter.viewholder

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.stas.picker.databinding.RvCameraPreviewItemBinding

class CameraViewHolder(
    private val lifecycleOwner: LifecycleOwner,
    private val binding: RvCameraPreviewItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {
        val context = binding.root.context
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({

            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.cameraView.surfaceProvider)
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                   lifecycleOwner, cameraSelector, preview)

            } catch(exc: Exception) {
                Log.e("TAG", "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(context))
    }
}