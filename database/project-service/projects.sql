DELETE FROM projects;

-- Reset sequences
ALTER SEQUENCE projects_id_seq RESTART WITH 1;

INSERT INTO projects (client_id, title, description, budget_min, budget_max, duration_days, required_skills, category, status, deadline, assigned_freelancer, created_at, updated_at) VALUES
(1, 'E-Commerce Website Development', 'Need a full-featured e-commerce website with payment gateway integration, product catalog, shopping cart, and admin dashboard. Must be responsive and SEO-friendly.', 5000.00, 8000.00, 60, '["React", "Node.js", "PostgreSQL", "Payment Integration", "AWS"]', 'Web Development', 'COMPLETED', '2024-09-15', 21, '2024-07-01 09:00:00', '2024-09-15 16:30:00'),

(2, 'Mobile Banking App UI/UX Redesign', 'Complete redesign of our mobile banking application. Need modern, intuitive interface with focus on user experience and accessibility.', 3500.00, 5000.00, 45, '["Figma", "UI/UX Design", "Mobile Design", "Prototyping"]', 'Design', 'COMPLETED', '2024-09-30', 22, '2024-08-01 10:15:00', '2024-09-30 14:20:00'),

(3, 'Content Writing for Tech Blog', 'Looking for experienced tech writer to create 20 SEO-optimized blog posts covering latest trends in software development, AI, and cloud computing.', 1500.00, 2500.00, 30, '["SEO Writing", "Technical Writing", "Content Strategy"]', 'Content Writing', 'IN_PROGRESS', '2024-11-30', 23, '2024-10-15 11:30:00', '2024-11-01 09:45:00'),

(4, 'Social Media Marketing Campaign', 'Need comprehensive social media marketing campaign across Facebook, Instagram, and LinkedIn for product launch. Including content creation and ad management.', 2000.00, 3500.00, 60, '["Social Media Marketing", "Facebook Ads", "Content Creation", "Analytics"]', 'Digital Marketing', 'IN_PROGRESS', '2024-12-15', 24, '2024-10-01 08:00:00', '2024-10-20 10:30:00'),

(5, 'Microservices Architecture Implementation', 'Migrate monolithic application to microservices architecture. Need expertise in Spring Boot, Docker, Kubernetes, and cloud deployment.', 8000.00, 12000.00, 90, '["Java", "Spring Boot", "Microservices", "Docker", "Kubernetes"]', 'Backend Development', 'COMPLETED', '2024-10-15', 25, '2024-07-10 09:30:00', '2024-10-15 17:00:00'),

(6, 'Business Process Consulting', 'Seeking business consultant to analyze current operations and recommend process improvements to reduce costs and increase efficiency.', 5000.00, 8000.00, 45, '["Business Strategy", "Process Optimization", "Data Analysis"]', 'Consulting', 'COMPLETED', '2024-09-01', 26, '2024-07-15 10:00:00', '2024-09-01 15:30:00'),

(7, 'Sales Analytics Dashboard', 'Build interactive sales analytics dashboard with real-time data visualization, reporting, and forecasting capabilities.', 3000.00, 5000.00, 40, '["Python", "Tableau", "SQL", "Data Visualization"]', 'Data Analytics', 'COMPLETED', '2024-08-30', 27, '2024-07-20 11:00:00', '2024-08-30 16:45:00'),

(8, 'Brand Identity Design', 'Create complete brand identity for new coffee chain including logo, color palette, typography, and marketing materials.', 2500.00, 4000.00, 35, '["Adobe Illustrator", "Brand Design", "Logo Design", "Print Design"]', 'Graphic Design', 'COMPLETED', '2024-09-20', 28, '2024-08-10 09:15:00', '2024-09-20 14:00:00'),

(9, 'Cross-Platform Mobile App Development', 'Develop fitness tracking mobile app for iOS and Android with workout plans, nutrition tracking, and social features.', 6000.00, 10000.00, 75, '["React Native", "Firebase", "Mobile Development", "API Integration"]', 'Mobile Development', 'COMPLETED', '2024-10-05', 29, '2024-07-15 10:30:00', '2024-10-05 18:20:00'),

