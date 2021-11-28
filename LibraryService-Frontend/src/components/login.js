import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function PatronDTO(firstName, lastName, onlineAccount, address, validatedAccount, password, balance, email, userID) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.onlineAccount = onlineAccount;
    this.address = address;
    this.validatedAccount = validatedAccount;
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
                patronAccounts: [],
                librarianAccounts: [],
                firstName: '',
                lastName: '',
                userID: '', 
                password: ''
            },
            currentPatron: '',
            errorPatron: '',
            response: []
        }
    },
    // created: function () {
    //     // Initializing persons from backend
    //     AXIOS.get('/patron/')
    //     .then(response => {
    //       // JSON responses are automatically parsed.
    //       this.persons = response.data
    //     })
    //     .catch(e => {
    //       this.errorPerson = e
    //     })
    methods: {
        getPatron: function(userID, password) {
            var userID = document.getElementById("input-userID").value
            AXIOS.get('/patron/'.concat(userID)).then (response => {
                this.currentPatron = response.data
                ​this.errorRegistration = errorMsg
            })
            .catch(e => {
                var errorMsg = e.response.data.message
                console.log(errorMsg)
                this.errorPatron = errorMsg
            })
        }
    }
}
