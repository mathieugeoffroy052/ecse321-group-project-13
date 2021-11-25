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
        var anIsbn = document.querySelector('input[type="radio"]:checked').value;
        if(anIsbn != undefined){
          var params = {
            isbn: anIsbn
          }
          AXIOS.get('/items/isbn/', {params})
          .then(response => {
              console.log(response.data)
              this.existingBorrowableItems = response.data
              console.log(this.existingBorrowableItems)
          })
          .catch(e => {
              this.errorLibraryItem = e
          })
          console.log(this.existingBorrowableItems)
          if(this.existingBorrowableItems != undefined){
            console.log(this.existingBorrowableItems)
            var aBarCodeNumber = this.existingBorrowableItems[0]["barCodeNumber"]
            console.log(aPatronID)
            var params = {
              barCodeNumber: aBarCodeNumber,
              userID: aPatronID
            }
            AXIOS.post('/reserve-item', {}, {params})
            .then(response => {
                console.log('hello')
                this.transactions.push(response.data)
                this.errorTransaction = ''
                this.newTransaction = ''
              })
              .catch(e => {
                var errorMessage = e.response.data.message
                console.log(errorMessage)
                this.errorTransaction = errorMessage
              })
          }
          // Reset the name field for new people
          this.existingBorrowableItems = []
          this.existingPatron = ''
          this.existingBorrowableItem = ''
        }
      }
    }
  }