package com.org.androidhilt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class Scope : AppCompatActivity() {

    @Inject
    lateinit var someScopeClass: SomeScopeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scope)
        println(someScopeClass.doThing())
    }
}

@AndroidEntryPoint
class MYFragment : Fragment()
{
    @Inject
    lateinit var someScopeClass: SomeScopeClass

}

@ActivityScoped
class SomeScopeClass @Inject constructor() {
    fun doThing(): String {
        return "Look I did a Thing for scope"
    }
}

//Notes : If you are using scope function as @Singleton for SomeScopeClass then It will work compile time and does not
// give you any Compile time error

// If you are using scope function as @ActivityScoped for SomeScopeClass then It will work compile time and does not
// give you any Compile time error

// If you are using scope function as @FragmentScoped for SomeScopeClass then It will not work at compile time and gives
// error  i.e. Dagger/IncompatiblyScopedBindings
// so if you want to run this code we have to remove all injection code from Scope.class
//Because scoping function is working as per hierarchy based (Which is available on developer site in HILT)

