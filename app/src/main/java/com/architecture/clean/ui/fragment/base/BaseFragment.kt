package com.architecture.clean.ui.fragment.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.architecture.clean.ui.utils.LoadingListener
import com.architecture.clean.ui.view_model.BaseViewModel
import dagger.android.support.DaggerFragment

abstract class BaseFragment<VM : BaseViewModel>  : DaggerFragment() {

    private val TAG = BaseFragment::class.java.simpleName

    internal abstract var layoutResourceId: Int
    private var mLoader: LoadingListener? = null
    abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResourceId, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            errorLiveData.observe(viewLifecycleOwner, Observer {
                Toast.makeText(context, "${it?.message}", Toast.LENGTH_LONG).show()
            })

            cancellationMsgLiveData.observe(viewLifecycleOwner, Observer {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            })

            isLoadingLiveData.observe(viewLifecycleOwner, Observer {
                showLoading(it)
                Log.d(TAG,"Loading observer is called")
            })
        }

    }

    open fun showLoading(show: Boolean) {
        mLoader?.showLoading(show)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.let {
            if (context is LoadingListener)
                mLoader = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mLoader = null
    }
}