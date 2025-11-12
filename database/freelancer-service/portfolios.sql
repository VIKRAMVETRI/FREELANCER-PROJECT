-- delete all 
DELETE FROM portfolios;

-- Reset the sequence for the portfolios table to start from 1
ALTER SEQUENCE portfolios_id_seq RESTART WITH 1;

-- Insert 20 Portfolio entries
-- Distributed across different freelancers (freelancer_ids 1-20)

INSERT INTO portfolios (freelancer_id, title, description, project_url, image_url, technologies_used, completion_date, created_at) VALUES
(1, 'E-Commerce Platform for Fashion Retail', 'Developed a full-featured e-commerce platform with payment integration, inventory management, and admin dashboard. Handled 10,000+ daily users.', 'https://github.com/alexdev/fashion-ecommerce', 'https://images.unsplash.com/photo-1557821552-17105176677c?w=800', 'React, Node.js, PostgreSQL, Stripe, AWS S3, Redux', '2024-08-15', CURRENT_TIMESTAMP),

(1, 'Real-Time Chat Application', 'Built a scalable real-time messaging app with WebSocket support, file sharing, and group chat functionality.', 'https://github.com/alexdev/realtime-chat', 'https://images.unsplash.com/photo-1611606063065-ee7946f0787a?w=800', 'React, Socket.io, Express, MongoDB, Redis', '2024-10-20', CURRENT_TIMESTAMP),

(2, 'Mobile Banking App Redesign', 'Complete UI/UX redesign of a mobile banking application, improving user engagement by 45% and reducing transaction time by 30%.', 'https://behance.net/mariadesigner/banking-app', 'https://images.unsplash.com/photo-1563986768609-322da13575f3?w=800', 'Figma, Adobe XD, Prototyping, User Research', '2024-09-10', CURRENT_TIMESTAMP),

(2, 'SaaS Dashboard Design System', 'Created a comprehensive design system for a SaaS platform including components library, style guide, and documentation.', 'https://behance.net/mariadesigner/design-system', 'https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800', 'Figma, Storybook, Design Tokens, Accessibility Standards', '2024-07-22', CURRENT_TIMESTAMP),

(3, 'Tech Blog Content Strategy', 'Developed and executed content strategy for tech blog, increasing organic traffic by 200% in 6 months with SEO-optimized articles.', 'https://techinsights.blog', 'https://images.unsplash.com/photo-1499750310107-5fef28a66643?w=800', 'SEO, Google Analytics, WordPress, Content Marketing', '2024-06-30', CURRENT_TIMESTAMP),

(4, 'Social Media Campaign for Startup', 'Managed comprehensive social media campaign across platforms, achieving 500K+ impressions and 15% conversion rate.', 'https://portfolio.sophiamarketer.com/startup-campaign', 'https://images.unsplash.com/photo-1611162617474-5b21e879e113?w=800', 'Facebook Ads, Instagram, LinkedIn, Google Ads, Analytics', '2024-09-05', CURRENT_TIMESTAMP),

(5, 'Microservices Architecture for FinTech', 'Designed and implemented microservices architecture for a fintech platform handling 1M+ transactions daily.', 'https://github.com/lucasengineer/fintech-microservices', 'https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800', 'Java, Spring Boot, Kafka, Docker, Kubernetes, PostgreSQL', '2024-10-01', CURRENT_TIMESTAMP),

(6, 'Business Process Optimization', 'Streamlined business processes for manufacturing company, reducing operational costs by 35% and improving efficiency.', 'https://oliviaconsult.com/case-studies/manufacturing', 'https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?w=800', 'Process Mapping, Lean Six Sigma, Data Analysis', '2024-08-18', CURRENT_TIMESTAMP),

(7, 'Sales Analytics Dashboard', 'Built interactive sales analytics dashboard with real-time insights, forecasting, and custom reporting capabilities.', 'https://github.com/ethananalyst/sales-dashboard', 'https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800', 'Python, Tableau, SQL, Power BI, Data Visualization', '2024-07-25', CURRENT_TIMESTAMP),

(8, 'Brand Identity for Coffee Chain', 'Created complete brand identity including logo, color palette, typography, and marketing collateral for coffee chain with 20+ locations.', 'https://behance.net/isabellaartist/coffee-brand', 'https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=800', 'Adobe Illustrator, Photoshop, Brand Strategy, Print Design', '2024-09-12', CURRENT_TIMESTAMP),

(9, 'Cross-Platform Fitness Tracking App', 'Developed fitness tracking mobile app with workout plans, nutrition tracking, and social features. 50K+ downloads.', 'https://github.com/noahprogrammer/fitness-tracker', 'https://images.unsplash.com/photo-1461896836934-ffe607ba8211?w=800', 'React Native, Firebase, Redux, Push Notifications', '2024-08-28', CURRENT_TIMESTAMP),

