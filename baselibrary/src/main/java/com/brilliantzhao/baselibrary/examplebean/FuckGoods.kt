package com.brilliantzhao.baselibrary.examplebean

/**
 * description: 示例工程的数据类
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
data class FuckGoods(
        val _id: String,
        val createdAt: String,
        val desc: String,
        val images: Array<String>,
        val publishedAt: String,
        val source: String,
        val type: String,
        val url: String,
        val used: Boolean,
        val who: String

) {
    fun hasImg(): Boolean {
        return images != null
    }

    fun create() = createdAt.substring(0, 10)

}