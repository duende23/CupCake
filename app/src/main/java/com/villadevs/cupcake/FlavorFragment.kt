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
import com.villadevs.cupcake.databinding.FragmentStartBinding
import com.villadevs.cupcake.viewmodel.OrderViewModel

private const val ARG_PARAM1 = "param1"
class FlavorFragment : Fragment() {

    private val sharedViewModel: OrderViewModel by activityViewModels()

    private var _binding: FragmentFlavorBinding? = null
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
        _binding =FragmentFlavorBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.flavor.observe(viewLifecycleOwner) { newFlavorChoosed ->
            Toast.makeText(activity, newFlavorChoosed, Toast.LENGTH_SHORT).show()
        }

        sharedViewModel.price.observe(viewLifecycleOwner) { newPrice ->
            binding?.subtotal?.text = getString(R.string.subtotal_price, newPrice.toString())
        }

        binding?.apply {
            nextButton.setOnClickListener { goToNextScreen() }
            flavorOptions.setOnCheckedChangeListener { group, checkedId ->chooseflavor()  }
            cancelButton.setOnClickListener { cancelOrder() }
        }

    }

    private fun chooseflavor() {
        val choosedFlavor = when (binding?.flavorOptions?.checkedRadioButtonId){
            R.id.vanilla -> getString(R.string.vanilla)
            R.id.chocolate -> getString(R.string.chocolate)
            R.id.red_velvet ->  getString(R.string.red_velvet)
            R.id.salted_caramel ->  getString(R.string.salted_caramel)
            else ->  getString(R.string.coffee)
        }
        sharedViewModel.setFlavor(choosedFlavor)
        //Toast.makeText(activity, choosedFlavor, Toast.LENGTH_SHORT).show()
    }




    private fun goToNextScreen() {
        //Toast.makeText(activity, "Next", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_flavorFragment_to_pickupFragment)
    }


    private fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_flavorFragment_to_startFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}