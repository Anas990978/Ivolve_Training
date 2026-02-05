def call(string imageName,string deploymentFile, string apiServerCredID, string tokenCredID) {

    sh '''sed -i "s|image: .*|image: $IMAGE_NAME:$BUILD_NUMBER|" $DEPLOYMENT_FILE'''
    withCredentials([
                    string(credentialsId: 'ServiceAccount-Token', variable: 'TOKEN'),
                    string(credentialsId: 'API Server', variable: 'API_SERVER')
                ]) {
                    sh '''
                        cd jenkins-ci-cd/lab-22/K8s_App
                        kubectl apply -f $DEPLOYMENT_FILE -n ivolve \
                            --server=$API_SERVER --token=$TOKEN \
                            --insecure-skip-tls-verify=true --validate=false
                    '''
                }
            }   
