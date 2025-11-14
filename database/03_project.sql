DELETE FROM project_milestones;
ALTER SEQUENCE project_milestones_id_seq RESTART WITH 1;

DELETE FROM proposals;
ALTER SEQUENCE proposals_id_seq RESTART WITH 1;

DELETE FROM projects;
ALTER SEQUENCE projects_id_seq RESTART WITH 1;


INSERT INTO projects (client_id, title, description, budget_min, budget_max, duration_days, required_skills, category, status, deadline, assigned_freelancer, created_at, updated_at) VALUES
(1, 'E-Commerce Website', 'Full-feature e-commerce with payment integration', 5000, 8000, 60, '["React","Node.js","PostgreSQL","AWS"]', 'Web Development','COMPLETED', '2024-09-15', 21, '2024-07-01','2024-09-15'),
(2, 'Mobile Banking App', 'Redesign of banking app UI/UX', 3500, 5000, 45, '["Figma","UI/UX"]','Design','COMPLETED','2024-09-30',22,'2024-08-01','2024-09-30'),
(3, 'Tech Blog Content','20 SEO blog posts','1500','2500',30,'["SEO","Writing"]','Content Writing','IN_PROGRESS','2024-11-30',23,'2024-10-15','2024-11-01'),
(4, 'Social Media Campaign','Marketing across platforms',2000,3500,60,'["Social Media","Analytics"]','Digital Marketing','IN_PROGRESS','2024-12-15',24,'2024-10-01','2024-10-20'),
(5, 'Microservices Implementation','Migrate monolith to microservices',8000,12000,90,'["Java","Spring Boot","Docker"]','Backend Development','COMPLETED','2024-10-15',25,'2024-07-10','2024-10-15'),
(6, 'Business Process Consulting','Analyze operations and optimize',5000,8000,45,'["Business","Process"]','Consulting','COMPLETED','2024-09-01',26,'2024-07-15','2024-09-01'),
(7, 'Sales Dashboard','Interactive analytics dashboard',3000,5000,40,'["Python","SQL","Tableau"]','Data Analytics','COMPLETED','2024-08-30',27,'2024-07-20','2024-08-30'),
(8, 'Brand Identity','Complete brand identity','2500','4000',35,'["Logo","Design"]','Graphic Design','COMPLETED','2024-09-20',28,'2024-08-10','2024-09-20'),
(9, 'Mobile Fitness App','iOS and Android tracking app',6000,10000,75,'["React Native","Firebase"]','Mobile Development','COMPLETED','2024-10-05',29,'2024-07-15','2024-10-05'),
(10, 'Product Photography','200+ product photos','2000','3500',30,'["Photography","Editing"]','Photography','COMPLETED','2024-10-25',30,'2024-09-20','2024-10-25'),
(11, 'AWS Cloud Migration','Migrate infrastructure to AWS',7000,11000,60,'["AWS","Terraform"]','Cloud Services','COMPLETED','2024-08-15',31,'2024-06-10','2024-08-15'),
(12, 'Legal Translation','Translate legal docs','1800','3000',25,'["Translation","French","German"]','Translation','IN_PROGRESS','2024-11-20',32,'2024-10-25','2024-11-05'),
(13, 'Training Video Production','Motion graphics videos','4000','6500',50,'["Video","After Effects"]','Video Production','COMPLETED','2024-09-10',33,'2024-07-20','2024-09-10'),
(14, 'SaaS Copywriting','Landing page content','1000','2000',15,'["Copywriting","SaaS"]','Copywriting','COMPLETED','2024-10-20',34,'2024-10-01','2024-10-20'),
(15, 'Restaurant PWA','Progressive Web App','5500','8500',65,'["Vue.js","PWA"]','Web Development','IN_PROGRESS','2024-12-01',35,'2024-10-10','2024-10-28'),
(16, 'Healthcare UX Research','Telemedicine app design','3000','5000',45,'["UX","Accessibility"]','UX Design','IN_PROGRESS','2024-11-25',36,'2024-10-08','2024-10-22'),
(17, 'SEO Audit','SEO improvements','2500','4000',40,'["SEO","Analytics"]','SEO Services','OPEN',NULL,NULL,'2024-11-01','2024-11-01'),
(18, 'API Documentation','Developer docs portal','2000','3500',30,'["Documentation","Markdown"]','Technical Writing','OPEN',NULL,NULL,'2024-10-28','2024-10-28'),
(19, 'CI/CD Pipeline Setup','Automated deployment','4000','6000',35,'["Jenkins","Docker","Kubernetes"]','DevOps','OPEN',NULL,NULL,'2024-11-05','2024-11-05'),
(20, 'Children Book Illustrations','50+ illustrations','3500','5500',55,'["Illustration","Procreate"]','Illustration','OPEN',NULL,NULL,'2024-11-02','2024-11-02');



