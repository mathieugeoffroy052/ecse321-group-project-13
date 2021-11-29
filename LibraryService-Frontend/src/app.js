import axios from 'axios'
var config = require('../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'app',
    data () {
      return {
        userLink: '',
        user: ''
      }
    },
    created : function() {
        // var theUserID = sessionStorage.getItem("existingUserID")
        console.log("hello")
        var theUserID = 9
        if (theUserID != null){
            AXIOS.get('/headLibrarian/'.concat(theUserID))
            .then(response => {
                this.userLink = './'
                document.getElementById("services").innerHTML = "Services"
            })
            .catch(e => {
                var errorMessage = e.response.data.message
                console.log("Error Message: " + errorMessage)
                AXIOS.get('/librarians/'.concat(theUserID))
                .then(response => {
                    document.getElementById("services").innerHTML = "Services"
                    this.userLink = "./#/libraryservices"
                })
                .catch(e => {
                    var errorMessage = e.response.data.message
                    console.log("Error Message: " + errorMessage)
                    AXIOS.get('/patron/'.concat(theUserID))
                    .then(response => {
                        document.getElementById("services").innerHTML = ""
                        this.userLink = ""
                    })
                    .catch(e => {
                        var errorMessage = e.response.data.message
                        console.log("Error Message: " + errorMessage)
                    })
                })
            })
        }
        else {
            this.userLink = ''
            document.getElementById("services").innerHTML = ""
        }

    }
}