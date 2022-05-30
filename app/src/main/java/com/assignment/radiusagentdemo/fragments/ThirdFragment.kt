package com.assignment.radiusagentdemo.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.assignment.radiusagentdemo.R
import com.assignment.radiusagentdemo.databinding.ThirdFragmentBinding
import com.assignment.radiusagentdemo.room.FacilityDatabase
import com.assignment.radiusagentdemo.room.Repository
import com.assignment.radiusagentdemo.ui.FacilityViewModel
import com.assignment.radiusagentdemo.ui.FacilityViewModelFactory

class ThirdFragment(private var count: Int) : Fragment() {

    private lateinit var facilityViewModel: FacilityViewModel
    private lateinit var binding: ThirdFragmentBinding
    private lateinit var displayText: TextView
    private lateinit var spinner: Spinner
    private lateinit var imageView: ImageView


    companion object {

        var facilityThreeArrayList: ArrayList<String> = ArrayList()
        var optionNameMap: HashMap<String, String>? = HashMap()
        var optionIdMap: HashMap<String, String>? = HashMap()
        var optionSelectedName = ""

        var updatedList: ArrayList<String?> = ArrayList()

        private var flag = 0

        fun updateList(id: String?, context: Context) {
            Log.i("Update list 3 ", id.toString())
            Log.i("exclusion 3", SecondFragment.exclusionArrayList.toString())
            if (updatedList.size > 3 || updatedList.size == 5) updatedList.clear()
            var ele: String? = null
            if (id != null) {
                for (i in SecondFragment.exclusionArrayList.indices) {
                    if (SecondFragment.exclusionArrayList[i] == id) {
                        updatedList.addAll(facilityThreeArrayList)
                        updatedList.distinct()
                        ele = if (i % 2 == 0)
                            SecondFragment.exclusionArrayList[i + 1]
                        else
                            SecondFragment.exclusionArrayList[i - 1]
                        break
                    }
                }
            }
            Log.i("current list 3", updatedList.toString())

            if (ele != null) {
                updatedList.remove(optionIdMap?.get(ele))
                Log.i("updated list 3", updatedList.toString())
                updatedList.distinct()
            }
            if(FirstFragment.optionSelected == 2){
                updatedList.remove("Garage")
                updatedList.distinct()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ThirdFragmentBinding.inflate(layoutInflater)
        displayText = binding.root.findViewById(R.id.display_text_3)
        spinner = binding.root.findViewById(R.id.spinner_third_frag)
        imageView = binding.root.findViewById(R.id.imageView)

        val facilityItems = Repository(FacilityDatabase.invoke(requireContext()))

        val facilityViewModelFactory = FacilityViewModelFactory(facilityItems)
        facilityViewModel =
            ViewModelProvider(this, facilityViewModelFactory)[FacilityViewModel::class.java]

        facilityViewModel.getFacilitiesFromId((count).toString()).observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                optionIdMap = HashMap(it.size)
                optionNameMap = HashMap(it.size)
                displayText.text = "Select " + it[0].facilityName + " as required"
                for (i in it.indices) {
                    facilityThreeArrayList.add(it[i].optionsName)
                    optionIdMap!![it[i].optionsId] = it[i].optionsName
                    optionNameMap!![it[i].optionsName] = it[i].optionsId
                }
                if (flag == 0) {
                    spinner.adapter = ArrayAdapter(
                        requireContext(),
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        facilityThreeArrayList
                    )
                    flag = 1
                }
                Log.i("Facility list 3", "populated")
            }
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner.adapter = ArrayAdapter(
            requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            facilityThreeArrayList
        )

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("erreur")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val type = parent?.getItemAtPosition(position).toString()
                updateImage(position)
                optionSelectedName = type
                updateList(SecondFragment.optionNameMap!![type], requireContext())


            }

        }
    }


    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)

        if (menuVisible) {

            updateList(
                SecondFragment.optionNameMap!![SecondFragment.optionSelectedName],
                requireContext()
            )

            spinner.adapter = ArrayAdapter(
                requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                updatedList
            )

        }
    }

    private fun updateImage(position: Int) {

        when (position) {
            0 -> imageView.setImageResource(R.drawable.swimming)
            1 -> imageView.setImageResource(R.drawable.garden)
            2 -> imageView.setImageResource(R.drawable.garage)
        }

    }


}