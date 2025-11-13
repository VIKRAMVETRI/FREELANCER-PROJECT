#!/bin/bash

# ===========================================
# 🚀 Build All Maven Services Script
# ===========================================
# This script runs mvn clean install for all microservices.
# Run from the project root directory.
# ===========================================

SERVICES=("user-service" "project-service" "payment-service" "freelancer-service" "notification-service")

echo "🏗️  Starting Maven build for all services..."
echo ""

for service in "${SERVICES[@]}"; do
    if [ -d "$service" ]; then
        echo "==========================================="
        echo "📦 Building: $service"
        echo "==========================================="
        cd "$service" || exit 1

        mvn clean install -DskipTests=false

        if [ $? -ne 0 ]; then
            echo "❌ Build failed for $service"
            echo "⛔ Stopping build process..."
            exit 1
        else
            echo "✅ Build successful for $service"
        fi

        cd ..
        echo ""
    else
        echo "⚠️ Skipping $service — directory not found."
    fi
done

echo "✅ All services built successfully!"
