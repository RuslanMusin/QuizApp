package com.example.quiz.repository.curator

import com.example.quiz.api.services.UserService
import com.example.quiz.model.user.Lector
import com.example.quiz.model.user.User
import com.example.quiz.utils.RxUtils
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() :
    UserRepository {


    override lateinit var currentCurator: User

    @Inject lateinit var apiService: UserService

    override fun findById(id: String): Single<Result<User>> {
        return apiService.findById(id).compose(RxUtils.asyncSingle())
    }

    override fun findAll(): Single<Result<List<User>>> {
        return apiService.findAll().compose(RxUtils.asyncSingle())
    }

    override fun update(id: String, curator: User): Single<Result<User>> {
        return apiService.update(id, curator).compose(RxUtils.asyncSingle())
    }

    override fun createUser(user: User): Single<Result<Lector>> {
        return apiService.create(user).compose(RxUtils.asyncSingle())
    }


}