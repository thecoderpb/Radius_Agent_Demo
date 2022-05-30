package com.assignment.radiusagentdemo.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.ArrayAdapter
import com.assignment.radiusagentdemo.R
import com.assignment.radiusagentdemo.databinding.FirstFragmentBinding
import com.assignment.radiusagentdemo.room.FacilityDatabase
import com.assignment.radiusagentdemo.room.Repository
import com.assignment.radiusagentdemo.ui.ExclusionsViewModel
import com.assignment.radiusagentdemo.ui.ExclusionsViewModelFactory
import com.assignment.radiusagentdemo.ui.FacilityViewModel
import com.assignment.radiusagentdemo.ui.FacilityViewModelFactory

class FirstFragment(private var count: Int) : Fragment() {

    private lateinit var facilityViewModel: FacilityViewModel
    private lateinit var exclusionsViewModel: ExclusionsViewModel
    private lateinit var binding: FirstFragmentBinding
    private lateinit var displayText: TextView
    private lateinit var spinner: Spinner
    private lateinit var imageView: ImageView

    companion object{

        lateinit var optionNameMap: HashMap<String,String>
        lateinit var optionIdMap: HashMap<String,String>
        lateinit var facilityOneArrayList: ArrayList<String>
        private var flag = 0
        var optionSelected = -1
        var optionSelectedName = ""

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FirstFragmentBinding.inflate(layoutInflater)
        displayText = binding.root.findViewById(R.id.display_text_1)
        spinner = binding.root.findViewById(R.id.spinner_second_frag)
        imageView = binding.root.findViewById(R.id.imageView)

        val repositoryItems = Repository(FacilityDatabase.invoke(requireContext()))
        val facilityViewModelFactory = FacilityViewModelFactory(repositoryItems)
        val exclusionsViewModelFactory = ExclusionsViewModelFactory(repositoryItems)

        facilityViewModel = ViewModelProvider(this, facilityViewModelFactory)[FacilityViewModel::class.java]
        exclusionsViewModel = ViewModelProvider(this, exclusionsViewModelFactory)[ExclusionsViewModel::class.java]

        exclusionsViewModel.allExclusionItems().observe(viewLifecycleOwner){
            SecondFragment.exclusionArrayList = ArrayList(it.size)
            for(i in it.indices)
                SecondFragment.exclusionArrayList.add(it[i].optionsId)
        }

        facilityViewModel.getFacilitiesFromId(count.toString()).observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                facilityOneArrayList = ArrayList(it.size)
                optionNameMap = HashMap(it.size)
                optionIdMap = HashMap(it.size)
                displayText.text = "Select " + it[0].facilityName
                for(i in it.indices){
                    facilityOneArrayList.add(it[i].optionsName)
                    optionNameMap[it[i].optionsName] = it[i].optionsId
                    optionIdMap[it[i].optionsId] = it[i].optionsName
                }
                if(flag == 0){
                    spinner.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, facilityOneArrayList)
                    //flag = 1
                }
            }
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //spinner.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, facilityOneArrayList)

        spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("erreur")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemAtPosition(position).toString()
                updateImage(position)
                optionSelected = position
                optionSelectedName = type
                SecondFragment.updateList(optionNameMap[type],requireContext())

            }
        }

    }

    private fun updateImage(position: Int) {
        when(position){
            0 -> imageView.setImageResource(R.drawable.apartment)
            1 -> imageView.setImageResource(R.drawable.condo)
            2 -> imageView.setImageResource(R.drawable.boat)
            3 -> imageView.setImageResource(R.drawable.land)
        }
    }
}