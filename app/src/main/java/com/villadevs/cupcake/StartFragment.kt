package com.villadevs.cupcake

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.villadevs.cupcake.databinding.FragmentStartBinding
import com.villadevs.cupcake.viewmodel.OrderViewModel


private const val ARG_PARAM1 = "param1"

class StartFragment : Fragment() {

    private val sharedViewModel: OrderViewModel by activityViewModels()

    private var _binding: FragmentStartBinding? = null
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
        // Inflate the layout for this fragment
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            // Set up the button click listeners
            orderOneCupcake.setOnClickListener { orderCupcake(1) }
            orderSixCupcakes.setOnClickListener { orderCupcake(6) }
            orderTwelveCupcakes.setOnClickListener { orderCupcake(12) }
        }

    }

    private fun orderCupcake(quantity: Int) {
        //Toast.makeText(activity, "Ordered $quantity cupcake(s)", Toast.LENGTH_SHORT).show()
            sharedViewModel.setQuantity(quantity)
            if (sharedViewModel.hasNoFlavorSet()) {
                sharedViewModel.setFlavor(getString(R.string.vanilla))
            }
            findNavController().navigate(R.id.action_startFragment_to_flavorFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}