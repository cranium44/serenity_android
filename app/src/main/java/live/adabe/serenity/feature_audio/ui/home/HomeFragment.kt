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
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private lateinit var rvAdapter: CategoryAdapter

    @Inject
    lateinit var navigationService: INavigationService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        initViewModel()
    }

    private fun initViewModel(){
        viewModel.getMusicByName()
        viewModel.musicListByName.observe(viewLifecycleOwner,{ list ->
            Timber.d(list.joinToString { it.toString() })
            rvAdapter = CategoryAdapter(list, navigationService)
            binding.homeRv.run {
                adapter = rvAdapter
                layoutManager = LinearLayoutManager(requireContext())
                rvAdapter.notifyDataSetChanged()
            }
        })

    }

}