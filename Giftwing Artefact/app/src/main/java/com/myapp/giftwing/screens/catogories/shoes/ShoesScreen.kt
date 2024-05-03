package com.myapp.giftwing.screens.catogories.shoes

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.myapp.giftwing.components.CustomInputField
import com.myapp.giftwing.components.DrawableButton
import com.myapp.giftwing.components.ProductItemLayout
import com.myapp.giftwing.models.Advertisement
import com.myapp.giftwing.sealed.UiState
import com.myapp.giftwing.ui.theme.Dimension
import kotlinx.coroutines.launch
import timber.log.Timber
import com.myapp.giftwing.R

@Composable
fun ChatbotButton() {
    val context = LocalContext.current
    val intent = Intent().apply {
        setClassName("com.example.chatbot", "com.example.chatbot.MainActivity") // Replace with the actual package name and activity name of the chatbot project
    }
    val onClick = {
        context.startActivity(intent)
    }

    DrawableButton(
        painter = painterResource(id = R.drawable.chat_icon),
        onButtonClicked = onClick,
        backgroundColor = MaterialTheme.colors.background,
        iconSize = Dimension.mdIcon,
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ShoesScreen(
    shoesViewModel: ShoesViewModel = hiltViewModel(),
    cartOffset: IntOffset,
    cartProductsIds: List<Int>,
    bookmarkProductsIds: List<Int>,
    onProductClicked: (productId: Int) -> Unit,
    onCartStateChanged: (productId: Int) -> Unit,
    onBookmarkStateChanged: (productId: Int) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        shoesViewModel.getHomeAdvertisements()
        shoesViewModel.getBrandsWithProducts()
    }

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val searchQuery by remember { shoesViewModel.searchQuery }

    val advertisementsUiState by remember { shoesViewModel.homeAdvertisementsUiState }
    val advertisements = shoesViewModel.advertisements

    val brandsUiState by remember { shoesViewModel.brandsUiState }
    val brands = shoesViewModel.brands

    val currentSelectedBrandIndex by remember { shoesViewModel.currentSelectedBrandIndex }

    /** Now we configure the pager to auto scroll each 2 seconds, using Handler */
    val mainHandler = Handler(Looper.getMainLooper())
    val autoPagerScrollCallback = remember {
        object : Runnable {
            override fun run() {
                /** Handle where to scroll */
                val currentPage = pagerState.currentPage
                val pagesCount = pagerState.pageCount
                Timber.d("Current pager page is $currentPage and count is $pagesCount")
                when {
                    currentPage < (pagesCount - 1) -> {
                        /** go to next page */
                        scope.launch {
                            pagerState.animateScrollToPage(currentPage.inc())
                        }
                    }
                    else -> {
                        /** Start from beginning */
                        scope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    }
                }
                mainHandler.postDelayed(this, 2000)
            }
        }
    }

    /** Staring our handler only once when the app is launched */
    LaunchedEffect(key1 = Unit) {
        mainHandler.post(autoPagerScrollCallback)
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
        verticalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
        contentPadding = PaddingValues(horizontal = Dimension.pagePadding),
    ) {
        item(
            span = {
                GridItemSpan(2)
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Home",
                    style = MaterialTheme.typography.h2,
                )

                ChatbotButton()

            }
        }
        /** Handling what to show depending on advertisement ui state */
        when (advertisementsUiState) {
            is UiState.Idle -> {}
            is UiState.Loading -> {
            }
            is UiState.Success -> {
                /** Search field section */
                item(
                    span = {
                        GridItemSpan(2)
                    }
                ) {
                    /** We should run the search now */
                    SearchField(
                        value = searchQuery,
                        onValueChange = {
                            shoesViewModel.updateSearchInputValue(it)
                        },
                        onFocusChange = {

                        },
                        onImeActionClicked = {
                            /** We should run the search now */
                        }
                    )
                }
                /** Advertisements section */
                item(
                    span = {
                        GridItemSpan(2)
                    }
                ) {
                    AdvertisementsPager(
                        pagerState = pagerState,
                        advertisements = advertisements,
                        onAdvertiseClicked = {}
                    )
                }
            }
            is UiState.Error -> {}
        }

        /** Handling what to show depending on brands ui state */
        when (brandsUiState) {
            is UiState.Loading -> {
                /** Still loading */

            }
            is UiState.Success -> {
                /** Loading finished successfully, Shoes brands row first! */
                item(
                    span = {
                        GridItemSpan(2)
                    }
                ) {
                    ManufacturersSection(
                        brands = brands.map { Triple(it.id, it.name, it.icon) },
                        activeBrandIndex = currentSelectedBrandIndex,
                    ) {
                        if (it != currentSelectedBrandIndex) {
                            shoesViewModel.updateCurrentSelectedBrandId(index = it)
                        }
                    }
                }
                /** Show selected brand's data */
                items(brands[currentSelectedBrandIndex].products) { product ->
                    ProductItemLayout(
                        modifier = Modifier
                            .fillMaxWidth(),
                        cartOffset = cartOffset,
                        image = product.image,
                        price = product.price,
                        title = product.name,
                        discount = product.discount,
                        onCart = product.id in cartProductsIds,
                        onBookmark = product.id in bookmarkProductsIds,
                        onProductClicked = {
                            onProductClicked(product.id)
                        },
                        onChangeCartState = {
                            onCartStateChanged(product.id)
                        },
                        onChangeBookmarkState = { onBookmarkStateChanged(product.id) },
                    )
                }
            }
            else -> {
                /** An error occur */
            }
        }
    }
}

