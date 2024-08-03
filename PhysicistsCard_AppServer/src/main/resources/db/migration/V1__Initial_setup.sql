-- V1__Initial_migration.sql

-- Table: Users
CREATE TABLE Users (
    userId VARCHAR(50) PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(20),
    passwordHash VARCHAR(255) NOT NULL,
    avatarUrl VARCHAR(500),
    bio TEXT,
    registerDate TIMESTAMP NOT NULL,
    isEmailVerified BOOLEAN NOT NULL,
    isPhoneVerified BOOLEAN NOT NULL,
    role VARCHAR(255) NOT NULL
);

-- Table: RefreshTokens
CREATE TABLE RefreshTokens (
    tokenId VARCHAR(50) PRIMARY KEY,
    userId VARCHAR(50) NOT NULL REFERENCES Users(userId),
    token VARCHAR(255) UNIQUE NOT NULL,
    expiryDate TIMESTAMP NOT NULL,
    issuedAt TIMESTAMP NOT NULL
);

-- Table: UserAddresses
CREATE TABLE UserAddresses (
    addressId VARCHAR(50) PRIMARY KEY,
    userId VARCHAR(50) NOT NULL REFERENCES Users(userId),
    recipientName VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(20) NOT NULL,
    province VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    district VARCHAR(100) NOT NULL,
    street VARCHAR(255) NOT NULL,
    residentialCommunity VARCHAR(255),
    buildingNumber VARCHAR(50),
    unitNumber VARCHAR(50),
    roomNumber VARCHAR(50),
    zipCode VARCHAR(20) NOT NULL
);

-- Table: VerificationCodes
CREATE TABLE VerificationCodes (
    id SERIAL PRIMARY KEY,
    code VARCHAR(255) NOT NULL,
    target VARCHAR(255) NOT NULL,
    expiryDate TIMESTAMP NOT NULL,
    type VARCHAR(50) NOT NULL,
    isUsed BOOLEAN DEFAULT FALSE NOT NULL
);

-- Table: UserComments
CREATE TABLE UserComments (
    commentId VARCHAR(50) PRIMARY KEY,
    userId VARCHAR(50) NOT NULL,
    targetId VARCHAR(50) NOT NULL,
    targetType VARCHAR(10) NOT NULL,
    content TEXT NOT NULL,
    createdAt TIMESTAMP NOT NULL,
    parentId VARCHAR(50)
);

-- Table: UserFavorites
CREATE TABLE UserFavorites (
    favoriteId VARCHAR(50) PRIMARY KEY,
    userId VARCHAR(50) NOT NULL,
    targetId VARCHAR(50) NOT NULL,
    targetType VARCHAR(10) NOT NULL,
    createdAt TIMESTAMP NOT NULL
);

-- Table: UserLikes
CREATE TABLE UserLikes (
    likeId VARCHAR(50) PRIMARY KEY,
    userId VARCHAR(50) NOT NULL,
    targetId VARCHAR(50) NOT NULL,
    targetType VARCHAR(10) NOT NULL,
    createdAt TIMESTAMP NOT NULL
);

-- Table: Contents
CREATE TABLE Contents (
    contentId SERIAL PRIMARY KEY,
    postId INTEGER NOT NULL REFERENCES Posts(id),
    type VARCHAR(10) NOT NULL,
    order INTEGER NOT NULL
);

-- Table: ImageContents
CREATE TABLE ImageContents (
    contentId INTEGER PRIMARY KEY REFERENCES Contents(contentId),
    imageUrl VARCHAR(255) NOT NULL
);

-- Table: TextContents
CREATE TABLE TextContents (
    contentId INTEGER PRIMARY KEY REFERENCES Contents(contentId),
    text TEXT NOT NULL
);

-- Table: VideoContents
CREATE TABLE VideoContents (
    contentId INTEGER PRIMARY KEY REFERENCES Contents(contentId),
    videoUrl VARCHAR(255) NOT NULL
);

