package de.mm20.launcher2.search.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import de.mm20.launcher2.files.R
import de.mm20.launcher2.gservices.DriveFileMeta
import de.mm20.launcher2.gservices.GoogleApiHelper
import de.mm20.launcher2.helper.NetworkUtils
import de.mm20.launcher2.icons.LauncherIcon
import de.mm20.launcher2.preferences.LauncherPreferences

class GDriveFile(
    val fileId: String,
    override val label: String,
    path: String,
    mimeType: String,
    size: Long,
    isDirectory: Boolean,
    metaData: List<Pair<Int, String>>,
    val directoryColor: String?,
    val viewUri: String
) : File(0, path, mimeType, size, isDirectory, metaData) {

    override val key: String = "gdrive://$fileId"

    override val badgeKey: String
        get() = "gdrive://"

    override val isStoredInCloud = true

    override fun getLaunchIntent(context: Context): Intent? {
        return Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(viewUri)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }

    override suspend fun loadIcon(context: Context, size: Int): LauncherIcon? {
        return null
    }

    companion object {
        suspend fun search(context: Context, query: String): List<File> {
            if (query.length < 4) return emptyList()
            val prefs = LauncherPreferences.instance
            if (!prefs.searchGDrive) return emptyList()
            if (NetworkUtils.isOffline(context, prefs.searchGDriveMobileData)) return emptyList()
            val driveFiles = GoogleApiHelper.getInstance(context).queryGDriveFiles(query)
            return driveFiles.map {
                GDriveFile(
                    fileId = it.fileId,
                    label = it.label,
                    size = it.size,
                    mimeType = it.mimeType,
                    isDirectory = it.isDirectory,
                    path = "",
                    directoryColor = it.directoryColor,
                    viewUri = it.viewUri,
                    metaData = getMetadata(it.metadata)
                )
            }.sorted()
        }

        private fun getMetadata(file: DriveFileMeta): List<Pair<Int, String>> {
            val metaData = mutableListOf<Pair<Int, String>>()
            val owners = file.owners
            metaData.add(R.string.file_meta_owner to owners.joinToString(separator = ", "))
            val width = file.width ?: file.width
            val height = file.height ?: file.height
            if (width != null && height != null) metaData.add(R.string.file_meta_dimensions to "${width}x$height")
            return metaData
        }
    }
}