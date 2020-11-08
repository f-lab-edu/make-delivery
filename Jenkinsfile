pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "Maven 3.6.3"
    }

    stages {

        stage('Build') {
            steps {
                // Get some code from a GitHub repository
//checkout([$class: 'GitSCM', branches: [[name: '+refs/pull/*:refs/remotes/origin/pr/*']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '3a35554a-1806-4def-bbd9-919202cd3a37', url: 'https://github.com/f-lab-edu/make-delivery']]])
                checkout scm
                // Run Maven on a Unix agent.
                sh "mvn -Dmaven.test.failure.ignore=true clean package"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
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
