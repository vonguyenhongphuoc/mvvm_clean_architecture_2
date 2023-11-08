package com.devhp.newsapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.devhp.newsapp.R
import com.devhp.newsapp.databinding.FragmentSavedBinding
import com.devhp.newsapp.presentation.MainActivity
import com.devhp.newsapp.presentation.adapter.NewsAdapter
import com.devhp.newsapp.presentation.viewmodel.NewsViewModel

class SavedFragment : Fragment() {
    private lateinit var binding: FragmentSavedBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply { putSerializable("selected_article", it) }
            findNavController().navigate(R.id.action_savedFragment_to_infoFragment, bundle)
        }
        initRecyclerView()
        viewModel.getSavedNews().observe(viewLifecycleOwner) {
            newsAdapter.differ.submitList(it)
        }
    }

    private fun initRecyclerView() {
        binding.rvSaved.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


}