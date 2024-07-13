val logbackVersion: String by project
val kotlinVersion: String by project
val exposedVersion: String by project
val ktorVersion: String by project
val postgresqlVersion: String by project
val kotlinxDateTimeVersion: String by project
val mindrotVersion: String by project
val fuelVersion: String by project
val jsonVersion: String by project
val resultVersion: String by project
val tcnativeVersion: String by project
val javatimeVersion: String by project
val hoconVersion: String by project
val coroutinesVersion: String by project
val mockkVersion: String by project
val testcontainersVersion: String by project
val hikariCPVersion: String by project
val flywayVersion: String by project
val lettuceVersion: String by project
val kafkaVersion: String by project
val elasticsearchVersion: String by project
val scrimageVersion: String by project
val hikaricpVersion: String by project
val ehcacheVersion: String by project
val aliyunJavaSdkDmVersion: String by project
val aliyunJavaSdkCoreVersion: String by project
val aliyunJavaSdkDysmsapiVersion: String by project
val koinVersion: String by project



plugins {
    kotlin("jvm") version "1.9.23"
    id("io.ktor.plugin") version "2.3.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    // 依赖注入
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-core-jvm:$koinVersion")
    // 程序行为记录
    implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
    // 用户认证
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")
    // java8 的工具库
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    // 缓存配置依赖
    implementation("org.ehcache:ehcache:$ehcacheVersion")
    // 阿里云短信服务开发组件
    implementation("com.aliyun:aliyun-java-sdk-dm:$aliyunJavaSdkDmVersion")
    implementation("com.aliyun:aliyun-java-sdk-core:$aliyunJavaSdkCoreVersion")
    implementation("com.aliyun:aliyun-java-sdk-dysmsapi:$aliyunJavaSdkDysmsapiVersion")
    // 日志管理
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // 数据库和JDBC
    implementation("org.postgresql:postgresql:$postgresqlVersion") // PostgresSQL数据库驱动
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion") // Exposed框架核心
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion") // Exposed框架的DAO模块
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion") // Exposed框架的JDBC支持
    implementation("org.jetbrains.exposed:exposed-java-time:$javatimeVersion") // Java时间API支持
    // 数据库扩展
    implementation("com.zaxxer:HikariCP:$hikariCPVersion") // // HikariCP连接池
    implementation("org.flywaydb:flyway-core:$flywayVersion") // 数据库迁移和版本控制工具
    implementation("io.lettuce:lettuce-core:$lettuceVersion") //  Redis：用于缓存、会话存储，减轻数据库负载，快速响应用户请求
    implementation("org.apache.kafka:kafka-clients:$kafkaVersion") // 用于处理异步消息，如订单处理、邮件发送、用户通知等
    // ktor协程支持
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    // Ktor服务器核心和特性支持
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion") // Ktor服务器核心
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion") // Netty引擎支持
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion") // 内容协商
    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion") // 认证支持
    implementation("io.ktor:ktor-server-host-common-jvm:$ktorVersion") // 通用主机支持
    implementation("io.ktor:ktor-server-status-pages-jvm:$ktorVersion") // 状态页面
    implementation("io.ktor:ktor-server-sessions-jvm:$ktorVersion") // 会话管理
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktorVersion") // 调用日志
    implementation("io.ktor:ktor-server-cors-jvm:$ktorVersion") // CORS支持
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion") // JSON序列化器
    implementation("io.ktor:ktor-server-auto-head-response:$ktorVersion") // 自动头响应
    implementation("io.ktor:ktor-server-method-override:$ktorVersion") // Http方法重构
    implementation("io.ktor:ktor-server-websockets-jvm:$ktorVersion") // 交互式通讯

    // Ktor客户端
    implementation("io.ktor:ktor-client-core:$ktorVersion") // Ktor客户端核心
    implementation("io.ktor:ktor-client-json:$ktorVersion") // JSON支持
    implementation("io.ktor:ktor-client-serialization:$ktorVersion") // 客户端序列化
    implementation("io.ktor:ktor-client-cio:$ktorVersion") // CIO引擎支持

    // 安全性和密码哈希
    implementation("at.favre.lib:bcrypt:0.10.2") // 密码哈希库

    // 图像处理
    implementation("com.sksamuel.scrimage:scrimage-core:$scrimageVersion")

    // Kotlin Multiplatform特定依赖
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion") // Android项目

    // 测试依赖
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion") // Ktor服务器测试支持
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion") // Kotlin测试和JUnit集成
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("io.ktor:ktor-server-test-host-jvm:2.3.11") // 用于Kotlin的mocking库，特别适合协程的测试
}
