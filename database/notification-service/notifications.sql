delete from notifications;

-- Reset sequence
ALTER SEQUENCE notifications_id_seq RESTART WITH 1;

-- Notifications for User 1 (Client - John Smith) - Project 1
INSERT INTO notifications (user_id, type, title, message, is_read, email_sent, recipient_email, related_entity_type, related_entity_id, created_at, read_at, metadata) VALUES
(1, 'PROJECT_CREATED', 'Project Created Successfully', 'Your project "E-Commerce Website Development" has been created and is now open for proposals.', true, true, 'john.smith@client.com', 'PROJECT', 1, '2024-07-01 09:15:00', '2024-07-01 10:30:00', '{"project_title":"E-Commerce Website Development","budget":"$5000-$8000"}'),
(1, 'PROPOSAL_SUBMITTED', 'New Proposal Received', 'Alex Developer has submitted a proposal for your project "E-Commerce Website Development".', true, true, 'john.smith@client.com', 'PROPOSAL', 1, '2024-07-02 10:35:00', '2024-07-02 14:00:00', '{"freelancer_name":"Alex Developer","proposed_budget":"$7500"}'),
(1, 'PROPOSAL_SUBMITTED', 'New Proposal Received', 'Lucas Engineer has submitted a proposal for your project "E-Commerce Website Development".', true, false, 'john.smith@client.com', 'PROPOSAL', 2, '2024-07-03 09:20:00', '2024-07-03 11:00:00', '{"freelancer_name":"Lucas Engineer","proposed_budget":"$8000"}'),
(1, 'PROJECT_ASSIGNED', 'Proposal Accepted', 'You have accepted the proposal from Alex Developer for project "E-Commerce Website Development".', true, true, 'john.smith@client.com', 'PROJECT', 1, '2024-07-05 14:25:00', '2024-07-05 15:00:00', '{"freelancer_name":"Alex Developer","project_amount":"$7500"}'),
(1, 'PAYMENT_COMPLETED', 'Payment Successful', 'Payment of $2500.00 has been successfully processed for milestone "Initial Setup and Architecture".', true, true, 'john.smith@client.com', 'PAYMENT', 1, '2024-07-19 10:30:00', '2024-07-19 11:00:00', '{"transaction_id":"TXN-2024-07-001","amount":"$2500.00"}'),
(1, 'PAYMENT_COMPLETED', 'Payment Successful', 'Payment of $2500.00 has been successfully processed for milestone "Frontend Development".', true, true, 'john.smith@client.com', 'PAYMENT', 2, '2024-08-15 11:00:00', '2024-08-15 12:30:00', '{"transaction_id":"TXN-2024-08-001","amount":"$2500.00"}');

-- Notifications for User 21 (Freelancer - Alex Developer) - Project 1
INSERT INTO notifications (user_id, type, title, message, is_read, email_sent, recipient_email, related_entity_type, related_entity_id, created_at, read_at, metadata) VALUES
(21, 'PROPOSAL_ACCEPTED', 'Congratulations! Your Proposal Was Accepted', 'Your proposal for "E-Commerce Website Development" has been accepted by John Smith. Time to start working!', true, true, 'alex.developer@freelancer.com', 'PROPOSAL', 1, '2024-07-05 14:25:00', '2024-07-05 14:30:00', '{"client_name":"John Smith","project_amount":"$7500"}'),
(21, 'PROJECT_ASSIGNED', 'Project Assigned', 'You have been assigned to work on "E-Commerce Website Development". Check project details and milestones.', true, true, 'alex.developer@freelancer.com', 'PROJECT', 1, '2024-07-05 14:30:00', '2024-07-05 15:00:00', '{"project_title":"E-Commerce Website Development","deadline":"2024-09-15"}'),
(21, 'PAYMENT_COMPLETED', 'Payment Received', 'You have received payment of $2500.00 for milestone "Initial Setup and Architecture".', true, true, 'alex.developer@freelancer.com', 'PAYMENT', 1, '2024-07-19 10:30:00', '2024-07-19 10:45:00', '{"transaction_id":"TXN-2024-07-001","amount":"$2500.00"}'),
(21, 'PAYMENT_COMPLETED', 'Payment Received', 'You have received payment of $2500.00 for milestone "Frontend Development".', true, true, 'alex.developer@freelancer.com', 'PAYMENT', 2, '2024-08-15 11:00:00', '2024-08-15 11:15:00', '{"transaction_id":"TXN-2024-08-001","amount":"$2500.00"}'),
(21, 'PAYMENT_COMPLETED', 'Payment Received', 'You have received payment of $1500.00 for milestone "Backend and Payment Integration".', true, true, 'alex.developer@freelancer.com', 'PAYMENT', 3, '2024-08-30 09:45:00', '2024-08-30 10:00:00', '{"transaction_id":"TXN-2024-08-002","amount":"$1500.00"}'),
(21, 'PAYMENT_COMPLETED', 'Payment Received', 'You have received payment of $1000.00 for milestone "Testing and Deployment". Project completed!', true, true, 'alex.developer@freelancer.com', 'PAYMENT', 4, '2024-09-16 14:20:00', '2024-09-16 14:30:00', '{"transaction_id":"TXN-2024-09-001","amount":"$1000.00"}');

