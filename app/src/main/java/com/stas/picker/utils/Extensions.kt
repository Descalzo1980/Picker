package com.stas.picker.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.stas.picker.FileType
import com.stas.picker.model.FileCategory
import com.stas.picker.room.FileItem
import com.stas.picker.utils.UIConstants.ALLOWABLE_MIMES_IMAGE
import com.stas.picker.utils.UIConstants.ALLOWABLE_MIMES_VIDEO
import com.stas.picker.utils.UIConstants.ALLOWABLE_MIME_AUDIO
import com.stas.picker.utils.UIConstants.ALLOWABLE_MIME_DOCUMENT
import com.stas.picker.utils.UIConstants.ALLOWABLE_MIME_TYPE_ZIP
import com.stas.picker.utils.UIConstants.ALLOWABLE_MIME_XML
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun View.visible(visible: Boolean) {
    if (visible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun View.invisible(visible: Boolean) {
    if (visible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.INVISIBLE
    }
}

fun <V> Fragment.collectFlowLatest(
    collectableFlow: Flow<V>,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    actionOnCollect: suspend (value: V) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(lifecycleState) {
            collectableFlow.collectLatest { value ->
                actionOnCollect(value)
            }
        }
    }
}

fun checkFileType(mimeType: String?): FileCategory =
    when{
        ALLOWABLE_MIME_DOCUMENT.any { it == mimeType } -> FileCategory.DOCUMENT_DOC
        ALLOWABLE_MIME_DOCUMENT.any { it == mimeType } -> FileCategory.DOCUMENT_PDF
        ALLOWABLE_MIME_DOCUMENT.any { it == mimeType} -> FileCategory.DOCUMENT_TXT
        ALLOWABLE_MIME_DOCUMENT.any { it == mimeType} -> FileCategory.DOCUMENT_PPTX
        ALLOWABLE_MIMES_IMAGE.any { it == mimeType } -> FileCategory.IMAGE
        ALLOWABLE_MIMES_VIDEO.any { it == mimeType } -> FileCategory.VIDEO
        ALLOWABLE_MIME_AUDIO.any { it == mimeType } -> FileCategory.AUDIO
        ALLOWABLE_MIME_XML.any { it == mimeType } -> FileCategory.XML
        ALLOWABLE_MIME_TYPE_ZIP == mimeType -> FileCategory.ZIP
        else -> FileCategory.UNKNOWN
    }

