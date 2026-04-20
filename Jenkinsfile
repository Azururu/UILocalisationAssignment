pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "azuruu/ui-localisation-assignment"
        SONARQUBE_SERVER = 'SonarQubeServer'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'SonarQube',
                credentialsId: 'github-pat',
                url: 'https://github.com/Azururu/UILocalisationAssignment.git'
            }
        }
        stage('Build') {
                    steps {
                        bat 'mvn clean install'
                    }
                }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    bat """
                        ${tool 'SonarScanner'}\\bin\\sonar-scanner^
                        -Dsonar.projectKey=UILocalisationAssignment^
                        -Dsonar.sources=src^
                        -Dsonar.projectName=UILocalisationAssignment^
                        -Dsonar.host.url=http://localhost:9000^
                        -Dsonar.login=${env.SONAR_TOKEN}^
                        -Dsonar.java.binaries=target/classes
                    """
                }
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