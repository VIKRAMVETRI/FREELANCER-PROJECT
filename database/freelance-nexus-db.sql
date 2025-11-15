DELETE FROM NOTIFICATIONS
DELETE FROM TRANSACTION_HISTORY
DELETE FROM PAYMENTS
DELETE FROM PROJECT_MILESTONES
DELETE FROM RATINGS
DELETE FROM PORTFOLIOS
DELETE FROM SKILLS
DELETE FROM PROPOSALS
DELETE FROM PROJECTS
DELETE FROM FREELANCERS
DELETE FROM USERS 

ALTER SEQUENCE users_id_seq RESTART WITH 1;
ALTER SEQUENCE freelancers_id_seq RESTART WITH 1;
ALTER SEQUENCE projects_id_seq RESTART WITH 1;
ALTER SEQUENCE proposals_id_seq RESTART WITH 1;
ALTER SEQUENCE skills_id_seq RESTART WITH 1;
ALTER SEQUENCE portfolios_id_seq RESTART WITH 1;
ALTER SEQUENCE ratings_id_seq RESTART WITH 1;
ALTER SEQUENCE project_milestones_id_seq RESTART WITH 1;
ALTER SEQUENCE payments_id_seq RESTART WITH 1;
ALTER SEQUENCE transaction_history_id_seq RESTART WITH 1;
ALTER SEQUENCE notifications_id_seq RESTART WITH 1;



-- =============================================
-- FREELANCING PLATFORM - SAMPLE DATA
-- =============================================

-- USERS TABLE (10 Freelancers + 10 Clients = 20 records)
INSERT INTO USERS (id, email, password, full_name, phone, bio, profile_image_url, is_active, created_at, updated_at) VALUES
-- Freelancers (IDs 1-10)
(1, 'sarah.johnson@email.com', '$2b$10$rN7ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqX', 'Sarah Johnson', '+1-555-0101', 'Full-stack developer with 5 years experience', 'https://i.pravatar.cc/150?img=1', TRUE, '2024-01-15 10:30:00', '2024-01-15 10:30:00'),
(2, 'michael.chen@email.com', '$2b$10$rN7ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqY', 'Michael Chen', '+1-555-0102', 'UI/UX Designer specializing in mobile apps', 'https://i.pravatar.cc/150?img=2', TRUE, '2024-01-20 09:15:00', '2024-01-20 09:15:00'),
(3, 'emily.rodriguez@email.com', '$2b$10$rN7ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqZ', 'Emily Rodriguez', '+1-555-0103', 'Content writer and copywriter', 'https://i.pravatar.cc/150?img=3', TRUE, '2024-01-22 14:20:00', '2024-01-22 14:20:00'),
(4, 'david.kumar@email.com', '$2b$10$rN7ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqA', 'David Kumar', '+1-555-0104', 'Data scientist and ML engineer', 'https://i.pravatar.cc/150?img=4', TRUE, '2024-01-25 11:45:00', '2024-01-25 11:45:00'),
(5, 'lisa.martinez@email.com', '$2b$10$rN7ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqB', 'Lisa Martinez', '+1-555-0105', 'Digital marketing specialist', 'https://i.pravatar.cc/150?img=5', TRUE, '2024-02-01 08:30:00', '2024-02-01 08:30:00'),
(6, 'james.wilson@email.com', '$2b$10$rN7ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqC', 'James Wilson', '+1-555-0106', 'Mobile app developer (iOS & Android)', 'https://i.pravatar.cc/150?img=6', TRUE, '2024-02-05 10:00:00', '2024-02-05 10:00:00'),
(7, 'amanda.taylor@email.com', '$2b$10$rN7ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqD', 'Amanda Taylor', '+1-555-0107', 'Graphic designer and brand strategist', 'https://i.pravatar.cc/150?img=7', TRUE, '2024-02-08 13:15:00', '2024-02-08 13:15:00'),
(8, 'robert.brown@email.com', '$2b$10$rN7ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqE', 'Robert Brown', '+1-555-0108', 'DevOps engineer and cloud architect', 'https://i.pravatar.cc/150?img=8', TRUE, '2024-02-12 09:45:00', '2024-02-12 09:45:00'),
(9, 'sophia.lee@email.com', '$2b$10$rN7ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqF', 'Sophia Lee', '+1-555-0109', 'Video editor and motion graphics artist', 'https://i.pravatar.cc/150?img=9', TRUE, '2024-02-15 15:30:00', '2024-02-15 15:30:00'),
(10, 'daniel.garcia@email.com', '$2b$10$rN7ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqG', 'Daniel Garcia', '+1-555-0110', 'Blockchain developer and smart contracts expert', 'https://i.pravatar.cc/150?img=10', TRUE, '2024-02-18 11:00:00', '2024-02-18 11:00:00'),

-- Clients (IDs 11-20)
(11, 'john.smith@techcorp.com', '$2b$10$rN7ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqH', 'John Smith', '+1-555-0201', 'CTO at TechCorp', 'https://i.pravatar.cc/150?img=11', TRUE, '2024-01-10 08:00:00', '2024-01-10 08:00:00'),
(12, 'jennifer.white@startup.io', '$2b$10$rN7ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqI', 'Jennifer White', '+1-555-0202', 'Product Manager at Startup.io', 'https://i.pravatar.cc/150?img=12', TRUE, '2024-01-12 09:30:00', '2024-01-12 09:30:00'),
(13, 'william.anderson@ecommerce.com', '$2b$10$rN7ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqJ', 'William Anderson', '+1-555-0203', 'CEO at E-Commerce Solutions', 'https://i.pravatar.cc/150?img=13', TRUE, '2024-01-18 10:15:00', '2024-01-18 10:15:00'),
(14, 'mary.thomas@marketing.agency', '$2b$10$rN7ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqK', 'Mary Thomas', '+1-555-0204', 'Marketing Director', 'https://i.pravatar.cc/150?img=14', TRUE, '2024-01-23 14:45:00', '2024-01-23 14:45:00'),
(15, 'richard.moore@fintech.com', '$2b$10$rN7ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqL', 'Richard Moore', '+1-555-0205', 'VP of Engineering at FinTech Inc', 'https://i.pravatar.cc/150?img=15', TRUE, '2024-01-28 11:20:00', '2024-01-28 11:20:00'),
(16, 'patricia.jackson@healthtech.com', '$2b$10$rN7ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqM', 'Patricia Jackson', '+1-555-0206', 'Founder at HealthTech Solutions', 'https://i.pravatar.cc/150?img=16', TRUE, '2024-02-03 09:00:00', '2024-02-03 09:00:00'),
(17, 'charles.harris@consulting.com', '$2b$10$rN7ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqN', 'Charles Harris', '+1-555-0207', 'Business Consultant', 'https://i.pravatar.cc/150?img=17', TRUE, '2024-02-07 13:30:00', '2024-02-07 13:30:00'),
(18, 'linda.martin@realestate.com', '$2b$10$rN7ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqO', 'Linda Martin', '+1-555-0208', 'Real Estate Agency Owner', 'https://i.pravatar.cc/150?img=18', TRUE, '2024-02-10 10:45:00', '2024-02-10 10:45:00'),
(19, 'christopher.thompson@education.org', '$2b$10$rN7ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqP', 'Christopher Thompson', '+1-555-0209', 'EdTech Platform Director', 'https://i.pravatar.cc/150?img=19', TRUE, '2024-02-14 08:15:00', '2024-02-14 08:15:00'),
(20, 'elizabeth.davis@nonprofit.org', '$2b$10$rN7ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqX1YvPxKqHdCeh1ZqKqJxqQ', 'Elizabeth Davis', '+1-555-0210', 'Non-Profit Organization Manager', 'https://i.pravatar.cc/150?img=20', TRUE, '2024-02-17 12:00:00', '2024-02-17 12:00:00');

-- FREELANCERS TABLE (20 records for the 10 freelancer users, some with multiple profiles)
INSERT INTO FREELANCERS (id, user_id, title, bio, hourly_rate, availability, average_rating, completed_projects, total_earnings) VALUES
(1, 1, 'Senior Full-Stack Developer', 'Experienced developer specializing in React, Node.js, and PostgreSQL. Built 50+ web applications.', 85.00, 'Full-time', 4.8, 42, 125000.00),
(2, 2, 'UI/UX Design Expert', 'Creating beautiful and intuitive user experiences for mobile and web platforms.', 70.00, 'Part-time', 4.9, 38, 98000.00),
(3, 3, 'Professional Content Writer', 'SEO-optimized content creation for blogs, websites, and marketing materials.', 45.00, 'Full-time', 4.7, 95, 67000.00),
(4, 4, 'Data Science Consultant', 'Machine learning, data analysis, and predictive modeling expert with PhD.', 95.00, 'Part-time', 4.9, 28, 110000.00),
(5, 5, 'Digital Marketing Strategist', 'ROI-focused marketing campaigns across social media, SEO, and paid advertising.', 65.00, 'Full-time', 4.6, 52, 89000.00),
(6, 6, 'Mobile App Developer', 'Native iOS and Android development with 7 years of experience.', 80.00, 'Full-time', 4.8, 35, 102000.00),
(7, 7, 'Brand & Graphic Designer', 'Complete brand identity design, logos, and marketing materials.', 60.00, 'Full-time', 4.7, 68, 78000.00),
(8, 8, 'DevOps & Cloud Engineer', 'AWS, Azure, Docker, Kubernetes - infrastructure automation specialist.', 90.00, 'Part-time', 4.9, 31, 115000.00),
(9, 9, 'Video Production Specialist', 'Professional video editing, motion graphics, and animation services.', 75.00, 'Full-time', 4.8, 44, 95000.00),
(10, 10, 'Blockchain Developer', 'Smart contracts, DeFi, NFT platforms - Ethereum and Solidity expert.', 100.00, 'Part-time', 4.9, 22, 98000.00);

