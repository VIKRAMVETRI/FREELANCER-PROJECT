DELETE FROM proposals;

ALTER SEQUENCE proposals_id_seq RESTART WITH 1;


INSERT INTO proposals (project_id, freelancer_id, cover_letter, proposed_budget, delivery_days, ai_score, status, submitted_at, updated_at) VALUES
(1, 21, 'I have 5+ years of experience building e-commerce platforms. I''ve successfully delivered similar projects using React and Node.js with payment gateway integrations. I can provide you with a modern, scalable solution that meets all your requirements.', 7500.00, 55, 95.50, 'ACCEPTED', '2024-07-02 10:30:00', '2024-07-05 14:20:00'),
(1, 25, 'As a senior backend engineer, I specialize in building robust e-commerce solutions. My expertise in microservices and cloud architecture would be valuable for this project.', 8000.00, 60, 88.20, 'REJECTED', '2024-07-03 09:15:00', '2024-07-05 14:20:00');

-- Proposals for Project 2 (COMPLETED)
INSERT INTO proposals (project_id, freelancer_id, cover_letter, proposed_budget, delivery_days, ai_score, status, submitted_at, updated_at) VALUES
(2, 22, 'I''m a UI/UX designer with 5+ years of experience in mobile app design. I''ve redesigned several banking apps with focus on user experience and accessibility. I would love to help transform your app.', 4500.00, 40, 96.80, 'ACCEPTED', '2024-08-02 11:00:00', '2024-08-04 15:30:00'),
(2, 36, 'UX researcher and designer here. I conduct thorough user research and create intuitive designs. Would be great to work on this banking app redesign.', 4800.00, 45, 91.30, 'REJECTED', '2024-08-02 14:20:00', '2024-08-04 15:30:00');

-- Proposals for Project 3 (IN_PROGRESS)
INSERT INTO proposals (project_id, freelancer_id, cover_letter, proposed_budget, delivery_days, ai_score, status, submitted_at, updated_at) VALUES
(3, 23, 'Professional tech writer with 4+ years of experience. I specialize in SEO-optimized content and have written extensively about software development and cloud technologies. My articles consistently rank on first page of Google.', 2200.00, 28, 94.70, 'ACCEPTED', '2024-10-16 09:45:00', '2024-10-18 10:15:00');

-- Proposals for Project 4 (IN_PROGRESS)
INSERT INTO proposals (project_id, freelancer_id, cover_letter, proposed_budget, delivery_days, ai_score, status, submitted_at, updated_at) VALUES
(4, 24, 'Digital marketing specialist with proven track record in social media campaigns. I''ve managed successful product launches across multiple platforms with excellent ROI. I''m data-driven and results-focused.', 3000.00, 55, 93.50, 'ACCEPTED', '2024-10-02 10:30:00', '2024-10-05 11:00:00');

-- Proposals for Project 12 (IN_PROGRESS)
INSERT INTO proposals (project_id, freelancer_id, cover_letter, proposed_budget, delivery_days, ai_score, status, submitted_at, updated_at) VALUES
(12, 32, 'Certified translator with 10+ years of experience in legal translation. Fluent in English, Spanish, French, and German. I ensure accuracy and maintain legal terminology precision.', 2500.00, 23, 97.20, 'ACCEPTED', '2024-10-26 11:30:00', '2024-10-28 09:45:00');

-- Proposals for Project 15 (IN_PROGRESS)
INSERT INTO proposals (project_id, freelancer_id, cover_letter, proposed_budget, delivery_days, ai_score, status, submitted_at, updated_at) VALUES
(15, 35, 'Frontend developer specializing in Vue.js and PWA development. I''ve built several PWAs with excellent performance and offline capabilities. Excited to work on this restaurant platform.', 7500.00, 60, 92.80, 'ACCEPTED', '2024-10-11 10:00:00', '2024-10-13 14:30:00');

-- Proposals for Project 16 (IN_PROGRESS)
INSERT INTO proposals (project_id, freelancer_id, cover_letter, proposed_budget, delivery_days, ai_score, status, submitted_at, updated_at) VALUES
(16, 36, 'UX researcher with extensive experience in healthcare applications. I specialize in accessibility design and have conducted research with elderly users. I understand the unique challenges of this demographic.', 4200.00, 42, 95.60, 'ACCEPTED', '2024-10-09 09:30:00', '2024-10-11 11:15:00');

