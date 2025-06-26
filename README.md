# Sauce Automation Test

This is a Java-based Selenium automation framework configured with Maven. It appears to be built to run UI tests potentially on [Sauce Labs](https://saucelabs.com/)

## 📁 Project Structure

- `pom.xml` - Maven configuration file with dependencies and build plugins.
- `config.properties` - Configuration file for test settings.
- `.classpath`, `.project` - Eclipse IDE metadata files.
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

Update `config.properties` with your environment-specific settings (e.g., browser, Sauce Labs credentials, etc.)

## 🧪 Test Execution

Tests can be executed via the Maven Surefire plugin or directly from your IDE.

## ✨ Notes

- Test results and reports (if implemented) will be generated in the `target/` directory.

 ## 📊 Sample Test Report

Below are sample screenshots from the Extent Report generated after test execution.

### 📋 Dashboard Summary
<img width="600" alt="image" src="https://github.com/user-attachments/assets/ed63f2f3-3734-4ce7-916d-e2efe8bfc3af" />


### ✅ Test Execution Details
<img width="600" alt="image" src="https://github.com/user-attachments/assets/ca7491af-ea03-4781-8ee8-fa900f740851" />


📎 **Full Report:**  
[Click to open the full HTML report](target/reports/ExtentReport_20250626_135353.html)


