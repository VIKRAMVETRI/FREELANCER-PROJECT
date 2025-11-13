#!/bin/bash

# ===========================================
# ☁️ SonarCloud Analysis Runner
# ===========================================
# Runs all microservice test cases and uploads analysis results to SonarCloud.
# Make sure you have a valid SonarCloud token and organization.

SONAR_HOST="https://sonarcloud.io"
SONAR_TOKEN="edd8dba4b165e0c39e5c4ff87c83bfa8cd9e199b"   # 🔐 Replace with your actual SonarCloud token
SONAR_ORG="vikram1704"                        # e.g. your GitHub username or org on SonarCloud
SERVICES=("user-service" "project-service" "payment-service" "freelancer-service" "notification-service")

echo "🧪 Starting test execution & SonarCloud analysis for all services..."
echo "SonarCloud Host: $SONAR_HOST"
echo "Organization: $SONAR_ORG"

# Loop through each microservice
for service in "${SERVICES[@]}"; do
    if [ -d "$service" ]; then
        echo ""
        echo "==========================================="
        echo "📦 Running tests and SonarCloud analysis for: $service"
        echo "==========================================="

        cd "$service" || exit 1

        mvn clean verify sonar:sonar \
          -Dsonar.projectKey="$SONAR_ORG_$service" \
          -Dsonar.organization="$SONAR_ORG" \
          -Dsonar.host.url="$SONAR_HOST" \
          -Dsonar.login="$SONAR_TOKEN" \
          -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml

        if [ $? -ne 0 ]; then
            echo "❌ Failed analysis for $service"
        else
            echo "✅ Completed analysis for $service"
        fi

        cd ..
    else
        echo "⚠️ Skipping $service — directory not found."
    fi
done

echo ""
echo "🎉 All SonarCloud analyses completed successfully!"
echo "🌐 Check your dashboard: https://sonarcloud.io/projects"
