pipeline {
    agent { label 'buildnode' }
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
        timeout(time: 30, unit: 'MINUTES')
    }
    triggers {
        pollSCM('H/5 * * * *')
    }
    stages {
        stage('Build') {
            steps {
                sh './gradlew build'
                archiveArtifacts 'build/libs/*.?ar'
            }
        }
    }
    post {
        success {
            rocketSend avatar: 'https://chat.puzzle.ch/emoji-custom/success.png', channel: 'lightning-work', message: "Deployment success - Branch ${env.BRANCH_NAME} - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)", rawMessage: true
        }
        unstable {
            rocketSend avatar: 'https://chat.puzzle.ch/emoji-custom/unstable.png', channel: 'lightning-work', message: "Deployment unstable - Branch ${env.BRANCH_NAME} - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)", rawMessage: true
        }
        failure {
            rocketSend avatar: 'https://chat.puzzle.ch/emoji-custom/failure.png', channel: 'lightning-work', message: "Deployment failure - Branch ${env.BRANCH_NAME} - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)", rawMessage: true
        }
    }
}
