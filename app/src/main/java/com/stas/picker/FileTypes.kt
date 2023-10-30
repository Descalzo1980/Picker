package com.stas.picker

data class FileCategoryItem(val category: FileCategory)

enum class FileCategory(val label: String, val fileTypes: List<FileType>) {
    DOCUMENT("Documents", listOf(
        FileType("doc", R.drawable.ic_docx),
        FileType("docx", R.drawable.ic_docx),
        FileType("pdf", R.drawable.ic_pdf),
        FileType("txt", R.drawable.ic_txt)
    )),
    IMAGE("Images", listOf(
        FileType("png", R.drawable.ic_jpeg),
        FileType("jpg", R.drawable.ic_jpeg),
        FileType("jpeg", R.drawable.ic_jpeg),
        FileType("bmp", R.drawable.ic_jpeg),
        FileType("gif", R.drawable.ic_jpeg),
        FileType("svg", R.drawable.ic_jpeg),
        FileType("webp", R.drawable.ic_jpeg),
        FileType("ico", R.drawable.ic_jpeg),
        FileType("psd", R.drawable.ic_jpeg),
        FileType("tiff", R.drawable.ic_jpeg),
        FileType("raw", R.drawable.ic_jpeg),
        FileType("arw", R.drawable.ic_jpeg),
        FileType("x3f", R.drawable.ic_jpeg),
        FileType("srw", R.drawable.ic_jpeg),
        FileType("pef", R.drawable.ic_jpeg),
        FileType("rw2", R.drawable.ic_jpeg),
        FileType("nef", R.drawable.ic_jpeg),
        FileType("nrw", R.drawable.ic_jpeg),
        FileType("raf", R.drawable.ic_jpeg),
        FileType("erf", R.drawable.ic_jpeg),
        FileType("crw", R.drawable.ic_jpeg),
        FileType("cr2", R.drawable.ic_jpeg),
        FileType("orf", R.drawable.ic_jpeg),
        FileType("dng", R.drawable.ic_jpeg),
        FileType("ai", R.drawable.ic_jpeg),
        FileType("eps", R.drawable.ic_jpeg),
        FileType("3g2", R.drawable.ic_jpeg),
        FileType("3gp", R.drawable.ic_jpeg),
    )),
    VIDEO("Videos", listOf(
        FileType("avi", R.drawable.ic_video),
        FileType("flv", R.drawable.ic_video),
        FileType("m4v", R.drawable.ic_video),
        FileType("wmv", R.drawable.ic_video),
        FileType("ogv", R.drawable.ic_video),
        FileType("mkv", R.drawable.ic_video),
        FileType("mov", R.drawable.ic_video),
        FileType("mp4", R.drawable.ic_video),
        FileType("swf", R.drawable.ic_video),
        FileType("mpg", R.drawable.ic_video),
        FileType("vob", R.drawable.ic_video),
        FileType("asf", R.drawable.ic_video),
        FileType("webm", R.drawable.ic_video),
    )),
    XML("XML Files", listOf(
        FileType("xml", R.drawable.ic_xml),
        FileType("xlsx", R.drawable.ic_xml)
    )),
    UNKNOWN("Unknown", listOf(
        FileType("unknown", R.drawable.ic_placeholder)
    ));

    companion object {
        fun getFileTypeByExtension(extension: String): FileType? {
            return values().flatMap { it.fileTypes }.find { it.extension == extension }
        }
    }
}