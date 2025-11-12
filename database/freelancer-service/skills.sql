-- delete all 
DELETE FROM skills;

-- Reset the sequence for the skills table to start from 1
ALTER SEQUENCE skills_id_seq RESTART WITH 1;

-- Insert Skills for Freelancers
-- Each freelancer gets 3-5 relevant skills based on their specialization

-- Freelancer 1: Full Stack Developer
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(1, 'React', 'EXPERT', 5),
(1, 'Node.js', 'EXPERT', 5),
(1, 'PostgreSQL', 'EXPERT', 4),
(1, 'AWS', 'INTERMEDIATE', 3),
(1, 'TypeScript', 'EXPERT', 4);

-- Freelancer 2: UI/UX Designer
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(2, 'Figma', 'EXPERT', 6),
(2, 'Adobe XD', 'EXPERT', 5),
(2, 'User Research', 'EXPERT', 5),
(2, 'Prototyping', 'EXPERT', 6),
(2, 'Design Systems', 'EXPERT', 4);

-- Freelancer 3: Content Writer
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(3, 'SEO Writing', 'EXPERT', 4),
(3, 'Content Strategy', 'EXPERT', 5),
(3, 'Copywriting', 'EXPERT', 4),
(3, 'WordPress', 'INTERMEDIATE', 3);

-- Freelancer 4: Digital Marketing Specialist
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(4, 'Social Media Marketing', 'EXPERT', 5),
(4, 'Google Ads', 'EXPERT', 4),
(4, 'Facebook Ads', 'EXPERT', 5),
(4, 'Analytics', 'EXPERT', 4),
(4, 'Email Marketing', 'INTERMEDIATE', 3);

-- Freelancer 5: Senior Backend Engineer
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(5, 'Java', 'EXPERT', 8),
(5, 'Spring Boot', 'EXPERT', 7),
(5, 'Microservices', 'EXPERT', 6),
(5, 'Docker', 'EXPERT', 5),
(5, 'Kubernetes', 'EXPERT', 4);

-- Freelancer 6: Business Consultant
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(6, 'Business Strategy', 'EXPERT', 10),
(6, 'Process Optimization', 'EXPERT', 9),
(6, 'Financial Analysis', 'EXPERT', 8),
(6, 'Project Management', 'EXPERT', 10);

-- Freelancer 7: Data Analyst
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(7, 'Python', 'EXPERT', 5),
(7, 'Tableau', 'EXPERT', 4),
(7, 'SQL', 'EXPERT', 6),
(7, 'Power BI', 'EXPERT', 4),
(7, 'Data Visualization', 'EXPERT', 5);

-- Freelancer 8: Graphic Designer
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(8, 'Adobe Illustrator', 'EXPERT', 6),
(8, 'Adobe Photoshop', 'EXPERT', 7),
(8, 'Brand Design', 'EXPERT', 5),
(8, 'Print Design', 'EXPERT', 6);

-- Freelancer 9: Mobile App Developer
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(9, 'React Native', 'EXPERT', 4),
(9, 'Flutter', 'INTERMEDIATE', 2),
(9, 'Firebase', 'EXPERT', 4),
(9, 'Mobile UI/UX', 'EXPERT', 5),
(9, 'Redux', 'EXPERT', 4);

-- Freelancer 10: Professional Photographer
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(10, 'Product Photography', 'EXPERT', 7),
(10, 'Adobe Lightroom', 'EXPERT', 8),
(10, 'Studio Lighting', 'EXPERT', 7),
(10, 'Photo Retouching', 'EXPERT', 6);

-- Freelancer 11: Solutions Architect
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(11, 'AWS', 'EXPERT', 8),
(11, 'Azure', 'EXPERT', 6),
(11, 'System Design', 'EXPERT', 9),
(11, 'Terraform', 'EXPERT', 5),
(11, 'Cloud Security', 'EXPERT', 7);

-- Freelancer 12: Multilingual Translator
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(12, 'Spanish Translation', 'EXPERT', 10),
(12, 'French Translation', 'EXPERT', 8),
(12, 'German Translation', 'EXPERT', 6),
(12, 'Legal Translation', 'EXPERT', 7);

-- Freelancer 13: Video Editor
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(13, 'Adobe Premiere Pro', 'EXPERT', 6),
(13, 'After Effects', 'EXPERT', 5),
(13, 'Motion Graphics', 'EXPERT', 5),
(13, 'Color Grading', 'EXPERT', 4),
(13, 'DaVinci Resolve', 'INTERMEDIATE', 3);

-- Freelancer 14: SEO Copywriter
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(14, 'SEO Copywriting', 'EXPERT', 5),
(14, 'Conversion Optimization', 'EXPERT', 4),
(14, 'Keyword Research', 'EXPERT', 5),
(14, 'A/B Testing', 'INTERMEDIATE', 3);

-- Freelancer 15: Frontend Developer
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(15, 'Vue.js', 'EXPERT', 4),
(15, 'JavaScript', 'EXPERT', 6),
(15, 'CSS/SASS', 'EXPERT', 6),
(15, 'Responsive Design', 'EXPERT', 5),
(15, 'HTML5', 'EXPERT', 7);

-- Freelancer 16: UX Researcher & Designer
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(16, 'User Research', 'EXPERT', 6),
(16, 'Wireframing', 'EXPERT', 6),
(16, 'Usability Testing', 'EXPERT', 5),
(16, 'Accessibility Design', 'EXPERT', 4),
(16, 'Figma', 'EXPERT', 5);

-- Freelancer 17: SEO & SEM Expert
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(17, 'SEO', 'EXPERT', 6),
(17, 'Google Search Console', 'EXPERT', 6),
(17, 'Technical SEO', 'EXPERT', 5),
(17, 'Link Building', 'EXPERT', 5),
(17, 'SEM', 'EXPERT', 4);

-- Freelancer 18: Technical Writer
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(18, 'Technical Writing', 'EXPERT', 5),
(18, 'API Documentation', 'EXPERT', 4),
(18, 'Markdown', 'EXPERT', 5),
(18, 'Documentation Tools', 'EXPERT', 4);

-- Freelancer 19: DevOps Engineer
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(19, 'Jenkins', 'EXPERT', 5),
(19, 'Docker', 'EXPERT', 6),
(19, 'Kubernetes', 'EXPERT', 5),
(19, 'CI/CD', 'EXPERT', 6),
(19, 'Terraform', 'EXPERT', 4);

-- Freelancer 20: Brand Illustrator
INSERT INTO skills (freelancer_id, skill_name, proficiency_level, years_experience) VALUES
(20, 'Digital Illustration', 'EXPERT', 6),
(20, 'Character Design', 'EXPERT', 5),
(20, 'Adobe Illustrator', 'EXPERT', 7),
(20, 'Procreate', 'EXPERT', 4);

-- Verify the inserted data
SELECT s.id, s.freelancer_id, s.skill_name, s.proficiency_level, s.years_experience
FROM skills s
ORDER BY s.freelancer_id, s.id;

-- Count skills per freelancer
SELECT s.freelancer_id, COUNT(*) as skill_count
FROM skills s
GROUP BY s.freelancer_id
ORDER BY s.freelancer_id;

-- Count by proficiency level
SELECT s.proficiency_level, COUNT(*) as count
FROM skills s
GROUP BY s.proficiency_level
ORDER BY s.proficiency_level;