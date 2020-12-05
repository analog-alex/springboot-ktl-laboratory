package com.analog.alex.security.utils

import java.util.UUID

fun uuid() = UUID.randomUUID().toString().replace("-", "")