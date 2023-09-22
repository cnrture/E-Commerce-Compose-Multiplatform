package com.canerture.ecommerce.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.canerture.ecommerce.R
import com.canerture.ecommerce.common.loadImage
import com.canerture.ecommerce.common.setStrikeThrough
import com.canerture.ecommerce.data.model.response.ProductUI
import com.canerture.ecommerce.databinding.ItemSaleProductBinding

class SaleProductsAdapter(
    private val onProductClick: (Int) -> Unit,
    private val onFavoriteClick: (ProductUI) -> Unit,
) : ListAdapter<ProductUI, SaleProductsAdapter.SaleProductViewHolder>(ProductDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleProductViewHolder =
        SaleProductViewHolder(
            ItemSaleProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onProductClick,
            onFavoriteClick
        )

    override fun onBindViewHolder(holder: SaleProductViewHolder, position: Int) = holder.bind(getItem(position))

    class SaleProductViewHolder(
        private val binding: ItemSaleProductBinding,
        private val onProductClick: (Int) -> Unit,
        private val onFavoriteClick: (ProductUI) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductUI) = with(binding) {
            tvName.text = product.title
            tvPrice.text = "${product.price} ₺"
            tvSalePrice.text = "${product.salePrice} ₺"
            tvPrice.setStrikeThrough()

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