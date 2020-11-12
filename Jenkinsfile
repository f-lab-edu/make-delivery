pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "Maven 3.6.3"
    }

    environment {
        GIT_COMMIT_REV=''
        GIT_COMMIT_SHA=''
    }

    stages {


        stage('Git Checkout') {
            steps {
                //checkout scm
                script {
                      GIT_COMMIT_REV = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
                      GIT_COMMIT_SHA = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%H'").trim()
                       echo "${GIT_COMMIT_REV}"
                       echo "${GIT_COMMIT_SHA}"
                }
            }
        }



        stage('Build') {
            steps {

                sh "mvn -Dmaven.test.failure.ignore=true clean package"
                echo "@@@@@@"
                echo "See ${BUILD_URL}   Jenkins: ${JOB_NAME}: Build status is ${currentBuild.currentResult}"
            }



        }
    }//stages

                post {
                    // If Maven was able to run the tests, even if some of the test
                    // failed, record the test results and archive the jar file.

                    success {
                        echo "${GIT_COMMIT_SHA}"
                      sh ("curl -X POST -H \"Content-Type: application/json\" \
                      --data '{\"state\": \"success\", \"context\": \"pass ci test & build\", \"target_url\": \"http://101.101.208.234:8082/job/make-delivery\"}' \
                      \"https://${GITHUB_TOKEN}@api.github.com/repos/f-lab-edu/make-delivery/statuses/${GIT_COMMIT_SHA}\"")


                        //junit '**/target/surefire-reports/TEST-*.xml'
                        //archiveArtifacts 'target/*.jar'

                    }
                    failure {
                      sh ("curl -X POST -H \"Content-Type: application/json\" \
                      --data '{\"state\": \"failure\", \"context\": \"failure ci test & build\", \"target_url\": \"http://101.101.208.234:8082/job/make-delivery\"}' \
                      \"https://${GITHUB_TOKEN}@api.github.com/repos/f-lab-edu/make-delivery/statuses/${GIT_COMMIT_SHA}\"")
                    }
                 }

}//pipeline
