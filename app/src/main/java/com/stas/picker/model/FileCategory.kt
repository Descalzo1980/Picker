package com.stas.picker.model

import androidx.annotation.DrawableRes
import com.stas.picker.R

enum class FileCategory(@DrawableRes val iconDrawableId: Int) {
    DOCUMENT_DOC(R.drawable.ic_docx),
    DOCUMENT_PDF(R.drawable.ic_pdf),
    DOCUMENT_TXT(R.drawable.ic_txt),
    DOCUMENT_PPTX(R.drawable.ic_pptx),
    IMAGE(R.drawable.ic_jpeg),
    VIDEO(R.drawable.ic_video),
    AUDIO(R.drawable.ic_music),
    XML(R.drawable.ic_xml),
    ZIP(R.drawable.ic_zip),
    UNKNOWN(R.drawable.ic_placeholder)
}