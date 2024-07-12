package com.example.physicistscard.transmissionModels.store.product

import kotlinx.serialization.Serializable

@Serializable
enum class Era {
    ANCIENT, // 古代：早期哲学家和科学家对自然界的基本思考，如亚里士多德、阿基米德
    MEDIEVAL, // 中世纪：主要是天文学和哲学的发展，欧洲中世纪科学的逐步进步
    MODERN, // 现代：从牛顿力学开始，到19世纪末的电磁学和热力学的发展
    CONTEMPORARY // 当代：20世纪初至今，量子力学、相对论的创立及其后的发展
}