package com.example.repositories.store

import com.example.models.transmissionModels.store.product.Category
import com.example.repositories.Repository

interface ICategoryRepository : Repository<Category, String> {
    // 其他特定于Category的接口方法可以在这里添加
}
