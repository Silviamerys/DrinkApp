package com.example.drinkapp.presentation.ui


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.drinkapp.App
import com.example.drinkapp.presentation.adapter.CategoryAdapter
import com.example.drinkapp.R
import com.example.drinkapp.Utils
import com.example.drinkapp.data.mappers.Mappers
import com.example.drinkapp.data.model.CategoriesDBModel
import com.example.drinkapp.domain.model.CategoriesModel
import com.example.drinkapp.domain.model.CategoriesNameModel
import com.example.drinkapp.presentation.vm.MainViewModel
import com.example.drinkapp.presentation.vm.RoomViewModel
import com.example.drinkapp.presentation.vm.RoomViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var progress: ProgressBar

    private lateinit var vm: MainViewModel
    private lateinit var ctgrArray: List<CategoriesNameModel>
    private lateinit var adapter: CategoryAdapter

    private val roomViewModel by viewModels<RoomViewModel> {
        RoomViewModelFactory((activity?.application as App).repository)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        initViews(view)

        if (Utils.isOnline(requireContext())) {
            Log.e("onCreateView", "internet on ")
            vm.getCtgr()
            vm.ctgr.observe(viewLifecycleOwner) { it ->
                it?.let { it ->
                    ctgrArray = it.body()!!.categoriesNameModels

                    roomViewModel.getCategories.observe(viewLifecycleOwner) {
                        val newArray = it.filter { it.status }
                        setTabLayoutAndViewPager(
                            Mappers.mapCategoriesDBModelToCategoriesNameModel(
                                newArray
                            )
                        )

                        if (it.isNullOrEmpty()) {
                            roomViewModel.deleteCategories()
                            roomViewModel.deleteDrinks()
                            roomViewModel.insertCategories(ctgrArray.map {
                                CategoriesDBModel(
                                    strCategory = it.strCategory
                                )
                            })
                        }
                    }
                }
            }

        } else {
            Log.e("onCreateView", "internet off ")
            roomViewModel.getCategories.observe(viewLifecycleOwner) {
                val newArray = it.filter { it.status }
                setTabLayoutAndViewPager(Mappers.mapCategoriesDBModelToCategoriesNameModel(newArray))
            }
        }

        vm.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                progress.visibility = View.VISIBLE
            } else {
                progress.visibility = View.GONE
            }
        }

        return view
    }

    private fun setTabLayoutAndViewPager(ctgrArray: List<CategoriesNameModel>) {
        adapter = CategoryAdapter(requireActivity(), ctgrArray)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = ctgrArray[position].strCategory
        }.attach()
    }

    private fun initViews(view: View) {
        viewPager = view.findViewById(R.id.viewpager)
        tabLayout = view.findViewById(R.id.tab_layout)
        progress = view.findViewById(R.id.progress)
        vm = ViewModelProvider(this)[MainViewModel::class.java]

    }


}