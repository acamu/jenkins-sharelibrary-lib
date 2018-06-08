@GrabResolver(name='artifactory', root='https://localhost:8081/artifactory/public/')
@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovy.json.JsonOutput
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.GET
import static groovyx.net.http.ContentType.HTML
import static groovyx.net.http.ContentType.TEXT 
import static groovyx.net.http.ContentType.JSON

String callHTTPGET(String urlToCall, String item) {
 def http = new HTTPBuilder()
 
println 'call '+ urlToCall + ' ' + item
 
 http.request( urlToCall+item, GET, TEXT  ) { req ->
  // uri.path = item // overrides any path in the default URL
  // uri.query = [ v:'1.0', q: 'Calvin and Hobbes' ]
  headers.'User-Agent' = "Mozilla/5.0 Firefox/3.0.4"
  // headers.Accept = 'application/json'

   response.success = { resp, reader ->
     assert resp.statusLine.statusCode == 200
     println "Got response: ${resp.statusLine}"
     println "Content-Type: ${resp.headers.'Content-Type'}"
     println reader.text
     return reader.text
   }
  
  response.failure = { resp ->
     println 'failure'
  }

   response.'400' = { resp ->
     println 'Bad Request'
    return ''
   }
  
   response.'404' = { resp ->
     println 'Not found'
    return ''
   }
  
 }

 return '-1'
}

String callHTTPGET1(String urlToCall, String item) {

HTTPBuilder http = new HTTPBuilder(urlToCall, JSON);
    
    println urlToCall
    
//http.headers.Accept = JSON
//http.parser[JSON] = http.parser.'application/json'
http.request(GET) {
    response.success = { resp, json ->
       // println json.toString()         // Not valid JSON
      //  println JsonOutput.toJson(json) // Valid JSON
      //  println JsonOutput.prettyPrint(JsonOutput.toJson(json))
        println 'resultat ok'
        return JsonOutput.toJson(json)
    }
    
  }
}

