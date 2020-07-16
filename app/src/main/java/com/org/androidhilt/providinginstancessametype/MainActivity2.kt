package com.org.androidhilt.providinginstancessametype

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.org.androidhilt.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity2 : AppCompatActivity() {

    @Inject
    lateinit var someClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println(someClass.doAThing1())
        println(someClass.doAThing2())
    }
}

class SomeClass
@Inject
constructor(
    @Imp1 private val someInterfaceImpl1: SomeInterface,
    @Imp2 private val someInterfaceImpl2: SomeInterface

) {
    fun doAThing1(): String {
        return "Look I got: ${someInterfaceImpl1.getAThing()}"
    }

    fun doAThing2(): String {
        return "Look I got: ${someInterfaceImpl2.getAThing()}"
    }
}

class SomeInterfaceImpl1
@Inject constructor() : SomeInterface {
    override fun getAThing(): String {
        return "A Thing1"
    }
}

class SomeInterfaceImpl2
@Inject constructor() : SomeInterface {
    override fun getAThing(): String {
        return "A Thing2"
    }
}

interface SomeInterface {
    fun getAThing(): String
}

@InstallIn(ApplicationComponent::class)
@Module
class MyModule {

    @Imp1
    @Singleton
    @Provides
    fun provideSomeInterface1(): SomeInterface {
        return SomeInterfaceImpl1()
    }

    @Imp2
    @Singleton
    @Provides
    fun provideSomeInterface2(): SomeInterface {
        return SomeInterfaceImpl2()
    }
}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Imp1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Imp2