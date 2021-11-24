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

function BorrowableItemDTO(state, item, barCodeNumber){
  this.state = state;
  this.libraryItem = item;
  this.barCodeNumber = barCodeNumber;
}

function UserAccountDTO(firstName, lastName, onlineAccount, address, validatedAccount, password, balance, email, patronID){
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
        errorLibraryItem: '',
        borrowableItems: [],
        newBorrowableItem: '',
        errorBorrowableItem: '',
        patrons: [],
        newPatron: '',
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
      createReserveTransaction: function (personName) {
        // Create a new person and add it to the list of people
        var p = new PersonDto(personName)
        this.persons.push(p)
        // Reset the name field for new people
        this.newPerson = ''
      }
    }
  }