(10, 'Product Photography Services', 'Need professional product photography for 200+ products for e-commerce website. Must include editing and retouching.', 2000.00, 3500.00, 30, '["Product Photography", "Adobe Lightroom", "Photo Editing"]', 'Photography', 'COMPLETED', '2024-10-25', 30, '2024-09-20 08:45:00', '2024-10-25 16:00:00'),

(11, 'Cloud Migration to AWS', 'Migrate on-premise infrastructure to AWS. Need cloud architect experienced in AWS services, cost optimization, and security.', 7000.00, 11000.00, 60, '["AWS", "Cloud Architecture", "Terraform", "DevOps"]', 'Cloud Services', 'COMPLETED', '2024-08-15', 31, '2024-06-10 09:00:00', '2024-08-15 17:30:00'),

(12, 'Legal Document Translation', 'Translate legal contracts and documentation from English to Spanish, French, and German. Must be certified translator.', 1800.00, 3000.00, 25, '["Translation", "Legal Translation", "Spanish", "French", "German"]', 'Translation', 'IN_PROGRESS', '2024-11-20', 32, '2024-10-25 10:00:00', '2024-11-05 11:15:00'),

(13, 'Corporate Training Video Production', 'Produce series of professional training videos with motion graphics and animations for employee onboarding program.', 4000.00, 6500.00, 50, '["Video Editing", "After Effects", "Motion Graphics"]', 'Video Production', 'COMPLETED', '2024-09-10', 33, '2024-07-20 11:30:00', '2024-09-10 15:45:00'),

(14, 'SaaS Landing Page Copywriting', 'Write compelling, conversion-focused copy for SaaS product landing page. Must understand B2B SaaS marketing.', 1000.00, 2000.00, 15, '["Copywriting", "Conversion Optimization", "SaaS Marketing"]', 'Copywriting', 'COMPLETED', '2024-10-20', 34, '2024-10-01 09:00:00', '2024-10-20 14:30:00'),

(15, 'Progressive Web Application Development', 'Build PWA for restaurant chain with online ordering, table reservation, and loyalty program features.', 5500.00, 8500.00, 65, '["Vue.js", "PWA", "Service Workers", "Responsive Design"]', 'Web Development', 'IN_PROGRESS', '2024-12-01', 35, '2024-10-10 10:15:00', '2024-10-28 09:30:00'),

(16, 'Healthcare App UX Research', 'Conduct comprehensive UX research and design telemedicine app interface focused on accessibility for elderly patients.', 3000.00, 5000.00, 45, '["User Research", "UX Design", "Accessibility", "Healthcare"]', 'UX Design', 'IN_PROGRESS', '2024-11-25', 36, '2024-10-08 11:00:00', '2024-10-22 10:45:00'),

(17, 'SEO Audit and Implementation', 'Perform comprehensive SEO audit of website and implement improvements to increase organic traffic and search rankings.', 2500.00, 4000.00, 40, '["SEO", "Technical SEO", "Google Analytics", "Keyword Research"]', 'SEO Services', 'OPEN', '2024-12-10', NULL, '2024-11-01 09:30:00', '2024-11-01 09:30:00'),

(18, 'API Documentation Creation', 'Create comprehensive API documentation portal with interactive examples, code snippets, and tutorials for developer platform.', 2000.00, 3500.00, 30, '["Technical Writing", "API Documentation", "Markdown"]', 'Technical Writing', 'OPEN', '2024-12-05', NULL, '2024-10-28 10:00:00', '2024-10-28 10:00:00'),

(19, 'CI/CD Pipeline Setup', 'Set up automated CI/CD pipeline with Jenkins, Docker, and Kubernetes to streamline deployment process.', 4000.00, 6000.00, 35, '["Jenkins", "Docker", "Kubernetes", "CI/CD", "DevOps"]', 'DevOps', 'OPEN', '2024-11-30', NULL, '2024-11-05 11:15:00', '2024-11-05 11:15:00'),

(20, 'Children Book Illustrations', 'Create colorful illustrations for children book series. Need 50+ illustrations with consistent style and characters.', 3500.00, 5500.00, 55, '["Digital Illustration", "Character Design", "Procreate"]', 'Illustration', 'OPEN', '2024-12-20', NULL, '2024-11-02 09:45:00', '2024-11-02 09:45:00');