-- Proposals for Project 17 (OPEN - multiple proposals)
INSERT INTO proposals (project_id, freelancer_id, cover_letter, proposed_budget, delivery_days, ai_score, status, submitted_at, updated_at) VALUES
(17, 37, 'SEO expert with 6+ years of experience. I''ve helped numerous clients increase organic traffic by 150%+. I conduct thorough technical SEO audits and implement proven strategies for ranking improvements.', 3500.00, 38, 96.40, 'PENDING', '2024-11-02 10:15:00', '2024-11-02 10:15:00'),
(17, 34, 'SEO copywriter who understands both content and technical SEO. I can help optimize your website for search engines while maintaining engaging content.', 3000.00, 35, 89.70, 'PENDING', '2024-11-02 14:30:00', '2024-11-02 14:30:00'),
(17, 23, 'Content writer with strong SEO background. I can audit your content strategy and implement improvements for better search visibility.', 2800.00, 40, 85.30, 'PENDING', '2024-11-03 09:00:00', '2024-11-03 09:00:00');

-- Proposals for Project 18 (OPEN - multiple proposals)
INSERT INTO proposals (project_id, freelancer_id, cover_letter, proposed_budget, delivery_days, ai_score, status, submitted_at, updated_at) VALUES
(18, 38, 'Technical writer specializing in API documentation. I''ve created documentation portals for several SaaS companies with interactive examples and clear tutorials. I make complex technical concepts easy to understand.', 3000.00, 28, 98.10, 'PENDING', '2024-10-29 10:45:00', '2024-10-29 10:45:00'),
(18, 23, 'Experienced writer who can create clear, comprehensive documentation. I understand developer needs and can structure information effectively.', 2500.00, 30, 84.50, 'PENDING', '2024-10-30 11:30:00', '2024-10-30 11:30:00'),
(18, 34, 'Copywriter with technical background. I can create engaging documentation that developers will actually want to read.', 2800.00, 32, 81.20, 'PENDING', '2024-10-31 09:15:00', '2024-10-31 09:15:00');

-- Proposals for Project 19 (OPEN - multiple proposals)
INSERT INTO proposals (project_id, freelancer_id, cover_letter, proposed_budget, delivery_days, ai_score, status, submitted_at, updated_at) VALUES
(19, 39, 'DevOps engineer with 6+ years of experience in CI/CD pipelines. I''ve set up automated deployment systems for numerous companies, significantly reducing deployment time and errors. Proficient in Jenkins, Docker, and Kubernetes.', 5500.00, 33, 97.80, 'PENDING', '2024-11-06 09:30:00', '2024-11-06 09:30:00'),
(19, 25, 'Senior backend engineer with DevOps experience. I understand both development and operations, which helps in creating efficient CI/CD workflows.', 5800.00, 35, 90.60, 'PENDING', '2024-11-06 14:00:00', '2024-11-06 14:00:00'),
(19, 31, 'Solutions architect experienced in cloud infrastructure and automation. I can design and implement robust CI/CD pipelines.', 6000.00, 34, 92.40, 'PENDING', '2024-11-07 10:20:00', '2024-11-07 10:20:00'),
(19, 21, 'Full-stack developer with DevOps skills. I''ve implemented CI/CD pipelines for my projects and can set up automated workflows for your team.', 4500.00, 35, 86.90, 'PENDING', '2024-11-07 15:45:00', '2024-11-07 15:45:00');

-- Proposals for Project 20 (OPEN - multiple proposals)
INSERT INTO proposals (project_id, freelancer_id, cover_letter, proposed_budget, delivery_days, ai_score, status, submitted_at, updated_at) VALUES
(20, 40, 'Professional illustrator specializing in children book illustrations. I''ve illustrated several published children books with consistent, engaging art style. I create characters that kids love and remember.', 5000.00, 52, 96.90, 'PENDING', '2024-11-03 10:00:00', '2024-11-03 10:00:00'),
(20, 28, 'Graphic designer and illustrator. I have experience in character design and can create colorful, appealing illustrations for children.', 4500.00, 55, 88.50, 'PENDING', '2024-11-04 11:15:00', '2024-11-04 11:15:00'),
(20, 8, 'Creative artist with passion for children illustration. I work with digital tools and can deliver high-quality illustrations in various styles.', 4800.00, 50, 87.20, 'PENDING', '2024-11-05 09:45:00', '2024-11-05 09:45:00');
