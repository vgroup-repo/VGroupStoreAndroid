package com.vgroup.vgroupstore.core

object Config {


    const val STORE_FRONT_TOKEN = "STORE_FRONT_TOKEN"
    const val ADMIN_API_TOKEN = "ADMIN_API_TOKEN"
    const val STORE_FRONT_URL = "STORE_FRONT_URL"//graphql.json for login and signup
    const val ADMIN_PRODUCTS_URL = "ADMIN_PRODUCTS_URL"//products.json

    val CUSTOMER_CREATE_QUERY = """
    mutation customerCreate(${'$'}input: CustomerCreateInput!) {
        customerCreate(input: ${'$'}input) {
            customer {
                id
                email
                firstName
                lastName
            }
            customerUserErrors {
                field
                message
            }
        }
    }
""".trimIndent()


    val LOGIN_QUERY = """
        mutation customerAccessTokenCreate(${'$'}input: CustomerAccessTokenCreateInput!) {
            customerAccessTokenCreate(input: ${'$'}input) {
                customerAccessToken {
                    accessToken
                    expiresAt
                }
                customerUserErrors {
                    message
                    field
                }
            }
        }
    """.trimIndent()

    val USER_QUERY = """
        query getCustomer(${"$"}token: String!) { 
          customer(customerAccessToken: ${"$"}token) { 
            id 
            firstName 
            lastName 
            email 
            phone
            orders(first: 5) { 
              edges { 
                node { 
                  orderNumber 
                  processedAt 
                  totalPriceV2 { 
                    amount 
                    currencyCode 
                  } 
                } 
              } 
            } 
          } 
        }
        """


}
