package com.challenge.foodappchallenge3.data.dummy

import com.challenge.foodappchallenge3.model.Menu

interface DummyMenuDataSource {
    fun getMenuData(): List<Menu>
}

class DummyMenuDataSourceImplementation : DummyMenuDataSource {
    override fun getMenuData(): List<Menu> {
        return mutableListOf(
            Menu(
                id = 1,
                menuName = "Ayam Goreng",
                menuPrice = 11000.0,
                menuImg = "https://raw.githubusercontent.com/xdiffan/Asset-Challenge-FoodApp/main/iv_fried_chicken.webp",
                menuDesc = "Ayam Goreng adalah hidangan yang dibuat dari potongan daging ayam yang telah dilapisi dengan tepung atau adonan encer yang dibumbui dan digoreng."
            ),
            Menu(
                id = 2,
                menuName = "Ayam Geprek",
                menuPrice = 10000.0,
                menuImg = "https://raw.githubusercontent.com/xdiffan/Asset-Challenge-FoodApp/main/iv_roasted_chicken.jpg",
                menuDesc = "Ayam Geprek adalah makanan ayam goreng tepung khas Indonesia yang diulek atau dilumatkan bersama sambal bajak."
            ),

            Menu(
                id = 3,
                menuName = "Ayam Bakar Mantab",
                menuPrice = 14000.0,
                menuImg = "https://raw.githubusercontent.com/xdiffan/Asset-Challenge-FoodApp/main/iv_griiled_chicken.webp",
                menuDesc = "Ayam bakar adalah hidangan dari potongan ayam dipanggang atau dibakar hingga matang dan memiliki cita rasa manis, pedas, dan beraroma rempah."
            ),
            Menu(
                id = 4,
                menuName = "Nasi Goreng",
                menuPrice = 13000.0,
                menuImg = "https://raw.githubusercontent.com/xdiffan/Asset-Challenge-FoodApp/main/iv_fried_rice.webp",
                menuDesc = "Nasi goreng merupakan sajian nasi yang digoreng dalam sebuah wajan atau penggorengan yang menghasilkan cita rasa berbeda karena dicampur dengan bumbu-bumbu seperti garam, bawang putih, bawang merah, merica, rempah-rempah tertentu dan kecap manis."
            ), Menu(
                id = 5,
                menuName = "Ayam Bakar Mantab Jiwa",
                menuPrice = 23000.0,
                menuImg = "https://raw.githubusercontent.com/xdiffan/Asset-Challenge-FoodApp/main/iv_griiled_chicken.webp",
                menuDesc = "Ayam bakar adalah hidangan dari potongan ayam dipanggang atau dibakar hingga matang dan memiliki cita rasa manis, pedas, dan beraroma rempah."
            ),
            Menu(
                id = 6,
                menuName = "Nasi Goreng Spesial",
                menuPrice = 15000.0,
                menuImg = "https://raw.githubusercontent.com/xdiffan/Asset-Challenge-FoodApp/main/iv_fried_rice.webp",
                menuDesc = "Nasi goreng merupakan sajian nasi yang digoreng dalam sebuah wajan atau penggorengan yang menghasilkan cita rasa berbeda karena dicampur dengan bumbu-bumbu seperti garam, bawang putih, bawang merah, merica, rempah-rempah tertentu dan kecap manis."
            )
        )
    }
}