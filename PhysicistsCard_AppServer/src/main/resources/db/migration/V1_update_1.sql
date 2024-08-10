DROP TABLE IF EXISTS PostTagRelations CASCADE;
DROP TABLE IF EXISTS Contents CASCADE;
DROP TABLE IF EXISTS Posts CASCADE;
DROP TABLE IF EXISTS PostTags CASCADE;
DROP TABLE IF EXISTS PostCategories CASCADE;

-- 重新创建表的示例，具体根据你的需求调整
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE PostCategories (
    categoryId UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE Posts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    userId VARCHAR(50) NOT NULL,
    title VARCHAR(255) NOT NULL,
    createdAt TIMESTAMP NOT NULL,
    updatedAt TIMESTAMP NULL,
    category UUID REFERENCES PostCategories(categoryId)
);

CREATE TABLE PostTags (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE PostTagRelations (
    postId UUID REFERENCES Posts(id) ON DELETE CASCADE,
    tagId UUID REFERENCES PostTags(id) ON DELETE CASCADE,
    PRIMARY KEY (postId, tagId)
);

CREATE TABLE Contents (
    contentId UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    postId UUID REFERENCES Posts(id) ON DELETE CASCADE,
    type VARCHAR(50) NOT NULL,
    contentOrder INT NOT NULL, -- 使用不同的名称避免冲突
    textContent TEXT,
    imageUrl VARCHAR(255),
    videoUrl VARCHAR(255)
);