package com.example.playlistmaker.media.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentMediaBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MediaFragment: Fragment() {
    private var _binding: FragmentMediaBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for FragmentMediaBinding must not be null")

    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMediaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager: ViewPager2 = binding.mediaViewPager
        val tabLayout: TabLayout = binding.mediaTabLayout

        val pagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = pagerAdapter

        tabMediator = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = getString(R.string.media_tab1)
                1 -> tab.text = resources.getString(R.string.media_tab2)
            }
        }

        tabMediator.attach()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabMediator.detach()
    }

}