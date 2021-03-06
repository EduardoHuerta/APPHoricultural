package org.eveh.plantdetector.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
        val success: Boolean,
        val message: String,
        val userId: String = "",
        val displayName: String = ""
)