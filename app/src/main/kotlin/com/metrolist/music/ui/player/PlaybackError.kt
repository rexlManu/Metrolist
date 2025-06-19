package com.metrolist.music.ui.player

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.PlaybackException
import com.metrolist.music.R
import android.util.Log

@Composable
fun PlaybackError(
    error: PlaybackException,
    retry: () -> Unit,
) {
    // Enhanced error logging
    Log.e("PlaybackError", "Playback error occurred:")
    Log.e("PlaybackError", "  Error message: ${error.message}")
    Log.e("PlaybackError", "  Error code: ${error.errorCode}")
    Log.e("PlaybackError", "  Error code name: ${getErrorCodeName(error.errorCode)}")
    Log.e("PlaybackError", "  Timestamp: ${error.timestampMs}")
    Log.e("PlaybackError", "  Cause: ${error.cause}")
    Log.e("PlaybackError", "  Cause message: ${error.cause?.message}")
    Log.e("PlaybackError", "  Root cause: ${error.cause?.cause}")
    Log.e("PlaybackError", "  Root cause message: ${error.cause?.cause?.message}")
    Log.e("PlaybackError", "  Stack trace:", error)

    // Generate comprehensive error message
    val errorMessage = buildErrorMessage(error)

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier =
        Modifier.pointerInput(Unit) {
            detectTapGestures(
                onTap = { retry() },
            )
        },
    ) {
        Icon(
            painter = painterResource(R.drawable.info),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
        )

        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

private fun buildErrorMessage(error: PlaybackException): String {
    // Try to get the most descriptive error message available
    val messages = listOfNotNull(
        error.message?.takeIf { it.isNotBlank() },
        error.cause?.message?.takeIf { it.isNotBlank() },
        error.cause?.cause?.message?.takeIf { it.isNotBlank() }
    )

    return when {
        messages.isNotEmpty() -> {
            val primaryMessage = messages.first()
            val errorCodeName = getErrorCodeName(error.errorCode)
            if (errorCodeName.isNotEmpty()) {
                "$primaryMessage ($errorCodeName)"
            } else {
                primaryMessage
            }
        }
        else -> {
            val errorCodeName = getErrorCodeName(error.errorCode)
            if (errorCodeName.isNotEmpty()) {
                "Playback error: $errorCodeName (Code: ${error.errorCode})"
            } else {
                "Playback error occurred (Code: ${error.errorCode})"
            }
        }
    }
}

private fun getErrorCodeName(errorCode: Int): String {
    return when (errorCode) {
        PlaybackException.ERROR_CODE_UNSPECIFIED -> "Unspecified error"
        PlaybackException.ERROR_CODE_REMOTE_ERROR -> "Remote error"
        PlaybackException.ERROR_CODE_BEHIND_LIVE_WINDOW -> "Behind live window"
        PlaybackException.ERROR_CODE_TIMEOUT -> "Timeout"
        PlaybackException.ERROR_CODE_FAILED_RUNTIME_CHECK -> "Failed runtime check"
        PlaybackException.ERROR_CODE_IO_UNSPECIFIED -> "IO error"
        PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED -> "Network connection failed"
        PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT -> "Network connection timeout"
        PlaybackException.ERROR_CODE_IO_INVALID_HTTP_CONTENT_TYPE -> "Invalid HTTP content type"
        PlaybackException.ERROR_CODE_IO_BAD_HTTP_STATUS -> "Bad HTTP status"
        PlaybackException.ERROR_CODE_IO_FILE_NOT_FOUND -> "File not found"
        PlaybackException.ERROR_CODE_IO_NO_PERMISSION -> "No permission"
        PlaybackException.ERROR_CODE_IO_CLEARTEXT_NOT_PERMITTED -> "Cleartext not permitted"
        PlaybackException.ERROR_CODE_IO_READ_POSITION_OUT_OF_RANGE -> "Read position out of range"
        PlaybackException.ERROR_CODE_PARSING_CONTAINER_MALFORMED -> "Container malformed"
        PlaybackException.ERROR_CODE_PARSING_MANIFEST_MALFORMED -> "Manifest malformed"
        PlaybackException.ERROR_CODE_PARSING_CONTAINER_UNSUPPORTED -> "Container unsupported"
        PlaybackException.ERROR_CODE_PARSING_MANIFEST_UNSUPPORTED -> "Manifest unsupported"
        PlaybackException.ERROR_CODE_DECODER_INIT_FAILED -> "Decoder init failed"
        PlaybackException.ERROR_CODE_DECODER_QUERY_FAILED -> "Decoder query failed"
        PlaybackException.ERROR_CODE_DECODING_FAILED -> "Decoding failed"
        PlaybackException.ERROR_CODE_DECODING_FORMAT_EXCEEDS_CAPABILITIES -> "Format exceeds capabilities"
        PlaybackException.ERROR_CODE_DECODING_FORMAT_UNSUPPORTED -> "Format unsupported"
        PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED -> "Audio track init failed"
        PlaybackException.ERROR_CODE_AUDIO_TRACK_WRITE_FAILED -> "Audio track write failed"
        PlaybackException.ERROR_CODE_DRM_UNSPECIFIED -> "DRM error"
        PlaybackException.ERROR_CODE_DRM_SCHEME_UNSUPPORTED -> "DRM scheme unsupported"
        PlaybackException.ERROR_CODE_DRM_PROVISIONING_FAILED -> "DRM provisioning failed"
        PlaybackException.ERROR_CODE_DRM_LICENSE_ACQUISITION_FAILED -> "DRM license acquisition failed"
        PlaybackException.ERROR_CODE_DRM_DISALLOWED_OPERATION -> "DRM disallowed operation"
        PlaybackException.ERROR_CODE_DRM_SYSTEM_ERROR -> "DRM system error"
        PlaybackException.ERROR_CODE_DRM_DEVICE_REVOKED -> "DRM device revoked"
        PlaybackException.ERROR_CODE_DRM_LICENSE_EXPIRED -> "DRM license expired"
        else -> ""
    }
}
