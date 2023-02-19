package com.example.ubiquitiassignment.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ubiquitiassignment.R
import com.example.ubiquitiassignment.databinding.FragmentSearchBinding
import com.google.android.material.transition.MaterialSharedAxis

class SearchFragment  : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val activityMainViewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchAdapter by lazy { VerticalListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        initView()

        return binding.root
    }

    private fun initView() {
        binding.apply {
            back.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

            searchEditText.doAfterTextChanged {
                val input = it?.toString().orEmpty()
                val result = activityMainViewModel.airPollutionList.filter { airPollutionVO ->
                    airPollutionVO.siteName.contains(input)
                }

                when {
                    input.isEmpty() -> {
                        binding.hint.apply {
                            isVisible = true
                            text = getString(R.string.search_page_initial_text)
                        }
                    }
                    result.isEmpty() -> {
                        binding.hint.apply {
                            isVisible = true
                            text = getString(R.string.search_page_empty_result, input)
                        }
                    }
                    else -> {
                        binding.hint.isVisible = false
                        searchAdapter.submitList(result)
                    }
                }
            }

            searchRecyclerView.apply {
                setHasFixedSize(true)
                adapter = searchAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
