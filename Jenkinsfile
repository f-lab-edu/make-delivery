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

                sh "mvn -Dmaven.test.failure.ignore=true clean package"
                echo "@@@@@@"
                echo "See ${BUILD_URL}   Jenkins: ${JOB_NAME}: Build status is ${currentBuild.currentResult}"

                if [ "${BUILD_URL}" eq "SUCCESS" ]
                then echo "if here"
                fi

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