-- Notifications for User 2 (Client - Sarah Johnson) - Project 2
INSERT INTO notifications (user_id, type, title, message, is_read, email_sent, recipient_email, related_entity_type, related_entity_id, created_at, read_at, metadata) VALUES
(2, 'PROJECT_CREATED', 'Project Created Successfully', 'Your project "Mobile Banking App UI/UX Redesign" has been created and is now open for proposals.', true, true, 'sarah.johnson@client.com', 'PROJECT', 2, '2024-08-01 10:20:00', '2024-08-01 11:00:00', '{"project_title":"Mobile Banking App UI/UX Redesign","budget":"$3500-$5000"}'),
(2, 'PROPOSAL_SUBMITTED', 'New Proposal Received', 'Maria Designer has submitted a proposal for your project "Mobile Banking App UI/UX Redesign".', true, true, 'sarah.johnson@client.com', 'PROPOSAL', 3, '2024-08-02 11:05:00', '2024-08-02 13:00:00', '{"freelancer_name":"Maria Designer","proposed_budget":"$4500"}'),
(2, 'PROJECT_ASSIGNED', 'Proposal Accepted', 'You have accepted the proposal from Maria Designer for project "Mobile Banking App UI/UX Redesign".', true, true, 'sarah.johnson@client.com', 'PROJECT', 2, '2024-08-04 15:35:00', '2024-08-04 16:00:00', '{"freelancer_name":"Maria Designer","project_amount":"$4500"}'),
(2, 'PAYMENT_COMPLETED', 'Payment Successful', 'Payment of $4500.00 has been successfully processed for project completion.', true, true, 'sarah.johnson@client.com', 'PAYMENT', 5, '2024-10-01 10:15:00', '2024-10-01 11:00:00', '{"transaction_id":"TXN-2024-09-002","amount":"$4500.00"}');

-- Notifications for User 22 (Freelancer - Maria Designer)
INSERT INTO notifications (user_id, type, title, message, is_read, email_sent, recipient_email, related_entity_type, related_entity_id, created_at, read_at, metadata) VALUES
(22, 'PROPOSAL_ACCEPTED', 'Congratulations! Your Proposal Was Accepted', 'Your proposal for "Mobile Banking App UI/UX Redesign" has been accepted by Sarah Johnson.', true, true, 'maria.designer@freelancer.com', 'PROPOSAL', 3, '2024-08-04 15:35:00', '2024-08-04 15:40:00', '{"client_name":"Sarah Johnson","project_amount":"$4500"}'),
(22, 'PAYMENT_COMPLETED', 'Payment Received', 'You have received payment of $4500.00 for completing the Mobile Banking App UI/UX Redesign project.', true, true, 'maria.designer@freelancer.com', 'PAYMENT', 5, '2024-10-01 10:15:00', '2024-10-01 10:30:00', '{"transaction_id":"TXN-2024-09-002","amount":"$4500.00"}');

-- Notifications for User 3 (Client - Michael Williams) - Project 3 (IN_PROGRESS)
INSERT INTO notifications (user_id, type, title, message, is_read, email_sent, recipient_email, related_entity_type, related_entity_id, created_at, read_at, metadata) VALUES
(3, 'PROJECT_CREATED', 'Project Created Successfully', 'Your project "Content Writing for Tech Blog" has been created and is now open for proposals.', true, true, 'michael.williams@client.com', 'PROJECT', 3, '2024-10-15 11:35:00', '2024-10-15 12:00:00', '{"project_title":"Content Writing for Tech Blog","budget":"$1500-$2500"}'),
(3, 'PROPOSAL_SUBMITTED', 'New Proposal Received', 'Carlos Writer has submitted a proposal for your project "Content Writing for Tech Blog".', true, true, 'michael.williams@client.com', 'PROPOSAL', 5, '2024-10-16 09:50:00', '2024-10-16 11:00:00', '{"freelancer_name":"Carlos Writer","proposed_budget":"$2200"}'),
(3, 'PROJECT_ASSIGNED', 'Proposal Accepted', 'You have accepted the proposal from Carlos Writer for project "Content Writing for Tech Blog".', true, true, 'michael.williams@client.com', 'PROJECT', 3, '2024-10-18 10:20:00', '2024-10-18 11:00:00', '{"freelancer_name":"Carlos Writer","project_amount":"$2200"}'),
(3, 'PAYMENT_COMPLETED', 'Payment Successful', 'Payment of $1100.00 has been successfully processed for milestone "First 10 Articles".', true, true, 'michael.williams@client.com', 'PAYMENT', 6, '2024-11-11 09:30:00', '2024-11-11 10:00:00', '{"transaction_id":"TXN-2024-11-001","amount":"$1100.00"}');