-- Project 1 (COMPLETED)
INSERT INTO proposals (project_id, freelancer_id, cover_letter, proposed_budget, delivery_days, ai_score, status, submitted_at, updated_at) VALUES
(1, 21, 'I have 5+ years building e-commerce platforms. Can deliver a scalable solution.', 7500, 55, 95.5, 'ACCEPTED', '2024-07-02','2024-07-05'),
(1, 25, 'Senior backend engineer with microservices expertise.', 8000, 60, 88.2, 'REJECTED', '2024-07-03','2024-07-05');

-- Project 2 (COMPLETED)
INSERT INTO proposals VALUES
(2, 22, 'UI/UX designer with mobile banking experience.', 4500, 40, 96.8, 'ACCEPTED','2024-08-02','2024-08-04'),
(2, 36, 'UX researcher and designer here.', 4800, 45, 91.3, 'REJECTED','2024-08-02','2024-08-04');

-- Project 3 (IN_PROGRESS)
INSERT INTO proposals VALUES
(3, 23, 'Tech writer with SEO experience.', 2200, 28, 94.7, 'ACCEPTED','2024-10-16','2024-10-18');

-- Project 4 (IN_PROGRESS)
INSERT INTO proposals VALUES
(4, 24, 'Digital marketing specialist with social media campaigns.', 3000, 55, 93.5, 'ACCEPTED','2024-10-02','2024-10-05');

-- Project 5 (COMPLETED)
INSERT INTO proposals VALUES
(5, 25, 'Expert in microservices and cloud deployment.', 10000, 80, 97.1, 'ACCEPTED','2024-07-11','2024-07-20');

-- Project 6 (COMPLETED)
INSERT INTO proposals VALUES
(6, 26, 'Business consultant with process optimization skills.', 7000, 45, 92.5, 'ACCEPTED','2024-07-16','2024-07-25');

-- Project 7 (COMPLETED)
INSERT INTO proposals VALUES
(7, 27, 'Python developer for sales analytics dashboard.', 4500, 35, 94.0, 'ACCEPTED','2024-07-21','2024-08-01');

-- Project 8 (COMPLETED)
INSERT INTO proposals VALUES
(8, 28, 'Brand identity designer for coffee chain.', 3500, 30, 95.2, 'ACCEPTED','2024-08-11','2024-08-20');

-- Project 9 (COMPLETED)
INSERT INTO proposals VALUES
(9, 29, 'React Native developer for fitness app.', 8000, 70, 96.0, 'ACCEPTED','2024-07-16','2024-08-25');

-- Project 10 (COMPLETED)
INSERT INTO proposals VALUES
(10, 30, 'Professional photographer with editing skills.', 3000, 25, 94.5, 'ACCEPTED','2024-09-21','2024-10-20');

-- Project 11 (COMPLETED)
INSERT INTO proposals VALUES
(11, 31, 'Cloud architect for AWS migration.', 9500, 55, 97.0, 'ACCEPTED','2024-06-12','2024-07-30');

-- Project 12 (IN_PROGRESS)
INSERT INTO proposals VALUES
(12, 32, 'Certified legal translator.', 2500, 23, 97.2, 'ACCEPTED','2024-10-26','2024-10-28');

-- Project 13 (COMPLETED)
INSERT INTO proposals VALUES
(13, 33, 'Video producer with motion graphics experience.', 5000, 50, 95.6, 'ACCEPTED','2024-07-21','2024-08-25');

-- Project 14 (COMPLETED)
INSERT INTO proposals VALUES
(14, 34, 'Copywriter specialized in SaaS landing pages.', 1800, 15, 93.4, 'ACCEPTED','2024-10-02','2024-10-15');

-- Project 15 (IN_PROGRESS)
INSERT INTO proposals VALUES
(15, 35, 'Frontend developer for restaurant PWA.', 7500, 60, 92.8, 'ACCEPTED','2024-10-11','2024-10-13');

-- Project 16 (IN_PROGRESS)
INSERT INTO proposals VALUES
(16, 36, 'UX researcher for healthcare app.', 4200, 42, 95.6, 'ACCEPTED','2024-10-09','2024-10-11');

-- Project 17 (OPEN)
INSERT INTO proposals VALUES
(17, 37, 'SEO expert with proven results.', 3500, 38, 96.4, 'PENDING','2024-11-02','2024-11-02'),
(17, 34, 'SEO copywriter.', 3000, 35, 89.7, 'PENDING','2024-11-02','2024-11-02');

-- Project 18 (OPEN)
INSERT INTO proposals VALUES
(18, 38, 'Technical writer for API documentation.', 3000, 28, 98.1, 'PENDING','2024-10-29','2024-10-29'),
(18, 23, 'Experienced documentation writer.', 2500, 30, 84.5, 'PENDING','2024-10-30','2024-10-30');

-- Project 19 (OPEN)
INSERT INTO proposals VALUES
(19, 39, 'DevOps engineer for CI/CD.', 5500, 33, 97.8, 'PENDING','2024-11-06','2024-11-06'),
(19, 25, 'Backend engineer with DevOps.', 5800, 35, 90.6, 'PENDING','2024-11-06','2024-11-06');

