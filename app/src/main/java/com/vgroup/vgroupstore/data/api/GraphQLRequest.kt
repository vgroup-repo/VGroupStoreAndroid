package com.vgroup.vgroupstore.data.api

data class GraphQLRequest(
    val query: String,
    val variables: Map<String, Any>? = null
)


