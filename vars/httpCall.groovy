@GrabResolver(name='artifactory', root='https://localhost:8081/artifactory/public/')
@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovy.json.JsonOutput
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.GET
import static groovyx.net.http.ContentType.HTML

String callHTTPGET(String urlToCall, String item) {

HTTPBuilder http = new HTTPBuilder($urlToCall, ContentType.JSON);
http.headers.Accept = ContentType.JSON
http.parser[ContentType.JSON] = http.parser.'application/json'
http.request(Method.GET) {
    response.success = { resp, json ->
       // println json.toString()         // Not valid JSON
        println JsonOutput.toJson(json) // Valid JSON
        println JsonOutput.prettyPrint(JsonOutput.toJson(json))
        return JsonOutput.toJson(json)
    }
    
  }
}

