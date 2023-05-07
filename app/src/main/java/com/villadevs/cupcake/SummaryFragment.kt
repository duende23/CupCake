package com.villadevs.cupcake

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

import com.villadevs.cupcake.databinding.FragmentSummaryBinding
import com.villadevs.cupcake.viewmodel.OrderViewModel

private const val ARG_PARAM1 = "param1"

class SummaryFragment : Fragment() {

    private val sharedViewModel: OrderViewModel by activityViewModels()

    private var _binding: FragmentSummaryBinding? = null
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
        _binding =FragmentSummaryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.price.observe(viewLifecycleOwner) { newPrice ->
            binding?.total?.text = getString(R.string.total_price, newPrice.toString())
        }

        binding!!.quantity.text = sharedViewModel.quantity.value.toString()
        binding!!.flavor.text = sharedViewModel.flavor.value.toString()
        binding!!.date.text = sharedViewModel.date.value.toString()

        binding?.apply {
            sendButton.setOnClickListener { sendOrder() }
            cancelButton.setOnClickListener { cancelOrder() }
        }
    }

    private fun sendOrder() {
        //Singular or plurals
        val numberOfCupcakes = sharedViewModel.quantity.value ?: 0

        val orderSummary = getString(
            R.string.order_details,
            resources.getQuantityString(R.plurals.cupcakes, numberOfCupcakes, numberOfCupcakes),
            //sharedViewModel.quantity.value.toString(),
            sharedViewModel.flavor.value.toString(),
            sharedViewModel.date.value.toString(),
            sharedViewModel.price.value.toString()
        )

        val intent = Intent(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.new_cupcake_order))
            .putExtra(Intent.EXTRA_TEXT, orderSummary)

        if (activity?.packageManager?.resolveActivity(intent, 0) != null) {
            startActivity(intent)
        }
    }

    private fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_summaryFragment_to_startFragment)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}