-- PROJECTS TABLE (25 records)
INSERT INTO PROJECTS (id, client_id, assigned_freelancer, title, description, category, budget_min, budget_max, duration_days, required_skills, status, deadline, created_at, updated_at) VALUES
(1, 11, 1, 'E-Commerce Platform Development', 'Build a full-featured e-commerce platform with payment integration', 'Web Development', 8000.00, 12000.00, 60, 'React, Node.js, PostgreSQL, Stripe', 'COMPLETED', '2024-05-15', '2024-03-01 09:00:00', '2024-05-20 16:30:00'),
(2, 12, 2, 'Mobile App UI Redesign', 'Complete redesign of our mobile application user interface', 'Design', 3000.00, 5000.00, 30, 'Figma, UI/UX Design, Mobile Design', 'COMPLETED', '2024-04-20', '2024-03-10 10:30:00', '2024-04-25 14:00:00'),
(3, 13, 3, 'SEO Content Package', 'Create 20 SEO-optimized blog posts for e-commerce website', 'Content Writing', 1500.00, 2500.00, 45, 'SEO Writing, Content Marketing', 'IN_PROGRESS', '2024-12-01', '2024-10-15 11:00:00', '2024-11-01 09:00:00'),
(4, 14, 4, 'Customer Analytics Dashboard', 'Build ML-powered analytics dashboard for customer insights', 'Data Science', 6000.00, 9000.00, 40, 'Python, Machine Learning, Data Visualization', 'COMPLETED', '2024-06-30', '2024-05-01 08:30:00', '2024-07-05 17:00:00'),
(5, 15, 5, 'Social Media Marketing Campaign', 'Comprehensive 3-month social media marketing strategy', 'Marketing', 4000.00, 6000.00, 90, 'Social Media Marketing, Content Strategy', 'IN_PROGRESS', '2025-01-15', '2024-10-20 14:00:00', '2024-11-05 10:30:00'),
(6, 16, 6, 'Healthcare Mobile App', 'Develop a patient management mobile application', 'Mobile Development', 10000.00, 15000.00, 75, 'React Native, Healthcare, API Integration', 'IN_PROGRESS', '2025-01-30', '2024-11-01 09:00:00', '2024-11-10 15:00:00'),
(7, 17, 7, 'Corporate Branding Package', 'Complete brand identity including logo, guidelines, and templates', 'Design', 3500.00, 5500.00, 35, 'Branding, Logo Design, Adobe Creative Suite', 'COMPLETED', '2024-07-15', '2024-06-01 10:00:00', '2024-07-20 16:00:00'),
(8, 18, 8, 'Cloud Infrastructure Setup', 'Migrate and setup AWS cloud infrastructure with CI/CD', 'DevOps', 7000.00, 10000.00, 45, 'AWS, Docker, Kubernetes, CI/CD', 'COMPLETED', '2024-08-30', '2024-07-10 08:00:00', '2024-09-05 17:30:00'),
(9, 19, 9, 'Educational Video Series', 'Create 15 educational videos with motion graphics', 'Video Production', 5000.00, 8000.00, 60, 'Video Editing, Motion Graphics, After Effects', 'IN_PROGRESS', '2025-02-01', '2024-11-05 13:00:00', '2024-11-12 11:00:00'),
(10, 20, 10, 'NFT Marketplace Platform', 'Build a complete NFT marketplace on Ethereum', 'Blockchain', 12000.00, 18000.00, 90, 'Solidity, Web3, Smart Contracts, React', 'IN_PROGRESS', '2025-02-28', '2024-11-08 09:30:00', '2024-11-14 14:00:00'),
(11, 11, NULL, 'API Development Project', 'RESTful API development for mobile app backend', 'Web Development', 4000.00, 6000.00, 30, 'Node.js, Express, MongoDB', 'OPEN', '2025-01-15', '2024-11-10 10:00:00', '2024-11-10 10:00:00'),
(12, 12, NULL, 'Landing Page Design', 'Modern landing page design for SaaS product', 'Design', 1500.00, 2500.00, 14, 'Web Design, HTML/CSS, Figma', 'OPEN', '2024-12-30', '2024-11-11 11:30:00', '2024-11-11 11:30:00'),
(13, 13, 3, 'Product Descriptions', 'Write 100 product descriptions for online store', 'Content Writing', 800.00, 1200.00, 20, 'Copywriting, E-commerce', 'IN_PROGRESS', '2024-12-15', '2024-11-05 09:00:00', '2024-11-08 15:00:00'),
(14, 14, NULL, 'Data Migration Project', 'Migrate data from legacy system to new database', 'Data Science', 3000.00, 5000.00, 25, 'SQL, Python, ETL', 'OPEN', '2024-12-20', '2024-11-12 14:00:00', '2024-11-12 14:00:00'),
(15, 15, 5, 'Email Marketing Automation', 'Setup automated email marketing campaigns', 'Marketing', 2000.00, 3500.00, 21, 'Email Marketing, Marketing Automation', 'IN_PROGRESS', '2024-12-10', '2024-11-01 10:00:00', '2024-11-06 09:30:00'),
(16, 16, NULL, 'iOS App Development', 'Native iOS app for appointment booking', 'Mobile Development', 8000.00, 12000.00, 60, 'Swift, iOS Development, UI/UX', 'OPEN', '2025-02-15', '2024-11-13 08:30:00', '2024-11-13 08:30:00'),
(17, 17, 7, 'Marketing Materials Design', 'Brochures, flyers, and business cards design', 'Design', 1200.00, 2000.00, 15, 'Graphic Design, Print Design', 'COMPLETED', '2024-09-30', '2024-09-10 11:00:00', '2024-10-05 16:00:00'),
(18, 18, NULL, 'Server Monitoring Setup', 'Implement comprehensive server monitoring solution', 'DevOps', 2500.00, 4000.00, 20, 'Monitoring, Linux, DevOps', 'OPEN', '2024-12-25', '2024-11-14 09:00:00', '2024-11-14 09:00:00'),
(19, 19, NULL, 'Podcast Editing Service', 'Edit and produce 10 podcast episodes', 'Video Production', 1500.00, 2500.00, 30, 'Audio Editing, Podcast Production', 'OPEN', '2024-12-31', '2024-11-13 15:00:00', '2024-11-13 15:00:00'),
(20, 20, NULL, 'Smart Contract Audit', 'Security audit of existing smart contracts', 'Blockchain', 4000.00, 7000.00, 21, 'Solidity, Smart Contracts, Security', 'OPEN', '2024-12-28', '2024-11-14 10:30:00', '2024-11-14 10:30:00'),
(21, 11, 1, 'Website Performance Optimization', 'Optimize website loading speed and performance', 'Web Development', 2000.00, 3500.00, 14, 'Performance Optimization, React, Node.js', 'COMPLETED', '2024-10-15', '2024-09-25 08:00:00', '2024-10-20 17:00:00'),
(22, 12, 2, 'Dashboard UI Design', 'Design admin dashboard interface', 'Design', 2500.00, 4000.00, 21, 'UI Design, Dashboard Design, Figma', 'COMPLETED', '2024-08-31', '2024-08-05 09:30:00', '2024-09-05 15:30:00'),
(23, 14, 4, 'Predictive Analytics Model', 'Build predictive model for sales forecasting', 'Data Science', 5000.00, 8000.00, 35, 'Machine Learning, Python, Statistics', 'COMPLETED', '2024-09-30', '2024-08-20 10:00:00', '2024-10-05 16:00:00'),
(24, 16, 6, 'Telemedicine App MVP', 'Minimum viable product for telemedicine platform', 'Mobile Development', 9000.00, 13000.00, 55, 'React Native, Healthcare, Video Calling', 'COMPLETED', '2024-10-31', '2024-09-01 08:30:00', '2024-11-05 17:30:00'),
(25, 18, 8, 'Kubernetes Cluster Setup', 'Setup production-ready Kubernetes cluster', 'DevOps', 5000.00, 7500.00, 30, 'Kubernetes, Docker, Cloud Infrastructure', 'COMPLETED', '2024-09-15', '2024-08-10 09:00:00', '2024-09-20 16:00:00');

