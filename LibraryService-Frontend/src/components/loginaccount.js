 
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = process.env.NODE_ENV === 'production' ? "https://libraryservice-backend-g13.herokuapp.com" : 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})  
//This page renders the backend for the login page

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
            headLibrarianAcc: '',
            room: ''
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
        /**
         * This method allows the desired user to login is they have to correct credentials. It will check using their user id and password
         */
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
        redirectToItemSelect: function () {
            window.location.href='../#/item-select';
        },
        /**
         * This method is only used of the database is reset and no users exist in the system at his time.
         */
        initializeSystem: function() {
            this.noUsers = false
            //create head librarian account
            AXIOS.post('/createHeadLibrarian', {}, {params: {firstName:"Linda", lastName:"Ross", online:true, address:"3456 avenue McGill, Montreal, Quebec", password:"headlibrarianpassword", balance:0, email:"linda.ross@gmail.com"}}).then (response => {
                this.headLibrarianAcc = response.data
                //create single room in the library
                AXIOS.post('/createLibraryItem', {}, {params: {name:"Room", itemType:"Room", date:"2021-01-01", isViewable:false, isbn:87675, creator:"Group 13 Library System"}}).then (response => {
                    this.room = response.data
                    AXIOS.post('/createBorrowableItem', {}, {params: {creator: this.room.creator, title: this.room.name, itemState:"Available", isbn: this.room.isbn}}).then (response => {
                        
                      }).catch(e => {
                        alert(e.response.data.message)
                      })
                  }).catch(e => {
                    alert(e.response.data.message)
                  })
                alert("System Initialized.\n".concat("HeadLibrarian userID: ").concat(this.headLibrarianAcc.userID).concat(", password: ").concat(this.headLibrarianAcc.password).concat("\nThe single bookable room in the library was also created."))
              }).catch(e => {
                alert(e.response.data.message)
              })
        }
    }
}
