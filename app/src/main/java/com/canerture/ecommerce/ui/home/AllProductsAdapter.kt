package com.canerture.ecommerce.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.canerture.ecommerce.R
import com.canerture.ecommerce.common.loadImage
import com.canerture.ecommerce.common.setStrikeThrough
import com.canerture.ecommerce.common.visible
import com.canerture.ecommerce.data.model.response.ProductUI
import com.canerture.ecommerce.databinding.ItemProductBinding

class AllProductsAdapter(
    private val onProductClick: (Int) -> Unit,
    private val onFavoriteClick: (ProductUI) -> Unit,
) : ListAdapter<ProductUI, AllProductsAdapter.ProductViewHolder>(ProductDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onProductClick,
            onFavoriteClick
        )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bind(getItem(position))

    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val onProductClick: (Int) -> Unit,
        private val onFavoriteClick: (ProductUI) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductUI) = with(binding) {
            tvName.text = product.title
            tvPrice.text = "${product.price} ₺"

            if (product.saleState) {
                tvSalePrice.text = "${product.salePrice} ₺"
                tvSalePrice.visible()
                tvPrice.setStrikeThrough()
            }

            if (product.isFavorite) {
                ivFavorite.setImageResource(R.drawable.ic_fav)
            } else {
                ivFavorite.setImageResource(R.drawable.ic_unfav)
            }

            ivProduct.loadImage(product.imageOne)

            root.setOnClickListener {
                onProductClick(product.id)
            }

            ivFavorite.setOnClickListener {
                onFavoriteClick(product)
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