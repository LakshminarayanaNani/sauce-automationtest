# Sauce Automation Test

This is a Java-based Selenium automation framework configured with Maven. It appears to be built to run UI tests potentially on [Sauce Labs](https://saucelabs.com/)

## 📁 Project Structure

- `pom.xml` - Maven configuration file with dependencies and build plugins.
- `config.properties` - Configuration file for test settings.
- `.git/` - Git version control data.

## 🚀 Getting Started

### Prerequisites

- Java JDK 17 or above
- Maven 3.6+
- Git
- (Optional) Eclipse or IntelliJ IDEA

### Installation

1. **Clone the repository**  
   ```bash
   git clone <your-repo-url>
   cd sauce-automation-test
   ```

2. **Install dependencies**  
   ```bash
   mvn clean install
   ```

3. **Run the tests**  
   ```bash
   mvn test
   ```

## ⚙️ Configuration

Update `config.properties` according to environment-specific settings 

## 🧪 Test Execution

Tests can be executed via the Maven Surefire plugin or directly from IDE.

## ✨ Notes

- Test results and reports will be generated in the `target/` directory.

 ## 📊 Sample Test Report

Below are sample screenshots from the Extent Report generated after test execution.

### 📋 Dashboard Summary
<img width="450" alt="image" src="https://github.com/user-attachments/assets/a5b73ccc-c927-4f39-b6cc-d3714588f2dc" />


### ✅ Test Execution Details
<img width="450" alt="image" src="https://github.com/user-attachments/assets/ca7491af-ea03-4781-8ee8-fa900f740851" />


📎 **Full Report:**  
[Click to open the full HTML report](target/reports/ExtentReport_20250626_135353.html)


