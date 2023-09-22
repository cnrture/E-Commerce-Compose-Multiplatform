package com.canerture.ecommerce.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.canerture.ecommerce.common.loadImage
import com.canerture.ecommerce.data.model.response.ProductUI
import com.canerture.ecommerce.databinding.ItemCartProductBinding

/**
 * Created on 10.08.2023
 * @author Caner Türe
 */

class CartProductsAdapter(
    private val onProductClick: (Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit,
) : ListAdapter<ProductUI, CartProductsAdapter.CartProductViewHolder>(ProductDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder =
        CartProductViewHolder(
            ItemCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onProductClick,
            onDeleteClick
        )

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) = holder.bind(getItem(position))

    class CartProductViewHolder(
        private val binding: ItemCartProductBinding,
        private val onProductClick: (Int) -> Unit,
        private val onDeleteClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductUI) = with(binding) {
            tvName.text = product.title
            tvPrice.text = "${product.price} ₺"

            ivProduct.loadImage(product.imageOne)

            root.setOnClickListener {
                onProductClick(product.id)
            }

            ivDelete.setOnClickListener {
                onDeleteClick(product.id)
            }
        }
    }

    class ProductDiffCallBack : DiffUtil.ItemCallback<ProductUI>() {
        override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem == newItem
        }
    }
}