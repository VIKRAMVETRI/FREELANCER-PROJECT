#!/bin/bash

# Colors
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No color

PROJECT_ROOT=$(pwd)
LOG_DIR="$PROJECT_ROOT/logs"
mkdir -p "$LOG_DIR"

# List of Maven services
SERVICES=(
  "config-server"
  "eureka-server"
  "gateway-service"
  "user-service"
  "freelancer-service"
  "project-service"
  "payment-service"
  "notification-service"
)

echo -e "${YELLOW}==============================================${NC}"
echo -e "${YELLOW}   Building All Microservices (mvn clean install)${NC}"
echo -e "${YELLOW}==============================================${NC}"
echo ""

for service in "${SERVICES[@]}"; do
  echo -e "${YELLOW}🔧 Building $service...${NC}"

  if [ -d "$PROJECT_ROOT/$service" ]; then
    cd "$PROJECT_ROOT/$service"
    mvn clean install > "$LOG_DIR/$service-build.log" 2>&1

    if [ $? -eq 0 ]; then
      echo -e "${GREEN}✅ $service built successfully${NC}"
    else
      echo -e "${RED}❌ Failed to build $service. Check log: $LOG_DIR/$service-build.log${NC}"
    fi

    cd "$PROJECT_ROOT"
  else
    echo -e "${RED}⚠️ Directory $service not found${NC}"
  fi

  echo ""
done

echo -e "${GREEN}🎉 All services processed. Logs are in $LOG_DIR${NC}"
