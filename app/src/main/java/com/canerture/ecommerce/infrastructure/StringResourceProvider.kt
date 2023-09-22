package com.canerture.ecommerce.infrastructure

import android.content.Context

class StringResourceProvider(private val context: Context) {
    operator fun invoke(resourceId: Int): String {
        return context.getString(resourceId)
    }
}