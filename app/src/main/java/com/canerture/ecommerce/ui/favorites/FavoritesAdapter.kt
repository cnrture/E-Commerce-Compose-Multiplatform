package com.canerture.ecommerce.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.canerture.ecommerce.common.loadImage
import com.canerture.ecommerce.data.model.response.ProductUI
import com.canerture.ecommerce.databinding.ItemFavoriteProductBinding

class FavoritesAdapter(
    private val onProductClick: (Int) -> Unit,
    private val onDeleteClick: (ProductUI) -> Unit,
) : ListAdapter<ProductUI, FavoritesAdapter.ProductViewHolder>(ProductDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(
            ItemFavoriteProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onProductClick,
            onDeleteClick
        )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bind(getItem(position))

    class ProductViewHolder(
        private val binding: ItemFavoriteProductBinding,
        private val onProductClick: (Int) -> Unit,
        private val onDeleteClick: (ProductUI) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductUI) = with(binding) {
            tvName.text = product.title
            tvPrice.text = "${product.price} ₺"

            ivProduct.loadImage(product.imageOne)

            root.setOnClickListener {
                onProductClick(product.id)
            }

            ivDelete.setOnClickListener {
                onDeleteClick(product)
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