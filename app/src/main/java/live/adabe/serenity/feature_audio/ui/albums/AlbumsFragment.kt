package live.adabe.serenity.feature_audio.ui.albums

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.serenity.R
import live.adabe.serenity.databinding.FragmentAlbumsBinding
import live.adabe.serenity.feature_audio.ui.home.HomeViewModel


@AndroidEntryPoint
class AlbumsFragment : Fragment() {

    private lateinit var binding: FragmentAlbumsBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAlbumsBinding.inflate(inflater, container, false)

        return binding.root
    }


}