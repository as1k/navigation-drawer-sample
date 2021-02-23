package com.as1k.navigationdrawersample

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var containerMainPageFragments: FrameLayout
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var smoothDrawerToggle: SmoothActionBarDrawerToggle
    private lateinit var navView: NavigationView
    private lateinit var rvAppMenuItems: RecyclerView

    private var appMenuItemsAdapter: AppMenuItemsAdapter? = null
    private var appMenuItems: ArrayList<AppMenuItem> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
        initAppMenuItems()
        initHamburgerMenu()
    }

    private fun initHamburgerMenu() {
        appMenuItems.addAll(
            arrayListOf(
                AppMenuItem("profile", 0),
                AppMenuItem("fragment 1", 1),
                AppMenuItem("activity 2", 2)
            )
        )
        appMenuItemsAdapter?.notifyDataSetChanged()
        navView.bringToFront()
    }

    private fun bindViews() {
        drawerLayout = findViewById(R.id.appDrawerLayout)
        navView = findViewById(R.id.appNavView)
        containerMainPageFragments = findViewById(R.id.containerMainPageFragments)
        rvAppMenuItems = findViewById(R.id.rvAppMenuItems)

        smoothDrawerToggle = SmoothActionBarDrawerToggle(this, drawerLayout, 0, 1)
        drawerLayout.addDrawerListener(smoothDrawerToggle)
    }

    private fun initAppMenuItems() {
        val menuItemsManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvAppMenuItems.layoutManager = menuItemsManager
        appMenuItemsAdapter = AppMenuItemsAdapter(appMenuItems, menuItemClickListener)
        rvAppMenuItems.adapter = appMenuItemsAdapter
    }

    private val menuItemClickListener = object : RecyclerViewItemClick {
        override fun onItemClick(position: Int) {
            hideMenu()
            when (appMenuItems[position].type) {
                0 -> initFragment(R.id.containerMainPageFragments, FragmentA())
                1 -> initFragment(R.id.containerMainPageFragments, FragmentB())
            }
        }
    }

    private fun initFragment(containerId: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }

    fun showMenu() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    fun hideMenu() {
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    inner class SmoothActionBarDrawerToggle(
        activity: Activity?,
        drawerLayout: DrawerLayout?,
        openDrawerContentDescRes: Int,
        closeDrawerContentDescRes: Int
    ) :
        ActionBarDrawerToggle(
            activity,
            drawerLayout,
            openDrawerContentDescRes,
            closeDrawerContentDescRes
        ) {
        private var runnable: Runnable? = null

        override fun onDrawerOpened(drawerView: View) {
            super.onDrawerOpened(drawerView)
            this@MainActivity.invalidateOptionsMenu()
        }

        override fun onDrawerClosed(view: View) {
            super.onDrawerClosed(view)
            this@MainActivity.invalidateOptionsMenu()
        }

        override fun onDrawerStateChanged(newState: Int) {
            super.onDrawerStateChanged(newState)
            if (runnable != null && newState == DrawerLayout.STATE_IDLE) {
                runnable!!.run()
                runnable = null
            }
        }

        fun runWhenIdle(runnable: Runnable?) {
            this.runnable = runnable
        }
    }
}
