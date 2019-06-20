#!groovy

def releasedVersion

node('master') {
  def dockerTool = tool name: 'docker', type: 'org.jenkinsci.plugins.docker.commons.tools.DockerTool'
  withEnv(["DOCKER=${dockerTool}/bin"]) {
  
   
   stage('Cleanup'){
    	dockerCmd 'stop upmt'
    	dockerCmd 'rm upmt'
    }

     
    stage('Build') {
             git url: 'https://github.com/VishnuKoti/POC-Spring-API.git'
              dir('src') {
	        def mvnHome = tool 'M3'
	
 	 	sh "${mvnHome}/bin/mvn package && java -jar pricemanagements-0.0.2-SNAPSHOT.jar"
		 dockerCmd 'build --tag upmt/spring:SNAPSHOT1.0 .'
            }
    }
 
    stage('Deploy') {
        stage('Deploy') {
            dir('src') {
                dockerCmd 'run -d -p 4000:4000 --name "upmt" upmt/spring:SNAPSHOT1.0'
            }
        }
    }
    
  }
}

def dockerCmd(args) {
    sh "${DOCKER}/docker ${args}"
}

def getReleasedVersion() {
    return (readFile('pom.xml') =~ '<version>(.+)-SNAPSHOT</version>')[0][1]
}