# Columbia CourseWorks App

## Overview


The goal of this mobile application is to compliment the features of Columbia's Courseworks website and increase accessibility for students.

We are currently in the pre-Alpha build stage.

## Features

### Current Features
1. "Remember Me!" functionality.
2. OAuth 2.0 Resource Ownder Password Credentials Protocol

### Future & Proposed Features
1. OAuth 2.0 Authorization Code Protocol
2. Current Semester Homepage
3. Course names instead of Course numbers.
4. Seamless calendar integration with native mobild calendar applications.
5. A viewable roster
6. Easy downloads under "File & Resources"
7. Notification hub for emails and updates from professors to students

## Implementation & Efficiency
### Workflow
The current design of the app works in a three-step process: the preamble, the authentication, and the main process.

1. **Preamble Process**
2. **Authentication Process**
3. **Main Process**

For more extensive detail on the application, please refer to the [documentation][Documentation]

## Class Design
Please read the documentation [here][Documentation]

## Risks
### Security
Since the application is native, itis more conducive to store the user's credentials on the mobile device; thus, allowing the application to hold the user's credentials in a file system. However, this ease opens up a large security risk. The file holding the user's sensitive information (e.g. uni and password) is now secured through the use of AES encryption.

Please see [documentation][Documentation] for more security information.
    
## ToDo

1. Test Authentication Process
2. ~~Secure private information of user.~~
3. Update feature list based on student responses.
4. Move to alpha build once Courseworks servers are prepared.

Developed for CUIT

Author: Alexander Roth  
Date:   2014-04-22

[Documentation]: https://github.com/aisaacroth/CourseWorks/blob/master/Documentation/Design%20Documentation%2C%20Courseworks%20App.pdf