-- Project 20 (OPEN)
INSERT INTO proposals VALUES
(20, 40, 'Illustrator for children books.', 5000, 52, 96.9, 'PENDING','2024-11-03','2024-11-03'),
(20, 28, 'Graphic designer for illustrations.', 4500, 55, 88.5, 'PENDING','2024-11-04','2024-11-04');



-- Project 1 (COMPLETED)
INSERT INTO project_milestones (project_id, title, description, amount, due_date, status, completed_date, created_at) VALUES
(1, 'Setup & Architecture', 'Initial project setup, DB design, and architecture', 2500, '2024-07-20','COMPLETED','2024-07-18','2024-07-05'),
(1, 'Frontend Development', 'Develop product catalog and checkout pages', 2500, '2024-08-15','COMPLETED','2024-08-14','2024-07-05'),
(1, 'Backend & Payment', 'Implement APIs and integrate payment gateway', 1500, '2024-08-30','COMPLETED','2024-08-29','2024-07-05');

-- Project 2 (COMPLETED)
INSERT INTO project_milestones VALUES
(2, 'UI/UX Research', 'Analyze current app UI/UX and prepare redesign', 1500, '2024-08-15','COMPLETED','2024-08-14','2024-08-01'),
(2, 'Prototype & Feedback', 'Create prototypes and gather client feedback', 1500, '2024-08-25','COMPLETED','2024-08-24','2024-08-01');

-- Project 3 (IN_PROGRESS)
INSERT INTO project_milestones VALUES
(3, 'First 10 Articles', 'Write first batch of 10 blog posts', 1100, '2024-11-15','COMPLETED','2024-11-10','2024-10-18'),
(3, 'Second 10 Articles', 'Write remaining 10 blog posts', 1100, '2024-11-30','IN_PROGRESS',NULL,'2024-10-18');

-- Project 4 (IN_PROGRESS)
INSERT INTO project_milestones VALUES
(4, 'Strategy Planning', 'Develop campaign strategy and content plan', 1000, '2024-10-20','COMPLETED','2024-10-18','2024-10-05'),
(4, 'Campaign Launch', 'Launch campaigns and monitor performance', 1500, '2024-11-20','IN_PROGRESS',NULL,'2024-10-05'),
(4, 'Optimization & Reporting', 'Analyze results and finalize report', 500, '2024-12-15','PENDING',NULL,'2024-10-05');

-- Project 5 (COMPLETED)
INSERT INTO project_milestones VALUES
(5, 'Architecture Design', 'Design microservices architecture', 3000, '2024-08-01','COMPLETED','2024-07-30','2024-07-12'),
(5, 'Service Implementation', 'Implement core microservices', 4000, '2024-09-10','COMPLETED','2024-09-08','2024-07-12'),
(5, 'Testing & Deployment', 'Integration testing and production deployment', 3000, '2024-10-15','COMPLETED','2024-10-15','2024-07-12');

-- Project 6 (COMPLETED)
INSERT INTO project_milestones VALUES
(6, 'Operations Analysis', 'Analyze business processes', 2000, '2024-07-25','COMPLETED','2024-07-23','2024-07-15'),
(6, 'Recommendation Report', 'Provide process improvement recommendations', 3000, '2024-08-10','COMPLETED','2024-08-08','2024-07-15');

-- Project 7 (COMPLETED)
INSERT INTO project_milestones VALUES
(7, 'Data Integration', 'Integrate all sales data sources', 1500, '2024-07-30','COMPLETED','2024-07-28','2024-07-20'),
(7, 'Dashboard Development', 'Build interactive visualizations', 1500, '2024-08-15','COMPLETED','2024-08-14','2024-07-20');

-- Project 8 (COMPLETED)
INSERT INTO project_milestones VALUES
(8, 'Logo & Branding', 'Design logo and brand guidelines', 1200, '2024-08-20','COMPLETED','2024-08-19','2024-08-10'),
(8, 'Marketing Materials', 'Design print and digital assets', 1300, '2024-09-10','COMPLETED','2024-09-08','2024-08-10');

-- Project 9 (COMPLETED)
INSERT INTO project_milestones VALUES
(9, 'App Framework', 'Setup React Native project structure', 2000, '2024-07-25','COMPLETED','2024-07-23','2024-07-15'),
(9, 'Features Implementation', 'Develop workout tracking and social features', 3000, '2024-09-10','COMPLETED','2024-09-08','2024-07-15');

-- Project 10 (COMPLETED)
INSERT INTO project_milestones VALUES
(10, 'Photography', 'Capture and edit product images', 2000, '2024-10-15','COMPLETED','2024-10-14','2024-09-20'),
(10, 'Final Delivery', 'Deliver images and final edits', 1000, '2024-10-25','COMPLETED','2024-10-25','2024-09-20');





