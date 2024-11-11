pipeline {
    agent any
    stages {
        stage("Checkout"){
            steps {
                checkout scmGit(
                    branches: [[name: 'ngodat0103/config-jenkins']],
                    userRemoteConfigs: [[url: 'https://github.com/ngodat0103/yamp.git']]
                )
            }
        }
        stage("test") {
            steps {
                sh "echo 'Testing the project'"
            }
        }
        stage("Build") {
            steps {
                sh "echo 'Build the project'"
            }
        }
        stage("Deploy") {
            steps {
                sh "echo 'Deploy the project'"
            }
        }
    }
}