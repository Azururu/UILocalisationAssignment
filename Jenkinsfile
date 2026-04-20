pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "azuruu/ui-localisation-assignment"
        DOCKERHUB_CREDENTIALS_ID = 'azuruu'
        DOCKER_IMAGE_TAG = 'latest'
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
                script {
                    docker.build("${DOCKER_IMAGE}:${DOCKER_IMAGE_TAG}")
                }
            }
        }
        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKERHUB_CREDENTIALS_ID) {
                        docker.image("${DOCKER_IMAGE}:${DOCKER_IMAGE_TAG}").push()
                    }
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