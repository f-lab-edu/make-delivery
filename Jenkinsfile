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

    stages {

        stage('Git Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo "${BUILD_NUMBER}"
                echo "here #@@@@@@@@@@####"
                sh "git rev-parse --short HEAD > .git/commit-id"
                commit_id = readFile('.git/commit-id')
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
