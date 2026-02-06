def call(string imageName) {
    sh "trivy image ${imageName}:${env.BUILD_NUMBER}"
}
