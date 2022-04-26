package live.adabe.serenity

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.serenity.databinding.ActivityMainBinding
import live.adabe.serenity.feature_audio.navigation.INavigationService
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var navigationService: INavigationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getPermissions()
        navigationService.attachToActivity(this)
        navigationService.openHomeScreen()
    }

    override fun onDestroy() {
        super.onDestroy()
        navigationService.detachFromActivity()
    }




    private fun getPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 100
            )
        }
    }

}