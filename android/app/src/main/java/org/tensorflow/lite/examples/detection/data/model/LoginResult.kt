package org.tensorflow.lite.examples.detection.data.model

import org.tensorflow.lite.examples.detection.ui.login.LoggedInUserView

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
        val success: LoggedInUserView? = null,
        val error: Int? = null
)