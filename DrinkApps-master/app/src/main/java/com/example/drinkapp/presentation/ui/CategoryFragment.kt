package com.example.drinkapp.presentation.ui

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkapp.App
import com.example.drinkapp.R
import com.example.drinkapp.Utils
import com.example.drinkapp.data.mappers.Mappers
import com.example.drinkapp.data.model.CategoriesDBModel
import com.example.drinkapp.data.model.DrinkDBModel
import com.example.drinkapp.domain.model.Drink
import com.example.drinkapp.presentation.adapter.CategoryAdapter
import com.example.drinkapp.presentation.adapter.DrinkAdapter
import com.example.drinkapp.presentation.vm.CategoriesVm
import com.example.drinkapp.presentation.vm.RoomViewModel
import com.example.drinkapp.presentation.vm.RoomViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator


const val ARG_OBJECT = "object"

class CategoryFragment : Fragment() {

    private lateinit var progress: ProgressBar
    private lateinit var adapter: DrinkAdapter
    private lateinit var vm: CategoriesVm
    private lateinit var recyclerView: RecyclerView
    private var categoryName = ""


    private val roomViewModel: RoomViewModel by viewModels {
        RoomViewModelFactory((activity?.application as App).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //  setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)

        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            categoryName = getString(ARG_OBJECT).toString()

            if (Utils.isOnline(requireContext())) {
                vm.getDrinks(categoryName).observe(viewLifecycleOwner) {
                    it?.let {
                        adapter.setData(it.body()!!.drinks as ArrayList<Drink>)

                        // roomViewModel.deleteDrinks()
                        roomViewModel.insertDrinks(
                            Mappers.mapDrinkModelToDrinkDBModel(
                                (it.body()!!.drinks),
                                categoryName
                            )
                        )
                    }
                }

            } else {
                roomViewModel.getDrinks(categoryName).observe(viewLifecycleOwner) {
                    it?.let {
                        adapter.setData(it.map {
                            Drink(
                                it.idDrink,
                                it.stringDrink,
                                it.strDrinkThumb
                            )
                        } as ArrayList<Drink>)
                    }
                }
            }

        }

        vm.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                progress.visibility = View.VISIBLE
            } else {
                progress.visibility = View.GONE
            }
        }

    }

    private fun initViews(view: View) {
        vm = ViewModelProvider(this).get(CategoriesVm::class.java)
        adapter = DrinkAdapter(requireContext(), click)
        progress = view.findViewById(R.id.progress)
        recyclerView = view.findViewById(R.id.iv_pager)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter
    }

    private val click = object : DrinkAdapter.DrinkOnclick {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun clickItem(model: Drink) {
            val ldf = DetailFragment()
            val args = Bundle()
            args.putString("id", model.idDrink)
            ldf.arguments = args
            if (Utils.isOnline(requireContext())) {
                requireActivity().supportFragmentManager.beginTransaction().apply {
                    add(R.id.nav_host_fragment, ldf)
                    addToBackStack(null)
                    commit()

                }
                roomViewModel.insertHistory(Mappers.mapDrinkToHistoryModel(model))
            }
            else{
                Toast.makeText(requireContext(), "Check internet connection", Toast.LENGTH_SHORT).show()
            }
        }

        override fun longClickItem(model: Drink): Boolean {
            val builder = AlertDialog.Builder(requireContext())
            with(builder)
            {
                setMessage("Want to add to favorites ?")
                setPositiveButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }
                setNegativeButton("Add to favorite") { dialog, _ ->
                    roomViewModel.insertFavorites(Mappers.mapDrinkToFavoritesModel(model))
                    dialog.cancel()
                }
                show()
            }
//            Toast.makeText(requireContext(), "123", Toast.LENGTH_SHORT).show()

            return true
        }
    }
}
