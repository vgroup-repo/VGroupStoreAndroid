package com.vgroup.vgroupstore.domain.model


fun ProductList.toProduct(): Product {
    return Product(
        id = this.id,
        title = this.title,
        price = this.variants?.firstOrNull()?.price?.toDoubleOrNull() ?: 0.0,
        image = this.image?.src ?: ""
    )
}
