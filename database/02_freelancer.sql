-- ====================================
-- DELETE OLD DATA AND RESET SEQUENCES
-- ====================================
DELETE FROM ratings;
ALTER SEQUENCE ratings_id_seq RESTART WITH 1;

DELETE FROM portfolios;
ALTER SEQUENCE portfolios_id_seq RESTART WITH 1;

DELETE FROM skills;
ALTER SEQUENCE skills_id_seq RESTART WITH 1;

DELETE FROM freelancers;
ALTER SEQUENCE freelancers_id_seq RESTART WITH 1;

-- ====================================
-- INSERT FREELANCERS
-- ====================================
INSERT INTO freelancers (user_id, title, bio, hourly_rate, availability, total_earnings, completed_projects, average_rating, created_at, updated_at) VALUES
(21, 'Full Stack Developer', 'Experienced full-stack developer specializing in React, Node.js, and PostgreSQL. I build scalable web applications with clean code and modern architecture.', 85.00, 'FULL_TIME', 15420.00, 23, 4.85, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(22, 'UI/UX Designer', 'Creative designer with 5+ years of experience in user interface and user experience design. I create beautiful, intuitive designs that users love.', 75.00, 'FULL_TIME', 12800.00, 18, 4.92, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 'Content Writer & Copywriter', 'Professional content writer specializing in SEO-optimized blog posts, articles, and marketing copy. I help brands tell their story effectively.', 45.00, 'PART_TIME', 8500.00, 45, 4.78, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 'Digital Marketing Specialist', 'Results-driven digital marketer with expertise in social media marketing, PPC campaigns, and content strategy. I help businesses grow their online presence.', 65.00, 'FULL_TIME', 18900.00, 31, 4.88, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, 'Senior Backend Engineer', 'Backend engineer with extensive experience in Java, Spring Boot, and microservices architecture. I build robust and scalable server-side applications.', 95.00, 'FULL_TIME', 22500.00, 19, 4.95, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(26, 'Business Consultant', 'Strategic business consultant helping startups and SMEs optimize operations, develop growth strategies, and achieve their business goals.', 120.00, 'PART_TIME', 28600.00, 12, 4.90, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, 'Data Analyst', 'Data analyst specializing in data visualization, statistical analysis, and business intelligence. I turn data into actionable insights.', 70.00, 'FULL_TIME', 11200.00, 16, 4.75, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(28, 'Graphic Designer & Illustrator', 'Creative graphic designer and digital illustrator. I create stunning visuals for branding, marketing materials, and digital media.', 55.00, 'FULL_TIME', 9800.00, 28, 4.82, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(29, 'Mobile App Developer', 'Mobile app developer specializing in React Native and Flutter. I create cross-platform mobile applications with native performance.', 90.00, 'FULL_TIME', 19800.00, 14, 4.87, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(30, 'Professional Photographer', 'Professional photographer specializing in product photography, portraits, and commercial shoots. I capture moments that tell compelling stories.', 80.00, 'PART_TIME', 14500.00, 34, 4.93, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31, 'Solutions Architect', 'Cloud solutions architect with expertise in AWS, Azure, and system design. I design scalable, secure, and cost-effective cloud architectures.', 110.00, 'FULL_TIME', 25300.00, 15, 4.91, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, 'Multilingual Translator', 'Professional translator fluent in English, Spanish, French, and German. I provide accurate translations for technical, legal, and creative content.', 50.00, 'FULL_TIME', 7600.00, 52, 4.80, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(33, 'Video Editor & Motion Graphics', 'Creative video editor specializing in promotional videos, YouTube content, and motion graphics. I bring stories to life through compelling visuals.', 60.00, 'FULL_TIME', 13200.00, 26, 4.86, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34, 'SEO Copywriter', 'SEO-focused copywriter helping businesses rank higher on search engines while engaging their audience with persuasive content.', 55.00, 'PART_TIME', 10400.00, 38, 4.79, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(35, 'Frontend Developer', 'Frontend developer passionate about creating beautiful, responsive websites using React, Vue.js, and modern CSS frameworks.', 75.00, 'FULL_TIME', 16700.00, 21, 4.84, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(36, 'UX Researcher & Designer', 'UX researcher and designer focused on user-centered design. I conduct research, create wireframes, and design intuitive user experiences.', 85.00, 'FULL_TIME', 17900.00, 17, 4.89, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(37, 'SEO & SEM Expert', 'SEO and search engine marketing expert with proven track record of increasing organic traffic and ROI through strategic optimization.', 70.00, 'PART_TIME', 12100.00, 22, 4.83, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(38, 'Technical Writer & Editor', 'Technical writer and editor specializing in documentation, user guides, and API documentation. I make complex topics easy to understand.', 60.00, 'FULL_TIME', 9200.00, 29, 4.77, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(39, 'DevOps Engineer', 'DevOps engineer experienced in CI/CD pipelines, Docker, Kubernetes, and infrastructure automation. I streamline development and deployment processes.', 100.00, 'FULL_TIME', 21400.00, 13, 4.94, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(40, 'Brand Illustrator', 'Creative illustrator specializing in brand identity, character design, and editorial illustrations. I create unique visuals that capture brand essence.', 65.00, 'PART_TIME', 11800.00, 24, 4.81, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ====================================
-- INSERT PORTFOLIOS
-- ====================================
INSERT INTO portfolios (freelancer_id, title, description, project_url, image_url, technologies_used, completion_date, created_at) VALUES
(1, 'E-Commerce Platform', 'Built scalable e-commerce website with payment integration.', 'https://github.com/alexdev/fashion-ecommerce', 'https://images.unsplash.com/photo-1557821552-17105176677c?w=800', 'React, Node.js, PostgreSQL', '2024-08-15', CURRENT_TIMESTAMP),
(2, 'Mobile Banking App Redesign', 'UI/UX redesign increasing engagement by 45%.', 'https://behance.net/mariadesigner/banking-app', 'https://images.unsplash.com/photo-1563986768609-322da13575f3?w=800', 'Figma, Adobe XD, Prototyping', '2024-09-10', CURRENT_TIMESTAMP),
(3, 'Tech Blog Content Strategy', 'SEO optimized blog posts driving 200% traffic increase.', 'https://techinsights.blog', 'https://images.unsplash.com/photo-1499750310107-5fef28a66643?w=800', 'SEO, WordPress, Content Marketing', '2024-06-30', CURRENT_TIMESTAMP),
(4, 'Social Media Campaign', 'Social media marketing campaign achieving 500K impressions.', 'https://portfolio.sophiamarketer.com/startup-campaign', 'https://images.unsplash.com/photo-1611162617474-5b21e879e113?w=800', 'Facebook Ads, Google Ads, Analytics', '2024-09-05', CURRENT_TIMESTAMP),
(5, 'Microservices Architecture', 'Designed microservices for fintech platform.', 'https://github.com/lucasengineer/fintech-microservices', 'https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800', 'Java, Spring Boot, Kafka, Docker', '2024-10-01', CURRENT_TIMESTAMP),
(6, 'Business Optimization', 'Reduced operational costs by 35%.', 'https://oliviaconsult.com/case-studies/manufacturing', 'https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?w=800', 'Lean Six Sigma, Process Mapping', '2024-08-18', CURRENT_TIMESTAMP),
(7, 'Sales Analytics Dashboard', 'Interactive dashboard with real-time insights.', 'https://github.com/ethananalyst/sales-dashboard', 'https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800', 'Python, Tableau, SQL', '2024-07-25', CURRENT_TIMESTAMP),
(8, 'Brand Identity for Coffee Chain', 'Logo, color palette, typography and collateral.', 'https://behance.net/isabellaartist/coffee-brand', 'https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=800', 'Adobe Illustrator, Photoshop, Branding', '2024-09-12', CURRENT_TIMESTAMP),
(9, 'Cross-Platform Fitness App', 'Fitness app with workout plans and social features.', 'https://github.com/noahprogrammer/fitness-tracker', 'https://images.unsplash.com/photo-1461896836934-ffe607ba8211?w=800', 'React Native, Firebase, Redux', '2024-08-28', CURRENT_TIMESTAMP),
(10, 'Product Photography Portfolio', 'Professional photography for e-commerce products.', 'https://miaphotography.com/portfolio', 'https://images.unsplash.com/photo-1606760227091-3dd870d97f1d?w=800', 'Studio Lighting, Adobe Lightroom', '2024-10-15', CURRENT_TIMESTAMP),
(11, 'Cloud Migration', 'Migrated infrastructure to AWS with 40% cost reduction.', 'https://williamarchitect.com/projects/cloud-migration', 'https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=800', 'AWS, Terraform, CloudFormation', '2024-07-10', CURRENT_TIMESTAMP),
(12, 'Legal Translation', 'Translated 500+ legal pages with 99.8% accuracy.', 'https://emmatranslator.com/legal-project', 'https://images.unsplash.com/photo-1450101499163-c8848c66ca85?w=800', 'Translation Tools, Legal Terminology', '2024-09-20', CURRENT_TIMESTAMP),
(13, 'Corporate Training Video Series', 'Produced 20-episode video series with motion graphics.', 'https://vimeo.com/benjaminvideo/training-series', 'https://images.unsplash.com/photo-1492619375914-88005aa9e8fb?w=800', 'Premiere Pro, After Effects, DaVinci Resolve', '2024-08-05', CURRENT_TIMESTAMP),
(14, 'SaaS Landing Page Copy', 'Conversion-focused copy increasing sign-ups by 25%.', 'https://avacopywriter.com/saas-landing', 'https://images.unsplash.com/photo-1499951360447-b19be8fe80f5?w=800', 'Copywriting, SEO, A/B Testing', '2024-10-08', CURRENT_TIMESTAMP),
(15, 'Progressive Web App', 'PWA for restaurant chain with loyalty program.', 'https://github.com/masondev/restaurant-pwa', 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=800', 'Vue.js, Service Workers, IndexedDB', '2024-09-18', CURRENT_TIMESTAMP),
(16, 'Healthcare App UX Design', 'Telemedicine app focusing on accessibility for elderly.', 'https://behance.net/charlotteux/healthcare-app', 'https://images.unsplash.com/photo-1576091160399-112ba8d25d1d?w=800', 'Wireframing, User Research, Figma', '2024-07-30', CURRENT_TIMESTAMP),
(17, 'SEO Audit & Implementation', 'Implemented SEO improvements increasing traffic 180%.', 'https://jacobseo.com/case-studies/organic-growth', 'https://images.unsplash.com/photo-1432888498266-38ffec3eaf0a?w=800', 'SEO Tools, Keyword Research, Technical SEO', '2024-08-22', CURRENT_TIMESTAMP),
(18, 'API Documentation Portal', 'Created interactive API documentation portal.', 'https://github.com/ameliaeditor/api-docs', 'https://images.unsplash.com/photo-1551650975-87deedd944c3?w=800', 'Markdown, Swagger, OpenAPI', '2024-10-12', CURRENT_TIMESTAMP),
(19, 'CI/CD Pipeline Implementation', 'Automated CI/CD pipeline reducing deployment time to 15 mins.', 'https://github.com/logandevops/cicd-pipeline', 'https://images.unsplash.com/photo-1618401471353-b98afee0b2eb?w=800', 'Jenkins, Docker, Kubernetes, Terraform', '2024-09-25', CURRENT_TIMESTAMP),
(20, 'Children Book Illustrations', '50+ colorful illustrations for children books.', 'https://harperillustrator.com/childrens-books', 'https://images.unsplash.com/photo-1513364776144-60967b0f800f?w=800', 'Digital Illustration, Procreate, Adobe Illustrator', '2024-07-15', CURRENT_TIMESTAMP);


-- ====================================
-- INSERT SKILLS
-- ====================================
-- Freelancer 1
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(1, 'React', 'EXPERT', 5),
(1, 'Node.js', 'EXPERT', 5),
(1, 'PostgreSQL', 'EXPERT', 4),
(1, 'AWS', 'INTERMEDIATE', 3),
(1, 'TypeScript', 'EXPERT', 4);

-- Freelancer 2
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(2, 'Figma', 'EXPERT', 6),
(2, 'Adobe XD', 'EXPERT', 5),
(2, 'User Research', 'EXPERT', 5),
(2, 'Prototyping', 'EXPERT', 6),
(2, 'Design Systems', 'EXPERT', 4);

-- Freelancer 3
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(3, 'SEO Writing', 'EXPERT', 4),
(3, 'Content Strategy', 'EXPERT', 5),
(3, 'Copywriting', 'EXPERT', 4),
(3, 'WordPress', 'INTERMEDIATE', 3);

-- Freelancer 4
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(4, 'Social Media Marketing', 'EXPERT', 5),
(4, 'Google Ads', 'EXPERT', 4),
(4, 'Facebook Ads', 'EXPERT', 5),
(4, 'Analytics', 'EXPERT', 4),
(4, 'Email Marketing', 'INTERMEDIATE', 3);

-- Freelancer 5
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(5, 'Java', 'EXPERT', 8),
(5, 'Spring Boot', 'EXPERT', 7),
(5, 'Microservices', 'EXPERT', 6),
(5, 'Docker', 'EXPERT', 5),
(5, 'Kubernetes', 'EXPERT', 4);

-- Freelancer 6
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(6, 'Business Strategy', 'EXPERT', 10),
(6, 'Process Optimization', 'EXPERT', 9),
(6, 'Financial Analysis', 'EXPERT', 8),
(6, 'Project Management', 'EXPERT', 10);

-- Freelancer 7
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(7, 'Python', 'EXPERT', 5),
(7, 'Tableau', 'EXPERT', 4),
(7, 'SQL', 'EXPERT', 6),
(7, 'Power BI', 'EXPERT', 4),
(7, 'Data Visualization', 'EXPERT', 5);

-- Freelancer 8
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(8, 'Adobe Illustrator', 'EXPERT', 6),
(8, 'Adobe Photoshop', 'EXPERT', 7),
(8, 'Brand Design', 'EXPERT', 5),
(8, 'Print Design', 'EXPERT', 6);

-- Freelancer 9
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(9, 'React Native', 'EXPERT', 4),
(9, 'Flutter', 'INTERMEDIATE', 2),
(9, 'Firebase', 'EXPERT', 4),
(9, 'Mobile UI/UX', 'EXPERT', 5),
(9, 'Redux', 'EXPERT', 4);

-- Freelancer 10
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(10, 'Product Photography', 'EXPERT', 7),
(10, 'Adobe Lightroom', 'EXPERT', 8),
(10, 'Studio Lighting', 'EXPERT', 7),
(10, 'Photo Retouching', 'EXPERT', 6);

-- Freelancer 11
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(11, 'AWS', 'EXPERT', 8),
(11, 'Azure', 'EXPERT', 6),
(11, 'System Design', 'EXPERT', 9),
(11, 'Terraform', 'EXPERT', 5),
(11, 'Cloud Security', 'EXPERT', 7);

-- Freelancer 12
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(12, 'Spanish Translation', 'EXPERT', 10),
(12, 'French Translation', 'EXPERT', 8),
(12, 'German Translation', 'EXPERT', 6),
(12, 'Legal Translation', 'EXPERT', 7);

-- Freelancer 13
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(13, 'Adobe Premiere Pro', 'EXPERT', 6),
(13, 'After Effects', 'EXPERT', 5),
(13, 'Motion Graphics', 'EXPERT', 5),
(13, 'Color Grading', 'EXPERT', 4),
(13, 'DaVinci Resolve', 'INTERMEDIATE', 3);

-- Freelancer 14
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(14, 'SEO Copywriting', 'EXPERT', 5),
(14, 'Conversion Optimization', 'EXPERT', 4),
(14, 'Keyword Research', 'EXPERT', 5),
(14, 'A/B Testing', 'INTERMEDIATE', 3);

-- Freelancer 15
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(15, 'Vue.js', 'EXPERT', 4),
(15, 'JavaScript', 'EXPERT', 6),
(15, 'CSS/SASS', 'EXPERT', 6),
(15, 'Responsive Design', 'EXPERT', 5),
(15, 'HTML5', 'EXPERT', 7);

-- Freelancer 16
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(16, 'User Research', 'EXPERT', 6),
(16, 'Wireframing', 'EXPERT', 6),
(16, 'Usability Testing', 'EXPERT', 5),
(16, 'Accessibility Design', 'EXPERT', 4),
(16, 'Figma', 'EXPERT', 5);

-- Freelancer 17
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(17, 'SEO', 'EXPERT', 6),
(17, 'Google Search Console', 'EXPERT', 6),
(17, 'Technical SEO', 'EXPERT', 5),
(17, 'Link Building', 'EXPERT', 5),
(17, 'SEM', 'EXPERT', 4);

-- Freelancer 18
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(18, 'Technical Writing', 'EXPERT', 5),
(18, 'API Documentation', 'EXPERT', 4),
(18, 'Markdown', 'EXPERT', 5),
(18, 'Documentation Tools', 'EXPERT', 4);

-- Freelancer 19
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(19, 'Jenkins', 'EXPERT', 5),
(19, 'Docker', 'EXPERT', 6),
(19, 'Kubernetes', 'EXPERT', 5),
(19, 'CI/CD', 'EXPERT', 6),
(19, 'Terraform', 'EXPERT', 4);

-- Freelancer 20
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(20, 'Digital Illustration', 'EXPERT', 6),
(20, 'Character Design', 'EXPERT', 5),
(20, 'Adobe Illustrator', 'EXPERT', 7),
(20, 'Procreate', 'EXPERT', 4);



-- ====================================
-- INSERT RATINGS
-- ====================================
-- DELETE existing ratings and reset sequence
DELETE FROM ratings;
ALTER SEQUENCE ratings_id_seq RESTART WITH 1;

-- Ratings for Projects 1–20
INSERT INTO ratings (project_id, freelancer_id, client_rating, client_feedback, created_at) VALUES
-- Project 1
(1, 21, 5, 'Excellent work! Delivered on time with high quality.', '2024-09-16 10:00:00'),
-- Project 2
(2, 22, 4, 'Very good UI/UX design, minor tweaks needed.', '2024-10-01 12:00:00'),
-- Project 3
(3, 23, 5, 'Content quality was outstanding and very SEO friendly.', '2024-12-01 11:00:00'),
-- Project 4
(4, 24, 4, 'Good campaign management, results satisfactory.', '2024-12-16 09:30:00'),
-- Project 5
(5, 25, 5, 'Microservices implemented perfectly, smooth migration.', '2024-10-16 14:20:00'),
-- Project 6
(6, 26, 4, 'Helpful insights, process optimization was good.', '2024-09-02 10:45:00'),
-- Project 7
(7, 27, 5, 'Dashboard is intuitive and provides real-time analytics.', '2024-09-01 15:00:00'),
-- Project 8
(8, 28, 5, 'Brand identity captured our vision perfectly.', '2024-09-21 13:30:00'),
-- Project 9
(9, 29, 4, 'App is functional, some minor UI issues.', '2024-10-06 12:15:00'),
-- Project 10
(10, 30, 5, 'Product photos are of excellent quality.', '2024-10-26 10:00:00'),
-- Project 11
(11, 31, 5, 'Cloud migration completed flawlessly, very professional.', '2024-08-16 16:00:00'),
-- Project 12
(12, 32, 5, 'Translation was accurate and timely.', '2024-11-21 14:30:00'),
-- Project 13
(13, 33, 4, 'Training videos were engaging, minor edits needed.', '2024-09-11 10:20:00'),
-- Project 14
(14, 34, 5, 'Landing page copy increased conversions as expected.', '2024-10-21 11:45:00'),
-- Project 15
(15, 35, 5, 'PWA development completed, works smoothly across devices.', '2024-12-02 15:30:00'),
-- Project 16
(16, 36, 4, 'UX research insights were valuable, design improvements noted.', '2024-11-26 10:10:00'),
-- Project 17 (OPEN, pick freelancer 37)
(17, 37, NULL, NULL, '2024-12-11 09:00:00'),
-- Project 18 (OPEN, pick freelancer 38)
(18, 38, NULL, NULL, '2024-12-06 10:15:00'),
-- Project 19 (OPEN, pick freelancer 39)
(19, 39, NULL, NULL, '2024-12-01 12:00:00'),
-- Project 20 (OPEN, pick freelancer 40)
(20, 40, NULL, NULL, '2024-12-21 09:30:00');
