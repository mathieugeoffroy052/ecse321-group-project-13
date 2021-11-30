 
import axios from 'axios'
var config = require('../../config')



var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function UserAccountDTO(firstName, lastName, onlineAccount, address, password, balance, email, userID) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.onlineAccount = onlineAccount;
    this.address = address;
    this.password = password;
    this.balance = balance;
    this.email = email;
    this.userID = userID;
}

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
                    this.redirectToItemSelect();
                 
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
        },
        redirectToItemSelect: function () {
            window.location.href='../#/item-select';
        }
    }
}
