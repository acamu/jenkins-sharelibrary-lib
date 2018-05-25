
//Simple function to calling Git
def git_sh(args) {
    println("calling git $args")
    sh(script: "git ${args}", returnStdout: true).trim()
}

//Create Release from new snapshot
def git_tag_local(String tag) {
    println "INFO : local_tag : ${tag}"
    git_sh "config user.email '${env.GIT_USER_EMAIL}'"
    git_sh "config user.name '${env.GIT_USER_NAME}'"
    git_sh "add ."
    def msg = "\"Automatic Release tag ${tag}\""
    sh "git tag -a ${tag} -m ${msg} "
}

def git_tag_push(String credentialId) {
    String url = env.GIT_URL
    withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: "${credentialId}", usernameVariable: 'USER', passwordVariable: 'PWD']]) {
        url = url.replaceFirst("://[^@]+@", "://${USER}:${PWD}@")
        git_sh "push --tags ${url}"
    }
}

