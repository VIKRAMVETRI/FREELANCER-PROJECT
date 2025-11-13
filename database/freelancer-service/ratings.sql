-- delete all 
DELETE FROM ratings;

-- Reset the sequence for the ratings table to start from 1
ALTER SEQUENCE ratings_id_seq RESTART WITH 1;

INSERT INTO ratings (freelancer_id, client_id, project_id, rating, review, created_at) VALUES
(1, 1, NULL, 5, 'Outstanding developer! Alex delivered a complex e-commerce platform ahead of schedule. The code quality is exceptional and the application performs flawlessly. Highly recommended!', '2024-08-20 10:30:00'),

(1, 5, NULL, 5, 'Excellent communication and technical skills. Built exactly what we needed with clean, maintainable code. Will definitely work with Alex again.', '2024-10-25 14:15:00'),

(2, 2, NULL, 5, 'Maria is incredibly talented! Her design work transformed our app completely. User engagement increased by 45% after the redesign. Amazing work!', '2024-09-15 09:20:00'),

(2, 8, NULL, 5, 'Professional, creative, and responsive. Maria delivered a beautiful design system that our entire team loves working with.', '2024-07-28 16:45:00'),

(3, 3, NULL, 5, 'Carlos is an exceptional writer. His content is engaging, well-researched, and SEO-optimized. Our traffic has doubled since working with him!', '2024-07-05 11:00:00'),

(3, 12, NULL, 4, 'Great content writer with good SEO knowledge. Delivered quality articles on time. Would recommend with minor revisions needed occasionally.', '2024-09-18 13:30:00'),

(4, 4, NULL, 5, 'Sophia exceeded all expectations! Our social media campaign was a huge success. She\'s strategic, data-driven, and creative. Can\'t wait to work together again.', '2024-09-10 15:20:00'),

(5, 6, NULL, 5, 'Lucas is a rockstar backend engineer. He architected our microservices platform beautifully. The system handles millions of transactions without any issues.', '2024-10-05 10:45:00'),

(5, 15, NULL, 5, 'Phenomenal technical expertise. Lucas solved complex scalability issues that other developers couldn\'t figure out. True professional!', '2024-08-12 14:00:00'),

(6, 7, NULL, 5, 'Olivia provided excellent business consulting. Her insights helped us reduce costs by 35% while improving efficiency. Worth every penny!', '2024-08-25 09:15:00'),

(7, 9, NULL, 5, 'Ethan created an amazing analytics dashboard that gives us real-time insights into our business. The visualizations are clear and actionable.', '2024-07-30 16:30:00'),

(8, 10, NULL, 5, 'Isabella is a brilliant designer! She created a stunning brand identity for our coffee chain. Customers love the new look. Highly creative!', '2024-09-18 11:45:00'),

(9, 11, NULL, 5, 'Noah built an excellent fitness app for us. The UI is smooth, features work perfectly, and we\'ve had great user feedback. Top-notch developer!', '2024-09-02 13:20:00'),

(10, 13, NULL, 5, 'Mia\'s product photography is absolutely stunning! Her work has significantly improved our e-commerce conversion rates. True artist!', '2024-10-20 10:00:00'),

(11, 14, NULL, 5, 'William led our cloud migration flawlessly. Zero downtime, 40% cost reduction, and improved performance. Expert-level work!', '2024-07-15 15:45:00'),

(12, 16, NULL, 5, 'Emma provided accurate, professional translations for our legal documents. Her attention to detail is impressive. Highly reliable!', '2024-09-25 09:30:00'),

(13, 17, NULL, 5, 'Benjamin created exceptional training videos for our company. Professional quality with great motion graphics. Our team loves them!', '2024-08-10 14:20:00'),

(14, 18, NULL, 4, 'Ava is a talented copywriter. The landing page copy she wrote improved our conversion rate significantly. Very pleased with the results.', '2024-10-13 11:15:00'),

(15, 19, NULL, 5, 'Mason built a fantastic PWA for our restaurant. Customers love the easy ordering system and the loyalty program features. Great job!', '2024-09-23 16:00:00'),

(16, 20, NULL, 5, 'Charlotte designed an incredibly user-friendly healthcare app. Her focus on accessibility for elderly patients was exactly what we needed. Excellent work!', '2024-08-05 10:30:00');