-- Notifications for User 23 (Freelancer - Carlos Writer)
INSERT INTO notifications (user_id, type, title, message, is_read, email_sent, recipient_email, related_entity_type, related_entity_id, created_at, read_at, metadata) VALUES
(23, 'PROPOSAL_ACCEPTED', 'Congratulations! Your Proposal Was Accepted', 'Your proposal for "Content Writing for Tech Blog" has been accepted by Michael Williams.', true, true, 'carlos.writer@freelancer.com', 'PROPOSAL', 5, '2024-10-18 10:20:00', '2024-10-18 10:30:00', '{"client_name":"Michael Williams","project_amount":"$2200"}'),
(23, 'PAYMENT_COMPLETED', 'Payment Received', 'You have received payment of $1100.00 for milestone "First 10 Articles".', false, true, 'carlos.writer@freelancer.com', 'PAYMENT', 6, '2024-11-11 09:30:00', NULL, '{"transaction_id":"TXN-2024-11-001","amount":"$1100.00"}');

-- Notifications for User 17 (Client) - Project 17 (OPEN - with pending proposals)
INSERT INTO notifications (user_id, type, title, message, is_read, email_sent, recipient_email, related_entity_type, related_entity_id, created_at, read_at, metadata) VALUES
(17, 'PROJECT_CREATED', 'Project Created Successfully', 'Your project "SEO Audit and Implementation" has been created and is now open for proposals.', true, true, 'ryan.taylor@client.com', 'PROJECT', 17, '2024-11-01 09:35:00', '2024-11-01 10:00:00', '{"project_title":"SEO Audit and Implementation","budget":"$2500-$4000"}'),
(17, 'PROPOSAL_SUBMITTED', 'New Proposal Received', 'Jacob SEO Expert has submitted a proposal for your project "SEO Audit and Implementation".', false, true, 'ryan.taylor@client.com', 'PROPOSAL', 17, '2024-11-02 10:20:00', NULL, '{"freelancer_name":"Jacob SEO Expert","proposed_budget":"$3500"}'),
(17, 'PROPOSAL_SUBMITTED', 'New Proposal Received', 'Ava Copywriter has submitted a proposal for your project "SEO Audit and Implementation".', false, true, 'ryan.taylor@client.com', 'PROPOSAL', 18, '2024-11-02 14:35:00', NULL, '{"freelancer_name":"Ava Copywriter","proposed_budget":"$3000"}'),
(17, 'PROPOSAL_SUBMITTED', 'New Proposal Received', 'Carlos Writer has submitted a proposal for your project "SEO Audit and Implementation".', false, false, 'ryan.taylor@client.com', 'PROPOSAL', 19, '2024-11-03 09:05:00', NULL, '{"freelancer_name":"Carlos Writer","proposed_budget":"$2800"}');

-- Notifications for User 37 (Freelancer - Jacob SEO)
INSERT INTO notifications (user_id, type, title, message, is_read, email_sent, recipient_email, related_entity_type, related_entity_id, created_at, read_at, metadata) VALUES
(37, 'PROPOSAL_SUBMITTED', 'Proposal Submitted Successfully', 'Your proposal for "SEO Audit and Implementation" has been submitted. The client will review it soon.', true, true, 'jacob.seo@freelancer.com', 'PROPOSAL', 17, '2024-11-02 10:20:00', '2024-11-02 10:25:00', '{"project_title":"SEO Audit and Implementation","proposed_budget":"$3500"}');

