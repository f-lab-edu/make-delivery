pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "Maven 3.6.3"
    }

    options
        {
            skipDefaultCheckout()
        }

        environment {
            GIT_COMMIT_REV=''
          }

    stages {

        stage('Git Checkout') {
            steps {
                checkout scm

                script {
                      // Checkout the repository and save the resulting metadata

                      GIT_COMMIT_REV = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
//                       def scmVars = checkout([
//                         $class: 'GitSCM',
//                         ...
//                       ])
//
//                       // Display the variable using scmVars
//                       echo "scmVars.GIT_COMMIT"
                       echo "${GIT_COMMIT_REV}"
//
//                       // Displaying the variables saving it as environment variable
//                       env.GIT_COMMIT = scmVars.GIT_COMMIT
//                       echo "env.GIT_COMMIT"
//                       echo "${env.GIT_COMMIT}"
                    }
            }
        }

        stage('Build') {
            steps {
                echo "${BUILD_NUMBER}"
                echo "here #@@@@@@@@@@####"

                echo "${commit_id}"
                //sh "mvn -Dmaven.test.failure.ignore=true clean package"
            }



            // post {
            //     // If Maven was able to run the tests, even if some of the test
            //     // failed, record the test results and archive the jar file.
            //     success {
            //         junit '**/target/surefire-reports/TEST-*.xml'
            //         archiveArtifacts 'target/*.jar'
            //     }
            // }
        }
    }
}
