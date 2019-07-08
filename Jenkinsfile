#!groovy

def releasedVersion

node('master') {
  def dockerTool = tool name: 'docker', type: 'org.jenkinsci.plugins.docker.commons.tools.DockerTool'
  withEnv(["DOCKER=${dockerTool}/bin"]) {
  

    
     
    stage('Build') {
             git url: 'https://github.com/VishnuKoti/POC-Spring-API.git'
        
	        def mvnHome = tool 'M3'
		sh "${mvnHome}/bin/mvn clean package -dskip test"
		dockerCmd 'build --tag upmt/spring:SNAPSHOT1.0 .'
        
    }
    
       stage('Test') {
                
    	        def mvnHome = tool 'M3'
    		sh "${mvnHome}/bin/mvn test"
            
    }
 
    stage('Deploy') {
          
                dockerCmd 'run -d -p 8090:8090 --name "upmt" upmt/spring:SNAPSHOT1.0'
              
        }    
  }
}

def dockerCmd(args) {
    sh "${DOCKER}/docker ${args}"
}

def getReleasedVersion() {
    return (readFile('pom.xml') =~ '<version>(.+)-SNAPSHOT</version>')[0][1]
}