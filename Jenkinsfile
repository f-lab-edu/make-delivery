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

    options {
        skipDefaultCheckout()
    }

    stages {

        stage('git checkout & clone') {
            steps {
                script {

                    GIT_CHANGE_BRANCH_NAME = sh(returnStdout: true, script: 'echo ${payload} | python3 -c \"import sys,json;print(json.load(sys.stdin,strict=False)[\'ref\'][11:])\"').trim()
                    GIT_COMMIT_SHA = sh(returnStdout: true, script: 'echo ${payload} | python3 -c \"import sys,json;print(json.load(sys.stdin,strict=False)[\'head_commit\'][\'id\'])\"').trim()
                    echo "arrive ${GIT_CHANGE_BRANCH_NAME}"
                    sh "git clone -b ${GIT_CHANGE_BRANCH_NAME} --single-branch \"https://github.com/f-lab-edu/make-delivery.git\""
                    }
                }
            }

        stage('Build') {
            steps {
                sh "mvn -f make-delivery/pom.xml -DskipTests clean package"
                archiveArtifacts 'make-delivery/target/*.jar'
            }

            post {
                 success {
                        echo "${GIT_COMMIT_SHA}"
                      sh ("curl -X POST -H \"Content-Type: application/json\" \
                      --data '{\"state\": \"success\", \"context\": \"@@pass ci test & build\", \"target_url\": \"http://115.85.180.192:8080/job/make-delivery\"}' \
                      \"https://${GITHUB_TOKEN}@api.github.com/repos/f-lab-edu/make-delivery/statuses/${GIT_COMMIT_SHA}\"")
                        //junit '**/target/surefire-reports/TEST-*.xml'
                    }

                    failure {
                      sh ("curl -X POST -H \"Content-Type: application/json\" \
                      --data '{\"state\": \"failure\", \"context\": \"@@failure ci test & build\", \"target_url\": \"http://115.85.180.192:8080/job/make-delivery\"}' \
                      \"https://${GITHUB_TOKEN}@api.github.com/repos/f-lab-edu/make-delivery/statuses/${GIT_COMMIT_SHA}\"")
                    }
            }
        }

        stage('Send Build jar') {
            steps {
                    sh "scp -P 1039 make-delivery/target/*.jar root@106.10.53.113:~/app/make-delivery.jar"
            }
        }

        stage('Deploy') {
            steps {
                script {
                    sh "ssh -p 1039 -T root@106.10.53.113 sh < /var/lib/jenkins/ab.sh"
                }
            }
        }


    }//stages

                post {
                    always {
                        cleanWs()
                    }
                 }

}//pipeline
