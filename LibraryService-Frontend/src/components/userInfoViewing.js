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
        var userID = 7;
        AXIOS.get('/patron/'.concat(userID)).then (response => {
            this.currentPatron = response.data
        })
        .catch(e => {
            this.currentPatron = ''
            alert(e.response.data.message)
            
        })
       
    


    },
   methods: {
     updatePassword: function ()
     {
         var password= document.getElementById("updatePassword").value
         var password2= document.getElementById("updatePassword1").value
        if(password==password2) {
            AXIOS.put('/updatePassword/', {},{params:{password: password2, userID: sessionStorage.getItem("existingUserID") }}).then (response => {
              this.currentPatron = response.data
            }).catch (e => {
                alert(e.response.data.message)
                
            })
          
        }
        else alert("passwords do not match")


    }, 
    updatePassword: function ()
    {
        var emailnew= document.getElementById("updateEmail").value
       
       
           AXIOS.put('/updateEmail/', {},{params:{email: emailnew, userID: sessionStorage.getItem("existingUserID") }}).then (response => {
             this.currentPatron = response.data
           }).catch (e => {
               alert(e.response.data.message)
               
           })
         
      


   }, 


 







     
    }
}
