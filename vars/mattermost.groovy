//Generic function to print message to mattermost
def notify(String message, String to = null, color = "#439FE0") {
    println message
    if (to) {
        mattermostSend color: "${color}", message: "[${env.BRANCH_NAME}/#${env.BUILD_NUMBER}](${env.BUILD_URL_BLUE}) : ${message}"
    } else {
        mattermostSend color: "${color}", message: "[${env.BRANCH_NAME}/#${env.BUILD_NUMBER}](${env.BUILD_URL_BLUE}) : ${message}"
    }
}
