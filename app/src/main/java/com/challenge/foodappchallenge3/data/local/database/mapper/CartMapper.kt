package com.challenge.foodappchallenge3.data.local.database.mapper

import com.challenge.foodappchallenge3.data.local.database.entity.CartEntity
import com.challenge.foodappchallenge3.data.local.database.relation.CartMenuRelation
import com.challenge.foodappchallenge3.model.Cart
import com.challenge.foodappchallenge3.model.CartMenu
// Entity > View Object
fun CartEntity?.toCart() = Cart(
    id = this?.id ?: 0,
    menuId = this?.menuId ?: 0,
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes.orEmpty()
)
// View Object > Entity
fun Cart?.toCartEntity() = CartEntity(
    id = this?.id,
    menuId = this?.menuId ?: 0,
    itemQuantity = this?.itemQuantity ?: 0,
    itemNotes = this?.itemNotes.orEmpty()
)
// list of entity > list of view object
fun List<CartEntity?>.toCartList() = this.map { it.toCart() }
// Entity > View Object
fun CartMenuRelation.toCartMenu() = CartMenu(
    cart = this.cart.toCart(),
    menu = this.menu.toMenu()
)
// list of entity > list of view object
fun List<CartMenuRelation>.toCartMenuList() = this.map { it.toCartMenu() }