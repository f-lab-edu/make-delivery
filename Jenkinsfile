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
                      GIT_COMMIT_REV = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
                       echo "${GIT_COMMIT_REV}"
                    }
            }
        }

        stage('Build') {
            steps {
                echo "${BUILD_NUMBER}"
                echo "here #@@@@@@@@@@####"
                echo "${bre_bre}"
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
