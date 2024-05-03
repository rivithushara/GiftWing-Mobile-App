package com.myapp.giftwing.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myapp.giftwing.R
import com.myapp.giftwing.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [
        Advertisement::class,
        Manufacturer::class,
        Review::class,
        User::class,
        PaymentProvider::class,
        UserPaymentProvider::class,
        Product::class,
        BookmarkItem::class,
        Location::class,
        CartItem::class,
        Order::class,
        OrderItem::class,
        OrderPayment::class,
        Notification::class,
        ProductColor::class,
        ProductSize::class,
    ],
    version = 1, exportSchema = false)
abstract class RoomDb : RoomDatabase() {

    /** A function that used to retrieve Room's related dao instance */
    abstract fun getDao(): RoomDao

    class PopulateDataClass @Inject constructor(
        private val client: Provider<RoomDb>,
        private val scope: CoroutineScope,
    ) : RoomDatabase.Callback() {
        private val description = "This is the description text .\n"
        private val manufacturers = listOf(
            Manufacturer(id = 1, name = "All", icon = R.drawable.all_logo),
            Manufacturer(id = 2, name = "Shoes", icon = R.drawable.shoes_logo),
            Manufacturer(id = 3, name = "Caps", icon = R.drawable.cap_logo),
            Manufacturer(id = 4, name = "T Shirts", icon = R.drawable.shirt_logo),
            Manufacturer(id = 5,  name = "Watches", icon = R.drawable.watch_logo),
        )
        private val advertisements = listOf(
            Advertisement(1, R.drawable.nike_haurache_new_ad, 1, 0),

        )
        // All Products
        private val allProducts = listOf(

            Product(
                id = 1,
                name = "Nike Air Huarache Gold",
                image = R.drawable.air_huarache_le_gold_black,
                price = 8500.00,
                description = description,
                manufacturerId = 1,
                mainColorName = "gold",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.mainColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "gray",
                        image = R.drawable.air_huarache_le_gray_dark),
                    ProductColor(productId = it.id,
                        colorName = "pink",
                        image = R.drawable.air_huarache_le_pink_black),
                    ProductColor(productId = it.id,
                        colorName = "red",
                        image = R.drawable.air_huarache_le_red_black),
                )
            },
            Product(
                id = 2,
                name = "Calvin Klein Men's Black T-Shirt",
                image = R.drawable.men_ck_black_tshirt,
                price = 3000.00,
                description = description,
                manufacturerId = 1,
                mainColorName = "black",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.mainColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "gray",
                        image = R.drawable.men_ck_tshirt_gray),
                    ProductColor(productId = it.id,
                        colorName = "red",
                        image = R.drawable.men_ck_tshirt_red),
                )
            },
            Product(
                id = 3,
                name = "Under Armour Cap",
                image = R.drawable.under_armour_black_cap,
                price = 1500.00,
                description = description,
                manufacturerId = 1,
                mainColorName = "black",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.mainColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "gray",
                        image = R.drawable.under_armour_gray_cap),
                    ProductColor(productId = it.id,
                        colorName = "pink",
                        image = R.drawable.under_armour_rose_cap),
                    ProductColor(productId = it.id,
                        colorName = "red",
                        image = R.drawable.underamour_red_cap),
                )
            },
            Product(
                id = 4,
                name = "Women's Adidas Defiant Generation Green",
                image = R.drawable.ladies_adidas_defiant_red,
                price = 11000.00,
                description = description,
                manufacturerId = 1,
                mainColorName = "green",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.mainColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "red",
                        image = R.drawable.ladies_adidas_defiant_green),
                )
            },

            Product(
                id = 5,
                name = "Women's Calvin klein T-Shirt",
                image = R.drawable.women_ck_tshirt_green,
                price = 2000.00,
                description = description,
                manufacturerId = 1,
                mainColorName = "dark-green",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.mainColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "lemon",
                        image = R.drawable.women_ck_tshirt_white),
                )
            },
            Product(
                id = 6,
                name = "Men's Nike Pegasus Trail",
                image = R.drawable.nike_pegasus_trail,
                price = 8000.00,
                description = description,
                manufacturerId = 1,
                mainColorName = "dark-green",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.mainColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "lemon",
                        image = R.drawable.pegasus_trail_3_gore_tex_lemon),
                )
            },
            Product(
                id = 7,
                name = "Nike Blazer Low Black",
                image = R.drawable.blazer_low_black,
                price = 7000.00,
                description = description,
                manufacturerId = 1,
                mainColorName = "black",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.mainColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "pink",
                        image = R.drawable.blazer_low_pink),
                    ProductColor(productId = it.id,
                        colorName = "lemon",
                        image = R.drawable.blazer_low_light_green),
                )
            },
        )

        //All Shoes
        private val adidasProducts = listOf(
            Product(
                id = 101,
                name = "Adidas Defiant Generation Green",
                image = R.drawable.ladies_adidas_defiant_green,
                price = 11000.00,
                description = description,
                manufacturerId = 2,
                mainColorName = "green",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.mainColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "red",
                        image = R.drawable.ladies_adidas_defiant_red),
                )
            },
            Product(
                id = 102,
                name = "Nike Air Huarache Gold",
                image = R.drawable.air_huarache_le_gold_black,
                price = 8500.00,
                description = description,
                manufacturerId = 2,
                mainColorName = "gold",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.mainColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "gray",
                        image = R.drawable.air_huarache_le_gray_dark),
                    ProductColor(productId = it.id,
                        colorName = "pink",
                        image = R.drawable.air_huarache_le_pink_black),
                    ProductColor(productId = it.id,
                        colorName = "red",
                        image = R.drawable.air_huarache_le_red_black),
                )
            },
            Product(
                id = 103,
                name = "Women's Adidas Defiant Generation Green",
                image = R.drawable.ladies_adidas_defiant_red,
                price = 11000.00,
                description = description,
                manufacturerId = 2,
                mainColorName = "green",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.mainColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "red",
                        image = R.drawable.ladies_adidas_defiant_green),
                )
            },

            Product(
                id = 105,
                name = "Men's Nike Pegasus Trail",
                image = R.drawable.nike_pegasus_trail,
                price = 8000.00,
                description = description,
                manufacturerId = 2,
                mainColorName = "dark-green",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.mainColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "lemon",
                        image = R.drawable.pegasus_trail_3_gore_tex_lemon),
                )
            },
            Product(
                id = 106,
                name = "Nike Blazer Low Black",
                image = R.drawable.blazer_low_black,
                price = 7000.00,
                description = description,
                manufacturerId = 2,
                mainColorName = "black",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.mainColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "pink",
                        image = R.drawable.blazer_low_pink),
                    ProductColor(productId = it.id,
                        colorName = "lemon",
                        image = R.drawable.blazer_low_light_green),
                )
            },

            Product(
                id = 107,
                name = "Adidas Solarthon Primegreen",
                image = R.drawable.adidas_solarthon_primegreen_gray,
                price = 10500.00,
                description = description,
                manufacturerId = 2,
                mainColorName = "gray",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.mainColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "black",
                        image = R.drawable.solarthon_primegreen_black),
                    ProductColor(productId = it.id,
                        colorName = "red",
                        image = R.drawable.solarthon_primegreen_red),
                )
            },
        )

        //payment
        private val paymentProviders = listOf(
            PaymentProvider(
                id = "apple",
                title = R.string.apple_pay,
                icon = R.drawable.ic_apple,
            ),
            PaymentProvider(
                id = "master",
                title = R.string.master_card,
                icon = R.drawable.ic_master_card,
            ),
            PaymentProvider(
                id = "visa",
                title = R.string.visa,
                icon = R.drawable.ic_visa,
            ),
        )
        private val userPaymentAccounts = listOf(
            UserPaymentProvider(
                providerId = "apple",
                cardNumber = "7878-8888-8989-2222"
            ),
            UserPaymentProvider(
                providerId = "master",
                cardNumber = "8888-4545-5664-8989"
            ),
            UserPaymentProvider(
                providerId = "visa",
                cardNumber = "7877-0283-8787-4574"
            ),
        )
        private val userLocation = Location(
            address = "Wetiya, Brawakumbuka",
            city = "Embilipitiya",
            country = "Sri Lanka",
        )

        init {
            allProducts.onEach {
                it.sizes = mutableListOf(
                    ProductSize(it.id, 38),
                    ProductSize(it.id, 40),
                    ProductSize(it.id, 42),
                    ProductSize(it.id, 44),
                    ProductSize(it.id, 48),
                )
            }
            adidasProducts.onEach {
                it.sizes = mutableListOf(
                    ProductSize(it.id, 38),
                    ProductSize(it.id, 40),
                    ProductSize(it.id, 42),
                    ProductSize(it.id, 44),
                    ProductSize(it.id, 48),
                )
            }

            scope.launch {
                populateDatabase(dao = client.get().getDao(), scope = scope)
            }
        }

        private suspend fun populateDatabase(dao: RoomDao, scope: CoroutineScope) {
            /** Save users */
            scope.launch {
                dao.saveUser(
                    User(
                        userId = 1,
                        name = "Rivi Thushara",
                        profile = R.drawable.rivi_profile,
                        phone = "+94712213341",
                        email = "rivithushara@gmail.com",
                        password = "rivi1010",
                        token = "778fs98f9s9898",
                    )
                )
            }
            /** insert manufacturers */
            scope.launch {
                manufacturers.forEach {
                    dao.insertManufacturer(it)
                }
            }
            /** insert advertisements */
            scope.launch {
                advertisements.forEach {
                    dao.insertAdvertisement(it)
                }
            }
            /** Insert products */
            scope.launch {
                allProducts.plus(adidasProducts).forEach {
                    /** Insert the product itself */
                    dao.insertProduct(product = it)
                    /** Insert colors */
                    it.colors?.forEach { productColor ->
                        dao.insertOtherProductCopy(productColor)
                    }
                    /** Insert size */
                    it.sizes?.forEach { productSize ->
                        dao.insertSize(productSize)
                    }
                }
            }
            /** Insert payment providers */
            scope.launch {
                paymentProviders.forEach {
                    dao.savePaymentProvider(paymentProvider = it)
                }
            }
            /** Insert user's payment providers */
            scope.launch {
                userPaymentAccounts.forEach {
                    dao.saveUserPaymentProvider(it)
                }
            }
            /** Insert user's location */
            scope.launch {
                dao.saveLocation(location = userLocation)
            }
        }
    }

}