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
                       echo "${GITHUB_TOKEN}"
                    }
            }
        }


        stage('Build') {
            steps {

                sh "mvn -Dmaven.test.failure.ignore=true clean package"
                echo "@@@@@@"
                echo "See ${BUILD_URL}   Jenkins: ${JOB_NAME}: Build status is ${currentBuild.currentResult}"
            }



//             post {
//                 // If Maven was able to run the tests, even if some of the test
//                 // failed, record the test results and archive the jar file.
//                 success {
//                   curl -X POST -H 'Content-Type: application/json' \
//                   --data '{"state": "success", "target_url": "https://101.101.208.234:8082/build/status", \
//                   "description": "성공 했다 @@", "context": "continuous-integration/jenkins"}' \
//                   https://ca38d57b3d2aa32bea67c9828dd3c6cf899b7812@api.github.com/repos/f-lab-edu/make-delivery/statuses/${GIT_COMMIT_REV}
//
//                     //junit '**/target/surefire-reports/TEST-*.xml'
//                     //archiveArtifacts 'target/*.jar'
//                 }
//                 failure {
//                   echo "failure failure@@@@"
//                 }
//             }
        }
    }
}
