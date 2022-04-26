package live.adabe.serenity.feature_audio.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

class ViewPagerAdapter(
    private val fragmentManager: FragmentManager,

) : FragmentPagerAdapter(fragmentManager) {

    private var fragmentsList: ArrayList<Fragment> = arrayListOf()
    private var titles: ArrayList<String> = arrayListOf()

    fun addFragment(fragment: Fragment, title: String){
        fragmentsList.add(fragment)
        titles.add(title)
    }

    override fun getCount(): Int {
        return fragmentsList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentsList[position]
    }
}