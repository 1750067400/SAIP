-- 产业园促进会管理系统数据库建表语句

-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone_number VARCHAR(20),
    role VARCHAR(20) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    last_login_time DATETIME,
    created_time DATETIME NOT NULL,
    updated_time DATETIME NOT NULL
);

-- 会员表
CREATE TABLE IF NOT EXISTS members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    company_name VARCHAR(200),
    contact_person VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(100),
    address TEXT,
    industry VARCHAR(100),
    member_type VARCHAR(20) NOT NULL,
    join_date DATE,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_time DATETIME NOT NULL,
    updated_time DATETIME NOT NULL
);

-- 预约服务表
CREATE TABLE IF NOT EXISTS appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT,
    service_type VARCHAR(50) NOT NULL,
    appointment_date DATETIME NOT NULL,
    description TEXT,
    status VARCHAR(20) DEFAULT 'PENDING',
    created_time DATETIME NOT NULL,
    updated_time DATETIME NOT NULL,
    FOREIGN KEY (member_id) REFERENCES members(id)
);

-- 活动表
CREATE TABLE IF NOT EXISTS events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    event_type VARCHAR(50) NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    location VARCHAR(200),
    max_participants INT,
    current_participants INT DEFAULT 0,
    is_published BOOLEAN DEFAULT FALSE,
    created_by BIGINT,
    created_time DATETIME NOT NULL,
    updated_time DATETIME NOT NULL,
    FOREIGN KEY (created_by) REFERENCES users(id)
);

-- 项目表
CREATE TABLE IF NOT EXISTS projects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    project_type VARCHAR(50) NOT NULL,
    budget DECIMAL(15,2),
    start_date DATETIME,
    end_date DATETIME,
    status VARCHAR(20) NOT NULL,
    progress INT DEFAULT 0,
    manager_id BIGINT,
    created_by BIGINT,
    created_time DATETIME NOT NULL,
    updated_time DATETIME NOT NULL,
    FOREIGN KEY (manager_id) REFERENCES users(id),
    FOREIGN KEY (created_by) REFERENCES users(id)
);

-- 文档表
CREATE TABLE IF NOT EXISTS documents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    file_name VARCHAR(255),
    file_path VARCHAR(500),
    file_size BIGINT,
    file_type VARCHAR(50),
    document_type VARCHAR(50) NOT NULL,
    is_public BOOLEAN DEFAULT FALSE,
    version VARCHAR(20) DEFAULT '1.0',
    uploaded_by BIGINT,
    created_time DATETIME NOT NULL,
    updated_time DATETIME NOT NULL,
    FOREIGN KEY (uploaded_by) REFERENCES users(id)
);

-- 财务记录表
CREATE TABLE IF NOT EXISTS payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT,
    payment_type VARCHAR(50) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    payment_date DATE NOT NULL,
    description TEXT,
    status VARCHAR(20) DEFAULT 'PENDING',
    created_time DATETIME NOT NULL,
    updated_time DATETIME NOT NULL,
    FOREIGN KEY (member_id) REFERENCES members(id)
);

-- 企业表
CREATE TABLE IF NOT EXISTS companies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    industry VARCHAR(100),
    contact_person VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(100),
    address TEXT,
    registration_date DATE,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_time DATETIME NOT NULL,
    updated_time DATETIME NOT NULL
);

-- 产业园表
CREATE TABLE IF NOT EXISTS industrial_parks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    location VARCHAR(200),
    total_area DECIMAL(10,2),
    occupied_area DECIMAL(10,2),
    occupancy_rate DECIMAL(5,2),
    description TEXT,
    created_time DATETIME NOT NULL,
    updated_time DATETIME NOT NULL
);

-- 系统日志表
CREATE TABLE IF NOT EXISTS system_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    action VARCHAR(100) NOT NULL,
    description TEXT,
    ip_address VARCHAR(45),
    user_agent TEXT,
    log_level VARCHAR(20) DEFAULT 'INFO',
    created_time DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 插入初始数据
