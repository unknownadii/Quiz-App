package com.aditya.quizapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.aditya.quizapp.R
import com.aditya.quizapp.adapters.TeacherDashboardAdapter
import com.aditya.quizapp.api.UserApi
import com.aditya.quizapp.databinding.FragmentTeacherDashboardBinding
import com.aditya.quizapp.models.addSubjectTeacher.request.TeacherAddSubjectDataModel
import com.aditya.quizapp.repository.UserRepository
import com.example.quizapplication.retrofit.RetrofitHelper
import com.aditya.quizapp.viewModels.AuthViewModel
import com.example.quizapplication.viewModels.AuthViewModelFactory
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar

class TeacherDashboardFragment : Fragment() {

    private lateinit var binding: FragmentTeacherDashboardBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var accessTokens: String
    private lateinit var bottomDialog: BottomSheetDialog
    private val subjectData: ArrayList<String> = ArrayList()
    private lateinit var sharePref: SharedPreferences
    private var subjectNameToAdd = ""
    private lateinit var shimmerLayout: ShimmerFrameLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var refreshTokens: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeacherDashboardBinding.inflate(layoutInflater)

        //starting the teacher
        shimmerLayout = binding.shimmerViewContainerTeacher
        shimmerLayout.startShimmer()

        // api calling for retrofit
        val interfaceApi = RetrofitHelper.getInstance().create(UserApi::class.java)
        val repository = UserRepository(interfaceApi)
        viewModel =
            ViewModelProvider(this, AuthViewModelFactory(repository))[AuthViewModel::class.java]

        setUpNavigationDrawer()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Setting layoutmanager for recycler view
        binding.rvTeachersDashBoard.layoutManager = GridLayoutManager(requireActivity(), 2)

        sharePref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        accessTokens = sharePref.getString(getString(R.string.access_token), null).toString()
        refreshTokens = sharePref.getString(getString(R.string.refresh_token), null).toString()


        try {
            //  binding.progressBarTeacherDashboard.visibility = View.VISIBLE
            viewModel.getTeacherDashBoard("Bearer $accessTokens")
        } catch (e: Exception) {
            Snackbar.make(binding.root, e.toString(), Snackbar.LENGTH_SHORT).show()
        }
        binding.btnAddSubject.setOnClickListener {
            showBottomSheetDialog()
        }
        setUpTeacherDashBoardObserver()
        setUpLogoutObserver()
    }

    private fun setUpTeacherDashBoardObserver() {
        viewModel.responseTeacherDashboardLiveData.observe(requireActivity()) {
            Log.d("AdityaDashboard", it.toString())
            if (it != null && accessTokens != null) {
                Log.d("DataTesting", it.data.toString())
                //setting up recyclerview data
                shimmerLayout.startShimmer()
                shimmerLayout.visibility = View.GONE
                binding.rvTeachersDashBoard.visibility = View.VISIBLE
                val adapter =
                    TeacherDashboardAdapter(it.data, onItemClickViewQuiz = { position, subject ->
                        if (!subject.isNullOrEmpty()) {
                            val bundle = Bundle()
                            bundle.putString("SubjectName", subject)
                            findNavController().navigate(
                                R.id.action_teacherDashboardFragment_to_viewSubjectQuizFragment,
                                bundle
                            )
                        }
                    }, onItemClickAddQuestion = { position, subject ->
                        if (!subject.isNullOrEmpty()) {
                            val bundle = Bundle()
                            bundle.putString("SubjectName", subject)
                            findNavController().navigate(
                                R.id.action_teacherDashboardFragment_to_addQuestionsFragment,
                                bundle
                            )
                        }
                    })
                //binding.progressBarTeacherDashboard.visibility = View.GONE
                binding.rvTeachersDashBoard.adapter = adapter
                //Toast.makeText(requireContext(), it.data.toString(), Toast.LENGTH_SHORT).show()
            } else {
                //binding.progressBarTeacherDashboard.visibility = View.GONE
                shimmerLayout.startShimmer()
                shimmerLayout.visibility = View.GONE
                binding.rvTeachersDashBoard.visibility = View.VISIBLE
                Snackbar.make(binding.root, "Something Went Wrong", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpAddSubjectDataObserver() {
        viewModel.responseTeacherSubjectNameLiveData.observe(requireActivity()) {
            if (it != null) {
                Toast.makeText(requireActivity(), it.Message, Toast.LENGTH_SHORT).show()
                viewModel.getTeacherDashBoard("Bearer $accessTokens")
                bottomDialog.dismiss()
            } else {
                Toast.makeText(
                    bottomDialog.context,
                    "Something went wrong",
                    Toast.LENGTH_SHORT
                ).show()
                bottomDialog.dismiss()
            }
        }
    }


    private fun showBottomSheetDialog() {

        bottomDialog = BottomSheetDialog(requireActivity())
        val view = layoutInflater.inflate(R.layout.fragment_add_sub, null)

        val addQuestion = view.findViewById<AppCompatButton>(R.id.btnAddSubject)
        val btnClose = view.findViewById<ImageView>(R.id.closeAddSubject)
        val tvSubjectName = view.findViewById<AppCompatEditText>(R.id.inputSubjectName)

        addQuestion.setOnClickListener {
            if (!tvSubjectName.text.isNullOrEmpty()) {
                subjectNameToAdd = tvSubjectName.text.toString()
                try {
                    viewModel.getTeacherAddSubject(
                        "Bearer $accessTokens",
                        TeacherAddSubjectDataModel(subject = subjectNameToAdd)
                    )
                    setUpAddSubjectDataObserver()
                } catch (e: Exception) {
                    Toast.makeText(requireActivity(), "Unable To Add Data", Toast.LENGTH_SHORT)
                        .show()
                }
                bottomDialog.dismiss()
            } else {
                Toast.makeText(requireActivity(), "Enter Subject", Toast.LENGTH_SHORT).show()
            }
        }
        btnClose.setOnClickListener {
            bottomDialog.dismiss()
        }

        bottomDialog.setCancelable(false)
        bottomDialog.setContentView(view)
        bottomDialog.show()

    }

    private fun setUpNavigationDrawer() {
        // setting up toggle btn and
        toggle = ActionBarDrawerToggle(
            activity,
            binding.drawerLayout,
            binding.toolbar.toolbar,
            R.string.open,
            R.string.close
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.toolbar.toolbar.title = "Quiz App"

        //handling click for content inside drawer layout
        binding.navViewTeacherDash.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.userProfile -> Toast.makeText(requireActivity(), "Profile", Toast.LENGTH_SHORT)
                    .show()
                R.id.userLogout -> {
                    viewModel.logoutUser("Bearer $accessTokens", refreshTokens)
                }
            }
            true
        }
    }

    private fun setUpLogoutObserver() {
        viewModel.userLogoutResponseLiveData.observe(requireActivity()) {
            if (it == null) {
                findNavController().navigate(R.id.action_teacherDashboardFragment_to_splashFragment)
                sharePref.edit().remove(getString(R.string.refresh_token))
                    .apply()
                sharePref.edit().remove(getString(R.string.access_token)).apply()
                sharePref.edit().remove(getString(R.string.person_type)).apply()
                Toast.makeText(requireActivity(), "Logging Out", Toast.LENGTH_SHORT).show()
            }
        }
    }
    /*
    private fun setUpOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isEnabled) {
                    Toast.makeText(requireContext(), "GoBack", Toast.LENGTH_SHORT).show()
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            }

        })
    }

     */
}