-- PROPOSALS TABLE (30 records - multiple proposals per open project)
INSERT INTO PROPOSALS (id, project_id, freelancer_id, cover_letter, proposal_budget, delivery_days, ai_score, status, submitted_at) VALUES
(1, 1, 1, 'I have extensive experience building e-commerce platforms with React and Node.js. I have successfully delivered 15+ similar projects with payment integration.', 10000.00, 55, 0.92, 'ACCEPTED', '2024-03-01 14:30:00'),
(2, 2, 2, 'Your mobile app redesign project aligns perfectly with my expertise. I have redesigned 20+ mobile applications with great user feedback.', 4000.00, 28, 0.88, 'ACCEPTED', '2024-03-10 16:00:00'),
(3, 3, 3, 'I am an experienced SEO content writer with proven results in improving organic traffic. I can deliver high-quality, engaging content.', 2000.00, 42, 0.85, 'ACCEPTED', '2024-10-15 13:30:00'),
(4, 4, 4, 'I specialize in building ML-powered dashboards. I have worked with customer analytics before and can deliver actionable insights.', 7500.00, 38, 0.90, 'ACCEPTED', '2024-05-01 10:45:00'),
(5, 5, 5, 'With 6 years in digital marketing, I have managed successful campaigns for 30+ clients. I can create a comprehensive strategy for you.', 5000.00, 85, 0.87, 'ACCEPTED', '2024-10-20 15:30:00'),
(6, 6, 6, 'I have developed 12 healthcare applications with HIPAA compliance. Your project is exactly what I specialize in.', 12000.00, 70, 0.91, 'ACCEPTED', '2024-11-01 11:00:00'),
(7, 7, 7, 'Brand identity is my passion. I have created complete branding packages for 40+ companies across various industries.', 4500.00, 32, 0.89, 'ACCEPTED', '2024-06-01 12:30:00'),
(8, 8, 8, 'As an AWS certified DevOps engineer, I have successfully migrated 25+ applications to cloud with zero downtime.', 8500.00, 42, 0.93, 'ACCEPTED', '2024-07-10 09:45:00'),
(9, 9, 9, 'I have produced over 100 educational videos with motion graphics. I can create engaging content that keeps viewers interested.', 6500.00, 58, 0.88, 'ACCEPTED', '2024-11-05 14:30:00'),
(10, 10, 10, 'I am a blockchain specialist with experience building 5 NFT marketplaces. I understand the technical and security requirements.', 15000.00, 85, 0.94, 'ACCEPTED', '2024-11-08 10:30:00'),
(11, 11, 1, 'I can build a robust RESTful API with proper documentation and testing. I have built 30+ APIs in the past.', 5000.00, 28, 0.89, 'PENDING', '2024-11-10 15:00:00'),
(12, 11, 6, 'Mobile backend development is my specialty. I can create scalable APIs with proper authentication and security.', 4800.00, 30, 0.86, 'PENDING', '2024-11-11 09:30:00'),
(13, 12, 2, 'Modern, conversion-focused landing pages are what I do best. I can create a design that drives results.', 2000.00, 12, 0.90, 'PENDING', '2024-11-11 14:00:00'),
(14, 12, 7, 'I have designed 50+ landing pages with average conversion rates above 15%. I understand user psychology.', 2200.00, 14, 0.87, 'PENDING', '2024-11-12 10:00:00'),
(15, 13, 3, 'Product descriptions that sell - that is my specialty. I understand e-commerce and can write compelling copy.', 1000.00, 18, 0.88, 'ACCEPTED', '2024-11-05 11:00:00'),
(16, 14, 4, 'Data migration is complex but I have successfully handled 15+ migration projects without data loss.', 4000.00, 23, 0.91, 'PENDING', '2024-11-12 16:30:00'),
(17, 14, 1, 'I have experience with database migrations and can ensure data integrity throughout the process.', 3800.00, 25, 0.84, 'PENDING', '2024-11-13 09:00:00'),
(18, 15, 5, 'Email marketing automation is crucial for growth. I have setup campaigns that achieved 40%+ open rates.', 2800.00, 20, 0.89, 'ACCEPTED', '2024-11-01 12:00:00'),
(19, 16, 6, 'Native iOS development ensures best performance. I have published 18 apps on the App Store.', 10000.00, 58, 0.92, 'PENDING', '2024-11-13 11:00:00'),
(20, 16, 1, 'I can develop the iOS app using Swift with a clean, maintainable codebase and proper testing.', 9500.00, 60, 0.85, 'PENDING', '2024-11-13 15:30:00'),
(21, 17, 7, 'Print design requires attention to detail. I have created marketing materials for 100+ businesses.', 1600.00, 14, 0.90, 'ACCEPTED', '2024-09-10 13:00:00'),
(22, 18, 8, 'Server monitoring is critical for uptime. I can implement comprehensive monitoring with alerting.', 3200.00, 18, 0.91, 'PENDING', '2024-11-14 10:30:00'),
(23, 18, 1, 'I have experience setting up monitoring solutions using industry-standard tools.', 3000.00, 20, 0.82, 'PENDING', '2024-11-14 14:00:00'),
(24, 19, 9, 'Podcast editing is an art. I can make your episodes sound professional and engaging.', 2000.00, 28, 0.88, 'PENDING', '2024-11-13 16:00:00'),
(25, 19, 7, 'I have experience with audio editing and can deliver high-quality podcast episodes.', 1800.00, 30, 0.79, 'PENDING', '2024-11-14 09:00:00'),
(26, 20, 10, 'Smart contract security is my priority. I have audited 30+ contracts and found critical vulnerabilities.', 6000.00, 20, 0.94, 'PENDING', '2024-11-14 11:30:00'),
(27, 20, 4, 'I can perform thorough security analysis using automated tools and manual review.', 5500.00, 21, 0.86, 'PENDING', '2024-11-14 15:00:00'),
(28, 21, 1, 'I can optimize your website performance using modern techniques and best practices.', 2800.00, 13, 0.91, 'ACCEPTED', '2024-09-25 10:00:00'),
(29, 22, 2, 'Dashboard UI design requires understanding of data visualization and user workflows.', 3200.00, 20, 0.89, 'ACCEPTED', '2024-08-05 11:30:00'),
(30, 23, 4, 'Predictive analytics using machine learning can transform your sales strategy.', 6500.00, 33, 0.92, 'ACCEPTED', '2024-08-20 12:00:00');

