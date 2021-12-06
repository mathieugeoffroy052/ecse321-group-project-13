import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = process.env.NODE_ENV === 'production' ? "https://libraryservice-backend-g13.herokuapp.com" : 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

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
    name: 'booksearch',
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
        AXIOS.get('/books/')
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
      /*
      * Creates a reserve transaction between the currently logged-in
      * user and the library item selected from the list
      */
      createReserveTransaction: function () {
        var aPatronID = sessionStorage.getItem("existingUserID")
        var anIsbn = undefined
        if(document.querySelector('input[type="radio"]:checked') != null){
          anIsbn = document.querySelector('input[type="radio"]:checked').value;
        }
        if(anIsbn != undefined){  // if a library item is selected
          var params = {
            isbn: anIsbn
          }
          // GET all borrowable items associated with that library item (with the unique isbn)
          AXIOS.get('/items/isbn/', {params})
          .then(response => {
              this.existingBorrowableItems = response.data
              if(this.existingBorrowableItems != []){
                var aBarCodeNumber = undefined
                // Loop through borrowable items to see if there are any available for reservation
                for (let i = 0; i < this.existingBorrowableItems.length; i++) {
                  if(this.existingBorrowableItems[i]["itemState"] == "Available"){
                      aBarCodeNumber = this.existingBorrowableItems[i]["barCodeNumber"]
                  }
                }
                if(aBarCodeNumber != undefined){  // at least one available borrowable item was found
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
                else{   // no available borrowable items were found
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
      /*
      * Runs a (filtered) search depending on the inputs of the
      *   "title" and "creator" text field
      * Updating this.libraryItems list (based on the response of the
      *    GET HTTP request) will allow the items displayed on the frontend
      *    in the search results to be updated accordingly
      */
      runSearch : function(){
        var requestedTitle = document.getElementById("requestedTitle").value
        var requestedAuthor = document.getElementById("requestedAuthor").value
        if(requestedTitle == "" && requestedAuthor == "") { // both title and author field are empty
          AXIOS.get('/books/')
          .then(response => {
              this.libraryItems = response.data
          })
          .catch(e => {
            this.errorLibraryItem = e
          })
          document.getElementById("invalidInput").innerHTML = "Please enter a title or author"
        } 
        else if(requestedTitle != "" && requestedAuthor== "") { // title field is not empty, but author field is
          console.log("title not empty, author empty")
          this.errorLibraryItem = null
          var params = {
            title: requestedTitle
          }
          AXIOS.get('/books/title/', {params})
          .then(response => {
            this.libraryItems = response.data
            if(response.data.length == 0){
              document.getElementById("invalidInput").innerHTML = "No books found with this title"
            }
          })
          .catch(e => {
            this.errorLibraryItem = e
          })
          if(this.errorLibraryItem != null){ // GET request gave an error
            alert("ERROR");
          } 
        } 
        else if(requestedTitle == ""){ // author field is not empty, but title field is
          console.log("title empty, author not empty")
          this.errorLibraryItem = null
          var params = {
            author: requestedAuthor
          }
          AXIOS.get('/books/author/', {params})
          .then(response => {
            this.libraryItems = response.data
            if(response.data.length == 0){
              document.getElementById("invalidInput").innerHTML = "No books found by this author"
            }
          })
          .catch(e => {
            this.errorLibraryItem = e
          })
          if(this.errorLibraryItem != null){ // GET request gave an error
            alert("ERROR");
          } 
        } 
        else{ // both title and author field have input in them
          console.log("title and author not empty")
          this.errorLibraryItem = null
          var params = {
            title : requestedTitle,
            author: requestedAuthor
          }
          AXIOS.get('/books/title/author/', {params})
          .then(response => {            
            this.libraryItems = []
            if(response.data.length == 0) document.getElementById("invalidInput").innerHTML = "No books found by this title or author"
            else this.libraryItems[0] = response.data
          })
          .catch(e => {
            this.errorLibraryItem = e
            this.libraryItems = []
            document.getElementById("invalidInput").innerHTML = "No books found by this title or author"
          })
          if(this.errorLibraryItem != null){ // GET request gave an error
            alert("ERROR");
          } 
        }
      },
      /* Reset the messages displayed on the UI */
      resetMessages : function(){
        document.getElementById("invalidInput").innerHTML = ""
        document.getElementById("transaction").innerHTML = ""
      },
      redirectToItemSelect : function(){
        window.location.href='../#/item-select';
      }
    }
  }