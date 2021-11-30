 
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
            existingUsers: '',
            errorUser: '',
            response: [],
            noUsers: '',
            headLibrarianAcc: ''
        }
    },

    created: function () {
        // Initializing userAccounts from backend
        AXIOS.get('/accounts')
        .then(response => {
          this.existingUsers = response.data
          this.noUsers = false
        })
        .catch(e => {
          if (e.response.data.message == "There are no Users in the system") {
              this.noUsers = true
          } else {
              alert(e.response.data.message)
          }
        })
    },
    methods: {
        login: function() {
            var userID = document.getElementById("typeUserIDX-2").value;
            var password = document.getElementById("typePasswordX-2").value;
            AXIOS.get('/account/'.concat(userID)).then (response => {
                this.user = response.data
                if (password != response.data.password) {
                    this.errorUser = "Incorrect password."
                    alert(this.errorUser)
                } else if (!this.user.onlineAccount) {
                    this.errorUser = "This is not an online account."
                    alert(this.errorUser)
                } else {
                    sessionStorage.setItem("existingUserID", userID)  
                    window.location.href='../#/item-select'; 
                    window.location.reload(true); 
                }
            })
            .catch(e => {
                this.user = ''
                this.errorUser = ''
                this.userAccounts = []
                alert(e.response.data.message)
            })
                    
        },
        onSubmit(event) {
            this.login()
            event.preventDefault()
        },
        initializeSystem: function() {
            this.noUsers = false
            AXIOS.post('/createHeadLibrarian', {}, {params: {firstName:"Linda", lastName:"Ross", online:true, address:"3456 avenue McGill, Montreal, Quebec", password:"headlibrarianpassword", balance:0, email:"linda.ross@gmail.com"}}).then (response => {
                this.headLibrarianAcc = response.data
                alert("System Initialized.\n".concat("HeadLibrarian userID: ").concat(this.headLibrarianAcc.userID).concat(", password: ").concat(this.headLibrarianAcc.password))
              }).catch(e => {
                alert(e.response.data.message)
              })
        }
    }
}
