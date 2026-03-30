pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "azuruu/ui-localisation-assignment"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                credentialsId: 'github-pat',
                url: 'https://github.com/Azururu/UILocalisationAssignment.git'
            }
        }
        stage('Build & Test') {
                    steps {
                        bat 'mvn clean verify'
                    }
                }
        stage('Build Docker Image') {
            steps {
                bat 'docker build -t %DOCKER_IMAGE% .'
            }
        }
        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    bat 'echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin'
                    bat 'docker push %DOCKER_IMAGE%'
                }
            }
        }
    }

    post {
        always {
            script {
                if (fileExists('target/surefire-reports')) {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    recordCoverage(
                        tools: [[parser: 'JACOCO', pattern: '**/target/site/jacoco/jacoco.xml']]
                    )
                } else {
                    echo 'No tests found, skipping JUnit report'
                }
            }
        }
    }
}