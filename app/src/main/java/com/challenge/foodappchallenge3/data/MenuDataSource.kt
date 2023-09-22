package com.challenge.foodappchallenge3.data


import com.challenge.foodappchallenge3.R
import com.challenge.foodappchallenge3.model.Menu

interface MenuDataSource {
    fun getMenuData() : List<Menu>
}
class MenuDataSourceImplementation() : MenuDataSource{
    override fun getMenuData(): List<Menu> {
        return  mutableListOf(
            Menu(
                menuName = "Ayam Geprek",
                menuPrice = 10000.0,
                menuImg = R.drawable.iv_roasted_chicken,
                menuDesc = "Ayam Geprek adalah makanan ayam goreng tepung khas Indonesia yang diulek atau dilumatkan bersama sambal bajak."
            ),
            Menu(
                menuName = "Ayam Goyeng",
                menuPrice = 11000.0,
                menuImg = R.drawable.iv_fried_chicken,
                menuDesc = "Ayam Goyeng adalah hidangan yang dibuat dari potongan daging ayam yang telah dilapisi dengan tepung atau adonan encer yang dibumbui dan digoreng."
            ),
            Menu(
                menuName = "Ayam Bakar Mantab",
                menuPrice =14000.0,
                menuImg = R.drawable.iv_griiled_chicken,
                menuDesc = "Ayam bakar adalah hidangan dari potongan ayam dipanggang atau dibakar hingga matang dan memiliki cita rasa manis, pedas, dan beraroma rempah."
            ),
            Menu(
                menuName = "Nasi Goreng",
                menuPrice = 13000.0,
                menuImg = R.drawable.iv_fried_rice,
                menuDesc = "Nasi goreng merupakan sajian nasi yang digoreng dalam sebuah wajan atau penggorengan yang menghasilkan cita rasa berbeda karena dicampur dengan bumbu-bumbu seperti garam, bawang putih, bawang merah, merica, rempah-rempah tertentu dan kecap manis."
            ) ,  Menu(
                menuName = "Ayam Bakar Mantab Jiwa",
                menuPrice =23000.0,
                menuImg = R.drawable.iv_griiled_chicken,
                menuDesc = "Ayam bakar adalah hidangan dari potongan ayam dipanggang atau dibakar hingga matang dan memiliki cita rasa manis, pedas, dan beraroma rempah."
            ),
            Menu(
                menuName = "Nasi Goreng Spesial",
                menuPrice = 15000.0,
                menuImg = R.drawable.iv_fried_rice,
                menuDesc = "Nasi goreng merupakan sajian nasi yang digoreng dalam sebuah wajan atau penggorengan yang menghasilkan cita rasa berbeda karena dicampur dengan bumbu-bumbu seperti garam, bawang putih, bawang merah, merica, rempah-rempah tertentu dan kecap manis."
            )
        )
    }
}