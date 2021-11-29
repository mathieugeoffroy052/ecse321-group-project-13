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
      runSearch : function(){
        var requestedTitle = document.getElementById("requestedTitle").value
        var requestedWriter = document.getElementById("requestedWriter").value
        if(requestedTitle == "" && requestedWriter == "") { // both title and writer field are empty
          AXIOS.get('/newspapers/')
          .then(response => {
              this.libraryItems = response.data
          })
          .catch(e => {
              this.errorLibraryItem = e
          })
          document.getElementById("invalidInput").innerHTML = "Please enter a title or artist"
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
            if(response.data.length == 0){
              document.getElementById("invalidInput").innerHTML = "No newspaper articles found with this title"
            }
          })
          .catch(e => {
            this.errorLibraryItem = e
          })
          if(this.errorLibraryItem != null){ // GET request gave an error
            alert("ERROR");
          } 
        }
        else if(requestedTitle == ""){ // writer field is not empty, but title field is
          console.log("title empty, writer not empty")
          this.errorLibraryItem = null
          var params = {
            writer: requestedWriter
          }
          AXIOS.get('/newspapers/writer/', {params})
          .then(response => {
            this.libraryItems = response.data
            if(response.data.length == 0){
              document.getElementById("invalidInput").innerHTML = "No articles found by this newspaper"
            }
          })
          .catch(e => {
            this.errorLibraryItem = e
          })
          if(this.errorLibraryItem != null){ // GET request gave an error
            alert("ERROR");
          } 
        } 
        else{ // both title and writer field have input in them
          console.log("title and writer not empty")
          this.errorLibraryItem = null
          var params = {
            title : requestedTitle,
            writer: requestedWriter
          }
          AXIOS.get('/newspapers/title/writer/', {params})
          .then(response => {
            this.libraryItems = []
            if(response.data.length == 0) document.getElementById("invalidInput").innerHTML = "No articles found with this title and newspaper"
            else this.libraryItems[0] = response.data
          })
          .catch(e => {
            this.errorLibraryItem = e
            this.libraryItems = []
            document.getElementById("invalidInput").innerHTML = "No articles found with this title and newspaper"
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