-- SKILLS TABLE (50 records - multiple skills per freelancer)
INSERT INTO SKILLS (id, freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(1, 1, 'React', 'Expert', 5),
(2, 1, 'Node.js', 'Expert', 5),
(3, 1, 'PostgreSQL', 'Advanced', 4),
(4, 1, 'TypeScript', 'Advanced', 3),
(5, 1, 'AWS', 'Intermediate', 2),
(6, 2, 'Figma', 'Expert', 6),
(7, 2, 'Adobe XD', 'Expert', 5),
(8, 2, 'UI Design', 'Expert', 6),
(9, 2, 'UX Research', 'Advanced', 4),
(10, 2, 'Prototyping', 'Expert', 5),
(11, 3, 'SEO Writing', 'Expert', 7),
(12, 3, 'Content Strategy', 'Advanced', 5),
(13, 3, 'Copywriting', 'Expert', 6),
(14, 3, 'WordPress', 'Advanced', 4),
(15, 3, 'Technical Writing', 'Advanced', 3),
(16, 4, 'Python', 'Expert', 8),
(17, 4, 'Machine Learning', 'Expert', 6),
(18, 4, 'TensorFlow', 'Advanced', 4),
(19, 4, 'Data Visualization', 'Expert', 5),
(20, 4, 'SQL', 'Expert', 7),
(21, 5, 'Social Media Marketing', 'Expert', 6),
(22, 5, 'Google Ads', 'Advanced', 5),
(23, 5, 'SEO', 'Advanced', 4),
(24, 5, 'Content Marketing', 'Expert', 6),
(25, 5, 'Analytics', 'Advanced', 4),
(26, 6, 'Swift', 'Expert', 7),
(27, 6, 'Kotlin', 'Advanced', 5),
(28, 6, 'React Native', 'Expert', 4),
(29, 6, 'iOS Development', 'Expert', 7),
(30, 6, 'Android Development', 'Advanced', 5),
(31, 7, 'Adobe Illustrator', 'Expert', 8),
(32, 7, 'Adobe Photoshop', 'Expert', 8),
(33, 7, 'Brand Design', 'Expert', 7),
(34, 7, 'Logo Design', 'Expert', 6),
(35, 7, 'Print Design', 'Advanced', 5),
(36, 8, 'AWS', 'Expert', 6),
(37, 8, 'Docker', 'Expert', 5),
(38, 8, 'Kubernetes', 'Expert', 4),
(39, 8, 'CI/CD', 'Expert', 5),
(40, 8, 'Linux', 'Expert', 8),
(41, 9, 'Adobe Premiere Pro', 'Expert', 6),
(42, 9, 'After Effects', 'Expert', 5),
(43, 9, 'Motion Graphics', 'Expert', 4),
(44, 9, 'Color Grading', 'Advanced', 4),
(45, 9, 'Video Editing', 'Expert', 6),
(46, 10, 'Solidity', 'Expert', 4),
(47, 10, 'Web3.js', 'Expert', 3),
(48, 10, 'Smart Contracts', 'Expert', 4),
(49, 10, 'Ethereum', 'Expert', 4),
(50, 10, 'DeFi', 'Advanced', 2);

-- PORTFOLIOS TABLE (30 records)
INSERT INTO PORTFOLIOS (id, freelancer_id, title, description, image_url, technologies_used, completion_date) VALUES
(1, 1, 'E-Commerce Platform', 'Full-stack e-commerce solution with payment gateway integration', 'https://picsum.photos/seed/portfolio1/800/600', 'React, Node.js, PostgreSQL, Stripe', '2024-03-15'),
(2, 1, 'Task Management App', 'Collaborative task management application with real-time updates', 'https://picsum.photos/seed/portfolio2/800/600', 'React, Socket.io, MongoDB', '2024-01-20'),
(3, 1, 'Restaurant Booking System', 'Online reservation system for restaurant chains', 'https://picsum.photos/seed/portfolio3/800/600', 'Next.js, PostgreSQL, Tailwind CSS', '2023-11-10'),
(4, 2, 'Banking Mobile App', 'Modern mobile banking application redesign', 'https://picsum.photos/seed/portfolio4/800/600', 'Figma, Mobile UI, User Research', '2024-02-28'),
(5, 2, 'Fitness Tracking App', 'Complete UI/UX design for fitness and wellness app', 'https://picsum.photos/seed/portfolio5/800/600', 'Figma, Adobe XD, Prototyping', '2023-12-15'),
(6, 2, 'SaaS Dashboard', 'Admin dashboard design for B2B SaaS platform', 'https://picsum.photos/seed/portfolio6/800/600', 'Figma, Data Visualization, UI Design', '2024-04-05'),
(7, 3, 'Tech Blog Content', '50+ SEO-optimized articles for technology blog', 'https://picsum.photos/seed/portfolio7/800/600', 'SEO Writing, Content Strategy', '2024-03-30'),
(8, 3, 'E-Book: Digital Marketing Guide', 'Comprehensive guide on digital marketing strategies', 'https://picsum.photos/seed/portfolio8/800/600', 'Technical Writing, Research', '2023-10-20'),
(9, 3, 'Product Launch Campaign', 'Complete content package for product launch', 'https://picsum.photos/seed/portfolio9/800/600', 'Copywriting, Marketing Content', '2024-05-15'),
(10, 4, 'Sales Forecasting Model', 'Machine learning model for retail sales prediction', 'https://picsum.photos/seed/portfolio10/800/600', 'Python, TensorFlow, Data Analysis', '2024-04-20'),
(11, 4, 'Customer Churn Analysis', 'Predictive analytics for customer retention', 'https://picsum.photos/seed/portfolio11/800/600', 'Python, Machine Learning, Visualization', '2023-12-10'),
(12, 4, 'Sentiment Analysis Tool', 'Real-time sentiment analysis for social media', 'https://picsum.photos/seed/portfolio12/800/600', 'Python, NLP, APIs', '2024-06-01'),
(13, 5, 'Social Media Campaign', 'Multi-platform campaign with 300% engagement increase', 'https://picsum.photos/seed/portfolio13/800/600', 'Social Media, Content Strategy', '2024-05-10'),
(14, 5, 'SEO Strategy Implementation', 'Comprehensive SEO overhaul resulting in 250% traffic growth', 'https://picsum.photos/seed/portfolio14/800/600', 'SEO, Google Analytics, Content', '2023-11-25'),
(15, 5, 'Email Marketing Automation', 'Automated email campaigns with 45% open rate', 'https://picsum.photos/seed/portfolio15/800/600', 'Email Marketing, Automation', '2024-02-14'),
(16, 6, 'Food Delivery App', 'Complete food delivery platform for iOS and Android', 'https://picsum.photos/seed/portfolio16/800/600', 'React Native, Firebase, Maps API', '2024-06-30'),
(17, 6, 'Fitness Tracking App', 'Health and fitness tracking mobile application', 'https://picsum.photos/seed/portfolio17/800/600', 'Swift, HealthKit, Core Data', '2023-09-15'),
(18, 6, 'Social Networking App', 'Instagram-like social media application', 'https://picsum.photos/seed/portfolio18/800/600', 'Kotlin, Firebase, Android', '2024-03-20'),
(19, 7, 'Coffee Brand Identity', 'Complete branding package for artisan coffee company', 'https://picsum.photos/seed/portfolio19/800/600', 'Adobe Illustrator, Brand Strategy', '2024-04-15'),
(20, 7, 'Tech Startup Logo', 'Modern logo design for AI startup', 'https://picsum.photos/seed/portfolio20/800/600', 'Adobe Illustrator, Logo Design', '2023-12-05'),
(21, 7, 'Restaurant Menu Design', 'Elegant menu design for fine dining restaurant', 'https://picsum.photos/seed/portfolio21/800/600', 'Adobe InDesign, Print Design', '2024-07-10'),
(22, 8, 'Cloud Migration Project', 'Complete AWS migration for enterprise application', 'https://picsum.photos/seed/portfolio22/800/600', 'AWS, Docker, Terraform', '2024-05-25'),
(23, 8, 'CI/CD Pipeline Setup', 'Automated deployment pipeline for microservices', 'https://picsum.photos/seed/portfolio23/800/600', 'Jenkins, Docker, Kubernetes', '2023-11-30'),
(24, 8, 'Infrastructure as Code', 'Terraform implementation for multi-cloud setup', 'https://picsum.photos/seed/portfolio24/800/600', 'Terraform, AWS, Azure', '2024-08-15'),
(25, 9, 'Product Launch Video', 'High-impact product launch video with motion graphics', 'https://picsum.photos/seed/portfolio25/800/600', 'Premiere Pro, After Effects', '2024-06-20'),
(26, 9, 'Educational Course Videos', 'Series of 20 educational videos with animations', 'https://picsum.photos/seed/portfolio26/800/600', 'Video Editing, Motion Graphics', '2023-10-15'),
(27, 9, 'Corporate Promotional Video', 'Professional promotional video for B2B company', 'https://picsum.photos/seed/portfolio27/800/600', 'Premiere Pro, Color Grading', '2024-04-30'),
(28, 10, 'NFT Marketplace', 'Decentralized NFT trading platform on Ethereum', 'https://picsum.photos/seed/portfolio28/800/600', 'Solidity, Web3.js, React', '2024-07-15'),
(29, 10, 'DeFi Lending Protocol', 'Smart contracts for decentralized lending platform', 'https://picsum.photos/seed/portfolio29/800/600', 'Solidity, Ethereum, DeFi', '2023-12-20'),
(30, 10, 'Token Launch Platform', 'Platform for launching ERC-20 tokens', 'https://picsum.photos/seed/portfolio30/800/600', 'Solidity, Smart Contracts, Web3', '2024-05-05');

-- RATINGS TABLE (25 records)
INSERT INTO RATINGS (id, project_id, client_id, freelancer_id, rating, review, created_at) VALUES
(1, 1, 11, 1, 5, 'Excellent work! Sarah delivered a robust e-commerce platform that exceeded our expectations. Communication was clear throughout.', '2024-05-21 10:00:00'),
(2, 2, 12, 2, 5, 'Michael created an amazing UI design. Our users love the new interface. Highly recommend!', '2024-04-26 14:30:00'),
(3, 4, 14, 4, 5, 'David built an exceptional analytics dashboard. The ML insights are incredibly valuable to our business.', '2024-07-06 09:15:00'),
(4, 7, 17, 7, 5, 'Amanda delivered a beautiful brand identity. The logo perfectly captures our company vision.', '2024-07-21 11:45:00'),
(5, 8, 18, 8, 5, 'Robert handled our cloud migration flawlessly. Zero downtime and excellent documentation.', '2024-09-06 16:20:00'),
(6, 17, 17, 7, 4, 'Great marketing materials. Minor revisions needed but overall very satisfied.', '2024-10-06 13:00:00'),
(7, 21, 11, 1, 5, 'Website performance improved dramatically. Load times reduced by 60%. Great job!', '2024-10-21 15:30:00'),
(8, 22, 12, 2, 5, 'The dashboard UI is intuitive and beautiful. Users adapted to it immediately.', '2024-09-06 10:00:00'),
(9, 23, 14, 4, 5, 'The predictive model accuracy is impressive. Already seeing better sales forecasting.', '2024-10-06 14:45:00'),
(10, 24, 16, 6, 5, 'James delivered an excellent telemedicine app. Video calling works perfectly.', '2024-11-06 09:30:00'),
(11, 25, 18, 8, 5, 'Kubernetes setup is production-ready and well-documented. Smooth deployment.', '2024-09-21 11:15:00'),
(12, 1, 11, 1, 5, 'Payment integration works flawlessly. Sarah was very professional throughout.', '2024-05-22 08:00:00'),
(13, 2, 12, 2, 5, 'Mobile app looks stunning on both iOS and Android. Perfect pixel alignment.', '2024-04-27 16:00:00'),
(14, 4, 14, 4, 5, 'Data visualization in the dashboard is top-notch. Very easy to understand.', '2024-07-07 10:30:00'),
(15, 7, 17, 7, 4, 'Brand guidelines are comprehensive and easy to follow. Minor tweaks requested.', '2024-07-22 14:00:00'),
(16, 8, 18, 8, 5, 'CI/CD pipeline saves us hours every week. Excellent automation setup.', '2024-09-07 09:45:00'),
(17, 21, 11, 1, 5, 'Code quality is excellent. Clean, maintainable, and well-tested.', '2024-10-22 12:00:00'),
(18, 22, 12, 2, 5, 'Color scheme and typography choices are perfect for our brand.', '2024-09-07 15:30:00'),
(19, 23, 14, 4, 4, 'Model works well but needed some additional training data. Overall good.', '2024-10-07 11:00:00'),
(20, 24, 16, 6, 5, 'HIPAA compliance handled perfectly. Security was top priority.', '2024-11-07 13:45:00'),
(21, 25, 18, 8, 5, 'Auto-scaling works great. Infrastructure is rock solid.', '2024-09-22 10:00:00'),
(22, 1, 11, 1, 5, 'Sarah provided excellent support even after project completion.', '2024-05-23 14:30:00'),
(23, 7, 17, 7, 5, 'Templates provided are very useful for our marketing team.', '2024-07-23 16:15:00'),
(24, 8, 18, 8, 5, 'Monitoring and alerting setup is comprehensive. Great documentation.', '2024-09-08 08:30:00'),
(25, 24, 16, 6, 4, 'App works great. Would have liked more customization options.', '2024-11-08 11:00:00');

-- PROJECT_MILESTONES TABLE (40 records)
INSERT INTO PROJECT_MILESTONES (id, project_id, title, description, amount, due_date, completed_at, status) VALUES
(1, 1, 'Database Design', 'Complete database schema and ERD', 2000.00, '2024-03-15', '2024-03-14 17:00:00', 'COMPLETED'),
(2, 1, 'API Development', 'RESTful API endpoints for all features', 3000.00, '2024-04-01', '2024-03-30 16:30:00', 'COMPLETED'),
(3, 1, 'Frontend Development', 'Complete React frontend with responsive design', 3000.00, '2024-04-20', '2024-04-18 15:00:00', 'COMPLETED'),
(4, 1, 'Payment Integration', 'Stripe payment gateway integration and testing', 2000.00, '2024-05-10', '2024-05-08 14:00:00', 'COMPLETED'),
(5, 2, 'User Research', 'Conduct user interviews and create personas', 1000.00, '2024-03-20', '2024-03-19 12:00:00', 'COMPLETED'),
(6, 2, 'Wireframes', 'Create wireframes for all screens', 1200.00, '2024-04-01', '2024-03-31 16:00:00', 'COMPLETED'),
(7, 2, 'High-Fidelity Mockups', 'Design all screens in high fidelity', 1800.00, '2024-04-15', '2024-04-14 17:30:00', 'COMPLETED'),
(8, 3, 'Content Research', 'Research and outline 20 blog topics', 500.00, '2024-10-25', '2024-10-24 11:00:00', 'COMPLETED'),
(9, 3, 'First 10 Articles', 'Write and deliver first 10 articles', 1000.00, '2024-11-10', NULL, 'IN_PROGRESS'),
(10, 3, 'Final 10 Articles', 'Write and deliver final 10 articles', 1000.00, '2024-12-01', NULL, 'PENDING'),
(11, 4, 'Data Collection', 'Gather and clean customer data', 1500.00, '2024-05-15', '2024-05-14 10:00:00', 'COMPLETED'),
(12, 4, 'Model Development', 'Build and train ML models', 3000.00, '2024-06-05', '2024-06-03 16:00:00', 'COMPLETED'),
(13, 4, 'Dashboard Creation', 'Create interactive dashboard', 2500.00, '2024-06-25', '2024-06-23 15:30:00', 'COMPLETED'),
(14, 5, 'Strategy Development', 'Create comprehensive marketing strategy', 1500.00, '2024-11-05', '2024-11-04 14:00:00', 'COMPLETED'),
(15, 5, 'Content Creation', 'Create content calendar and assets', 2000.00, '2024-11-25', NULL, 'IN_PROGRESS'),
(16, 5, 'Campaign Execution', 'Launch and manage campaigns', 2500.00, '2025-01-15', NULL, 'PENDING'),
(17, 6, 'Requirements Analysis', 'Document all feature requirements', 2000.00, '2024-11-10', '2024-11-09 13:00:00', 'COMPLETED'),
(18, 6, 'UI/UX Design', 'Complete app design and prototypes', 3000.00, '2024-11-25', NULL, 'IN_PROGRESS'),
(19, 6, 'Development Phase 1', 'Core features development', 4000.00, '2024-12-20', NULL, 'PENDING'),
(20, 6, 'Testing and Launch', 'QA testing and app store submission', 3000.00, '2025-01-30', NULL, 'PENDING'),
(21, 7, 'Discovery Session', 'Brand workshop and strategy session', 1000.00, '2024-06-08', '2024-06-07 15:00:00', 'COMPLETED'),
(22, 7, 'Logo Concepts', 'Present 3 logo concepts', 1500.00, '2024-06-20', '2024-06-19 14:00:00', 'COMPLETED'),
(23, 7, 'Brand Guidelines', 'Complete brand guidelines document', 2000.00, '2024-07-10', '2024-07-09 16:30:00', 'COMPLETED'),
(24, 8, 'Infrastructure Planning', 'AWS architecture design and planning', 2000.00, '2024-07-20', '2024-07-19 11:00:00', 'COMPLETED'),
(25, 8, 'Migration Phase 1', 'Migrate database and core services', 3000.00, '2024-08-10', '2024-08-09 17:00:00', 'COMPLETED'),
(26, 8, 'CI/CD Setup', 'Implement automated deployment pipeline', 2500.00, '2024-08-25', '2024-08-24 15:00:00', 'COMPLETED'),
(27, 9, 'Script Development', 'Write scripts for all 15 videos', 1500.00, '2024-11-15', '2024-11-14 12:00:00', 'COMPLETED'),
(28, 9, 'First 5 Videos', 'Produce and deliver first 5 videos', 2000.00, '2024-11-30', NULL, 'IN_PROGRESS'),
(29, 9, 'Next 5 Videos', 'Produce and deliver next 5 videos', 2000.00, '2024-12-20', NULL, 'PENDING'),
(30, 9, 'Final 5 Videos', 'Produce and deliver final 5 videos', 2000.00, '2025-01-20', NULL, 'PENDING'),
(31, 10, 'Smart Contract Development', 'Develop all smart contracts', 5000.00, '2024-11-25', NULL, 'IN_PROGRESS'),
(32, 10, 'Frontend Development', 'Build Web3 frontend interface', 4000.00, '2024-12-20', NULL, 'PENDING'),
(33, 10, 'Security Audit', 'Conduct thorough security testing', 3000.00, '2025-01-15', NULL, 'PENDING'),
(34, 10, 'Deployment', 'Deploy to mainnet and documentation', 3000.00, '2025-02-28', NULL, 'PENDING'),
(35, 13, 'First 50 Descriptions', 'Write first 50 product descriptions', 500.00, '2024-11-20', NULL, 'IN_PROGRESS'),
(36, 13, 'Final 50 Descriptions', 'Write final 50 product descriptions', 500.00, '2024-12-15', NULL, 'PENDING'),
(37, 15, 'Email Template Design', 'Design email templates', 800.00, '2024-11-10', '2024-11-09 15:00:00', 'COMPLETED'),
(38, 15, 'Automation Setup', 'Configure automation workflows', 1200.00, '2024-11-25', NULL, 'IN_PROGRESS'),
(39, 15, 'Testing and Launch', 'Test campaigns and launch', 800.00, '2024-12-10', NULL, 'PENDING'),
(40, 21, 'Performance Audit', 'Complete site performance audit', 800.00, '2024-10-01', '2024-09-30 14:00:00', 'COMPLETED');

-- PAYMENTS TABLE (35 records)
INSERT INTO PAYMENTS (id, project_id, payer_id, payee_id, transaction_id, amount, currency, payment_method, upi_id, status, payment_date, description) VALUES
(1, 1, 11, 1, 'TXN_2024050001', 10000.00, 'USD', 'STRIPE', NULL, 'COMPLETED', '2024-05-20 18:00:00', 'Payment for E-Commerce Platform Development'),
(2, 2, 12, 2, 'TXN_2024042501', 4000.00, 'USD', 'PAYPAL', NULL, 'COMPLETED', '2024-04-25 16:30:00', 'Payment for Mobile App UI Redesign'),
(3, 4, 14, 4, 'TXN_2024070501', 7500.00, 'USD', 'STRIPE', NULL, 'COMPLETED', '2024-07-05 19:00:00', 'Payment for Customer Analytics Dashboard'),
(4, 7, 17, 7, 'TXN_2024072001', 4500.00, 'USD', 'BANK_TRANSFER', NULL, 'COMPLETED', '2024-07-20 17:30:00', 'Payment for Corporate Branding Package'),
(5, 8, 18, 8, 'TXN_2024090501', 8500.00, 'USD', 'STRIPE', NULL, 'COMPLETED', '2024-09-05 18:45:00', 'Payment for Cloud Infrastructure Setup'),
(6, 17, 17, 7, 'TXN_2024100501', 1600.00, 'USD', 'PAYPAL', NULL, 'COMPLETED', '2024-10-05 17:00:00', 'Payment for Marketing Materials Design'),
(7, 21, 11, 1, 'TXN_2024102001', 2800.00, 'USD', 'STRIPE', NULL, 'COMPLETED', '2024-10-20 18:30:00', 'Payment for Website Performance Optimization'),
(8, 22, 12, 2, 'TXN_2024090501', 3200.00, 'USD', 'STRIPE', NULL, 'COMPLETED', '2024-09-05 16:00:00', 'Payment for Dashboard UI Design'),
(9, 23, 14, 4, 'TXN_2024100501', 6500.00, 'USD', 'BANK_TRANSFER', NULL, 'COMPLETED', '2024-10-05 17:30:00', 'Payment for Predictive Analytics Model'),
(10, 24, 16, 6, 'TXN_2024110501', 12000.00, 'USD', 'STRIPE', NULL, 'COMPLETED', '2024-11-05 19:00:00', 'Payment for Telemedicine App MVP'),
(11, 25, 18, 8, 'TXN_2024092001', 5500.00, 'USD', 'STRIPE', NULL, 'COMPLETED', '2024-09-20 17:45:00', 'Payment for Kubernetes Cluster Setup'),
(12, 1, 11, 1, 'TXN_2024031501', 2000.00, 'USD', 'STRIPE', NULL, 'COMPLETED', '2024-03-15 10:00:00', 'Milestone: Database Design'),
(13, 1, 11, 1, 'TXN_2024040101', 3000.00, 'USD', 'STRIPE', NULL, 'COMPLETED', '2024-04-01 11:00:00', 'Milestone: API Development'),
(14, 1, 11, 1, 'TXN_2024042001', 3000.00, 'USD', 'STRIPE', NULL, 'COMPLETED', '2024-04-20 12:00:00', 'Milestone: Frontend Development'),
(15, 1, 11, 1, 'TXN_2024051001', 2000.00, 'USD', 'STRIPE', NULL, 'COMPLETED', '2024-05-10 14:00:00', 'Milestone: Payment Integration'),
(16, 3, 13, 3, 'TXN_2024102501', 500.00, 'USD', 'UPI', 'writer@upi', 'COMPLETED', '2024-10-25 15:00:00', 'Milestone: Content Research'),
(17, 3, 13, 3, 'TXN_2024111001', 1000.00, 'USD', 'UPI', 'writer@upi', 'PENDING', NULL, 'Milestone: First 10 Articles'),
(18, 5, 15, 5, 'TXN_2024110501', 1500.00, 'USD', 'PAYPAL', NULL, 'COMPLETED', '2024-11-05 16:00:00', 'Milestone: Strategy Development'),
(19, 5, 15, 5, 'TXN_2024112501', 2000.00, 'USD', 'PAYPAL', NULL, 'PENDING', NULL, 'Milestone: Content Creation'),
(20, 6, 16, 6, 'TXN_2024111001', 2000.00, 'USD', 'STRIPE', NULL, 'COMPLETED', '2024-11-10 14:00:00', 'Milestone: Requirements Analysis'),
(21, 6, 16, 6, 'TXN_2024112501', 3000.00, 'USD', 'STRIPE', NULL, 'PENDING', NULL, 'Milestone: UI/UX Design'),
(22, 9, 19, 9, 'TXN_2024111501', 1500.00, 'USD', 'BANK_TRANSFER', NULL, 'COMPLETED', '2024-11-15 15:00:00', 'Milestone: Script Development'),
(23, 9, 19, 9, 'TXN_2024113001', 2000.00, 'USD', 'BANK_TRANSFER', NULL, 'PENDING', NULL, 'Milestone: First 5 Videos'),
(24, 10, 20, 10, 'TXN_2024112501', 5000.00, 'USD', 'CRYPTO', NULL, 'PENDING', NULL, 'Milestone: Smart Contract Development'),
(25, 13, 13, 3, 'TXN_2024112001', 500.00, 'USD', 'UPI', 'writer@upi', 'PENDING', NULL, 'Milestone: First 50 Descriptions'),
(26, 15, 15, 5, 'TXN_2024111001', 800.00, 'USD', 'PAYPAL', NULL, 'COMPLETED', '2024-11-10 16:00:00', 'Milestone: Email Template Design'),
(27, 15, 15, 5, 'TXN_2024112501', 1200.00, 'USD', 'PAYPAL', NULL, 'PENDING', NULL, 'Milestone: Automation Setup'),
(28, 2, 12, 2, 'TXN_2024032001', 1000.00, 'USD', 'PAYPAL', NULL, 'COMPLETED', '2024-03-20 12:00:00', 'Milestone: User Research'),
(29, 2, 12, 2, 'TXN_2024040101', 1200.00, 'USD', 'PAYPAL', NULL, 'COMPLETED', '2024-04-01 13:00:00', 'Milestone: Wireframes'),
(30, 2, 12, 2, 'TXN_2024041501', 1800.00, 'USD', 'PAYPAL', NULL, 'COMPLETED', '2024-04-15 14:00:00', 'Milestone: High-Fidelity Mockups'),
(31, 4, 14, 4, 'TXN_2024051501', 1500.00, 'USD', 'STRIPE', NULL, 'COMPLETED', '2024-05-15 11:00:00', 'Milestone: Data Collection'),
(32, 4, 14, 4, 'TXN_2024060501', 3000.00, 'USD', 'STRIPE', NULL, 'COMPLETED', '2024-06-05 12:00:00', 'Milestone: Model Development'),
(33, 4, 14, 4, 'TXN_2024062501', 2500.00, 'USD', 'STRIPE', NULL, 'COMPLETED', '2024-06-25 13:00:00', 'Milestone: Dashboard Creation'),
(34, 7, 17, 7, 'TXN_2024060801', 1000.00, 'USD', 'BANK_TRANSFER', NULL, 'COMPLETED', '2024-06-08 10:00:00', 'Milestone: Discovery Session'),
(35, 7, 17, 7, 'TXN_2024062001', 1500.00, 'USD', 'BANK_TRANSFER', NULL, 'COMPLETED', '2024-06-20 11:00:00', 'Milestone: Logo Concepts');

-- TRANSACTION_HISTORY TABLE (50 records)
INSERT INTO TRANSACTION_HISTORY (id, payment_id, status_change, notes, changed_at) VALUES
(1, 1, 'PENDING', 'Payment initiated by client', '2024-05-20 17:00:00'),
(2, 1, 'COMPLETED', 'Payment processed successfully', '2024-05-20 18:00:00'),
(3, 2, 'PENDING', 'Payment initiated via PayPal', '2024-04-25 15:30:00'),
(4, 2, 'COMPLETED', 'PayPal payment confirmed', '2024-04-25 16:30:00'),
(5, 3, 'PENDING', 'Payment initiated by client', '2024-07-05 18:00:00'),
(6, 3, 'COMPLETED', 'Stripe payment successful', '2024-07-05 19:00:00'),
(7, 4, 'PENDING', 'Bank transfer initiated', '2024-07-20 16:00:00'),
(8, 4, 'COMPLETED', 'Bank transfer received', '2024-07-20 17:30:00'),
(9, 5, 'PENDING', 'Payment initiated by client', '2024-09-05 17:30:00'),
(10, 5, 'COMPLETED', 'Payment processed successfully', '2024-09-05 18:45:00'),
(11, 6, 'PENDING', 'PayPal payment initiated', '2024-10-05 16:00:00'),
(12, 6, 'COMPLETED', 'PayPal payment confirmed', '2024-10-05 17:00:00'),
(13, 7, 'PENDING', 'Payment initiated by client', '2024-10-20 17:30:00'),
(14, 7, 'COMPLETED', 'Stripe payment successful', '2024-10-20 18:30:00'),
(15, 8, 'PENDING', 'Payment initiated by client', '2024-09-05 15:00:00'),
(16, 8, 'COMPLETED', 'Payment processed successfully', '2024-09-05 16:00:00'),
(17, 9, 'PENDING', 'Bank transfer initiated', '2024-10-05 16:00:00'),
(18, 9, 'COMPLETED', 'Bank transfer received', '2024-10-05 17:30:00'),
(19, 10, 'PENDING', 'Payment initiated by client', '2024-11-05 18:00:00'),
(20, 10, 'COMPLETED', 'Stripe payment successful', '2024-11-05 19:00:00'),
(21, 11, 'PENDING', 'Payment initiated by client', '2024-09-20 16:45:00'),
(22, 11, 'COMPLETED', 'Payment processed successfully', '2024-09-20 17:45:00'),
(23, 12, 'PENDING', 'Milestone payment initiated', '2024-03-15 09:00:00'),
(24, 12, 'COMPLETED', 'Milestone payment completed', '2024-03-15 10:00:00'),
(25, 13, 'PENDING', 'Milestone payment initiated', '2024-04-01 10:00:00'),
(26, 13, 'COMPLETED', 'Milestone payment completed', '2024-04-01 11:00:00'),
(27, 14, 'PENDING', 'Milestone payment initiated', '2024-04-20 11:00:00'),
(28, 14, 'COMPLETED', 'Milestone payment completed', '2024-04-20 12:00:00'),
(29, 15, 'PENDING', 'Milestone payment initiated', '2024-05-10 13:00:00'),
(30, 15, 'COMPLETED', 'Milestone payment completed', '2024-05-10 14:00:00'),
(31, 16, 'PENDING', 'UPI payment initiated', '2024-10-25 14:00:00'),
(32, 16, 'COMPLETED', 'UPI payment received', '2024-10-25 15:00:00'),
(33, 17, 'PENDING', 'Awaiting milestone completion', '2024-11-10 10:00:00'),
(34, 18, 'PENDING', 'Payment initiated by client', '2024-11-05 15:00:00'),
(35, 18, 'COMPLETED', 'PayPal payment confirmed', '2024-11-05 16:00:00'),
(36, 19, 'PENDING', 'Awaiting milestone completion', '2024-11-15 10:00:00'),
(37, 20, 'PENDING', 'Payment initiated by client', '2024-11-10 13:00:00'),
(38, 20, 'COMPLETED', 'Stripe payment successful', '2024-11-10 14:00:00'),
(39, 21, 'PENDING', 'Awaiting milestone completion', '2024-11-20 10:00:00'),
(40, 22, 'PENDING', 'Payment initiated by client', '2024-11-15 14:00:00'),
(41, 22, 'COMPLETED', 'Bank transfer received', '2024-11-15 15:00:00'),
(42, 23, 'PENDING', 'Awaiting milestone completion', '2024-11-25 10:00:00'),
(43, 24, 'PENDING', 'Crypto payment initiated', '2024-11-20 09:00:00'),
(44, 25, 'PENDING', 'Awaiting milestone completion', '2024-11-18 10:00:00'),
(45, 26, 'PENDING', 'Payment initiated by client', '2024-11-10 15:00:00'),
(46, 26, 'COMPLETED', 'PayPal payment confirmed', '2024-11-10 16:00:00'),
(47, 27, 'PENDING', 'Awaiting milestone completion', '2024-11-22 10:00:00'),
(48, 28, 'PENDING', 'Milestone payment initiated', '2024-03-20 11:00:00'),
(49, 28, 'COMPLETED', 'Milestone payment completed', '2024-03-20 12:00:00'),
(50, 29, 'PENDING', 'Milestone payment initiated', '2024-04-01 12:00:00');

-- NOTIFICATIONS TABLE (45 records)
INSERT INTO NOTIFICATIONS (id, user_id, type, title, message, related_entity_id, related_entity_type, is_read, email_sent, recipient_email, created_at) VALUES
(1, 1, 'ACCOUNT_CREATED', 'Welcome to FreelancePlatform!', 'Your freelancer account has been created successfully.', 1, 'USER', TRUE, TRUE, 'sarah.johnson@email.com', '2024-01-15 10:30:00'),
(2, 11, 'ACCOUNT_CREATED', 'Welcome to FreelancePlatform!', 'Your client account has been created successfully.', 11, 'USER', TRUE, TRUE, 'john.smith@techcorp.com', '2024-01-10 08:00:00'),
(3, 11, 'PROJECT_CREATED', 'Project Posted Successfully', 'Your project "E-Commerce Platform Development" has been posted.', 1, 'PROJECT', TRUE, TRUE, 'john.smith@techcorp.com', '2024-03-01 09:00:00'),
(4, 1, 'PROPOSAL_SUBMITTED', 'Proposal Submitted', 'Your proposal for "E-Commerce Platform Development" has been submitted.', 1, 'PROPOSAL', TRUE, FALSE, 'sarah.johnson@email.com', '2024-03-01 14:30:00'),
(5, 11, 'PROPOSAL_SUBMITTED', 'New Proposal Received', 'You received a proposal from Sarah Johnson for your project.', 1, 'PROPOSAL', TRUE, TRUE, 'john.smith@techcorp.com', '2024-03-01 14:30:00'),
(6, 1, 'PROPOSAL_ACCEPTED', 'Proposal Accepted!', 'Congratulations! Your proposal has been accepted.', 1, 'PROPOSAL', TRUE, TRUE, 'sarah.johnson@email.com', '2024-03-02 10:00:00'),
(7, 11, 'PROJECT_ASSIGNED', 'Project Assigned', 'Sarah Johnson has been assigned to your project.', 1, 'PROJECT', TRUE, TRUE, 'john.smith@techcorp.com', '2024-03-02 10:00:00'),
(8, 1, 'PAYMENT_COMPLETED', 'Payment Received', 'You received $2,000 for milestone: Database Design', 12, 'PAYMENT', TRUE, TRUE, 'sarah.johnson@email.com', '2024-03-15 10:00:00'),
(9, 11, 'PAYMENT_COMPLETED', 'Payment Sent', 'Payment of $2,000 sent for milestone: Database Design', 12, 'PAYMENT', TRUE, TRUE, 'john.smith@techcorp.com', '2024-03-15 10:00:00'),
(10, 1, 'PAYMENT_COMPLETED', 'Payment Received', 'You received $3,000 for milestone: API Development', 13, 'PAYMENT', TRUE, TRUE, 'sarah.johnson@email.com', '2024-04-01 11:00:00'),
(11, 2, 'PROPOSAL_ACCEPTED', 'Proposal Accepted!', 'Your proposal for Mobile App UI Redesign has been accepted.', 2, 'PROPOSAL', TRUE, TRUE, 'michael.chen@email.com', '2024-03-11 09:00:00'),
(12, 12, 'PROJECT_ASSIGNED', 'Project Assigned', 'Michael Chen has been assigned to your project.', 2, 'PROJECT', TRUE, TRUE, 'jennifer.white@startup.io', '2024-03-11 09:00:00'),
(13, 3, 'PROPOSAL_ACCEPTED', 'Proposal Accepted!', 'Your proposal for SEO Content Package has been accepted.', 3, 'PROPOSAL', TRUE, TRUE, 'emily.rodriguez@email.com', '2024-10-15 14:00:00'),
(14, 13, 'PROJECT_ASSIGNED', 'Project Assigned', 'Emily Rodriguez has been assigned to your project.', 3, 'PROJECT', TRUE, TRUE, 'william.anderson@ecommerce.com', '2024-10-15 14:00:00'),
(15, 3, 'PAYMENT_COMPLETED', 'Payment Received', 'You received $500 for milestone: Content Research', 16, 'PAYMENT', TRUE, TRUE, 'emily.rodriguez@email.com', '2024-10-25 15:00:00'),
(16, 4, 'PROPOSAL_ACCEPTED', 'Proposal Accepted!', 'Your proposal for Customer Analytics Dashboard has been accepted.', 4, 'PROPOSAL', TRUE, TRUE, 'david.kumar@email.com', '2024-05-01 11:00:00'),
(17, 14, 'PROJECT_ASSIGNED', 'Project Assigned', 'David Kumar has been assigned to your project.', 4, 'PROJECT', TRUE, TRUE, 'mary.thomas@marketing.agency', '2024-05-01 11:00:00'),
(18, 5, 'PROPOSAL_ACCEPTED', 'Proposal Accepted!', 'Your proposal for Social Media Marketing Campaign has been accepted.', 5, 'PROPOSAL', TRUE, TRUE, 'lisa.martinez@email.com', '2024-10-20 16:00:00'),
(19, 15, 'PROJECT_ASSIGNED', 'Project Assigned', 'Lisa Martinez has been assigned to your project.', 5, 'PROJECT', TRUE, TRUE, 'richard.moore@fintech.com', '2024-10-20 16:00:00'),
(20, 6, 'PROPOSAL_ACCEPTED', 'Proposal Accepted!', 'Your proposal for Healthcare Mobile App has been accepted.', 6, 'PROPOSAL', TRUE, TRUE, 'james.wilson@email.com', '2024-11-01 12:00:00'),
(21, 16, 'PROJECT_ASSIGNED', 'Project Assigned', 'James Wilson has been assigned to your project.', 6, 'PROJECT', TRUE, TRUE, 'patricia.jackson@healthtech.com', '2024-11-01 12:00:00'),
(22, 7, 'PROPOSAL_ACCEPTED', 'Proposal Accepted!', 'Your proposal for Corporate Branding Package has been accepted.', 7, 'PROPOSAL', TRUE, TRUE, 'amanda.taylor@email.com', '2024-06-01 13:00:00'),
(23, 17, 'PROJECT_ASSIGNED', 'Project Assigned', 'Amanda Taylor has been assigned to your project.', 7, 'PROJECT', TRUE, TRUE, 'charles.harris@consulting.com', '2024-06-01 13:00:00'),
(24, 8, 'PROPOSAL_ACCEPTED', 'Proposal Accepted!', 'Your proposal for Cloud Infrastructure Setup has been accepted.', 8, 'PROPOSAL', TRUE, TRUE, 'robert.brown@email.com', '2024-07-10 10:00:00'),
(25, 18, 'PROJECT_ASSIGNED', 'Project Assigned', 'Robert Brown has been assigned to your project.', 8, 'PROJECT', TRUE, TRUE, 'linda.martin@realestate.com', '2024-07-10 10:00:00'),
(26, 9, 'PROPOSAL_ACCEPTED', 'Proposal Accepted!', 'Your proposal for Educational Video Series has been accepted.', 9, 'PROPOSAL', TRUE, TRUE, 'sophia.lee@email.com', '2024-11-05 14:00:00'),
(27, 19, 'PROJECT_ASSIGNED', 'Project Assigned', 'Sophia Lee has been assigned to your project.', 9, 'PROJECT', TRUE, TRUE, 'christopher.thompson@education.org', '2024-11-05 14:00:00'),
(28, 10, 'PROPOSAL_ACCEPTED', 'Proposal Accepted!', 'Your proposal for NFT Marketplace Platform has been accepted.', 10, 'PROPOSAL', TRUE, TRUE, 'daniel.garcia@email.com', '2024-11-08 11:00:00'),
(29, 20, 'PROJECT_ASSIGNED', 'Project Assigned', 'Daniel Garcia has been assigned to your project.', 10, 'PROJECT', TRUE, TRUE, 'elizabeth.davis@nonprofit.org', '2024-11-08 11:00:00'),
(30, 1, 'PAYMENT_COMPLETED', 'Payment Received', 'Final payment of $10,000 received for E-Commerce Platform Development', 1, 'PAYMENT', TRUE, TRUE, 'sarah.johnson@email.com', '2024-05-20 18:00:00'),
(31, 11, 'PAYMENT_COMPLETED', 'Payment Sent', 'Final payment of $10,000 sent for project completion', 1, 'PAYMENT', TRUE, TRUE, 'john.smith@techcorp.com', '2024-05-20 18:00:00'),
(32, 2, 'PAYMENT_COMPLETED', 'Payment Received', 'Payment of $4,000 received for Mobile App UI Redesign', 2, 'PAYMENT', TRUE, TRUE, 'michael.chen@email.com', '2024-04-25 16:30:00'),
(33, 12, 'PAYMENT_COMPLETED', 'Payment Sent', 'Payment of $4,000 sent for project completion', 2, 'PAYMENT', TRUE, TRUE, 'jennifer.white@startup.io', '2024-04-25 16:30:00'),
(34, 11, 'PROJECT_CREATED', 'Project Posted', 'Your project "API Development Project" has been posted.', 11, 'PROJECT', FALSE, TRUE, 'john.smith@techcorp.com', '2024-11-10 10:00:00'),
(35, 12, 'PROJECT_CREATED', 'Project Posted', 'Your project "Landing Page Design" has been posted.', 12, 'PROJECT', FALSE, TRUE, 'jennifer.white@startup.io', '2024-11-11 11:30:00'),
(36, 1, 'PROPOSAL_SUBMITTED', 'Proposal Submitted', 'Your proposal for "API Development Project" has been submitted.', 11, 'PROPOSAL', FALSE, FALSE, 'sarah.johnson@email.com', '2024-11-10 15:00:00'),
(37, 11, 'PROPOSAL_SUBMITTED', 'New Proposal Received', 'You received a proposal from Sarah Johnson.', 11, 'PROPOSAL', FALSE, TRUE, 'john.smith@techcorp.com', '2024-11-10 15:00:00'),
(38, 2, 'PROPOSAL_SUBMITTED', 'Proposal Submitted', 'Your proposal for "Landing Page Design" has been submitted.', 13, 'PROPOSAL', FALSE, FALSE, 'michael.chen@email.com', '2024-11-11 14:00:00'),
(39, 12, 'PROPOSAL_SUBMITTED', 'New Proposal Received', 'You received a proposal from Michael Chen.', 13, 'PROPOSAL', FALSE, TRUE, 'jennifer.white@startup.io', '2024-11-11 14:00:00'),
(40, 5, 'PAYMENT_COMPLETED', 'Payment Received', 'You received $1,500 for milestone: Strategy Development', 18, 'PAYMENT', TRUE, TRUE, 'lisa.martinez@email.com', '2024-11-05 16:00:00'),
(41, 15, 'PAYMENT_COMPLETED', 'Payment Sent', 'Payment of $1,500 sent for milestone completion', 18, 'PAYMENT', TRUE, TRUE, 'richard.moore@fintech.com', '2024-11-05 16:00:00'),
(42, 6, 'PAYMENT_COMPLETED', 'Payment Received', 'You received $2,000 for milestone: Requirements Analysis', 20, 'PAYMENT', TRUE, TRUE, 'james.wilson@email.com', '2024-11-10 14:00:00'),
(43, 16, 'PAYMENT_COMPLETED', 'Payment Sent', 'Payment of $2,000 sent for milestone completion', 20, 'PAYMENT', TRUE, TRUE, 'patricia.jackson@healthtech.com', '2024-11-10 14:00:00'),
(44, 9, 'PAYMENT_COMPLETED', 'Payment Received', 'You received $1,500 for milestone: Script Development', 22, 'PAYMENT', TRUE, TRUE, 'sophia.lee@email.com', '2024-11-15 15:00:00'),
(45, 19, 'PAYMENT_COMPLETED', 'Payment Sent', 'Payment of $1,500 sent for milestone completion', 22, 'PAYMENT', TRUE, TRUE, 'christopher.thompson@education.org', '2024-11-15 15:00:00');

-- =============================================
-- END OF INSERT STATEMENTS
-- =============================================

-- Optional: Verify record counts
-- SELECT 'USERS' as table_name, COUNT(*) as record_count FROM USERS
-- UNION ALL SELECT 'FREELANCERS', COUNT(*) FROM FREELANCERS
-- UNION ALL SELECT 'PROJECTS', COUNT(*) FROM PROJECTS
-- UNION ALL SELECT 'PROPOSALS', COUNT(*) FROM PROPOSALS
-- UNION ALL SELECT 'SKILLS', COUNT(*) FROM SKILLS
-- UNION ALL SELECT 'PORTFOLIOS', COUNT(*) FROM PORTFOLIOS
-- UNION ALL SELECT 'RATINGS', COUNT(*) FROM RATINGS
-- UNION ALL SELECT 'PROJECT_MILESTONES', COUNT(*) FROM PROJECT_MILESTONES
-- UNION ALL SELECT 'PAYMENTS', COUNT(*) FROM PAYMENTS
-- UNION ALL SELECT 'TRANSACTION_HISTORY', COUNT(*) FROM TRANSACTION_HISTORY
-- UNION ALL SELECT 'NOTIFICATIONS', COUNT(*) FROM NOTIFICATIONS;