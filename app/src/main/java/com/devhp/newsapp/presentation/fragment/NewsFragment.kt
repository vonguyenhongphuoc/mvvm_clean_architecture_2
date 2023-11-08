package com.devhp.newsapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devhp.newsapp.R
import com.devhp.newsapp.data.util.Resource
import com.devhp.newsapp.databinding.FragmentNewsBinding
import com.devhp.newsapp.presentation.MainActivity
import com.devhp.newsapp.presentation.adapter.NewsAdapter
import com.devhp.newsapp.presentation.util.AppLog
import com.devhp.newsapp.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private var country = "us"
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            findNavController().navigate(R.id.action_newsFragment_to_infoFragment, bundle)
        }
        initRecyclerView()
        viewNewsList()
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        setSearchView()
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (page > 1) {
                page--
                viewModel.getNewsHeadLines(country, page)
                isScrolling = false
            } else {
                this.isEnabled = false
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        AppLog.logD("NewsFragment Destroy View")
    }

    override fun onDestroy() {
        super.onDestroy()
        AppLog.logD("NewsFragment Destroy View")
    }

    override fun onResume() {
        super.onResume()
        callback.isEnabled = true
    }

    private fun setSearchView() {
        // Truyền trực tiếp object expression vào hàm setOnQueryTextListener
        binding.searchNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                // Xử lý sự kiện khi người dùng nhấn nút submit
                viewModel.searchNews("us", p0.toString(), page)
                viewSearchedNews()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                // Xử lý sự kiện khi người dùng thay đổi nội dung tìm kiếm
                MainScope().launch {
                    delay(2000)
                    viewModel.searchNews("us", p0.toString(), page)
                    viewSearchedNews()
                }

                return false
            }
        })

        binding.searchNews.setOnCloseListener {
            initRecyclerView()
            viewNewsList()
            false
        }

    }

    private fun viewSearchedNews() {
        if (view != null) {
            viewModel.searchedNews.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Loading -> {
                        showProgressBar()
                    }

                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let {
                            newsAdapter.differ.submitList(it.articles.toList())
                            AppLog.logD(it.totalResults.toString())
                            pages = if (it.totalResults % 20 == 0) {
                                it.totalResults / 20
                            } else {
                                it.totalResults / 20 + 1
                            }
                            AppLog.logD("Pages: $pages - Page: $page")
                            isLastPage = page == pages

                        }
                    }

                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let {
                            Toast.makeText(activity, "An error occurred: $it", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }

    }


    private fun viewNewsList() {
        viewModel.getNewsHeadLines(country, page)
        viewModel.newsHeadLines.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                }

                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        AppLog.logD(it.totalResults.toString())
                        pages = if (it.totalResults % 20 == 0) {
                            it.totalResults / 20
                        } else {
                            it.totalResults / 20 + 1
                        }
                        AppLog.logD("Pages: $pages - Page: $page")
                        isLastPage = page == pages

                    }

                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred: $it", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }


    private fun initRecyclerView() {
        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@NewsFragment.onScrollListener)

        }
    }

    private fun showProgressBar() {
        isLoading = true
        binding.progressbar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false
        binding.progressbar.visibility = View.INVISIBLE
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = binding.rvNews.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition + visibleItems >= sizeOfTheCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling
            if (shouldPaginate) {
                page++
                viewModel.getNewsHeadLines(country, page)
                isScrolling = false
            }
        }

    }

}
