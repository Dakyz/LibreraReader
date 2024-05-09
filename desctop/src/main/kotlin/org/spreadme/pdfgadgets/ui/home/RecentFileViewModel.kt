package org.spreadme.pdfgadgets.ui.home

import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.spreadme.pdfgadgets.common.ViewModel
import org.spreadme.pdfgadgets.common.viewModelScope
import org.spreadme.pdfgadgets.model.FileMetadata
import org.spreadme.pdfgadgets.repository.FileMetadataParser
import org.spreadme.pdfgadgets.repository.FileMetadataRepository
import java.io.File

class RecentFileViewModel(
    private val fileMetadataRepository: FileMetadataRepository
) : ViewModel() {
    private val logger = KotlinLogging.logger { }

    val fileMetadatas = mutableStateListOf<FileMetadata>()

    fun load() {
        viewModelScope.launch {
            fileMetadatas.clear()
            fileMetadatas.addAll(fileMetadataRepository.query())
        }
    }

    fun delete(fileMetadata: FileMetadata) {
        fileMetadatas.remove(fileMetadata)
        viewModelScope.launch {
            fileMetadataRepository.deleteByPath(fileMetadata.path())
        }
    }

    fun reacquire() {
        fileMetadatas.clear()
        viewModelScope.launch {
            fileMetadatas.addAll(fileMetadataRepository.query())
        }
    }

}