(10, 'Product Photography Portfolio', 'Professional product photography for e-commerce brands including jewelry, electronics, and fashion items. 200+ products shot.', 'https://miaphotography.com/portfolio', 'https://images.unsplash.com/photo-1606760227091-3dd870d97f1d?w=800', 'Studio Lighting, Adobe Lightroom, Photoshop, Product Styling', '2024-10-15', CURRENT_TIMESTAMP),

(11, 'Cloud Migration Strategy', 'Led cloud migration project moving on-premise infrastructure to AWS, reducing costs by 40% and improving scalability.', 'https://williamarchitect.com/projects/cloud-migration', 'https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=800', 'AWS, Terraform, CloudFormation, Migration Strategy, Cost Optimization', '2024-07-10', CURRENT_TIMESTAMP),

(12, 'Legal Document Translation Project', 'Translated 500+ pages of legal documentation from English to Spanish, French, and German with 99.8% accuracy.', 'https://emmatranslator.com/legal-project', 'https://images.unsplash.com/photo-1450101499163-c8848c66ca85?w=800', 'Translation Tools, Legal Terminology, Quality Assurance', '2024-09-20', CURRENT_TIMESTAMP),

(13, 'Corporate Training Video Series', 'Produced 20-episode corporate training video series including motion graphics, animations, and professional voiceover.', 'https://vimeo.com/benjaminvideo/training-series', 'https://images.unsplash.com/photo-1492619375914-88005aa9e8fb?w=800', 'Adobe Premiere Pro, After Effects, DaVinci Resolve, Motion Graphics', '2024-08-05', CURRENT_TIMESTAMP),

(14, 'SaaS Landing Page Copy', 'Wrote conversion-focused landing page copy for SaaS product, achieving 25% increase in sign-ups and 18% boost in demo requests.', 'https://avacopywriter.com/saas-landing', 'https://images.unsplash.com/photo-1499951360447-b19be8fe80f5?w=800', 'Copywriting, A/B Testing, Conversion Optimization, SEO', '2024-10-08', CURRENT_TIMESTAMP),

(15, 'Progressive Web Application', 'Developed PWA for restaurant chain with online ordering, table reservation, and loyalty program features.', 'https://github.com/masondev/restaurant-pwa', 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=800', 'Vue.js, Service Workers, IndexedDB, PWA, Responsive Design', '2024-09-18', CURRENT_TIMESTAMP),

(16, 'Healthcare App UX Design', 'Designed user experience for telemedicine app, focusing on accessibility and ease of use for elderly patients.', 'https://behance.net/charlotteux/healthcare-app', 'https://images.unsplash.com/photo-1576091160399-112ba8d25d1d?w=800', 'User Research, Wireframing, Prototyping, Accessibility, Figma', '2024-07-30', CURRENT_TIMESTAMP),

(17, 'SEO Audit and Implementation', 'Conducted comprehensive SEO audit and implemented improvements, increasing organic traffic by 180% in 4 months.', 'https://jacobseo.com/case-studies/organic-growth', 'https://images.unsplash.com/photo-1432888498266-38ffec3eaf0a?w=800', 'SEO Tools, Google Search Console, Keyword Research, Technical SEO', '2024-08-22', CURRENT_TIMESTAMP),

(18, 'API Documentation Portal', 'Created comprehensive API documentation portal with interactive examples, code snippets, and tutorials.', 'https://github.com/ameliaeditor/api-docs', 'https://images.unsplash.com/photo-1551650975-87deedd944c3?w=800', 'Markdown, Swagger, OpenAPI, Technical Writing, Documentation Tools', '2024-10-12', CURRENT_TIMESTAMP),

(19, 'CI/CD Pipeline Implementation', 'Implemented automated CI/CD pipeline reducing deployment time from 2 hours to 15 minutes and eliminating manual errors.', 'https://github.com/logandevops/cicd-pipeline', 'https://images.unsplash.com/photo-1618401471353-b98afee0b2eb?w=800', 'Jenkins, GitLab CI, Docker, Kubernetes, Terraform, Ansible', '2024-09-25', CURRENT_TIMESTAMP),

(20, 'Children Book Illustrations', 'Illustrated a series of children books with 50+ colorful illustrations, published by major publishing house.', 'https://harperillustrator.com/childrens-books', 'https://images.unsplash.com/photo-1513364776144-60967b0f800f?w=800', 'Digital Illustration, Procreate, Adobe Illustrator, Character Design', '2024-07-15', CURRENT_TIMESTAMP);

-- Verify the inserted data
SELECT p.id, p.freelancer_id, p.title, p.technologies_used, p.completion_date
FROM portfolios p
ORDER BY p.id;