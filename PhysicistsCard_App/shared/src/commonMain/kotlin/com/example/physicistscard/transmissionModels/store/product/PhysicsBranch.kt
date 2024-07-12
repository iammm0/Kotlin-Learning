package com.example.physicistscard.transmissionModels.store.product

import kotlinx.serialization.Serializable

@Serializable
enum class PhysicsBranch {
    MECHANICS, // 力学
    THERMODYNAMICS, // 热力学
    QUANTUM_MECHANICS, // 量子力学
    ELECTROMAGNETISM, // 电磁学
    RELATIVITY, // 相对论
    PARTICLE_PHYSICS, // 粒子物理
    NUCLEAR_PHYSICS, // 核物理
    ASTROPHYSICS, // 天体物理
    CONDENSED_MATTER_PHYSICS, // 凝聚态物理
    FIELD_THEORY // 场论
}