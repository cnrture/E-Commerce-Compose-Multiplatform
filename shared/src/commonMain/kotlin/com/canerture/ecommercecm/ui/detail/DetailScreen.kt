package com.canerture.ecommercecm.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.canerture.ecommercecm.data.model.response.ProductUI
import com.canerture.ecommercecm.ui.components.ECEmptyScreen
import com.canerture.ecommercecm.ui.components.ECErrorScreen
import com.canerture.ecommercecm.ui.components.ECProgressBar
import com.canerture.ecommercecm.ui.components.ECScaffold
import com.canerture.ecommercecm.ui.components.ECToolbar
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun DetailRoute(
    id: Int,
    navigateBack: () -> Unit
) {
    val viewModel = getViewModel(
        key = "detail",
        factory = viewModelFactory { DetailViewModel(id) }
    )

    val state by viewModel.state.collectAsState()

    DetailScreen(
        state = state,
        onBackClick = navigateBack
    )
}

@Composable
fun DetailScreen(
    state: DetailState,
    onBackClick: () -> Unit
) {
    ECScaffold(
        topBar = {
            ECToolbar(
                isBackButtonVisible = true,
                onBackClick = onBackClick
            )
        },
        content = {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {

                when (state) {
                    DetailState.Loading -> ECProgressBar()

                    is DetailState.Product -> {
                        DetailContent(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(PaddingValues(16.dp)),
                            product = state.product
                        )
                    }

                    is DetailState.Error -> {
                        ECErrorScreen(
                            desc = state.throwable.message ?: "Unknown Error",
                            buttonText = "Try Again",
                            onButtonClick = {}
                        )
                    }

                    is DetailState.EmptyScreen -> {
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
fun DetailContent(
    modifier: Modifier = Modifier,
    product: ProductUI
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(240.dp),
            painter = rememberImagePainter(product.imageOne),
            contentDescription = "image",
        )

        Text(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp),
            text = product.title,
            fontWeight = FontWeight.SemiBold,
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

        Text(
            modifier = Modifier
                .padding(start = 8.dp).align(Alignment.Start),
            text = product.description
        )
    }
}
