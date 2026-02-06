def call(String imageName) {
    sh "trivy image ${imageName}:${env.BUILD_NUMBER}"
}
