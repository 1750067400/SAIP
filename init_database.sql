-- =====================================================
-- 产业园促进会管理系统 - 数据库初始化脚本
-- 创建时间: 2024年
-- 说明: 此脚本用于创建数据库、表结构和初始数据
-- =====================================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS saip 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE saip;

-- =====================================================
-- 1. 用户表
-- =====================================================
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    real_name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    role VARCHAR(20) DEFAULT 'USER',
    is_active BOOLEAN DEFAULT TRUE,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_role (role),
    INDEX idx_is_active (is_active)
);

-- =====================================================
-- 2. 会员表
-- =====================================================
CREATE TABLE IF NOT EXISTS members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_code VARCHAR(50) UNIQUE,
    company_name VARCHAR(200) NOT NULL,
    contact_person VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    address TEXT,
    industry VARCHAR(100),
    member_type VARCHAR(50),
    join_date DATE,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    annual_fee DECIMAL(10,2),
    payment_status VARCHAR(20) DEFAULT 'UNPAID',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_member_code (member_code),
    INDEX idx_company_name (company_name),
    INDEX idx_status (status),
    INDEX idx_join_date (join_date)
);

-- =====================================================
-- 3. 预约表
-- =====================================================
CREATE TABLE IF NOT EXISTS appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT,
    service_type VARCHAR(100),
    appointment_date DATE,
    appointment_time TIME,
    description TEXT,
    status VARCHAR(20) DEFAULT 'PENDING',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES members(id),
    INDEX idx_member_id (member_id),
    INDEX idx_appointment_date (appointment_date),
    INDEX idx_status (status)
);

-- =====================================================
-- 4. 活动表
-- =====================================================
CREATE TABLE IF NOT EXISTS events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    event_type VARCHAR(50),
    start_time DATETIME,
    end_time DATETIME,
    location VARCHAR(200),
    max_participants INT,
    current_participants INT DEFAULT 0,
    status VARCHAR(20) DEFAULT 'UPCOMING',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_event_type (event_type),
    INDEX idx_start_time (start_time),
    INDEX idx_status (status)
);

-- =====================================================
-- 5. 项目表
-- =====================================================
CREATE TABLE IF NOT EXISTS projects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    project_type VARCHAR(50),
    status VARCHAR(20) DEFAULT 'PLANNING',
    start_date DATE,
    end_date DATE,
    budget DECIMAL(12,2),
    manager VARCHAR(100),
    progress INT DEFAULT 0,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_project_type (project_type),
    INDEX idx_status (status),
    INDEX idx_start_date (start_date)
);

-- =====================================================
-- 6. 文档表
-- =====================================================
CREATE TABLE IF NOT EXISTS documents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    document_type VARCHAR(50),
    file_path VARCHAR(500),
    file_size BIGINT,
    is_public BOOLEAN DEFAULT FALSE,
    uploaded_by BIGINT,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (uploaded_by) REFERENCES users(id),
    INDEX idx_document_type (document_type),
    INDEX idx_is_public (is_public),
    INDEX idx_created_time (created_time)
);

-- =====================================================
-- 7. 支付表
-- =====================================================
CREATE TABLE IF NOT EXISTS payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT,
    payment_type VARCHAR(50),
    amount DECIMAL(10,2),
    payment_date DATE,
    payment_method VARCHAR(50),
    status VARCHAR(20) DEFAULT 'PENDING',
    description TEXT,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES members(id),
    INDEX idx_member_id (member_id),
    INDEX idx_payment_date (payment_date),
    INDEX idx_status (status)
);

-- =====================================================
-- 8. 公司表
-- =====================================================
CREATE TABLE IF NOT EXISTS companies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    industry VARCHAR(100),
    scale VARCHAR(50),
    contact_person VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    address TEXT,
    registration_date DATE,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_industry (industry),
    INDEX idx_status (status),
    INDEX idx_registration_date (registration_date)
);

-- =====================================================
-- 9. 产业园表
-- =====================================================
CREATE TABLE IF NOT EXISTS industrial_parks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    location VARCHAR(200),
    area DECIMAL(10,2),
    established_date DATE,
    description TEXT,
    contact_person VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_location (location),
    INDEX idx_status (status)
);

-- =====================================================
-- 10. 系统日志表
-- =====================================================
CREATE TABLE IF NOT EXISTS system_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    action VARCHAR(100),
    description TEXT,
    ip_address VARCHAR(45),
    user_agent TEXT,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_user_id (user_id),
    INDEX idx_action (action),
    INDEX idx_created_time (created_time)
);

