import axios from 'axios'
var config = require('../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = process.env.NODE_ENV == '"production"' ? "https://libraryservice-backend-g13.herokuapp.com" : 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'app',
    data () {
      return {
        userID: 0,
        show: 0
      }
    },
    created : function() {
        var theUserID = sessionStorage.getItem("existingUserID")
        console.log("USER ID: " + theUserID)
        this.userID = theUserID
        if (theUserID != "null" && theUserID != 0){
            AXIOS.get('/headLibrarian/'.concat(theUserID))
            .then(response => {
                this.show = 2
            })
            .catch(e => {
                var errorMessage = e.response.data.message
                console.log("Error Message: " + errorMessage)
                AXIOS.get('/librarians/'.concat(theUserID))
                .then(response => {
                    this.show = 1
                })
                .catch(e => {
                    var errorMessage = e.response.data.message
                    console.log("Error Message: " + errorMessage)
                    AXIOS.get('/patron/'.concat(theUserID))
                    .then(response => {
                        this.show = 0
                    })
                    .catch(e => {
                        var errorMessage = e.response.data.message
                        console.log("Error Message: " + errorMessage)
                    })
                })
            })
        }
        else {
            console.log("hellooooooo")
            this.show = 0
        }

    },
    methods: {
        logout: function(){
            sessionStorage.setItem("existingUserID", null)
        }
    }
}