package com.shz.logger.kit.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Base class of any LoggerKit SDK Activity.
 * It's strongly recommended not to extend this class in your client-side app module.
 *
 * Implements viewBinding functionality
 */
abstract class BaseLoggerKitActivity<V : ViewBinding> : AppCompatActivity() {

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