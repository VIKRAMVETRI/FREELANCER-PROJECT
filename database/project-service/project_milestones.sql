DELETE FROM project_milestones;

ALTER SEQUENCE project_milestones_id_seq RESTART WITH 1;

INSERT INTO project_milestones (project_id, title, description, amount, due_date, status, completed_date, created_at) VALUES
(1, 'Initial Setup and Architecture', 'Set up project structure, database design, and architecture planning', 2500.00, '2024-07-20', 'COMPLETED', '2024-07-18 16:00:00', '2024-07-05 15:00:00'),
(1, 'Frontend Development', 'Develop product catalog, shopping cart, and checkout pages', 2500.00, '2024-08-15', 'COMPLETED', '2024-08-14 17:30:00', '2024-07-05 15:00:00'),
(1, 'Backend and Payment Integration', 'Implement backend APIs and integrate payment gateway', 1500.00, '2024-08-30', 'COMPLETED', '2024-08-29 18:00:00', '2024-07-05 15:00:00'),
(1, 'Testing and Deployment', 'Complete testing, bug fixes, and production deployment', 1000.00, '2024-09-15', 'COMPLETED', '2024-09-15 16:30:00', '2024-07-05 15:00:00');

-- Milestones for Project 3 (IN_PROGRESS)
INSERT INTO project_milestones (project_id, title, description, amount, due_date, status, completed_date, created_at) VALUES
(3, 'First 10 Articles', 'Research and write first 10 blog posts', 1100.00, '2024-11-15', 'COMPLETED', '2024-11-10 14:00:00', '2024-10-18 11:00:00'),
(3, 'Second 10 Articles', 'Research and write remaining 10 blog posts', 1100.00, '2024-11-30', 'IN_PROGRESS', NULL, '2024-10-18 11:00:00');

-- Milestones for Project 4 (IN_PROGRESS)
INSERT INTO project_milestones (project_id, title, description, amount, due_date, status, completed_date, created_at) VALUES
(4, 'Strategy and Content Planning', 'Develop campaign strategy and create content calendar', 1000.00, '2024-10-20', 'COMPLETED', '2024-10-18 15:00:00', '2024-10-05 12:00:00'),
(4, 'Campaign Launch and Management', 'Launch campaigns across all platforms and manage ads', 1500.00, '2024-11-20', 'IN_PROGRESS', NULL, '2024-10-05 12:00:00'),
(4, 'Optimization and Reporting', 'Optimize campaigns based on performance and provide final report', 500.00, '2024-12-15', 'PENDING', NULL, '2024-10-05 12:00:00');

-- Milestones for Project 5 (COMPLETED)
INSERT INTO project_milestones (project_id, title, description, amount, due_date, status, completed_date, created_at) VALUES
(5, 'Architecture Design', 'Design microservices architecture and define service boundaries', 3000.00, '2024-08-01', 'COMPLETED', '2024-07-30 17:00:00', '2024-07-12 10:00:00'),
(5, 'Service Implementation', 'Implement core microservices with APIs', 4000.00, '2024-09-10', 'COMPLETED', '2024-09-08 18:30:00', '2024-07-12 10:00:00'),
(5, 'Testing and Deployment', 'Integration testing and production deployment', 3000.00, '2024-10-15', 'COMPLETED', '2024-10-15 17:00:00', '2024-07-12 10:00:00');

-- Milestones for Project 15 (IN_PROGRESS)
INSERT INTO project_milestones (project_id, title, description, amount, due_date, status, completed_date, created_at) VALUES
(15, 'Design and Architecture', 'UI/UX design and technical architecture setup', 2500.00, '2024-10-30', 'COMPLETED', '2024-10-28 16:00:00', '2024-10-13 15:00:00'),
(15, 'Core Features Development', 'Implement ordering system and table reservation', 3000.00, '2024-11-20', 'IN_PROGRESS', NULL, '2024-10-13 15:00:00'),
(15, 'Testing and Launch', 'Testing, bug fixes, and production launch', 2000.00, '2024-12-01', 'PENDING', NULL, '2024-10-13 15:00:00');

-- Milestones for Project 16 (IN_PROGRESS)
INSERT INTO project_milestones (project_id, title, description, amount, due_date, status, completed_date, created_at) VALUES
(16, 'User Research Phase', 'Conduct user interviews and usability testing with elderly users', 1500.00, '2024-11-05', 'COMPLETED', '2024-11-03 15:30:00', '2024-10-11 12:00:00'),
(16, 'Design Phase', 'Create wireframes and high-fidelity designs', 1700.00, '2024-11-20', 'IN_PROGRESS', NULL, '2024-10-11 12:00:00'),
(16, 'Prototyping and Testing', 'Build interactive prototype and conduct final testing', 1000.00, '2024-11-25', 'PENDING', NULL, '2024-10-11 12:00:00');
