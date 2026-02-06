def call(string sonarTokenCredID) {
    withCredentials([string(credentialsId: sonarTokenCredID, variable: 'SONAR_TOKEN')]) {
        sh """
            cd jenkins-ci-cd/lab-23/Jenkins_App
            mvn verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \\
                -Dsonar.projectKey=Anas990978_Ivolve_Training \\
                -Dsonar.token=\$SONAR_TOKEN
        """
    }
}