pipeline {
    agent any

    tools {
        jdk 'jdk17'
        gradle 'Gradle'
    }

    stages {
        stage('Build') {
            steps {
                bat 'java -version'
                bat 'gradle.bat clean build'
            }
        }
    }

    post {
        always {
            echo 'fase always'
        }
        success {
            echo 'fase success'
        }
        failure {
            echo 'fase failure'
        }
    }
}
