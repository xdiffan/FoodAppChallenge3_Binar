package com.challenge.foodappchallenge3.data


import com.challenge.foodappchallenge3.R
import com.challenge.foodappchallenge3.model.Menu

interface MenuDataSource {
    fun getMenuData() : List<Menu>
}
class MenuDataSourceImpl() : MenuDataSource{
    override fun getMenuData(): List<Menu> {
        return  mutableListOf(
            Menu(
                menuName = "Ayam Geprek",
                menuPrice = 35000.0,
                menuImg = R.drawable.iv_fried_chicken,
                menuDesc = "Ayam geprek adalah hidangan dari potongan ayam goreng yang digeprek atau dihancurkan dengan ulekan, lalu dilumuri dengan saus sambal pedas."
            ),
            Menu(
                menuName = "Ayam Goreng",
                menuPrice = 40000.0,
                menuImg = R.drawable.iv_fried_chicken,
                menuDesc = "Ayam goreng adalah hidangan ayam yang digoreng dalam minyak panas hingga kulitnya menjadi renyah dan dagingnya matang sempurna. Biasanya, ayam ini dibumbui dengan campuran rempah-rempah seperti garam, merica, kunyit, bawang putih, dan bawang merah untuk memberikan rasa yang gurih dan lezat."
            ),
            Menu(
                menuName = "Ayam Bakar Mantab",
                menuPrice = 50000.0,
                menuImg = R.drawable.iv_mie,
                menuDesc = "Ayam bakar adalah hidangan dari potongan ayam dipanggang atau dibakar hingga matang dan memiliki cita rasa manis, pedas, dan beraroma rempah."
            ),
            Menu(
                menuName = "Sate Ayam",
                menuPrice = 30000.0,
                menuImg = R.drawable.iv_fried_rice,
                menuDesc = "Sate ayam adalah hidangan yang terbuat dari potongan daging ayam yang ditusuk dengan tusukan bambu atau sate, kemudian dipanggang di atas bara api atau gril. Daging ayam biasanya sebelumnya telah dibumbui dengan campuran kecap manis, bawang putih, bawang merah, dan rempah-rempah lainnya."
            ),  Menu(
                menuName = "Sate Ayam Pedas",
                menuPrice = 30000.0,
                menuImg = R.drawable.iv_fried_rice,
                menuDesc = "Sate ayam adalah hidangan yang terbuat dari potongan daging ayam yang ditusuk dengan tusukan bambu atau sate, kemudian dipanggang di atas bara api atau gril. Daging ayam biasanya sebelumnya telah dibumbui dengan campuran kecap manis, bawang putih, bawang merah, dan rempah-rempah lainnya."
            ),        Menu(
                menuName = "Sate Ayam Asin",
                menuPrice = 30000.0,
                menuImg = R.drawable.iv_fried_rice,
                menuDesc = "Sate ayam adalah hidangan yang terbuat dari potongan daging ayam yang ditusuk dengan tusukan bambu atau sate, kemudian dipanggang di atas bara api atau gril. Daging ayam biasanya sebelumnya telah dibumbui dengan campuran kecap manis, bawang putih, bawang merah, dan rempah-rempah lainnya."
            ),
        )
    }
}