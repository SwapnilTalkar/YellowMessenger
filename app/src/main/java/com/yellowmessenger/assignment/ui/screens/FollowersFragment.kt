package com.yellowmessenger.assignment.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yellowmessenger.assignment.R
import com.yellowmessenger.assignment.dataSource.User
import com.yellowmessenger.assignment.databinding.FollowersFragmentBinding
import com.yellowmessenger.assignment.di.component.DaggerApplicationComponent
import com.yellowmessenger.assignment.ui.callbacks.UserActionCallback
import com.yellowmessenger.assignment.ui.recyclerview.BaseDataBindingRecyclerviewAdapter
import com.yellowmessenger.assignment.utils.ConnectivityListener
import com.yellowmessenger.assignment.utils.showSnackBar
import com.yellowmessenger.assignment.viewmodel.MainSharedViewModel
import com.yellowmessenger.assignment.viewmodel.MainSharedViewModelFactory
import javax.inject.Inject

class FollowersFragment: Fragment(), UserActionCallback {

    @Inject
    lateinit var connectivityListener: ConnectivityListener

    @Inject
    lateinit var mainSharedViewModelFactory: MainSharedViewModelFactory

    private val pageViewModel by viewModels<MainSharedViewModel>(
        ownerProducer = { this },
        factoryProducer = { mainSharedViewModelFactory }
    )

    private lateinit var binding: FollowersFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val component = DaggerApplicationComponent.factory().create(requireContext())
        component.getActivityComponent().inject(this)

        val followersUrl = arguments?.getString(MainSharedViewModel.FollowerUrl)
        when(followersUrl.isNullOrEmpty()) {
            true -> showErrorMessage(getString(R.string.invalid_url))
            false -> makeApiCall(followersUrl)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FollowersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            viewModel = pageViewModel
            callback = this@FollowersFragment
            lifecycleOwner = viewLifecycleOwner
        }

        binding.recyclerview.adapter = BaseDataBindingRecyclerviewAdapter(
            bindingLayoutResId = R.layout.card_view_design,
            callback = this
        )

        pageViewModel.action.observe(viewLifecycleOwner){
            when(it) {
                is MainSharedViewModel.Action.ShowError -> showErrorMessage(
                    message = getString(it.errorResourceId)
                )
            }
        }
    }

    private fun makeApiCall(followersUrl: String) {
        when(connectivityListener.isConnected()) {
            true -> pageViewModel.makeSecondaryApiCall(followersUrl)
            false -> showErrorMessage(getString(R.string.connectivity_error))
        }
    }

    private fun showErrorMessage(message: String) {
        showSnackBar(binding.root, message)
    }

    override fun onSearched() {
        Log.d("TAG", "No Action to perform")
    }

    override fun onUserSelected(user: User) {
        Log.d("TAG", "No Action to perform")
    }

}