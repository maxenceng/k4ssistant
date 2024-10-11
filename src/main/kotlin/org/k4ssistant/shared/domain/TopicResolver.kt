package org.k4ssistant.shared.domain

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.google.gson.reflect.TypeToken

class TopicResolver {
    companion object {
        fun <T> getTopic(): String =
            PropertyNamingStrategies.KebabCaseStrategy().translate(
                getClassName<T>()
            )

        private fun <T> getClassName(): String = object : TypeToken<T>() {}.javaClass.name.toString()
    }
}