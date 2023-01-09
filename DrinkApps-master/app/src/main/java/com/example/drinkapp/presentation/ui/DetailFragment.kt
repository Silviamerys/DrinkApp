package com.example.drinkapp.presentation.ui

import android.annotation.SuppressLint
import android.app.Fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.drinkapp.R
import com.example.drinkapp.presentation.vm.DetailViewModel

class DetailFragment : Fragment() {

    private lateinit var vm: DetailViewModel
    private lateinit var tvDetail: TextView
    private lateinit var image: ImageView
    private lateinit var progress: ProgressBar
    private lateinit var imageButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)


    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initViews(view)

        vm.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                progress.visibility = View.VISIBLE
            } else {
                progress.visibility = View.GONE
            }
        }

        arguments?.takeIf { it.containsKey("id") }?.apply {
            vm.getDetailsDrinkById(getString("id").toString()).observe(viewLifecycleOwner) { it ->
                it.body()?.let {
                    val details = it.drinks[0]
                    tvDetail.text = "DateModified : ${details.dateModified} \n" +
                            "idDrink : ${details.idDrink} \n" +
                            "strAlcoholic : ${details.strAlcoholic} \n" +
                            "strCategory : ${details.strCategory} \n" +
                            "strCreativeCommonsConfirmed : ${details.strCreativeCommonsConfirmed} \n" +
                            "strDrink : ${details.strDrink} \n" +
                            "strDrinkAlternate : ${details.strDrinkAlternate} \n" +
                            "strDrinkThumb : ${details.strDrinkThumb} \n" +
                            "strGlass : ${details.strGlass} \n" +
                            "strImageSource : ${details.strImageSource} \n" +
                            "strIngredient1 : ${details.strIngredient1} \n" +
                            "strIngredient2 : ${details.strIngredient2} \n" +
                            "strIngredient3 : ${details.strIngredient3} \n" +
                            "strIngredient4 : ${details.strIngredient4} \n" +
                            "strInstructionsDE : ${details.strInstructionsDE} \n" +
                            "strInstructions : ${details.strInstructions} \n" +
                            "strMeasure3 : ${details.strMeasure3} \n" +
                            "strMeasure2 : ${details.strMeasure2} \n" +
                            "strImageAttribution : ${details.strImageAttribution} \n"

                    Glide
                        .with(requireContext())
                        .load(details.strDrinkThumb)
                        .centerCrop()
                        .into(image)
                }
            }
        }
    }


    private fun initViews(view: View) {
        vm = ViewModelProvider(this)[DetailViewModel::class.java]
        tvDetail = view.findViewById(R.id.tv_drink_detail)
        progress = view.findViewById(R.id.progress)
        image = view.findViewById(R.id.iv_image)
        imageButton = view.findViewById(R.id.imgToolbarBtnBack)
        imageButton = view.findViewById(R.id.imgToolbarBtnFav)
    }

}