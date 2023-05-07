package com.villadevs.cupcake

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.villadevs.cupcake.databinding.FragmentFlavorBinding
import com.villadevs.cupcake.databinding.FragmentPickupBinding
import com.villadevs.cupcake.viewmodel.OrderViewModel

private const val ARG_PARAM1 = "param1"


class PickupFragment : Fragment() {

    private val sharedViewModel: OrderViewModel by activityViewModels()

    private var _binding: FragmentPickupBinding? = null
    private val binding get() = _binding!!

    private var param1: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding =FragmentPickupBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.date.observe(viewLifecycleOwner) { newDate ->
            Toast.makeText(activity, newDate, Toast.LENGTH_SHORT).show()
        }

        sharedViewModel.price.observe(viewLifecycleOwner) { newPrice ->
            binding?.subtotal?.text = getString(R.string.subtotal_price, newPrice.toString())
        }

        binding?.apply {
            option0.text = sharedViewModel.dateOptions[0]
            option1.text = sharedViewModel.dateOptions[1]
            option2.text = sharedViewModel.dateOptions[2]
            option3.text = sharedViewModel.dateOptions[3]

            dateOptions.setOnCheckedChangeListener { group, checkedId ->chooseDate()  }

            cancelButton.setOnClickListener { cancelOrder() }

            nextButton.setOnClickListener { goToNextScreen() }
        }
    }

    /**
     * Navigate to the next screen to see the order summary.
     */
    fun goToNextScreen() {
        findNavController().navigate(R.id.action_pickupFragment_to_summaryFragment)
    }


    private fun chooseDate() {
        val choosedDate = when (binding?.dateOptions?.checkedRadioButtonId) {
            R.id.option0 -> binding!!.option0.text.toString()
            R.id.option1 -> binding!!.option1.text.toString()
            R.id.option2->  binding!!.option2.text.toString()
            else -> binding!!.option3.text.toString()
        }
        sharedViewModel.setDate(choosedDate)
        //Toast.makeText(activity, choosedFlavor, Toast.LENGTH_SHORT).show()
    }


    private fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_pickupFragment_to_startFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}