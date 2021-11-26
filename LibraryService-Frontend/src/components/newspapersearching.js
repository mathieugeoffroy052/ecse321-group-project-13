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
    name: 'newspapersearch',
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
        AXIOS.get('/newspapers/')
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
                // this.this.existingBorrowableItems.pop()["barCode"]
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
        var requestedWriter = document.getElementById("requestedWriter").value
        if(requestedTitle == "" && requestedWriter == "") { // both title and writer field are empty
          alert("No input")
        } 
        else if(requestedTitle != "" && requestedWriter == "") { // title field is not empty, but writer field is
          console.log("title not empty, writer empty")
          this.errorLibraryItem = null
          var params = {
            title: requestedTitle
          }
          AXIOS.get('/newspapers/title/', {params})
          .then(response => {
            this.libraryItems = response.data
          })
          .catch(e => {
            this.errorLibraryItem = e
          })
          if(this.errorLibraryItem != null){ // GET request gave an error
            this.libraryItems = []
            alert("No items found with this title");
          } 
        }
        else if(requestedTitle == ""){ // writer field is not empty, but title field is
          console.log("title empty, writer not empty")
          this.errorLibraryItem = null
          var params = {
            writer: requestedWriter
          }
          AXIOS.get('/newspaper/writer/', {params})
          .then(response => {
            this.libraryItems = response.data
          })
          .catch(e => {
            this.errorLibraryItem = e
          })
          if(this.errorLibraryItem != null){ // GET request gave an error
            this.libraryItems = []
            alert(e);
          } 
        } 
        else{ // both title and writer field have input in them
          console.log("title and writer not empty")
          this.errorLibraryItem = null
          var params = {
            title : requestedTitle,
            writer: requestedWriter
          }
          AXIOS.get('/newspaper/title/writer/', {params})
          .then(response => {
            this.libraryItems = []
            this.libraryItems[0] = response.data
          })
          .catch(e => {
            this.errorLibraryItem = e
          })
          if(this.errorLibraryItem != null){ // GET request gave an error
            this.libraryItems = []
            alert("No items found with this title and writer");
          } 
        }
      }
    }
  }