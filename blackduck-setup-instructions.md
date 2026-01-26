# Black Duck Integration Setup Instructions

## Overview
Black Duck (Synopsys) has been integrated into the CI/CD pipeline to scan for open-source vulnerabilities and license compliance issues.

## Required GitHub Secrets

You need to add the following secrets to your GitHub repository:

### 1. Navigate to Repository Settings
- Go to: https://github.com/kaushikd123-hub/copilot-autofix-dependabot-demo/settings/secrets/actions

### 2. Add Secrets

Click "New repository secret" and add:

#### BLACKDUCK_URL
- **Name**: `BLACKDUCK_URL`
- **Value**: Your Black Duck server URL
- **Example**: `https://your-company.blackduck.com` or `https://blackduck.synopsys.com`

#### BLACKDUCK_API_TOKEN
- **Name**: `BLACKDUCK_API_TOKEN`
- **Value**: Your Black Duck API token
- **How to get**: 
  1. Log in to your Black Duck server
  2. Go to User Settings → Access Tokens
  3. Create a new token with read/write permissions
  4. Copy and paste the token value

## Workflow Configuration

The Black Duck workflow (`.github/workflows/blackduck.yml`) will:

✅ Run on every push to master/main branches
✅ Run on pull requests
✅ Run weekly on Sundays (scheduled scan)
✅ Can be manually triggered via GitHub Actions UI

## Scan Features

- **Dependency Detection**: Identifies all open-source components in Gradle project
- **Vulnerability Detection**: Finds known CVEs in dependencies
- **License Compliance**: Checks for license policy violations
- **Policy Enforcement**: Fails build on BLOCKER, CRITICAL, or MAJOR severity issues
- **Risk Reports**: Generates PDF risk report and notices report

## After Setup

1. Add the required secrets to GitHub
2. The workflow will automatically run on the next push
3. View results in:
   - GitHub Actions → Black Duck Security Scan workflow
   - Your Black Duck server dashboard
   - Download PDF reports from workflow artifacts

## Monitoring

- **GitHub Actions**: https://github.com/kaushikd123-hub/copilot-autofix-dependabot-demo/actions/workflows/blackduck.yml
- **Artifacts**: Download Black Duck PDF reports from completed workflow runs

## Troubleshooting

If the scan fails:
1. Verify secrets are correctly set
2. Check Black Duck server connectivity
3. Verify API token has correct permissions
4. Review workflow logs for detailed error messages

## Alternative: Black Duck CLI (Local Scanning)

If you want to scan locally before pushing:

```bash
# Download Detect script
curl -O https://detect.synopsys.com/detect8.sh
chmod +x detect8.sh

# Run local scan
./detect8.sh \
  --blackduck.url=YOUR_BLACKDUCK_URL \
  --blackduck.api.token=YOUR_API_TOKEN \
  --detect.project.name=copilot-autofix-dependabot-demo \
  --detect.project.version.name=local-scan
```

## Next Steps

1. **Add GitHub Secrets** (required before workflow can run)
2. **Test the workflow** by pushing a commit or manually triggering it
3. **Review results** in Black Duck dashboard
4. **Fix vulnerabilities** identified by Black Duck