-- Table: PostCategories
CREATE TABLE PostCategories (
    categoryId VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

-- Table: Posts
CREATE TABLE Posts (
    id SERIAL PRIMARY KEY,
    userId VARCHAR(50) NOT NULL,
    title VARCHAR(255) NOT NULL,
    createdAt TIMESTAMP NOT NULL,
    updatedAt TIMESTAMP,
    category VARCHAR(50)
);

-- Table: PostTags
CREATE TABLE PostTags (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

-- Table: PostTagRelations
CREATE TABLE PostTagRelations (
    postId INTEGER NOT NULL REFERENCES Posts(id) ON DELETE CASCADE,
    tagId INTEGER NOT NULL REFERENCES PostTags(id) ON DELETE CASCADE,
    PRIMARY KEY (postId, tagId)
);

-- Table: StoreBags
CREATE TABLE StoreBags (
    bagId VARCHAR(50) PRIMARY KEY,
    userId VARCHAR(50) NOT NULL
);

-- Table: BagItems
CREATE TABLE BagItems (
    itemId SERIAL PRIMARY KEY,
    bagId VARCHAR(50) NOT NULL REFERENCES StoreBags(bagId),
    productId VARCHAR(50) NOT NULL,
    quantity INTEGER NOT NULL
);

-- Table: Orders
CREATE TABLE Orders (
    id SERIAL PRIMARY KEY,
    customerId INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL,
    totalAmount DOUBLE PRECISION NOT NULL,
    createdAt TIMESTAMP NOT NULL,
    updatedAt TIMESTAMP
);

-- Table: OrderItems
CREATE TABLE OrderItems (
    itemId SERIAL PRIMARY KEY,
    orderId INTEGER NOT NULL REFERENCES Orders(id),
    productId INTEGER NOT NULL,
    productName VARCHAR(255) NOT NULL,
    productImage VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL,
    pricePerItem DOUBLE PRECISION NOT NULL,
    productSpec VARCHAR(255)
);

-- Table: OrderHistories
CREATE TABLE OrderHistories (
    historyId VARCHAR(50) PRIMARY KEY,
    orderId INTEGER NOT NULL REFERENCES Orders(id),
    status VARCHAR(50) NOT NULL,
    changeReason VARCHAR(255),
    changedAt TIMESTAMP NOT NULL
);

-- Table: PaymentInfos
CREATE TABLE PaymentInfos (
    paymentId VARCHAR(50) PRIMARY KEY,
    orderId INTEGER NOT NULL REFERENCES Orders(id),
    paymentMethod VARCHAR(50) NOT NULL,
    amountPaid DOUBLE PRECISION NOT NULL,
    paymentStatus VARCHAR(50) NOT NULL,
    paymentDate TIMESTAMP NOT NULL,
    transactionId VARCHAR(255)
);

-- Table: Categories
CREATE TABLE Categories (
    categoryId VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

-- Table: Products
CREATE TABLE Products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    manufacturer VARCHAR(255),
    releaseDate DATE,
    price DOUBLE PRECISION NOT NULL,
    promotionalPrice DOUBLE PRECISION,
    isOnPromotion BOOLEAN NOT NULL,
    stockQuantity INTEGER NOT NULL,
    era VARCHAR(50) NOT NULL,
    physicsBranch VARCHAR(50) NOT NULL,
    bundleType VARCHAR(50) NOT NULL,
    ratings DOUBLE PRECISION,
    reviewCount INTEGER
);

-- Table: ProductDescriptions
CREATE TABLE ProductDescriptions (
    productId INTEGER NOT NULL REFERENCES Products(id),
    language VARCHAR(10) NOT NULL,
    text TEXT NOT NULL,
    PRIMARY KEY (productId, language)
);

-- Table: ProductPhysicists
CREATE TABLE ProductPhysicists (
    productId INTEGER NOT NULL REFERENCES Products(id),
    physicist VARCHAR(50) NOT NULL,
    PRIMARY KEY (productId, physicist)
);

-- Table: ProductTags
CREATE TABLE ProductTags (
    productId INTEGER NOT NULL REFERENCES Products(id),
    tag VARCHAR(50) NOT NULL,
    PRIMARY KEY (productId, tag)
);

-- Table: ProductVariants
CREATE TABLE ProductVariants (
    variantId VARCHAR(50) PRIMARY KEY,
    productId INTEGER NOT NULL REFERENCES Products(id),
    name VARCHAR(255) NOT NULL,
    stockQuantity INTEGER NOT NULL,
    additionalPrice DOUBLE PRECISION
);

-- Table: ShippingInfos
CREATE TABLE ShippingInfos (
    shippingId VARCHAR(50) PRIMARY KEY,
    orderId INTEGER NOT NULL REFERENCES Orders(id),
    carrier VARCHAR(255) NOT NULL,
    trackingNumber VARCHAR(255) NOT NULL,
    shippingStatus VARCHAR(50) NOT NULL,
    shippedDate TIMESTAMP,
    estimatedDeliveryDate TIMESTAMP,
    actualDeliveryDate TIMESTAMP
);