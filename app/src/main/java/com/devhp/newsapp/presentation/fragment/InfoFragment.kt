package com.devhp.newsapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.devhp.newsapp.databinding.FragmentInfoBinding
import com.devhp.newsapp.presentation.MainActivity
import com.devhp.newsapp.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class InfoFragment : Fragment() {


    private lateinit var binding: FragmentInfoBinding
    private lateinit var viewModel: NewsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: InfoFragmentArgs by navArgs()
        val article = args.selectedArticle
        viewModel = (activity as MainActivity).viewModel
        binding.wvInfo.apply {
            webViewClient = WebViewClient()
            if (article != null) {
                if (article.url != "") {
                    loadUrl(article.url)
                }
            }
        }

        binding.fabSave.setOnClickListener {
            if (article != null) {
                viewModel.saveArticle(article)
            }
            Snackbar.make(view, "Saved Successfully", Snackbar.LENGTH_LONG).show()
        }
    }


}