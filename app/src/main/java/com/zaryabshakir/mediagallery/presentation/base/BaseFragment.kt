package com.zaryabshakir.mediagallery.presentation.base

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment<V : ViewModel, T : ViewDataBinding> : Fragment() {
    private lateinit var binding: T
}