-- =====================================================
-- 11. 新闻表
-- =====================================================
CREATE TABLE IF NOT EXISTS news (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    summary VARCHAR(500),
    author VARCHAR(100),
    category VARCHAR(50),
    cover_image VARCHAR(255),
    is_published BOOLEAN DEFAULT FALSE,
    view_count INT DEFAULT 0,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    published_time DATETIME,
    INDEX idx_category (category),
    INDEX idx_author (author),
    INDEX idx_is_published (is_published),
    INDEX idx_created_time (created_time)
);

-- =====================================================
-- 12. 政策表
-- =====================================================
CREATE TABLE IF NOT EXISTS policies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    policy_type VARCHAR(50),
    issuing_authority VARCHAR(100),
    effective_date DATE,
    expiry_date DATE,
    is_active BOOLEAN DEFAULT TRUE,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_policy_type (policy_type),
    INDEX idx_effective_date (effective_date),
    INDEX idx_is_active (is_active)
);

-- =====================================================
-- 13. 内部消息表
-- =====================================================
CREATE TABLE IF NOT EXISTS internal_messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id BIGINT,
    receiver_id BIGINT,
    subject VARCHAR(200),
    content TEXT,
    is_read BOOLEAN DEFAULT FALSE,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES users(id),
    FOREIGN KEY (receiver_id) REFERENCES users(id),
    INDEX idx_sender_id (sender_id),
    INDEX idx_receiver_id (receiver_id),
    INDEX idx_is_read (is_read),
    INDEX idx_created_time (created_time)
);

-- =====================================================
-- 14. 会议表
-- =====================================================
CREATE TABLE IF NOT EXISTS meetings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    meeting_type VARCHAR(50),
    start_time DATETIME,
    end_time DATETIME,
    location VARCHAR(200),
    organizer_id BIGINT,
    max_participants INT,
    current_participants INT DEFAULT 0,
    status VARCHAR(20) DEFAULT 'SCHEDULED',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (organizer_id) REFERENCES users(id),
    INDEX idx_meeting_type (meeting_type),
    INDEX idx_start_time (start_time),
    INDEX idx_status (status)
);

-- =====================================================
-- 初始数据插入
-- =====================================================

-- 1. 用户数据
INSERT INTO users (username, password, real_name, email, phone, role) VALUES
('admin', 'YWRtaW4xMjM=', '系统管理员', 'admin@saip.com', '13800138000', 'ADMIN'),
('manager', 'bWFuYWdlcjEyMw==', '部门经理', 'manager@saip.com', '13800138001', 'MANAGER'),
('user1', 'dXNlcjEyMw==', '张三', 'zhangsan@company.com', '13800138002', 'USER'),
('user2', 'dXNlcjEyMw==', '李四', 'lisi@company.com', '13800138003', 'USER');

-- 2. 会员数据
INSERT INTO members (member_code, company_name, contact_person, phone, email, address, industry, member_type, join_date, status, annual_fee, payment_status) VALUES
('M001', '科技创新有限公司', '王总', '13900139001', 'wang@tech.com', '北京市朝阳区科技园区A座', '科技', '企业会员', '2024-01-15', 'ACTIVE', 5000.00, 'PAID'),
('M002', '绿色能源集团', '李经理', '13900139002', 'li@energy.com', '上海市浦东新区能源大厦', '能源', '企业会员', '2024-02-20', 'ACTIVE', 5000.00, 'PAID'),
('M003', '智能制造股份', '张总监', '13900139003', 'zhang@manufacture.com', '深圳市南山区智造园', '制造', '企业会员', '2024-03-10', 'ACTIVE', 5000.00, 'UNPAID'),
('M004', '金融服务公司', '陈经理', '13900139004', 'chen@finance.com', '广州市天河区金融中心', '金融', '企业会员', '2024-01-25', 'ACTIVE', 5000.00, 'PAID'),
('M005', '文化传媒集团', '刘总', '13900139005', 'liu@media.com', '杭州市西湖区文创园', '文化', '企业会员', '2024-02-15', 'ACTIVE', 5000.00, 'PAID');

