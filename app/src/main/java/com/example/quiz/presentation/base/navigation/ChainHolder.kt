package com.example.quiz.presentation.base.navigation

import android.support.v4.app.Fragment
import java.lang.ref.WeakReference

interface ChainHolder {
    val chain: MutableList<WeakReference<Fragment>>
}
