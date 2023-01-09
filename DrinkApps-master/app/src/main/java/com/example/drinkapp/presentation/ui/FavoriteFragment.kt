package com.example.drinkapp.presentation.ui

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkapp.App
import com.example.drinkapp.R
import com.example.drinkapp.Utils
import com.example.drinkapp.data.mappers.Mappers
import com.example.drinkapp.domain.model.Drink
import com.example.drinkapp.presentation.adapter.DrinkAdapter
import com.example.drinkapp.presentation.vm.RoomViewModel
import com.example.drinkapp.presentation.vm.RoomViewModelFactory

class FavoriteFragment : Fragment() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: DrinkAdapter
    private val roomViewModel: RoomViewModel by viewModels {
        RoomViewModelFactory((activity?.application as App).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        rv = view.findViewById(R.id.rv)
        adapter = DrinkAdapter(requireContext(), click)
        rv.adapter = adapter
        rv.layoutManager = GridLayoutManager(requireContext(), 2)

        roomViewModel.allFavorites.observe(viewLifecycleOwner) { it ->
            it?.let { it ->
                adapter.setData(it.map {
                    Drink(
                        it.idDrink,
                        it.drinkName,
                        it.drinkImage
                    )
                } as ArrayList<Drink>)
            }
        }

        return view
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
            } else{
                Toast.makeText(requireContext(), "Check internet connection", Toast.LENGTH_SHORT).show()
            }
        }

        override fun longClickItem(model: Drink): Boolean {
            val builder = AlertDialog.Builder(requireContext())
            with(builder)
            {
                setMessage("Аre you sure you want to delete ?")
                setPositiveButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }
                setNegativeButton("Delete  favorite") { dialog, _ ->
                    roomViewModel.deleteFavorites(model.idDrink.toInt())
                    adapter.notifyDataSetChanged()
                    dialog.cancel()
                }
                show()
            }
            return true
        }

    }
}