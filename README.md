# fusionAutomationLab
 An Appium-Java based android automation framework


### How to execute automation

#### 1.	Download the Project:
- Clone the project to the local.
- Update the below parameters as per the device under test in global.properties file present in the project root directory.
  
    - `ANDROID_SERIALID = <Serial Id>`
    - `APP_PATH = <App Name>`
    - `MAC_ANDROID_HOME= </Users/Sunmoon/Library/Android/sdk/platform-tools>`
  
#### 2. Start Appium Server
- To start the appium server, launch the Appium-Desktop by double clicking on it and set the below parameters:
    - `host: 0.0.0.0(default) or 127.0.0.1`
    - `Port: 4723`

- Click On Edit Configurations and set the below parameters (one time only). 

  **On Windows**
    - `ANDROID_HOME: C:\Users\<username>\AppData\Local\Android\Sdk`
    - `JAVA_HOME: C:\Program Files\<jdk-version>`

  **On MAC**
    - `ANDROID_HOME: /Users/<username>/Library/Android/sdk`
    - `JAVA_HOME: /Library/Java/JavaVirtualMachines/jdk<version>.jdk/Contents/Home`

- Then start the server by clicking on "Start Server" and keep the server running in background.

#### 3.	Maven Command to trigger the job:
- From the root directory, open the terminal and execute **any one command** from below to start the automation execution:

  **Maven Command -> Pick the device serialid from the global.properties**
  - `mvn clean test`
  
  **Maven Command -> enter the device serialid dynamically during run time**
  - `mvn clean test -DANDROID_SERIALID=<serialId>`
  
   **Maven Command - enter the device serialid and appium port dynamically during run time**
  - `mvn clean test -DANDROID_SERIALID=<serialId> -DPORT=<port>`
 
### Silent Features of this framework:
- Data Provder - Input from external file ie. Excel sheet(eg : username, pswd, 65-inch TV, LG 164 cm (65 inches) 4K Ultra HD)
- Generate Extent report inside report directory at root level.
- Add Assertions with Screenshots
- Attach screenshots for Failed test cases.
- Added loggers - configurable using log4j.properties file.
- Capture logcat for for the Failed testcases.
- Can be runned from both Windows PC as well as on MAC.
- Add JavaDoc to all the methods.
  
 
 
 
###### For any support or query, please contact Vikas Kumar [fusionvikas.py@gmail.com]  ######

