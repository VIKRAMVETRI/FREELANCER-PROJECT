#!/bin/bash

echo "🗄️ Starting database import process..."

# Database connection details
DB_HOST="localhost"
DB_PORT="5432"
DB_USER="postgres"
DB_PASSWORD="123456"

# Function to execute SQL file
execute_sql() {
    local db_name=$1
    local sql_file=$2
    local description=$3
    
    echo "📥 Importing $description into $db_name..."
    
    if [ ! -f "$sql_file" ]; then
        echo "⚠️  Warning: $sql_file not found, skipping..."
        return 1
    fi
    
    docker exec -i postgres-freelance \
        psql -U "$DB_USER" -d "$db_name" < "$sql_file" 2>&1 | grep -v "NOTICE"
    
    if [ $? -eq 0 ]; then
        echo "✅ Successfully imported $description"
    else
        echo "❌ Failed to import $description"
        return 1
    fi
}

# Function to check if database is ready
wait_for_db() {
    echo "⏳ Waiting for PostgreSQL to be ready..."
    max_attempts=30
    attempt=0
    
    until docker exec postgres-freelance pg_isready -U "$DB_USER" > /dev/null 2>&1 || [ $attempt -eq $max_attempts ]; do
        sleep 1
        attempt=$((attempt + 1))
    done
    
    if [ $attempt -eq $max_attempts ]; then
        echo "❌ PostgreSQL failed to become ready"
        exit 1
    fi
    
    echo "✅ PostgreSQL is ready"
}

# Wait for database to be ready
wait_for_db

# Set the base directory for SQL files
SQL_DIR="database"

echo ""
echo "=========================================="
echo "  USER SERVICE DATABASE"
echo "=========================================="
execute_sql "freelance_nexus_users" "$SQL_DIR/user-service/users.sql" "Users table"

echo ""
echo "=========================================="
echo "  FREELANCER SERVICE DATABASE"
echo "=========================================="
execute_sql "freelance_nexus_freelancers" "$SQL_DIR/freelancer-service/freelancers.sql" "Freelancers table"
execute_sql "freelance_nexus_freelancers" "$SQL_DIR/freelancer-service/portfolios.sql" "Portfolios table"
execute_sql "freelance_nexus_freelancers" "$SQL_DIR/freelancer-service/ratings.sql" "Ratings table"
execute_sql "freelance_nexus_freelancers" "$SQL_DIR/freelancer-service/skills.sql" "Skills table"

echo ""
echo "=========================================="
echo "  PROJECT SERVICE DATABASE"
echo "=========================================="
execute_sql "freelance_nexus_projects" "$SQL_DIR/project-service/projects.sql" "Projects table"
execute_sql "freelance_nexus_projects" "$SQL_DIR/project-service/proposals.sql" "Proposals table"
execute_sql "freelance_nexus_projects" "$SQL_DIR/project-service/project_milestones.sql" "Project Milestones table"

echo ""
echo "=========================================="
echo "  PAYMENT SERVICE DATABASE"
echo "=========================================="
execute_sql "freelance_nexus_payments" "$SQL_DIR/payment-service/payments.sql" "Payments table"
execute_sql "freelance_nexus_payments" "$SQL_DIR/payment-service/transaction_history.sql" "Transaction History table"

echo ""
echo "=========================================="
echo "  NOTIFICATION SERVICE DATABASE"
echo "=========================================="
execute_sql "freelance_nexus_notifications" "$SQL_DIR/notification-service/notifications.sql" "Notifications table"

echo ""
echo "=========================================="
echo "  DATABASE IMPORT SUMMARY"
echo "=========================================="

# Get row counts for verification
echo ""
echo "📊 Verification - Row Counts:"
echo ""

echo "User Service:"
docker exec -e PGPASSWORD="$DB_PASSWORD" postgres-freelance \
    psql -U "$DB_USER" -d freelance_nexus_users -t -c \
    "SELECT 'Users: ' || COUNT(*) FROM users;"

echo ""
echo "Freelancer Service:"
docker exec -e PGPASSWORD="$DB_PASSWORD" postgres-freelance \
    psql -U "$DB_USER" -d freelance_nexus_freelancers -t -c \
    "SELECT 'Freelancers: ' || COUNT(*) FROM freelancers;
     SELECT 'Portfolios: ' || COUNT(*) FROM portfolios;
     SELECT 'Ratings: ' || COUNT(*) FROM ratings;
     SELECT 'Skills: ' || COUNT(*) FROM skills;"

echo ""
echo "Project Service:"
docker exec -e PGPASSWORD="$DB_PASSWORD" postgres-freelance \
    psql -U "$DB_USER" -d freelance_nexus_projects -t -c \
    "SELECT 'Projects: ' || COUNT(*) FROM projects;
     SELECT 'Proposals: ' || COUNT(*) FROM proposals;
     SELECT 'Milestones: ' || COUNT(*) FROM project_milestones;"

echo ""
echo "Payment Service:"
docker exec -e PGPASSWORD="$DB_PASSWORD" postgres-freelance \
    psql -U "$DB_USER" -d freelance_nexus_payments -t -c \
    "SELECT 'Payments: ' || COUNT(*) FROM payments;
     SELECT 'Transaction History: ' || COUNT(*) FROM transaction_history;"

echo ""
echo "Notification Service:"
docker exec -e PGPASSWORD="$DB_PASSWORD" postgres-freelance \
    psql -U "$DB_USER" -d freelance_nexus_notifications -t -c \
    "SELECT 'Notifications: ' || COUNT(*) FROM notifications;"

echo ""
echo "=========================================="
echo "✅ Database import process completed!"
echo "=========================================="