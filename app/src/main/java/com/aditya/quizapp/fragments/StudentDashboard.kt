package com.aditya.quizapp.fragments


import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.quizapp.R
import com.aditya.quizapp.adapters.StudentDashboardAdapter
import com.aditya.quizapp.api.UserApi
import com.aditya.quizapp.databinding.FragmentStudentDashboardBinding
import com.aditya.quizapp.repository.UserRepository
import com.aditya.quizapp.viewModels.AuthViewModel
import com.example.quizapplication.retrofit.RetrofitHelper
import com.example.quizapplication.viewModels.AuthViewModelFactory
import com.google.android.material.snackbar.Snackbar


class StudentDashboard : Fragment(), OnItemClickListener {
    private lateinit var binding: FragmentStudentDashboardBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var accessTokens: String
    private lateinit var refreshTokens: String
    private lateinit var sharePref: SharedPreferences
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentStudentDashboardBinding.inflate(layoutInflater)
        val interfaceApi = RetrofitHelper.getInstance().create(UserApi::class.java)
        val repository = UserRepository(interfaceApi)
        setUpNavigationDrawer()
        viewModel =
            ViewModelProvider(this, AuthViewModelFactory(repository))[AuthViewModel::class.java]
        return binding.root
    }

    private fun setUpNavigationDrawer() {
        // setting up toggle btn and
        toggle = ActionBarDrawerToggle(
            activity,
            binding.drawerLayout,
            binding.tbStudentDashboard.toolbar,
            R.string.open,
            R.string.close
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.tbStudentDashboard.toolbar.title = "Quiz App"

        //handling click for content inside drawer layout
        binding.navViewStudentDash.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.userProfile -> {
                    Toast.makeText(requireActivity(), "Profile", Toast.LENGTH_SHORT).show()
                }
                R.id.userLogout -> {
                    viewModel.logoutUser("Bearer $accessTokens", refreshTokens)
                }
            }
            true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //starting shimmer effect
        binding.shimmerViewContainerDashboard.startShimmer()
        binding.rvStudentDashBoard.layoutManager = LinearLayoutManager(requireActivity())

        sharePref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        accessTokens = sharePref.getString(getString(R.string.access_token), null).toString()
        refreshTokens = sharePref.getString(getString(R.string.refresh_token), null).toString()
        try {
            viewModel.getStudentDashBoard("Bearer $accessTokens")
            setUpTeacherDashBoardObserver()
        } catch (e: Exception) {
            Snackbar.make(binding.root, e.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
        setUpLogoutObserver()
    }

    private fun setUpLogoutObserver() {
        viewModel.userLogoutResponseLiveData.observe(requireActivity()) {
            if (it == null) {
                findNavController().navigate(R.id.action_studentDashboard_to_splashFragment)
                sharePref.edit().remove(getString(R.string.refresh_token))
                    .apply()
                sharePref.edit().remove(getString(R.string.access_token)).apply()
                sharePref.edit().remove(getString(R.string.person_type)).apply()
                Toast.makeText(requireActivity(), "Logging Out", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpTeacherDashBoardObserver() {
        viewModel.responseStudentDashboardLiveData.observe(requireActivity()) {
            if (!it?.data.isNullOrEmpty()) {
                val adapter = StudentDashboardAdapter(it!!.data, onItemClick = { position ->
                    Toast.makeText(requireActivity(), "$position Clicked", Toast.LENGTH_SHORT)
                        .show()
                })
                binding.rvStudentDashBoard.adapter = adapter
                binding.shimmerViewContainerDashboard.stopShimmer()
                binding.shimmerViewContainerDashboard.visibility = View.GONE
                binding.rvStudentDashBoard.visibility = View.VISIBLE
            } else if (it != null) {
                binding.shimmerViewContainerDashboard.stopShimmer()
                binding.shimmerViewContainerDashboard.visibility = View.GONE
                Snackbar.make(binding.root, it.Message, Snackbar.LENGTH_SHORT).show()
            } else {
                binding.shimmerViewContainerDashboard.stopShimmer()
                binding.shimmerViewContainerDashboard.visibility = View.GONE
                Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun openCloseNavigationDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            binding.drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.END)
        }
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//        openCloseNavigationDrawer()
//        when (p1) {
//            toggle -> openCloseNavigationDrawer()
//        }
    }
}