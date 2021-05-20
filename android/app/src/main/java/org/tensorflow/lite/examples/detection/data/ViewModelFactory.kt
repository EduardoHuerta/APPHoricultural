package org.tensorflow.lite.examples.detection.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.tensorflow.lite.examples.detection.ui.login.LoginViewModel
import org.tensorflow.lite.examples.detection.ui.main_menu.MenuViewModel


/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class ViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                    repository = Repository(
                            dataSource = DataSource()
                    )
            ) as T
        } else if (modelClass.isAssignableFrom(MenuViewModel::class.java)) {
            return MenuViewModel(
                    repository = Repository(
                            dataSource = DataSource()
                    )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}