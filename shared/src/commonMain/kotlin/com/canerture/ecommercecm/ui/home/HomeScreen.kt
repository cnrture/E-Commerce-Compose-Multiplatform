package com.canerture.ecommercecm.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.canerture.ecommercecm.MR
import com.canerture.ecommercecm.data.model.response.ProductUI
import com.canerture.ecommercecm.theme.Gray
import com.canerture.ecommercecm.theme.White
import com.canerture.ecommercecm.ui.components.ECEmptyScreen
import com.canerture.ecommercecm.ui.components.ECErrorScreen
import com.canerture.ecommercecm.ui.components.ECProgressBar
import com.canerture.ecommercecm.ui.components.ECScaffold
import com.canerture.ecommercecm.ui.components.ECToolbar
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun HomeRoute(
    navigateToDetail: (Int) -> Unit
) {
    val viewModel = getViewModel(
        key = "home",
        factory = viewModelFactory { HomeViewModel() }
    )

    val state by viewModel.state.collectAsState()

    HomeScreen(
        state = state,
        onProductClick = navigateToDetail
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    onProductClick: (Int) -> Unit
) {
    ECScaffold(
        topBar = {
            ECToolbar(title = "All Products")
        },
        content = {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {

                when (state) {
                    HomeState.Loading -> ECProgressBar()

                    is HomeState.AllProductsContent -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(12.dp)
                        ) {
                            items(state.allProducts) { product ->
                                ProductItem(
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .height(220.dp),
                                    product = product,
                                    onProductClick = onProductClick
                                )
                            }
                        }
                    }

                    is HomeState.Error -> {
                        ECErrorScreen(
                            desc = state.throwable.message ?: "Unknown Error",
                            buttonText = "Try Again",
                            onButtonClick = {}
                        )
                    }

                    is HomeState.EmptyScreen -> {
                        ECEmptyScreen(
                            text = state.message
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    product: ProductUI,
    onProductClick: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .clickable {
                onProductClick(product.id)
            },
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(100.dp),
                painter = rememberImagePainter(product.imageOne),
                contentDescription = "image",
            )

            Text(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp),
                text = product.title,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier
                    .padding(start = 8.dp),
                text = product.price.toString(),
                textDecoration = TextDecoration.LineThrough
            )

            if (product.saleState) {
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 8.dp),
                    text = product.salePrice.toString(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.Red
                )
            }
        }
    }
}
