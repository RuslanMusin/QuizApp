package com.example.quiz.presentation.ui.test.add_test.question

import android.content.SharedPreferences
import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.data.repository.test.TestRepository
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.rx.transformer.PresentationSingleTransformer
import com.example.quiz.presentation.ui.Screens
import com.example.quiz.presentation.util.Const.TESTS
import com.example.quiz.presentation.util.Const.gson
import com.example.quiz.presentation.util.exceptionprocessor.ExceptionProcessor
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import com.google.gson.reflect.TypeToken



@InjectViewState
class AddQuestionTestPresenter @Inject constructor() : BasePresenter<AddQuestionTestView>() {

    @Inject
    lateinit var router: Router
    @Inject
    lateinit var testRepository: TestRepository
    @Inject
    lateinit var exceptionProcessor: ExceptionProcessor
    @Inject
    lateinit var prefs: SharedPreferences

    fun createTest(test: Test) {
        /*testRepository
            .createTest(test)
            .compose(PresentationSingleTransformer())
            .doOnSubscribe { viewState.showProgressDialog() }
            .doAfterTerminate { viewState.hideProgressDialog() }
            .subscribe({
                viewState.navigateToTest()
            }, {
                viewState.showSnackBar(exceptionProcessor.processException(it))
            }).disposeWhenDestroy()*/
        val type = object : TypeToken<List<Test>>(){}.type

        val listStr = prefs.getString(TESTS, "")
        var list: MutableList<Test> = ArrayList()
        if(!listStr.equals("")) {
            list = gson.fromJson(listStr, type)
        }
        val editor = prefs.edit()
        var flag = true
        for(item in list) {
            if(item.name?.equals(test.name)!!) {
                flag = true
            }
        }
        if(flag) {
            list.add(test)
        }
        editor.putString(TESTS, gson.toJson(list))
        editor.apply()
        viewState.navigateToTest()
    }

    fun onTestListClick() {
        router.navigateTo(Screens.TestListScreen())
    }

    fun onBeforeQuestionClick(args: Bundle) {
//        router.navigateTo(Screens.AddQuestionScreen(args))
        router.exit()
    }

    fun onNextQuestionClick(args: Bundle) {
        router.navigateTo(Screens.AddQuestionScreen(args))
    }

    fun onTestClick(args: Bundle) {
        router.navigateTo(Screens.TestScreen(args))
    }

}