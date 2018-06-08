import groovy.json.*
 
boolean checkQualityGateConformity(String test, String item) {
  boolean returnValue = true
  def jsonSlurper = new JsonSlurper()
  def data = jsonSlurper.parseText(test)
  def listdata = data.CONFORMITE
  for (itemdata in listdata) { 
        if (itemdata."$item" == "NOK") { 
                     return false 
        } 
  }     
return returnValue; 
} 

