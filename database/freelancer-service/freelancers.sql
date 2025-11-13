-- delete all 
DELETE FROM freelancers;

-- Reset the sequence for the freelancers table to start from 1
ALTER SEQUENCE freelancers_id_seq RESTART WITH 1;

-- Insert 20 Freelancer profiles
-- These correspond to user_ids 21-40 (the FREELANCER users we created earlier)

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
