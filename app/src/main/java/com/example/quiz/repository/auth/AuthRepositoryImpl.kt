package com.example.quiz.repository.auth

import com.example.quiz.api.services.AuthService
import com.example.quiz.api.services.UserService
import com.example.quiz.model.auth.LoginResult
import com.example.quiz.model.user.User
import com.example.quiz.utils.RxUtils
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    @Inject lateinit var authService: AuthService
    @Inject lateinit var userService: UserService

    override fun login(user: User): Single<Result<LoginResult>> {
        return authService
            .login(user)
            .compose(RxUtils.asyncSingle())
    }

    override fun logout(): Single<Result<Void>> {
        return userService
            .logout()
            .compose(RxUtils.asyncSingle())
    }
}
