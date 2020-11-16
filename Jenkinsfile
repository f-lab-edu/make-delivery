pipeline {
    agent any

    tools {
        maven "Maven 3.6.3"
    }

    environment {
        GIT_COMMIT_REV=''
        GIT_CHANGE_BRANCH_NAME=''
        GIT_COMMIT_SHA=''
    }

    stages {

        stage('git checkout & clone') {
            steps {
                script {
                    cleanWs()
                    GIT_CHANGE_BRANCH_NAME = sh(returnStdout: true, script: 'echo ${payload} | python3 -c \"import sys,json;print(json.load(sys.stdin,strict=False)[\'ref\'][11:])\"').trim()
                    GIT_COMMIT_SHA = sh(returnStdout: true, script: 'echo ${payload} | python3 -c \"import sys,json;print(json.load(sys.stdin,strict=False)[\'head_commit\'][\'id\'])\"').trim()
                    echo "arrive ${GIT_CHANGE_BRANCH_NAME}"
                    sh "git clone -b ${GIT_CHANGE_BRANCH_NAME} --single-branch \"https://github.com/f-lab-edu/make-delivery.git\""
                    }
                }
            }

        stage('Build') {
            steps {
                sh "mvn -f make-delivery/pom.xml -Dmaven.test.failure.ignore=true clean package"
                //echo "See ${BUILD_URL}   Jenkins: ${JOB_NAME}: Build status is ${currentBuild.currentResult}"
            }
        }

    }//stages

                post {
                    // If Maven was able to run the tests, even if some of the test
                    // failed, record the test results and archive the jar file.

                    success {
                        echo "${GIT_COMMIT_SHA}"
                      sh ("curl -X POST -H \"Content-Type: application/json\" \
                      --data '{\"state\": \"success\", \"context\": \"@@pass ci test & build\", \"target_url\": \"http://115.85.180.192:8080/job/make-delivery\"}' \
                      \"https://${GITHUB_TOKEN}@api.github.com/repos/f-lab-edu/make-delivery/statuses/${GIT_COMMIT_SHA}\"")


                        //junit '**/target/surefire-reports/TEST-*.xml'
                        //archiveArtifacts 'target/*.jar'

                    }
                    failure {
                      sh ("curl -X POST -H \"Content-Type: application/json\" \
                      --data '{\"state\": \"failure\", \"context\": \"@@failure ci test & build\", \"target_url\": \"http://115.85.180.192:8080/job/make-delivery\"}' \
                      \"https://${GITHUB_TOKEN}@api.github.com/repos/f-lab-edu/make-delivery/statuses/${GIT_COMMIT_SHA}\"")
                    }
                 }

}//pipeline