-- Payment failure notification
INSERT INTO notifications (user_id, type, title, message, is_read, email_sent, recipient_email, related_entity_type, related_entity_id, created_at, read_at, metadata) VALUES
(8, 'PAYMENT_FAILED', 'Payment Failed', 'Payment of $500.00 for bonus payment has failed due to insufficient funds. Please update your payment method.', true, true, 'amanda.davis@client.com', 'PAYMENT', 23, '2024-10-01 10:15:00', '2024-10-01 11:30:00', '{"transaction_id":"TXN-2024-10-007","amount":"$500.00","reason":"Insufficient funds"}');

-- Account notifications for new users
INSERT INTO notifications (user_id, type, title, message, is_read, email_sent, recipient_email, related_entity_type, related_entity_id, created_at, read_at, metadata) VALUES
(1, 'ACCOUNT_CREATED', 'Welcome to FreelanceNexus!', 'Your account has been created successfully. Start posting projects and hire talented freelancers.', true, true, 'john.smith@client.com', NULL, NULL, '2024-06-15 09:00:00', '2024-06-15 09:30:00', '{"account_type":"CLIENT"}'),
(21, 'ACCOUNT_CREATED', 'Welcome to FreelanceNexus!', 'Your freelancer account has been created successfully. Start bidding on exciting projects!', true, true, 'alex.developer@freelancer.com', NULL, NULL, '2024-06-20 10:00:00', '2024-06-20 10:15:00', '{"account_type":"FREELANCER"}');

-- System notifications (sent to multiple users)
INSERT INTO notifications (user_id, type, title, message, is_read, email_sent, recipient_email, related_entity_type, related_entity_id, created_at, read_at, metadata) VALUES
(1, 'SYSTEM_NOTIFICATION', 'Platform Maintenance Scheduled', 'FreelanceNexus will undergo scheduled maintenance on November 15, 2024 from 2:00 AM to 4:00 AM EST. Services may be temporarily unavailable.', false, true, 'john.smith@client.com', NULL, NULL, '2024-11-10 08:00:00', NULL, '{"maintenance_date":"2024-11-15","start_time":"02:00 AM","end_time":"04:00 AM"}'),
(2, 'SYSTEM_NOTIFICATION', 'Platform Maintenance Scheduled', 'FreelanceNexus will undergo scheduled maintenance on November 15, 2024 from 2:00 AM to 4:00 AM EST. Services may be temporarily unavailable.', false, true, 'sarah.johnson@client.com', NULL, NULL, '2024-11-10 08:00:00', NULL, '{"maintenance_date":"2024-11-15","start_time":"02:00 AM","end_time":"04:00 AM"}'),
(21, 'SYSTEM_NOTIFICATION', 'New Feature: AI-Powered Proposal Ranking', 'We have introduced AI-powered proposal ranking to help you submit better proposals. Check it out now!', true, true, 'alex.developer@freelancer.com', NULL, NULL, '2024-10-01 09:00:00', '2024-10-02 10:00:00', '{"feature":"AI_PROPOSAL_RANKING"}'),
(22, 'SYSTEM_NOTIFICATION', 'New Feature: AI-Powered Proposal Ranking', 'We have introduced AI-powered proposal ranking to help you submit better proposals. Check it out now!', false, false, 'maria.designer@freelancer.com', NULL, NULL, '2024-10-01 09:00:00', NULL, '{"feature":"AI_PROPOSAL_RANKING"}');

-- Recent unread notifications for active users
INSERT INTO notifications (user_id, type, title, message, is_read, email_sent, recipient_email, related_entity_type, related_entity_id, created_at, read_at, metadata) VALUES
(19, 'PROJECT_CREATED', 'Project Created Successfully', 'Your project "CI/CD Pipeline Setup" has been created and is now open for proposals.', false, true, 'kevin.jackson@client.com', 'PROJECT', 19, '2024-11-05 11:20:00', NULL, '{"project_title":"CI/CD Pipeline Setup","budget":"$4000-$6000"}'),
(19, 'PROPOSAL_SUBMITTED', 'New Proposal Received', 'Logan DevOps has submitted a proposal for your project "CI/CD Pipeline Setup".', false, true, 'kevin.jackson@client.com', 'PROPOSAL', 20, '2024-11-06 09:35:00', NULL, '{"freelancer_name":"Logan DevOps","proposed_budget":"$5500"}'),
(39, 'PROPOSAL_SUBMITTED', 'Proposal Submitted Successfully', 'Your proposal for "CI/CD Pipeline Setup" has been submitted. The client will review it soon.', false, true, 'logan.devops@freelancer.com', 'PROPOSAL', 20, '2024-11-06 09:35:00', NULL, '{"project_title":"CI/CD Pipeline Setup","proposed_budget":"$5500"}');
