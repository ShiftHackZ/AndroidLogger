package com.shz.logger.sample.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<V : ViewBinding> : AppCompatActivity() {

    /**
     * Contains current viewBinding instance
     */
    protected lateinit var binding: V

    /**
     * Custom viewBinding layout inflater invocation unit
     */
    abstract val inflater: (LayoutInflater) -> V

    /**
     * Default lifecycle method, initializes binding variable
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflater.invoke(layoutInflater)
        setContentView(binding.root)
    }
}