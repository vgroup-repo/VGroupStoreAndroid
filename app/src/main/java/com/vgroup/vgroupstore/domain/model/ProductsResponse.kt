package com.vgroup.vgroupstore.domain.model


data class ProductsResponse(
    val products: List<ProductList>
)

data class ProductList(
    val id: Long,
    val title: String,
    val body_html: String?,
    val vendor: String?,
    val product_type: String?,
    val created_at: String?,
    val handle: String?,
    val updated_at: String?,
    val published_at: String?,
    val template_suffix: String?,
    val published_scope: String?,
    val tags: String?,
    val status: String?,
    val admin_graphql_api_id: String?,
    val variants: List<ProductVariant>?,
    val options: List<ProductOption>?,
    val images: List<ProductImage>?,
    val image: ProductImage?


)

data class ProductVariant(
    val id: Long,
    val product_id: Long,
    val title: String?,
    val price: String?,
    val position: Int?,
    val inventory_policy: String?,
    val compare_at_price: String?,
    val option1: String?,
    val option2: String?,
    val option3: String?,
    val created_at: String?,
    val updated_at: String?,
    val taxable: Boolean?,
    val barcode: String?,
    val fulfillment_service: String?,
    val grams: Int?,
    val inventory_management: String?,
    val requires_shipping: Boolean?,
    val sku: String?,
    val weight: Int?,
    val weight_unit: String?,
    val inventory_item_id: Long?,
    val inventory_quantity: Int?,
    val old_inventory_quantity: Int?,
    val admin_graphql_api_id: String?,
    val image_id: Long?
)

data class ProductOption(
    val id: Long,
    val product_id: Long,
    val name: String?,
    val position: Int?,
    val values: List<String>?
)

data class ProductImage(
    val id: Long,
    val alt: String?,
    val position: Int?,
    val product_id: Long?,
    val created_at: String?,
    val updated_at: String?,
    val admin_graphql_api_id: String?,
    val width: Int?,
    val height: Int?,
    val src: String?,
    val variant_ids: List<Long>?
)

