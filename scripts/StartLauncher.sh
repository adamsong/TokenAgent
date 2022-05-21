oldToken = 'oldTokenHere'
newToken = 'newTokenHere'
java -javaagent:TokenAgent.jar="${oldToken}:${newToken}" -jar .\ATLauncher.jar
