import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function PatronDTO(firstName, lastName, onlineAccount, password, balance, address, email, userID, validatedAccount) {
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
    name: 'userDashBoard',
    data () {
        return {

           
            
            currentPatron: '',
            errorPatron: '',
            response: []
        }
    },


    created: function () {
        var userID = document.getElementById("123").value
        AXIOS.get('/patron/'.concat(userID)).then (response => {
            this.currentPatron = response.data
        })
        .catch(e => {
            this.currentPatron = ''
            alert(e.response.data.message)
            
        })
    
    


    },
   methods: {

   getUserInfo: function()
{
    var userID = document.getElementById("123").value
    AXIOS.get('/patron/'.concat(userID)).then (response => {
        this.currentPatron = response.data
    })
    .catch(e => {
        this.currentPatron = ''
        alert(e.response.data.message)
        
    })
  },







     
    }
}
