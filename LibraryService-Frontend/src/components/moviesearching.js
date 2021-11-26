import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function LibraryItemDTO(name, itemType, date, creator, isViewable, isbn)
{
  this.name = name;
  this.date = date;
  this.itemType = itemType;
  this.creator = creator;
  this.isViewable = isViewable;
  this.isbn = isbn;
}

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

function BorrowableItemDTO(state, item, barCodeNumber){
  this.state = state;
  this.libraryItem = item;
  this.barCodeNumber = barCodeNumber;
}

function PatronDTO(firstName, lastName, onlineAccount, address, validatedAccount, password, balance, email, patronID){
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.balance = balance;
    this.email = email;
    this.onlineAccount = onlineAccount;
    this.address = address;
    this.validatedAccount = validatedAccount;
    this.patronID = patronID;
}


export default {
    name: 'moviesearch',
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
        response: []
      }
    },

    created: function () {
        AXIOS.get('/movies/')
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
        if(anIsbn != undefined){
          var params = {
            isbn: anIsbn
          }
          AXIOS.get('/items/isbn/', {params})
          .then(response => {
              this.existingBorrowableItems = response.data
          })
          .catch(e => {
              this.errorLibraryItem = e
          })
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
                  console.log("yooooo")
                  this.transactions.push(response.data)
                  this.errorTransaction = ''
                  this.newTransaction = ''
                  document.getElementById("transaction").innerHTML = "Transaction Complete!"
                  //alert("Transaction complete!")
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
          // Reset the name field for new people
          this.existingBorrowableItems = []
          this.existingPatron = ''
          this.existingBorrowableItem = ''
        }
      },
      runSearch : function(){
        var requestedTitle = document.getElementById("requestedTitle").value
        var requestedDirector = document.getElementById("requestedDirector").value
        if(requestedTitle == "" && requestedDirector == "") { // both title and director field are empty
          //alert("No input")
          document.getElementById("errorMessage").innerHTML = "Please enter a title or artist"
        } 
        else if(requestedTitle != "" && requestedDirector == "") { // title field is not empty, but director field is
          console.log("title not empty, director empty")
          this.errorLibraryItem = null
          var params = {
            title: requestedTitle
          }
          AXIOS.get('/movies/title/', {params})
          .then(response => {
            this.libraryItems = response.data
            if(response.data.length == 0){
              document.getElementById("noItemFound").innerHTML = "No movies found with this title"
            }
          })
          .catch(e => {
            this.errorLibraryItem = e
          })
          if(this.errorLibraryItem != null){ // GET request gave an error
            this.libraryItems = []
            alert("ERROR");
          } 
        } 
        else if(requestedTitle == ""){ // director field is not empty, but title field is
          console.log("title empty, director not empty")
          this.errorLibraryItem = null
          var params = {
            director: requestedDirector
          }
          AXIOS.get('/movies/director/', {params})
          .then(response => {
            this.libraryItems = response.data
            if(response.data.length == 0){
              document.getElementById("noItemFound").innerHTML = "No movies found by this director"
            }
          })
          .catch(e => {
            this.errorLibraryItem = e
          })
          if(this.errorLibraryItem != null){ // GET request gave an error
            alert("ERROR");
          } 
        } 
        else{ // both title and director field have input in them
          console.log("title and director not empty")
          this.errorLibraryItem = null
          var params = {
            title : requestedTitle,
            director: requestedDirector
          }
          AXIOS.get('/movies/title/director/', {params})
          .then(response => {            
            this.libraryItems = []
            if(response.data.length == 0) document.getElementById("noItemFound").innerHTML = "No movies found with this title and director"
            else this.libraryItems[0] = response.data
          })
          .catch(e => {
            this.errorLibraryItem = e
          })
          if(this.errorLibraryItem != null){ // GET request gave an error
            alert("ERROR");
          } 
        }
      },
      resetMessages : function(){
        document.getElementById("errorMessage").innerHTML = ""
        document.getElementById("noItemFound").innerHTML = ""
        document.getElementById("transaction").innerHTML = ""
      }
    }
  }