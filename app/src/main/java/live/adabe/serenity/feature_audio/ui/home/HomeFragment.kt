package live.adabe.serenity.feature_audio.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.serenity.databinding.HomeFragmentBinding
import live.adabe.serenity.feature_audio.navigation.INavigationService
import live.adabe.serenity.feature_audio.ui.adapters.CategoryAdapter
import live.adabe.serenity.feature_audio.ui.adapters.ViewPagerAdapter
import live.adabe.serenity.feature_audio.ui.albums.AlbumsFragment
import live.adabe.serenity.feature_audio.ui.songs.SongsFragment
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {


    private lateinit var binding: HomeFragmentBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
    }



    private fun initViewPager(){
        viewPagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        viewPagerAdapter.run {
            addFragment(SongsFragment(), "Songs")
            addFragment(AlbumsFragment(), "Albums")
        }
        binding.run {
            homeViewPager.adapter = viewPagerAdapter
            tabLayout.setupWithViewPager(homeViewPager)
        }
    }

}