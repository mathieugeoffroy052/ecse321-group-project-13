import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function TransactionDTO(type, deadline, borrowableItem, userAccount, transactionID)
{
  this.transactionType = type;
  this.deadline = deadline;
  this.borrowableItem = borrowableItem;
  this.userAccount = userAccount;
  this.transactionID = transactionID;
}

function TransactionDTO(type, borrowableItem, userAccount){
  this(type, Date.parse("2001-01-01"), borrowableItem, userAccount);
}

export default {
    name: 'musicsearch',
    data () {
      return {
        libraryItems: [],
        newLibraryItem: '',
        existingLibraryItem: '',
        errorLibraryItem: '',
        transactions: [],
        newTransaction: '',
        existingTransaction: '',
        errorTransaction: '',
        borrowableItems: [],
        newBorrowableItem: '',
        existingBorrowableItems: [],
        existingBorrowableItem: '',
        errorBorrowableItem: '',
        patrons: [],
        newPatron: '',
        existingPatron: '',
        errorPatron: '',
        alertColour: 'light',
        response: []
      }
    },

    created: function () {
        AXIOS.get('/music/')
        .then(response => {
            this.libraryItems = response.data
        })
        .catch(e => {
            this.errorLibraryItem = e
        })
        AXIOS.get('/patrons/')
        .then(response => {
            this.patrons = response.data
        })
        .catch(e => {
            this.errorPatron = e
        })
      },

    methods: {
      createReserveTransaction: function (aPatronID) {
        var anIsbn = undefined
        if(document.querySelector('input[type="radio"]:checked') != null){
          anIsbn = document.querySelector('input[type="radio"]:checked').value;
        }
        else{
          alert("No library item was selected")
        }
        if(anIsbn != undefined){
          var params = {
            isbn: anIsbn
          }
          AXIOS.get('/items/isbn/', {params})
          .then(response => {
              this.existingBorrowableItems = response.data
              if(this.existingBorrowableItems != []){
                var aBarCodeNumber = undefined
                for (let i = 0; i < this.existingBorrowableItems.length; i++) {
                  if(this.existingBorrowableItems[i]["itemState"] == "Available"){
                      aBarCodeNumber = this.existingBorrowableItems[i]["barCodeNumber"]
                  }
                }
                if(aBarCodeNumber != undefined){
                  var params = {
                    barCodeNumber: aBarCodeNumber,
                    userID: aPatronID
                  }
                  AXIOS.post('/reserve-item', {}, {params})
                  .then(response => {
                      this.transactions.push(response.data)
                      this.errorTransaction = ''
                      this.newTransaction = ''
                      document.getElementById("transaction").innerHTML = "Transaction Complete!"
                      this.alertColour = 'success'
                    })
                    .catch(e => {
                      var errorMessage = e.response.data.message
                      console.log(errorMessage)
                      this.errorTransaction = errorMessage
                    })
                }
                else{
                  alert("No available item found")
                }
              }
          })
          .catch(e => {
              this.errorLibraryItem = e
          })
          // Reset the name field for new people
          this.existingBorrowableItems = []
          this.existingPatron = ''
          this.existingBorrowableItem = ''
        }
      },
      runSearch : function(){
        var requestedTitle = document.getElementById("requestedTitle").value
        var requestedArtist = document.getElementById("requestedArtist").value
        if(requestedTitle == "" && requestedArtist == "") { // both title and artist field are empty
          AXIOS.get('/music/')
          .then(response => {
              this.libraryItems = response.data
          })
          .catch(e => {
              this.errorLibraryItem = e
          })
          document.getElementById("invalidInput").innerHTML = "Please enter a title or artist"
        } 
        else if(requestedTitle != "" && requestedArtist == "") { // title field is not empty, but artist field is
          console.log("title not empty, artist empty")
          this.errorLibraryItem = null
          var params = {
            title: requestedTitle
          }
          AXIOS.get('/musics/title/', {params})
          .then(response => {
            this.libraryItems = response.data
            if(response.data.length == 0){
              document.getElementById("invalidInput").innerHTML = "No music albums found with this title"
            }
          })
          .catch(e => {
            this.errorLibraryItem = e
          })
          if(this.errorLibraryItem != null){ // GET request gave an error
            alert("ERROR");           
          } 
        } 
        else if(requestedTitle == ""){ // artist field is not empty, but title field is
          console.log("title empty, artist not empty")
          this.errorLibraryItem = null
          var params = {
            artist: requestedArtist
          }
          AXIOS.get('/musics/artist/', {params})
          .then(response => {
            this.libraryItems = response.data
            if(response.data.length == 0){
              document.getElementById("invalidInput").innerHTML = "No music albums found by this artist"
            }
          })
          .catch(e => {
            this.errorLibraryItem = e
          })
          if(this.errorLibraryItem != null){ // GET request gave an error
            alert("ERROR");
          } 
        } 
        else{ // both title and artist field have input in them
          console.log("title and artist not empty")
          this.errorLibraryItem = null
          var params = {
            title : requestedTitle,
            artist: requestedArtist
          }
          AXIOS.get('/musics/title/artist/', {params})
          .then(response => {
            this.libraryItems = []
            if(response.data.length == 0) document.getElementById("invalidInput").innerHTML = "No music albums found with this title and artist"
            else this.libraryItems[0] = response.data
          })
          .catch(e => {
            this.errorLibraryItem = e
            this.libraryItems = []
            document.getElementById("invalidInput").innerHTML = "No music albums found with this title and artist"
          })
          if(this.errorLibraryItem != null){ // GET request gave an error
            alert("ERROR");
          } 
        }
      },
      resetMessages : function(){
        document.getElementById("invalidInput").innerHTML = ""
        document.getElementById("transaction").innerHTML = ""
      },
      redirectToItemSelect : function(){
        window.location.href='../#/item-select';
      }
    }
  }