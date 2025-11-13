#!/bin/bash

GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m'

SONAR_TOKEN="${SONAR_TOKEN:-}"

if [ -z "$SONAR_TOKEN" ]; then
  echo -e "${RED}❌ SONAR_TOKEN not set${NC}"
  echo -e "${YELLOW}Run: source .env${NC}"
  exit 1
fi

echo -e "${BLUE}==============================================${NC}"
echo -e "${BLUE}   Analyzing FreelanceNexus Project${NC}"
echo -e "${BLUE}==============================================${NC}"
echo ""

cd /workspaces/FREELANCER-PROJECT

echo -e "${YELLOW}🔍 Running SonarCloud analysis...${NC}"

mvn clean verify sonar:sonar \
  -Dsonar.projectKey=VIKRAMVETRI_FREELANCER-PROJECT \
  -Dsonar.organization=vikramvetri \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.token=$SONAR_TOKEN

if [ $? -eq 0 ]; then
  echo ""
  echo -e "${GREEN}✅ Analysis completed successfully!${NC}"
  echo -e "${BLUE}📊 View results: https://sonarcloud.io/project/overview?id=VIKRAMVETRI_FREELANCER-PROJECT${NC}"
else
  echo ""
  echo -e "${RED}❌ Analysis failed${NC}"
  exit 1
fi
SCRIPT