pipeline {
    agent any
    tools {
        jdk 'JDK17'
        maven 'MavenNew'
    }
     environment {
        DOCKER_IMAGE = 'arora-sahil/weather-service'
        CONTAINER_NAME = 'your-container-name'
        DOCKER_PORT = '8080:8080'
    }
    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'main', changelog: false, credentialsId: 'a817ad49-7d82-483c-948d-d51e5151a3dd', poll: false, url: 'https://github.com/arora-sahil/weather-service.git'
            }
        }
        stage('Compile') {
            steps {
                // Run Maven build with secret properties passed as environment variables
                sh 'mvn clean compile'
            }
        }
        stage('SonarQube Analysis') {
                    steps {
                        // Run SonarScanner within withSonarQubeEnv block
                        withSonarQubeEnv('SonarQubeServer') {
                            sh 'mvn sonar:sonar'
                        }
                    }
        }
        stage('OWASP Dependency-Check') {
                     steps {
                         // Run OWASP Dependency-Check scanner
                         sh 'mvn org.owasp:dependency-check-maven:check'
                     }
        }
        stage('Build') {
                    steps {
                        // Run Maven build with secret properties passed as environment variables
                        sh 'mvn clean install -Dopenweathermap.api.key=$OPEN_WEATHERMAP_API_KEY'
                    }
        }
        stage('Build and Push Docker Image') {
                    steps {
                        // Build Docker image
                        sh 'docker build -t weather-service .'

                        // Tag Docker image
                        sh 'docker tag weather-service arora-sahil/weather-service'

                        // Push Docker image to registry
                        withCredentials([usernamePassword(credentialsId: 'docker-credentials-id', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                            sh 'docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD'
                            sh 'docker push arora-sahil/weather-service'
                        }
                    }
        }
         stage('Build and Push Docker Image') {
                    steps {
                        // Build Docker image
                        sh 'docker build -t $DOCKER_IMAGE .'

                        // Tag Docker image
                        sh 'docker tag $DOCKER_IMAGE $DOCKER_IMAGE'

                        // Push Docker image to registry
                        withCredentials([usernamePassword(credentialsId: 'docker-credentials-id', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                            sh 'docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD'
                            sh 'docker push $DOCKER_IMAGE'
                        }
                    }
                }
         stage('Deploy with Docker') {
              steps {
                        // Stop and remove existing container (if any)
                        sh 'docker stop $CONTAINER_NAME || true'
                        sh 'docker rm $CONTAINER_NAME || true'

                        // Pull Docker image from registry
                        sh 'docker pull $DOCKER_IMAGE'

                        // Run Docker container
                        sh "docker run -d -p $DOCKER_PORT --name $CONTAINER_NAME $DOCKER_IMAGE"
            }
         }
    }
}
