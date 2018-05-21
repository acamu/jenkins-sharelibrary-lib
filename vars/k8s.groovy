import groovy.json.*

def waitForServices() {
  sh "kubectl get svc -o json > services.json --kubeconfig=kubeconfig"
 
  while(!toServiceMap(readFile('services.json')).containsKey('command-svc')) {
        sleep(10)
        echo "Services are not yet ready, waiting 10 seconds"
        sh "kubectl get svc -o json > services.json --kubeconfig=kubeconfig"
  }
  echo "Services are ready, continuing"
}
 
@com.cloudbees.groovy.cps.NonCPS
Map toServiceMap(servicesJson) {
  def json = new JsonSlurper().parseText(servicesJson)
 
  def serviceMap = [:]
  json.items.each { i ->
    def serviceName = i.metadata.name
    def ingress = i.status.loadBalancer.ingress
    if(ingress != null) {
      def serviceUrl = ingress[0].hostname
      serviceMap.put(serviceName, serviceUrl)
    }
  }
 
  return serviceMap
}
