package com.testforme.newregistr

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.testforme.newregistr.databinding.ActivityMainBinding
import com.testforme.newregistr.stuff.application.SharedPrefHelper

class MainActivity : AppCompatActivity() {
    companion object {


        const val RC_SIGN_IN=123

//        val TAGMainCatalog = CatalogFragment::class.java.canonicalName!!
//        val TAGMainTrainingMaps = TrainingMapsFragment::class.java.canonicalName!!
//        val TAGMainAllCategoryOfTests = AllCategoryOfTestsFragment::class.java.canonicalName!!
//        val TAGMainTrafficRulesTests = TrafficRulesTestsFragment::class.java.canonicalName!!
//        val TAGMainRoadSigns = RoadSignsFragment::class.java.canonicalName!!
//        val TAGMainExams = ExamsFragment::class.java.canonicalName!!
//
//        val TAGMainStatsTickets = StatTicketsFragment::class.java.canonicalName!!
//        val TAGMainFavouritesTickets = FavouritesTicketsFragment::class.java.canonicalName!!
//        val TAGMainFavouritesSigns = FavouritesSignsFragment::class.java.canonicalName!!
//        val TAGFavouritesTraining = FavouritesTrainingFragment::class.java.canonicalName!!
//        val TAGMainStatsExams = StatExamsFragment::class.java.canonicalName!!
//        val TAGMainStatsSigns = StatSignsFragment::class.java.canonicalName!!
//        val TAGMainStatsTraining = StatTrainingFragment::class.java.canonicalName!!
//        val TAGMainProfile = ProfileFragment::class.java.canonicalName!!
//        val TAGMainActivity = MainActivity::class.java.canonicalName

        const val COMMAND_MAINACTIVITY = "Comand_MainActivity"
        const val ACTION_UPDATE_FRAGMENT = "ACTION_UPDATE_FRAGMENT"
        const val ACTION_UPDATE_ALL_FRAGMENTS = "ACTION_UPDATE_ALL_FRAGMENTS"
        const val ACTION_SHOW_STAT = "ACTION_SHOW_STAT"
        const val STAT_TESTS = 1
        const val STAT_EXAMS = 2
        const val STAT_SIGNS = 3
        const val STAT_TRAINING = 4

        const val SUBCATALOG_TESTS = 0
        const val SUBCATALOG_SIGNS = 1
        const val SUBCATALOG_TRAININGMAPS = 2

        const val MAIN_SITE_URL = "https://netoct.ru"

        fun getColorWrapper(context: Context, id: Int): Int {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.getColor(id)
            } else {
                context.resources.getColor(id)
            }
        }
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SharedPrefHelper.getInstance().init(applicationContext)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_profile, R.id.nav_authorization, R.id.nav_registration
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}