@Composable
fun SearchField(
    value: String,
    onValueChange: (value: String) -> Unit,
    onFocusChange: (hadFocus: Boolean) -> Unit,
    onImeActionClicked: KeyboardActionScope.() -> Unit,
) {
    CustomInputField(
        modifier = Modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        placeholder = "Search",
        textStyle = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Medium),
        padding = PaddingValues(
            horizontal = Dimension.pagePadding,
            vertical = Dimension.pagePadding.times(0.7f),
        ),
        backgroundColor = MaterialTheme.colors.surface,
        textColor = MaterialTheme.colors.onBackground,
        imeAction = ImeAction.Search,
        shape = MaterialTheme.shapes.large,
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .padding(end = Dimension.pagePadding.div(2))
                    .size(Dimension.mdIcon.times(0.7f)),
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground.copy(alpha = 0.4f),
            )
        },
        onFocusChange = onFocusChange,
        onKeyboardActionClicked = onImeActionClicked,
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AdvertisementsPager(
    pagerState: PagerState,
    advertisements: List<Advertisement>,
    onAdvertiseClicked: (advertisement: Advertisement) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimension.pagePadding.div(2)),
    ) {
        /** Horizontal pager section */
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth(),
            count = advertisements.size,
            state = pagerState,
            itemSpacing = Dimension.pagePadding.times(2),
        ) {
            val advertisement = advertisements[this.currentPage]
            AsyncImage(
                model = advertisement.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource(),
                        onClick = { onAdvertiseClicked(advertisement) }
                    ),
                contentScale = ContentScale.Crop,
            )
        }
        /** Horizontal pager indicators */
        LazyRow(
            contentPadding = PaddingValues(horizontal = Dimension.pagePadding.times(2)),
            horizontalArrangement = Arrangement.spacedBy(Dimension.sm),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(pagerState.pageCount) { index ->
                Box(
                    modifier = Modifier
                        .width(
                            if (pagerState.currentPage == index) Dimension.sm.times(3)
                            else Dimension.sm
                        )
                        .height(Dimension.sm)
                        .clip(CircleShape)
                        .background(
                            if (pagerState.currentPage == index) MaterialTheme.colors.primary
                            else MaterialTheme.colors.primary.copy(alpha = 0.4f)
                        )
                )
            }
        }
    }
}


@Composable
fun ManufacturersSection(
    brands: List<Triple<Int, String, Int>>,
    activeBrandIndex: Int,
    onBrandClicked: (index: Int) -> Unit,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding.div(2)),
    ) {
        itemsIndexed(brands) { index, (_, name, icon) ->
            val backgroundColor = if (activeBrandIndex == index) com.myapp.giftwing.ui.theme.SomeGreen
            else MaterialTheme.colors.background

            val contentColor = if (activeBrandIndex == index) MaterialTheme.colors.onPrimary
            else MaterialTheme.colors.onBackground

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimension.xs),
                modifier = Modifier
                    .clip(CircleShape)
                    .background(backgroundColor)
                    .clickable { onBrandClicked(index) }
                    .padding(
                        horizontal = Dimension.md,
                        vertical = Dimension.sm,
                    )
            ) {
                Icon(
                    modifier = Modifier.size(Dimension.mdIcon),
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = contentColor,
                )
                if (activeBrandIndex == index) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.body1,
                        color = contentColor,
                    )
                }
            }
        }
    }
}
