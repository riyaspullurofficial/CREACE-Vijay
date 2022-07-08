package com.riyas.creacevijay

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.riyas.creacevijay.adapter.MyViewHolder
import com.riyas.creacevijay.adapter.ProductRecyclerViewAdapter

import com.riyas.creacevijay.databinding.FragmentHomeBinding
import com.riyas.creacevijay.db.Product
import com.riyas.creacevijay.db.ProductDao
import com.riyas.creacevijay.db.ProductDatabase
import com.riyas.creacevijay.repository.ProductRepository
import com.riyas.creacevijay.viewmodel.ProductViewModel
import com.riyas.creacevijay.viewmodel.ProductViewModelFactory
import java.text.FieldPosition


class HomeFragment : Fragment(),ProductRecyclerViewAdapter.ClickListener {

   lateinit var productViewModel: ProductViewModel
   private lateinit var productBinding: FragmentHomeBinding
   private lateinit var productDelete:Product
/*    lateinit var binding:FragmentHomeBinding*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
/*
        binding=FragmentHomeBinding.inflate(layoutInflater)
        return binding.root*/


        productBinding=DataBindingUtil.inflate(inflater, com.riyas.creacevijay.R.layout.fragment_home,container,false)
        // Inflate the layout for this fragment
        return productBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


       productBinding.addBtn.setOnClickListener {
            replaceFragment(AddProductFragment())
        }

        val dao: ProductDao = ProductDatabase.getInstance(requireContext()).productDaoInProductDb
        val repository= ProductRepository(dao)
        val factory= ProductViewModelFactory(repository)

        productViewModel= ViewModelProvider(this,factory).get(ProductViewModel::class.java)

        productBinding.myViewModel=productViewModel

        productBinding.lifecycleOwner=this
        initRecyclerView()

    }
    private fun replaceFragment(fragment: Fragment) {

        val manager = parentFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(com.riyas.creacevijay.R.id.fragmentFL, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun  displayProductList(){
        productViewModel.products.observe(viewLifecycleOwner, Observer {
            Log.d("Riyasviewmodel",it.toString())

            productBinding.recyclerProductsID.adapter=ProductRecyclerViewAdapter(it,this)


            //right swipe delete
            val itemSwipe=object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showDialog(viewHolder,it)
            }


        }
            val swap=ItemTouchHelper(itemSwipe)
            swap.attachToRecyclerView(productBinding.recyclerProductsID)

        })

    }
    private fun showDialog(viewHolder: RecyclerView.ViewHolder,product:List<Product> ){
                val builder=AlertDialog.Builder(activity)
                builder.setTitle("Delete Item")
        builder.setMessage("Are You sure to delete")
                builder.setPositiveButton("Confirm"){ _, _ ->

                    val position=viewHolder.adapterPosition
                    productViewModel.delete(product[position])
                    initRecyclerView()
                    //listData.removeAt(position
                    //adapter.notifyItemRemoved(position)
                }
        builder.setNegativeButton("Cancel"){_,_->
           val position= viewHolder.adapterPosition
            //adapter.notifyItemChanged(position)
        }
        builder.show()
    }
    private fun initRecyclerView(){
        productBinding.recyclerProductsID.layoutManager=LinearLayoutManager(activity) //requireContext
        displayProductList()
    }

    override fun onItemClick(product: Product) {
       Toast.makeText(requireContext(),"Item clicked",Toast.LENGTH_SHORT).show()
    }
}