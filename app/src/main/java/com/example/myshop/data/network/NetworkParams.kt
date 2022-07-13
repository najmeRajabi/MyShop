package com.example.myshop.data.network

class NetworkParams {
    companion object {
        const val BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/"
        //private const val KEY = "ck_4e34eda4882e044742e643e35060ab5234231ab3"
        private const val KEY = "ck_63f4c52da932ddad1570283b31f3c96c4bd9fd6f"
        //private const val SECRET = "cs_28f528089254ed3dc6bc8cbd0ffeb34a52f69547"
        private const val SECRET = "cs_294e7de35430398f323b43c21dd1b29f67b5370b"

        fun getBaseOptions(): Map<String , String>{
            val baseOption = HashMap<String , String>()
            baseOption["consumer_key"] = KEY
            baseOption["consumer_secret"] = SECRET
            return baseOption
        }
    }


}