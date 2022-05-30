package com.assignment.radiusagentdemo.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.assignment.radiusagentdemo.R
import com.assignment.radiusagentdemo.databinding.SecondFragmentBinding
import com.assignment.radiusagentdemo.room.FacilityDatabase
import com.assignment.radiusagentdemo.room.Repository
import com.assignment.radiusagentdemo.ui.ExclusionsViewModel
import com.assignment.radiusagentdemo.ui.ExclusionsViewModelFactory
import com.assignment.radiusagentdemo.ui.FacilityViewModel
import com.assignment.radiusagentdemo.ui.FacilityViewModelFactory
import kotlin.concurrent.fixedRateTimer

class SecondFragment(private var count: Int) : Fragment() {

    private lateinit var facilityViewModel: FacilityViewModel
    private lateinit var binding: SecondFragmentBinding
    private lateinit var displayText: TextView
    private lateinit var imageView: ImageView

    companion object{

        var optionSelected = -1
        var optionSelectedName = ""
        lateinit var facilityTwoArrayList: ArrayList<String>
        lateinit var exclusionArrayList: ArrayList<String>
        var optionIdMap: HashMap<String, String>? = HashMap()
        var optionNameMap: HashMap<String, String>? = HashMap()
        var updatedList: ArrayList<String?> = ArrayList()
        private lateinit var spinner: Spinner

        private var flag = 0

        fun updateList(id: String?,context: Context) {
            Log.i("Update list 2 ",id.toString())
            Log.i("exclusion 2", exclusionArrayList.toString())


            var ele : String? = null
            if(id != null){
                for(i in exclusionArrayList.indices){
                    if(exclusionArrayList[i] == id){
                        updatedList.addAll(facilityTwoArrayList)
                        ele = if(i%2 == 0)
                            exclusionArrayList[i+1]
                        else
                            exclusionArrayList[i-1]
                        break
                    }
                }

            }
            Log.i("current list 2", updatedList.toString())

            if(ele != null){

                updatedList.remove(optionIdMap?.get(ele))
                Log.i("updated list 2", updatedList.toString())
                spinner.adapter = ArrayAdapter(context ,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    updatedList)

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SecondFragmentBinding.inflate(layoutInflater)
        displayText = binding.root.findViewById(R.id.display_text_2)
        spinner = binding.root.findViewById(R.id.spinner_second_frag)
        imageView = binding.root.findViewById(R.id.imageView)

        val repositoryItems = Repository(FacilityDatabase.invoke(requireContext()))

        val facilityViewModelFactory = FacilityViewModelFactory(repositoryItems)
        facilityViewModel = ViewModelProvider(this, facilityViewModelFactory)[FacilityViewModel::class.java]


        facilityViewModel.getFacilitiesFromId((count).toString()).observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                facilityTwoArrayList = ArrayList<String>(it.size)
                optionIdMap = HashMap(it.size)
                optionNameMap = HashMap(it.size)
                displayText.text = "Select " + it[0].facilityName

                for(i in it.indices){
                    facilityTwoArrayList.add(it[i].optionsName)
                    optionIdMap!![it[i].optionsId] = it[i].optionsName
                    optionNameMap!![it[i].optionsName] = it[i].optionsId
                }
                if(flag == 0){
                    spinner.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, facilityTwoArrayList)
                    flag = 1
                }

            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("erreur")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemAtPosition(position).toString()
                updateImage(position)
                optionSelected = position
                optionSelectedName = type
                ThirdFragment.updateList(optionNameMap!![type],requireContext())

            }

        }

    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if(menuVisible){
            //updateList(FirstFragment.optionSelected.toString(), requireContext())
            //updateList(optionSelected.toString(), requireContext())
            //ThirdFragment.updateList(optionNameMap!![FirstFragment.optionSelected.toString()],requireContext())
            //ThirdFragment.updateList(optionNameMap!![optionSelected.toString()],requireContext())
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    println("erreur")
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val type = parent?.getItemAtPosition(position).toString()
                    updateImage(position)
                    optionSelected = position
                    Log.i("option val", optionNameMap!![type].toString())
                    ThirdFragment.updateList(optionNameMap!![type],requireContext())

                }

            }
        }

    }


    private fun updateImage(position: Int) {

        when(position){
            0 -> imageView.setImageResource(R.drawable.rooms)
            1 -> imageView.setImageResource(R.drawable.no_room)
        }
    }

}