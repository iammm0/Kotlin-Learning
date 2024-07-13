自 2024年12月30日 至 2024年7月3日
当天，也就是我，项目创建者学习完Kotlin语言基础后，第一次从ChatGPT口中Ktor这个轻量级异步框架起
PhysicistsCard_AppServer 项目建立已经大致有6个月

整个服务端系统大致分为两个分别是商城系统与社区系统。
两个系统共用一个登录认证系统。
用户权限通过Role字段被分离为普通用户，商家，社区管理员，商城管理员，超级管理员。
使用JWT认证实现API端点调用权限的隔离。
整个软件架构采取标准架构分离为模型层，数据访问层，服务层，路由层。
模型层中主要分为传输模型与数据库表模型。
前者用于集成业务逻辑数据字段和在路由层中用于集成与传输，后者用于通过Exposed数据库框架进行自动化管理数据库。
服务层中又分为业务逻辑与第三方服务调用。
前者用于集成数据访问层中CRUD操作与第三方服务的调用并暴露接口至路由层。
截至2024年7月3日下午16时48分。
商城系统和社区系统所设计api接口足以满足移动应用客户端需求。
另外在项目中还有两个特殊的模块，分别为 utils 与 configs
在utils中编写标准模块中所需的工具对象。
在configs中编写自动化管理数据库的脚本以及安装与配置项目所需的插件。

在此之前
数据模型层基本确定其对业务需求的覆盖度已达到基本要求
数据访问层针对数据库所有基本的常规CRUD操作接口实现完毕
在application.conf中参数配置完毕
登录认证系统基本开发完毕
数据库表设计与写入完毕
数据库与服务端程序的连接建立完毕

接下来要做的事情就是：
根据设计好的api端点，逐步开发服务层模块
将物流，支付等api集成到商城系统的第三方服务调用模块
集成其模块到其路由代码

### 登录认证系统API路由字段

#### 用户注册、登录和身份验证

- `POST /user/register`
- `POST /user/email-code-login`
- `POST /user/phone-code-login`
- `POST /user/send-code`
- `POST /user/login`
- `POST /user/password/change-password`
- `POST /user/become_seller/`
- `POST /user/logout`
- `POST /user/change-avatar`
- `POST /user/binding-phone`
- `POST /user/binding-email`
- `POST /user/add-account`

这些字段涵盖了用户注册、登录、身份验证和信息管理等功能，基本满足PhysCard的需求。

### 社区系统API路由字段

#### 推送、评论和互动

- `GET /community/posts`
- `GET /community/posts/{postId}`
- `POST /community/posts`
- `PUT /community/posts/{postId}`
- `DELETE /community/posts/{postId}`
- `POST /community/comments/{postId}`
- `GET /community/comments/{postId}`
- `DELETE /community/comments/{postId}/{commentId}`
- `GET /community/posts/{postId}`
- `POST /community/posts/{postId}/like`
- `DELETE /community/posts/{postId}/like`
- `POST /community/posts/{postId}/favorite`
- `DELETE /community/posts/{postId}/favorite`
- `GET /community/posts/{postId}/stats`

这些字段涵盖了社区系统中的推送发布、评论、点赞和收藏等互动功能，基本满足PhysCard的需求。

### 用户属性和设置API路由字段

#### 用户信息和设置

- `GET /user/profile`
- `PUT /user/profile`
- `GET /user/{userId}/posts`
- `GET /user/{userId}/community/favorites`
- `GET /user/{userId}/community/likes`
- `GET /user/{userId}/store/favorites`
- `GET /user/{userId}/store/likes`
- `GET /settings`
- `PUT /settings`

这些字段涵盖了用户个人信息和设置的获取和更新，基本满足PhysCard的需求。

### 商家API路由字段

#### 商品和订单管理

- `POST /merchant/products`
- `PUT /merchant/products/{productId}`
- `DELETE /merchant/products/{productId}`
- `GET /merchant/products`
- `GET /merchant/orders`
- `PUT /merchant/orders/{orderId}/status`
- `GET /merchant/payments/{orderId}`
- `POST /merchant/payments/{orderId}/shipping`
- `PUT /merchant/payments/{orderId}/shipping`
- `GET /store/merchant/analytics/sales`
- `GET /store/merchant/analytics/popularity`
- `POST /store/merchant/promotions`
- `POST /store/merchant/coupons`
- `GET /store/merchant/coupons`


### 超级管理员API路由字段

#### 管理员和用户管理

- `GET /admin/super/admins`
- `POST /admin/super/admins`
- `DELETE /admin/super/admins/{adminId}`
- `PUT /admin/super/admins/{adminId}/permissions`
- `GET /admin/super/users`
- `DELETE /admin/super/users/{userId}`


### 商城系统管理员API路由字段

#### 商品和订单审核

- `GET /admin/store/products`
- `PUT /admin/store/products/{productId}/review`
- `DELETE /admin/store/products/{productId}`
- `GET /admin/store/orders`
- `PUT /admin/store/orders/{orderId}/status`


### 社区系统管理员API路由字段

#### 社区内容审核

- `GET /admin/community/posts`
- `PUT /admin/community/posts/{postId}/review`
- `GET /admin/community/comments`
- `DELETE /admin/community/comments/{commentId}`
- `GET /admin/community/likes`
- `DELETE /admin/community/likes/{likeId}`

