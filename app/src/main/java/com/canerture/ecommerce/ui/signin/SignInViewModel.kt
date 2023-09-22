package com.canerture.ecommerce.ui.signin

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canerture.ecommerce.R
import com.canerture.ecommerce.common.Resource
import com.canerture.ecommerce.data.repository.UserRepository
import com.canerture.ecommerce.infrastructure.StringResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val stringRes: StringResourceProvider
) : ViewModel() {

    private var _state = MutableLiveData<SignInState>()
    val state: LiveData<SignInState>
        get() = _state

    private fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _state.value = SignInState.Loading
            when (val result = userRepository.signIn(email, password)) {
                is Resource.Success -> {
                    _state.value = if (result.data) {
                        SignInState.GoToHome
                    } else {
                        SignInState.Error(Throwable(stringRes(R.string.something_went_wrong)))
                    }
                }

                is Resource.Error -> {
                    _state.value = SignInState.Error(result.throwable)
                }

                is Resource.Fail -> {

                }
            }
        }
    }

    fun checkInfo(email: String, password: String) {
        when {
            Patterns.EMAIL_ADDRESS.matcher(email).matches().not() -> {
                _state.value = SignInState.Error(Throwable(stringRes(R.string.invalid_mail)))
            }

            password.isEmpty() || password.length <= 6 -> {
                _state.value = SignInState.Error(Throwable(stringRes(R.string.invalid_password)))
            }

            else -> signIn(email, password)
        }
    }
}

sealed interface SignInState {
    object Loading : SignInState
    object GoToHome : SignInState
    data class Error(val throwable: Throwable) : SignInState
}