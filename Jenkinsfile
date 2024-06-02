pipeline {
    agent any
    tools {
        maven 'maven-3.9.7'
    }
    stages {
        stage('Build Maven') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'ssh_starwars', url: 'https://github.com/shalinishandilya/starwars']])
                sh 'mvn clean install'
            }
        }

        stage('OWASP Dependency-Check') {
            steps {
              script {
               sh 'mvn org.owasp:dependency-check-maven:6.1.5:check'
                }
             }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t shalini19/starwars .'
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                  withCredentials([string(credentialsId: 'dockerhubaws', variable: 'dockerpwd')]) {
                    sh 'docker login -u shalini19 -p ${dockerpwd}'
                  }
                   sh 'docker tag shalini19/starwars shalini19/starwars:latest'
                   sh 'docker push shalini19/starwars:latest'
                }
            }
        }

         stage('Deploy to K8s') {
            steps {
                script{
                    kubernetesDeploy (configs: 'deploymentservice.yaml',kubeconfigId: 'kubernetes_config_pwd')
                }
            }
        }
    }
}