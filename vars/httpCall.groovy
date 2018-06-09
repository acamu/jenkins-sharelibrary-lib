@GrabResolver(name='artifactory', root='https://localhost:8081/artifactory/public/')
@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovy.json.JsonOutput
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.GET
import static groovyx.net.http.ContentType.HTML
import static groovyx.net.http.ContentType.TEXT 
import static groovyx.net.http.ContentType.JSON
import groovy.json.JsonSlurper

  static String callHTTPGET(String urlToCall, String item) {
        println 'call ' + urlToCall + ' ' + item

        def connection = new URL(urlToCall).openConnection() as HttpURLConnection

// set some headers
        connection.setRequestProperty('User-Agent', 'groovy-2.4.4')
        connection.setRequestProperty('Accept', 'application/json')

        if (connection.responseCode == 200) {
            // get the response code - automatically sends the request
            String dataaa = connection.inputStream.text
            println connection.responseCode + ": " + dataaa

            return dataaa

        } else {
            println connection.responseCode + ": " + connection.inputStream.text
            return ''
        }
        return ''
    }

    static String callHTTPGET2(String urlToCall, String item) {

        String valuett= ''
        def http = new HTTPBuilder()

        println 'call ' + urlToCall + ' ' + item

        http.request(urlToCall, GET, TEXT) { req ->
            // uri.path = '/' //item // overrides any path in the default URL
            // uri.query = [ v:'1.0', q: 'Calvin and Hobbes' ]
            headers.'User-Agent' = "groovy-2.4.4"
            headers.'Accept' = 'application/json'

            response.success = { resp, reader ->
                //assert resp.statusLine.statusCode == 200
                println "Got response: ${resp.statusLine}"
                println "Content-Type: ${resp.headers.'Content-Type'}"
                

                println 'Response data: -----'
                valuett = reader.text
                println '\n--------------------'
                println '==>'+valuett
                println '\n--------------------'
            }

            response.failure = { resp ->
                println "Unexpected failure: ${resp.statusLine}"
                println 'failure'
            }

            response.'400' = { resp ->
                println 'Bad Request'
            }

            response.'404' = { resp ->
                println 'Not found'
            }

        }

        return valuett
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

