package com.shz.logger.kit.presentation.filter

import com.shz.logger.Logger

data class LogStats(
    val middlewareCount: Int = Logger.getMiddlewaresCount(),
    val middlewareNames: List<String> = Logger.getMiddlewaresNames()
)