-- 3. 活动数据
INSERT INTO events (title, description, event_type, start_time, end_time, location, max_participants, status) VALUES
('2024年产业园区发展论坛', '探讨产业园区发展趋势和机遇', '论坛', '2024-06-15 09:00:00', '2024-06-15 17:00:00', '北京国际会议中心', 200, 'UPCOMING'),
('科技创新项目路演', '优秀科技项目展示和投资对接', '路演', '2024-07-20 14:00:00', '2024-07-20 18:00:00', '上海科技大厦', 100, 'UPCOMING'),
('企业数字化转型培训', '帮助企业实现数字化转型', '培训', '2024-08-10 09:00:00', '2024-08-12 17:00:00', '深圳培训中心', 50, 'UPCOMING'),
('产业政策解读会', '最新产业政策解读和答疑', '会议', '2024-09-05 14:00:00', '2024-09-05 16:00:00', '广州政策中心', 150, 'UPCOMING');

-- 4. 项目数据
INSERT INTO projects (name, description, project_type, status, start_date, end_date, budget, manager, progress) VALUES
('智慧园区建设', '建设智能化产业园区管理系统', '建设', 'IN_PROGRESS', '2024-01-01', '2024-12-31', 1000000.00, '张经理', 60),
('企业服务平台', '开发企业服务在线平台', '开发', 'PLANNING', '2024-06-01', '2024-12-31', 500000.00, '李总监', 0),
('产业政策研究', '研究制定产业扶持政策', '研究', 'IN_PROGRESS', '2024-03-01', '2024-08-31', 200000.00, '王研究员', 40),
('招商引资项目', '吸引优质企业入驻园区', '招商', 'COMPLETED', '2024-01-01', '2024-05-31', 300000.00, '陈经理', 100);

-- 5. 新闻数据
INSERT INTO news (title, content, summary, author, category, is_published, view_count, published_time) VALUES
('产业园区发展新政策出台', '详细内容...', '最新产业园区发展政策解读', '政策部', '政策', TRUE, 150, '2024-05-01 10:00:00'),
('科技创新成果展示', '详细内容...', '园区企业科技创新成果展示', '科技部', '科技', TRUE, 89, '2024-05-05 14:30:00'),
('企业服务升级公告', '详细内容...', '园区企业服务体系全面升级', '服务部', '服务', TRUE, 67, '2024-05-10 09:15:00'),
('招商引资新进展', '详细内容...', '园区招商引资工作取得新进展', '招商部', '招商', TRUE, 123, '2024-05-15 16:45:00');

-- 6. 政策数据
INSERT INTO policies (title, content, policy_type, issuing_authority, effective_date, expiry_date, is_active) VALUES
('产业园区税收优惠政策', '详细政策内容...', '税收', '国家税务总局', '2024-01-01', '2024-12-31', TRUE),
('科技创新扶持政策', '详细政策内容...', '科技', '科技部', '2024-02-01', '2024-12-31', TRUE),
('企业融资支持政策', '详细政策内容...', '金融', '银保监会', '2024-03-01', '2024-12-31', TRUE),
('人才引进优惠政策', '详细政策内容...', '人才', '人社部', '2024-04-01', '2024-12-31', TRUE);

-- 7. 内部消息数据
INSERT INTO internal_messages (sender_id, receiver_id, subject, content, is_read) VALUES
(1, 2, '会议通知', '明天下午2点召开部门会议', FALSE),
(2, 1, '工作汇报', '本周工作完成情况汇报', FALSE),
(1, 3, '任务分配', '新项目任务分配通知', FALSE),
(3, 1, '进度汇报', '项目进展情况汇报', FALSE);

-- 8. 会议数据
INSERT INTO meetings (title, description, meeting_type, start_time, end_time, location, organizer_id, max_participants, status) VALUES
('部门周例会', '讨论本周工作进展和下周计划', '例会', '2024-06-10 14:00:00', '2024-06-10 15:30:00', '会议室A', 1, 20, 'SCHEDULED'),
('项目评审会', '评审新项目可行性', '评审', '2024-06-12 09:00:00', '2024-06-12 11:00:00', '会议室B', 2, 15, 'SCHEDULED'),
('政策研讨会', '讨论最新政策影响', '研讨', '2024-06-15 15:00:00', '2024-06-15 17:00:00', '会议室C', 1, 25, 'SCHEDULED');

-- =====================================================
-- 执行完成提示
-- =====================================================
SELECT '数据库初始化完成！' AS message;
SELECT COUNT(*) AS total_tables FROM information_schema.tables WHERE table_schema = 'saip'; 