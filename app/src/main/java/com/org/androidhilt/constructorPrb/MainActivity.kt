package com.org.androidhilt.constructorPrb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.org.androidhilt.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var someClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println(someClass.doAThing())
    }
}

class SomeClass
@Inject
constructor(
    private val someInterfaceImpl: SomeInterface,
    private val gson: Gson
) {
    fun doAThing(): String {
        return "Look I got: ${someInterfaceImpl.getAThing()}"
    }
}

class SomeInterfaceImpl
@Inject constructor(
    private val someDependency : String
) : SomeInterface {
    override fun getAThing(): String {
        return "A Thing $someDependency"
    }
}

interface SomeInterface {
    fun getAThing(): String
}

/*
// This is used for getting the dependency of  SomeInterface
// but we use for GSON It will not work
@InstallIn(ActivityComponent::class)
@Module
abstract class MyModule {

    @ActivityScoped
    @Binds
    abstract fun bindSomeDependency(someInterfaceImpl: SomeInterfaceImpl): SomeInterface

    @ActivityScoped
    @Binds
    abstract fun bindGson(gson: Gson): Gson
}*/

@InstallIn(ApplicationComponent::class)
@Module
class MyModule {

    @Singleton
    @Provides
    fun provideSomeString() : String{
        return "Some String"
    }

    @Singleton
    @Provides
    fun provideSomeInterface(someString : String): SomeInterface {
        return  SomeInterfaceImpl(someString)
    }

    @Singleton
    @Provides
    fun provideGson() : Gson{
        return Gson()
    }
}