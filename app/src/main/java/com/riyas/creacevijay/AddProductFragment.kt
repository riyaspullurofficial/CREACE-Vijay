package com.riyas.creacevijay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.riyas.creacevijay.databinding.FragmentAddProductBinding
import com.riyas.creacevijay.db.ProductDao
import com.riyas.creacevijay.db.ProductDatabase
import com.riyas.creacevijay.repository.ProductRepository
import com.riyas.creacevijay.viewmodel.ProductViewModel
import com.riyas.creacevijay.viewmodel.ProductViewModelFactory

open class AddProductFragment : Fragment() {

    lateinit var productViewModel: ProductViewModel
    lateinit var productBinding: FragmentAddProductBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        productBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_add_product,container,false)
        // Inflate the layout for this fragment
        return productBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao:ProductDao=ProductDatabase.getInstance(requireContext()).productDaoInProductDb
        val repository=ProductRepository(dao)
        val factory=ProductViewModelFactory(repository)

        productViewModel=ViewModelProvider(this,factory).get(ProductViewModel::class.java)

        productBinding.productViewModel=productViewModel

        productBinding.lifecycleOwner=this


    }
}