INSERT INTO users (username, password, name, email, role, created_time, updated_time) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', '系统管理员', 'admin@example.com', 'SUPER_ADMIN', NOW(), NOW()),
('manager', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', '管理员', 'manager@example.com', 'ADMIN', NOW(), NOW()),
('user', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', '普通用户', 'user@example.com', 'USER', NOW(), NOW());

-- 插入示例会员数据
INSERT INTO members (name, company_name, contact_person, phone, email, industry, member_type, join_date, created_time, updated_time) VALUES
('张三', 'ABC科技有限公司', '张三', '13800138001', 'zhangsan@abc.com', '科技行业', 'REGULAR', '2023-01-15', NOW(), NOW()),
('李四', 'XYZ制造有限公司', '李四', '13800138002', 'lisi@xyz.com', '制造业', 'REGULAR', '2023-02-20', NOW(), NOW()),
('王五', 'DEF服务有限公司', '王五', '13800138003', 'wangwu@def.com', '服务业', 'VIP', '2023-03-10', NOW(), NOW());

-- 插入示例活动数据
INSERT INTO events (title, description, event_type, start_time, end_time, location, max_participants, is_published, created_by, created_time, updated_time) VALUES
('2024年产业政策解读培训', '解读最新产业政策，帮助企业了解政策导向', 'TRAINING', '2024-02-15 09:00:00', '2024-02-15 17:00:00', '产业园会议中心', 50, TRUE, 1, NOW(), NOW()),
('企业交流会', '促进企业间交流合作', 'NETWORKING', '2024-02-20 14:00:00', '2024-02-20 18:00:00', '产业园多功能厅', 30, TRUE, 1, NOW(), NOW());

-- 插入示例项目数据
INSERT INTO projects (name, description, project_type, budget, start_date, end_date, status, progress, manager_id, created_by, created_time, updated_time) VALUES
('产业园基础设施升级', '升级产业园基础设施，提升园区品质', 'INFRASTRUCTURE', 1000000.00, '2024-01-01 00:00:00', '2024-06-30 00:00:00', 'IN_PROGRESS', 60, 1, 1, NOW(), NOW()),
('企业培训项目', '为园区企业提供专业培训服务', 'TRAINING', 500000.00, '2024-02-01 00:00:00', '2024-12-31 00:00:00', 'PLANNING', 20, 2, 1, NOW(), NOW());

-- 新闻表
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

-- 政策表
CREATE TABLE IF NOT EXISTS policies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    policy_number VARCHAR(50),
    issuing_authority VARCHAR(100),
    policy_type VARCHAR(50),
    effective_date DATETIME,
    expiry_date DATETIME,
    is_urgent BOOLEAN DEFAULT FALSE,
    is_published BOOLEAN DEFAULT FALSE,
    view_count INT DEFAULT 0,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    published_time DATETIME,
    INDEX idx_policy_type (policy_type),
    INDEX idx_issuing_authority (issuing_authority),
    INDEX idx_is_published (is_published),
    INDEX idx_is_urgent (is_urgent),
    INDEX idx_effective_date (effective_date)
);

-- 内部消息表
CREATE TABLE IF NOT EXISTS internal_messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT,
    message_type VARCHAR(50),
    priority VARCHAR(20) DEFAULT 'NORMAL',
    is_read BOOLEAN DEFAULT FALSE,
    is_urgent BOOLEAN DEFAULT FALSE,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    read_time DATETIME,
    INDEX idx_sender_id (sender_id),
    INDEX idx_receiver_id (receiver_id),
    INDEX idx_message_type (message_type),
    INDEX idx_is_read (is_read),
    INDEX idx_is_urgent (is_urgent),
    INDEX idx_created_time (created_time)
);

-- 会议表
CREATE TABLE IF NOT EXISTS meetings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    meeting_type VARCHAR(50),
    start_time DATETIME NOT NULL,
    end_time DATETIME,
    location VARCHAR(200),
    organizer_id BIGINT NOT NULL,
    participants TEXT,
    agenda TEXT,
    status VARCHAR(20) DEFAULT 'SCHEDULED',
    is_online BOOLEAN DEFAULT FALSE,
    meeting_link VARCHAR(255),
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_organizer_id (organizer_id),
    INDEX idx_meeting_type (meeting_type),
    INDEX idx_status (status),
    INDEX idx_start_time (start_time),
    INDEX idx_is_online (is_online)
);

-- 插入示例新闻数据
INSERT INTO news (title, content, summary, author, category, is_published, view_count, created_time, updated_time, published_time) VALUES
('产业园发展新政策发布', '最新产业政策解读，助力企业发展', '新政策将为园区企业带来更多发展机遇', '管理员', '政策资讯', TRUE, 150, NOW(), NOW(), NOW()),
('企业技术创新成果展示', '展示园区企业最新技术创新成果', '多家企业展示创新技术，促进产业升级', '管理员', '企业动态', TRUE, 89, NOW(), NOW(), NOW());

-- 插入示例政策数据
INSERT INTO policies (title, content, policy_number, issuing_authority, policy_type, is_urgent, is_published, view_count, created_time, updated_time, published_time) VALUES
('关于促进产业园区高质量发展的指导意见', '详细政策内容...', 'POL-2024-001', '市政府', '产业政策', TRUE, TRUE, 200, NOW(), NOW(), NOW()),
('企业科技创新支持政策', '支持企业科技创新的具体措施', 'POL-2024-002', '科技局', '科技政策', FALSE, TRUE, 120, NOW(), NOW(), NOW());

-- 插入示例内部消息数据
INSERT INTO internal_messages (title, content, sender_id, receiver_id, message_type, priority, is_read, is_urgent, created_time) VALUES
('系统维护通知', '系统将于今晚进行维护升级', 1, NULL, '通知', 'NORMAL', FALSE, FALSE, NOW()),
('重要会议安排', '请相关人员参加重要会议', 1, 2, '会议', 'HIGH', FALSE, TRUE, NOW());

-- 插入示例会议数据
INSERT INTO meetings (title, description, meeting_type, start_time, end_time, location, organizer_id, participants, agenda, status, is_online, created_time, updated_time) VALUES
('月度工作总结会', '总结本月工作，安排下月计划', '工作总结', '2024-02-28 14:00:00', '2024-02-28 16:00:00', '会议室A', 1, '1,2,3', '工作总结、问题讨论、下月计划', 'SCHEDULED', FALSE, NOW(), NOW()),
('在线培训会议', '企业数字化转型培训', '培训会议', '2024-03-01 09:00:00', '2024-03-01 11:00:00', '线上会议室', 1, '1,2,3,4,5', '数字化转型趋势、案例分析、实施建议', 'SCHEDULED', TRUE, NOW(), NOW()); 