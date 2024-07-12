package com.example.physicistscard.transmissionModels.store.product

import kotlinx.serialization.Serializable

@Serializable
enum class Physicist {
    EINSTEIN, // 爱因斯坦 相对论 光电效应
    NEWTON, // 牛顿 三大定律 色散 万有引力定律
    HAWKING, // 史蒂芬·霍金 天体物理学
    FEYNMAN, // 理查德·费曼，量子电动力学
    BOHR, // 尼尔斯·玻尔，原子结构和量子力学
    CURIE, // 居里夫人，放射性
    FARADAY, // 迈克尔·法拉第，电磁学
    MAXWELL, // 詹姆斯·克拉克·麦克斯韦，电磁理论
    PLANCK, // 马克斯·普朗克，量子理论
    HEISENBERG // 维尔纳·海森堡，量子力学的不确定性原理
}