import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'userDashBoard',
    data () {
        this.getTransactionsForPatron()
        return {
            currentPatronTransactions: [],
            currentPatron: '',
            errorPatron: '',
            response: []
        }
    },


    created: function () {
        var userID = sessionStorage.getItem("existingUserID")
        if (userID != '') {
            AXIOS.get('/patron/'.concat(userID)).then (response => {
                this.currentPatron = response.data
                this.isLibrarian = false
            })
            .catch(() => {
              AXIOS.get('/account/'.concat(userID)).then (response => {
                this.currentPatron = response.data
                this.isLibrarian = true
              }).catch(e => {
                this.currentPatron = ''
                alert(e.response.data.message)
              })
            })
          } else {
            this.currentPatron = ''
          }
    },
   methods: {
     updatePassword: function ()
     {
         var password= document.getElementById("updatePassword").value
         var password2= document.getElementById("updatePassword1").value
        if(password==password2) {
            AXIOS.put('/updatePassword/', {},{params:{password: password2, userID: this.currentPatron.userID }}).then (response => {
              this.currentPatron.password = response.data.password
            }).catch (e => {
                alert(e.response.data.message)
            })
        }
        else alert("passwords do not match")
    }, 
    updateEmail: function ()
    {
        var emailnew= document.getElementById("updateEmail").value
        AXIOS.put('/updateEmail/', {},{params:{email: emailnew, userID: this.currentPatron.userID }}).then (response => {
            this.currentPatron.email = response.data.email
        }).catch (e => {
            alert(e.response.data.message) 
        })
   }, 
   getTransactionsForPatron: function() {
    var userID = sessionStorage.getItem("existingUserID")
    this.currentPatronTransactions = []
    if (userID != '') {
      AXIOS.get('/transaction/viewall/id/'.concat(userID)).then (response => {
        response.data.forEach(element => {
          this.currentPatronTransactions.push({ID: element.transactionID, Name: element.borrowableItem.libraryItem.name, Creator: element.borrowableItem.libraryItem.creator, Barcode: element.borrowableItem.barCodeNumber, Item: element.borrowableItem.libraryItem.type, Type: element.transactionType, Deadline: element.deadline }) 
        })
      }).catch(e => {
        alert(e.response.data.message)
      })
    } 
  } 
 }
}
