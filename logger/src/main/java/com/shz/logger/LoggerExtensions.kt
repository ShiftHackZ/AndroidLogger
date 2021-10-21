package com.shz.logger

import kotlin.reflect.KClass

val Class<*>.TAG: String
    get() = this.simpleName

val KClass<*>.TAG: String
    get() = this.simpleName.toString()