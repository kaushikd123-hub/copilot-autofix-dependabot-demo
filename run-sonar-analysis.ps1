# SonarQube Local Analysis Script
# Prerequisites:
# 1. SonarQube server running locally (default: http://localhost:9000)
# 2. SonarQube Scanner CLI installed

# Build the project first
Write-Host "Building project..." -ForegroundColor Green
./gradlew clean build -x test

# Check if SonarQube is running
Write-Host "`nChecking SonarQube server..." -ForegroundColor Green
try {
    $response = Invoke-WebRequest -Uri "http://localhost:9000/api/system/status" -Method GET -UseBasicParsing
    Write-Host "SonarQube server is running!" -ForegroundColor Green
} catch {
    Write-Host "ERROR: SonarQube server is not running at http://localhost:9000" -ForegroundColor Red
    Write-Host "Please start SonarQube server first:" -ForegroundColor Yellow
    Write-Host "  - Download from: https://www.sonarsource.com/products/sonarqube/downloads/" -ForegroundColor Yellow
    Write-Host "  - Run: bin/windows-x86-64/StartSonar.bat" -ForegroundColor Yellow
    exit 1
}

# Run SonarQube Scanner
Write-Host "`nRunning SonarQube analysis..." -ForegroundColor Green
Write-Host "Note: Default credentials are admin/admin" -ForegroundColor Yellow

# Using sonar-scanner command
# You need to download and install SonarQube Scanner from:
# https://docs.sonarsource.com/sonarqube/latest/analyzing-source-code/scanners/sonarscanner/

# Option 1: Using sonar-scanner CLI
sonar-scanner `
    -Dsonar.host.url=http://localhost:9000 `
    -Dsonar.login=admin `
    -Dsonar.password=admin

# Option 2: Using Gradle plugin (add to build.gradle first)
# ./gradlew sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=admin

Write-Host "`nAnalysis complete! View results at: http://localhost:9000/dashboard?id=copilot-autofix-dependabot-demo" -ForegroundColor Green
