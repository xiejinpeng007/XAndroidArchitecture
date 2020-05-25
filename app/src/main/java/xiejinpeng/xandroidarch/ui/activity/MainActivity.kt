package xiejinpeng.xandroidarch.ui.activity

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import xiejinpeng.xandroidarch.R
import xiejinpeng.xandroidarch.databinding.ActivityMainBinding
import xiejinpeng.xandroidarch.ui.base.BaseActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import xiejinpeng.xandroidarch.ui.SharedViewModel
import xiejinpeng.xandroidarch.util.setupWithNavController

class MainActivity : BaseActivity(), KodeinAware {
    //retrieving the Application level kodein by closestKodein func
    //of course you can define the Activity level kodein extend that
    override val kodein by closestKodein()
    private var currentNavController: LiveData<NavController>? = null
    private lateinit var binding: ActivityMainBinding
    //shared data within activity
    //can this replace DI ?
    val viewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState

//        onBackPressedDispatcher.addCallback(this) {
        //handle onBackPress here instead of onBackPress()
//            super.onBackPressed()
//        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = binding.bottomNavView.setupWithNavController(
            navGraphIds = listOf(
                R.navigation.nav_graph_home,
                R.navigation.nav_graph_order,
                R.navigation.nav_graph_mine
            ),
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller

//        binding.bottomNavView.getOrCreateBadge(R.id.nav_graph_order).number = 1
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}
