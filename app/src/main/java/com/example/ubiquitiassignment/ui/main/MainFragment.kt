package com.example.ubiquitiassignment.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.example.ubiquitiassignment.R
import com.example.ubiquitiassignment.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val activityMainViewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val goodConditionAdapter by lazy { GoodConditionAdapter() }
    private val badConditionAdapter by lazy { VerticalListAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initView()
        initViewModel()
    }

    private fun initView() {
        binding.toolBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    parentFragmentManager.commit(allowStateLoss = true) {
                        add(R.id.container, SearchFragment.newInstance())
                        addToBackStack(null)
                    }
                    true
                }
                else -> false
            }
        }

        binding.swipeContainer.setOnRefreshListener {
            activityMainViewModel.fetchAirPollutions()
        }

        binding.horizontalRecyclerView.apply {
            setHasFixedSize(true)
            adapter = goodConditionAdapter
        }
        binding.verticalRecyclerView.apply {
            setHasFixedSize(true)
            adapter = badConditionAdapter
        }
    }

    private fun initViewModel() {
        activityMainViewModel.apply {
            goodAirPollutions.observe(viewLifecycleOwner) {
                it.result(
                    onLoading = { isLoading, _ ->
                        binding.swipeContainer.isRefreshing = isLoading
                    }, onSuccess = { list ->
                        Timber.d("goodAirPollutions: $list")
                        goodConditionAdapter.submitList(list)
                    }, onError = { _, errorMsg ->
                        Timber.e("goodAirPollutions: onError=$errorMsg")
                        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
                    }
                )
            }
            
            badPollutions.observe(viewLifecycleOwner) {
                badConditionAdapter.submitList(it)
            }

            fetchAirPollutions() 
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
