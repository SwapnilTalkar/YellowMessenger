package com.yellowmessenger.assignment.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yellowmessenger.assignment.R
import com.yellowmessenger.assignment.dataSource.User
import com.yellowmessenger.assignment.databinding.UserFragmentBinding
import com.yellowmessenger.assignment.di.component.DaggerApplicationComponent
import com.yellowmessenger.assignment.ui.callbacks.UserActionCallback
import com.yellowmessenger.assignment.ui.recyclerview.BaseDataBindingRecyclerviewAdapter
import com.yellowmessenger.assignment.utils.ConnectivityListener
import com.yellowmessenger.assignment.utils.hideKeyboard
import com.yellowmessenger.assignment.utils.showSnackBar
import com.yellowmessenger.assignment.viewmodel.MainSharedViewModel
import com.yellowmessenger.assignment.viewmodel.MainSharedViewModelFactory
import javax.inject.Inject

class UserFragment: Fragment(), UserActionCallback {

    @Inject
    lateinit var connectivityListener: ConnectivityListener

    @Inject
    lateinit var mainSharedViewModelFactory: MainSharedViewModelFactory

    private val pageViewModel by viewModels<MainSharedViewModel>(
        ownerProducer = { this },
        factoryProducer = { mainSharedViewModelFactory }
    )

    private lateinit var binding: UserFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val component = DaggerApplicationComponent.factory().create(requireContext())
        component.getActivityComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UserFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            viewModel = pageViewModel
            callback = this@UserFragment
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

    private fun showErrorMessage(message: String) {
        showSnackBar(binding.root, message)
    }

    private fun hideKeyBoard() {
        requireContext().hideKeyboard(binding.root)
    }

    // region callback
    override fun onSearched() {

        hideKeyBoard()

        // Return early if no search data is provided
        val searchText = binding.searchSource.text.toString()
        if (searchText.isEmpty()) {
            showErrorMessage(
                message = getString(R.string.search_error)
            )
            return
        }

        // Check for connectivity & perform appropriate action
        when (connectivityListener.isConnected()) {
            true -> pageViewModel.makePrimaryApiCall(
                searchedText = searchText
            )
            false -> showErrorMessage(
                message = getString(R.string.connectivity_error)
            )
        }
    }

    override fun onUserSelected(user: User) {

        val followersFragment = FollowersFragment().apply {
            arguments = Bundle().apply {
                putString(MainSharedViewModel.FollowerUrl, user.followersUrl.orEmpty())
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, followersFragment)
            .addToBackStack("UserFragment")
            .commit()

    }
    // endregion callback

}