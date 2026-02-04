def call(string imageName,string deploymentFile, string apiServerCredID, string tokenCredID) {

    sh '''sed -i "s|image: .*|image: $IMAGE_NAME:$BUILD_NUMBER|" $DEPLOYMENT_FILE'''
    
    withCredentials([usernamePassword(
        credentialsId: apiServerCredID, 
        usernameVariable: 'APISERVER_USERNAME', 
        passwordVariable: 'APISERVER_PASSWORD'
    ), string(
        credentialsId: tokenCredID, 
        variable: 'TOKEN'
    )]) {
        sh """
            kubectl --server=https://$APISERVER_USERNAME --token=$TOKEN apply -f ${deploymentFile}
        """
    }

}