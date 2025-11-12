-- Step 1: Delete all existing data
DELETE FROM users;

-- Step 2: Reset the auto-increment sequence to start from 1
ALTER SEQUENCE users_id_seq RESTART WITH 1;

-- Step 3: Insert 20 CLIENT users
INSERT INTO users (email, password, full_name, phone, role, is_active, profile_image_url, created_at, updated_at) VALUES
('john.smith@client.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'John Smith', '+1-555-0101', 'CLIENT', true, 'https://i.pravatar.cc/150?img=1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sarah.johnson@client.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Sarah Johnson', '+1-555-0102', 'CLIENT', true, 'https://i.pravatar.cc/150?img=5', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('michael.williams@client.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Michael Williams', '+1-555-0103', 'CLIENT', true, 'https://i.pravatar.cc/150?img=12', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('emily.brown@client.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Emily Brown', '+1-555-0104', 'CLIENT', true, 'https://i.pravatar.cc/150?img=9', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('david.jones@client.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'David Jones', '+1-555-0105', 'CLIENT', true, 'https://i.pravatar.cc/150?img=13', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('jessica.garcia@client.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Jessica Garcia', '+1-555-0106', 'CLIENT', true, 'https://i.pravatar.cc/150?img=10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('robert.miller@client.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Robert Miller', '+1-555-0107', 'CLIENT', true, 'https://i.pravatar.cc/150?img=14', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('amanda.davis@client.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Amanda Davis', '+1-555-0108', 'CLIENT', true, 'https://i.pravatar.cc/150?img=20', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('christopher.rodriguez@client.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Christopher Rodriguez', '+1-555-0109', 'CLIENT', true, 'https://i.pravatar.cc/150?img=15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('jennifer.martinez@client.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Jennifer Martinez', '+1-555-0110', 'CLIENT', true, 'https://i.pravatar.cc/150?img=24', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('matthew.hernandez@client.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Matthew Hernandez', '+1-555-0111', 'CLIENT', true, 'https://i.pravatar.cc/150?img=17', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ashley.lopez@client.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Ashley Lopez', '+1-555-0112', 'CLIENT', true, 'https://i.pravatar.cc/150?img=23', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('daniel.gonzalez@client.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Daniel Gonzalez', '+1-555-0113', 'CLIENT', true, 'https://i.pravatar.cc/150?img=18', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('lisa.wilson@client.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Lisa Wilson', '+1-555-0114', 'CLIENT', true, 'https://i.pravatar.cc/150?img=29', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('james.anderson@client.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'James Anderson', '+1-555-0115', 'CLIENT', true, 'https://i.pravatar.cc/150?img=19', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('michelle.thomas@client.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Michelle Thomas', '+1-555-0116', 'CLIENT', true, 'https://i.pravatar.cc/150?img=26', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ryan.taylor@client.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Ryan Taylor', '+1-555-0117', 'CLIENT', true, 'https://i.pravatar.cc/150?img=21', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('elizabeth.moore@client.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Elizabeth Moore', '+1-555-0118', 'CLIENT', true, 'https://i.pravatar.cc/150?img=32', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('kevin.jackson@client.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Kevin Jackson', '+1-555-0119', 'CLIENT', true, 'https://i.pravatar.cc/150?img=22', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('nicole.white@client.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Nicole White', '+1-555-0120', 'CLIENT', true, 'https://i.pravatar.cc/150?img=27', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Step 4: Insert 20 FREELANCER users
INSERT INTO users (email, password, full_name, phone, role, is_active, profile_image_url, created_at, updated_at) VALUES
('alex.developer@freelancer.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Alex Developer', '+1-555-0201', 'FREELANCER', true, 'https://i.pravatar.cc/150?img=33', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('maria.designer@freelancer.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Maria Designer', '+1-555-0202', 'FREELANCER', true, 'https://i.pravatar.cc/150?img=38', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('carlos.writer@freelancer.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Carlos Writer', '+1-555-0203', 'FREELANCER', true, 'https://i.pravatar.cc/150?img=34', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sophia.marketer@freelancer.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Sophia Marketer', '+1-555-0204', 'FREELANCER', true, 'https://i.pravatar.cc/150?img=40', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('lucas.engineer@freelancer.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Lucas Engineer', '+1-555-0205', 'FREELANCER', true, 'https://i.pravatar.cc/150?img=35', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('olivia.consultant@freelancer.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Olivia Consultant', '+1-555-0206', 'FREELANCER', true, 'https://i.pravatar.cc/150?img=41', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ethan.analyst@freelancer.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Ethan Analyst', '+1-555-0207', 'FREELANCER', true, 'https://i.pravatar.cc/150?img=36', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('isabella.artist@freelancer.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Isabella Artist', '+1-555-0208', 'FREELANCER', true, 'https://i.pravatar.cc/150?img=43', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('noah.programmer@freelancer.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Noah Programmer', '+1-555-0209', 'FREELANCER', true, 'https://i.pravatar.cc/150?img=37', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('mia.photographer@freelancer.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Mia Photographer', '+1-555-0210', 'FREELANCER', true, 'https://i.pravatar.cc/150?img=45', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('william.architect@freelancer.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'William Architect', '+1-555-0211', 'FREELANCER', true, 'https://i.pravatar.cc/150?img=39', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('emma.translator@freelancer.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Emma Translator', '+1-555-0212', 'FREELANCER', true, 'https://i.pravatar.cc/150?img=47', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('benjamin.videographer@freelancer.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Benjamin Videographer', '+1-555-0213', 'FREELANCER', true, 'https://i.pravatar.cc/150?img=42', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ava.copywriter@freelancer.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Ava Copywriter', '+1-555-0214', 'FREELANCER', true, 'https://i.pravatar.cc/150?img=49', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('mason.developer@freelancer.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Mason Developer', '+1-555-0215', 'FREELANCER', true, 'https://i.pravatar.cc/150?img=44', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('charlotte.uiux@freelancer.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Charlotte UI/UX', '+1-555-0216', 'FREELANCER', true, 'https://i.pravatar.cc/150?img=48', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('jacob.seo@freelancer.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Jacob SEO Expert', '+1-555-0217', 'FREELANCER', true, 'https://i.pravatar.cc/150?img=46', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('amelia.editor@freelancer.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Amelia Editor', '+1-555-0218', 'FREELANCER', true, 'https://i.pravatar.cc/150?img=50', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('logan.devops@freelancer.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Logan DevOps', '+1-555-0219', 'FREELANCER', true, 'https://i.pravatar.cc/150?img=51', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('harper.illustrator@freelancer.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhLu', 'Harper Illustrator', '+1-555-0220', 'FREELANCER', true, 'https://i.pravatar.cc/150?img=52', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Step 5: Verify the data
SELECT COUNT(*) as total_users, role, COUNT(*) 
FROM users 
GROUP BY role;

-- Step 6: Display data
select * from users;