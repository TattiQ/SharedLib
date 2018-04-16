#!/usr/bin/env groovy

def call(function) {
    try {
      function
    } catch (hudson.AbortException ae) {
        // this ambiguous condition means during a shell step, user probably aborted
        if (ae.getMessage().contains('script returned exit code 143')) {
            println "well there is a 143"
            println "${ae.getCause()}"
            currentBuild.result = "ABORTED"
            throw ae
          }
    } catch (e) {
        currentBuild.result = "FAILED"
        throw e
    } finally {
      // Success or failure, always send notifications
      stage('Send deployment status') {
        currentBuild.result = "SUCCESS"
        println "Yeeeyyy"
        println "${currentBuild.result}"
      }
    }
}
