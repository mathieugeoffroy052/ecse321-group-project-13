 
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'login',
    data () {
        return {
            form: {
                userAccounts: [],
                userID: '', 
                password: ''
            },
            existingUser: '',
            errorUser: '',
            response: []
        }
    },

    created: function () {
        // Initializing userAccounts from backend
        AXIOS.get('/account')
        .then(response => {
          this.existingUser = response.data
        })
        .catch(e => {
          this.errorUser = e
        })
    },
    methods: {
        login: function() {
            var userID = document.getElementById("typeUserIDX-2").value;
            var password = document.getElementById("typePasswordX-2").value;
            AXIOS.get('/account/'.concat(userID)).then (response => {
                if (password != response.data.password) {
                    this.errorUser = "Incorrect password."
                    alert(this.errorUser)

                } else {
                    this.user = response.data
                    sessionStorage.setItem("existingUserID", userID)
                    //window.location.href='../#/'; 
                }
            })
            .catch(e => {
                this.userAccounts = []
                alert(e.response.data.message)
                  
              })
                    
        },
        onSubmit(event) {
            login()
            event.preventDefault()
            alert(JSON.stringify(this.form))
        }
    }
}
