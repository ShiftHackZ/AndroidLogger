package com.shz.logger.kit.presentation.viewer

import java.io.Serializable

internal data class LogViewerPayload(
    val className: String,
    val showOnlyCurrentSession: Boolean
) : Serializable