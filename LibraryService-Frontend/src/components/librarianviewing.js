import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function PatronDTO(firstName, lastName, onlineAccount, address, validatedAccount, password, balance, email, userID) {
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

function TransactionDTO(type, deadline, borrowableItem, userAccount, transactionID)
{
  this.transactionType = type;
  this.deadline = deadline;
  this.borrowableItem = borrowableItem;
  this.userAccount = userAccount;
  this.transactionID = transactionID;
}

function LibraryItemDTO(name, itemType, date, creator, isViewable, isbn)
{
  this.name = name;
  this.date = date;
  this.itemType = itemType;
  this.creator = creator;
  this.isViewable = isViewable;
  this.isbn = isbn;
}

export default {
    name: 'librarianview',
    data () {
        return {
            form: {
                firstName: '',
                // name: '',
                // food: null,
                // // checked: []
                // if (document.getElementById('online').checked) {
                //     checked = document.getElementById('online').value;
                //   }
            },
            errorBorrowableItem: '',
            borrowableItem: '',
            creator: '',
            transaction: '',
            transactions: [],
            errorTransaction: '',
            newPatron: '',
            currentPatron: '',
            errorPatron: '',
            response: [],
            selectedTransactionType: null,
            optionsTransactionType: [
              { value: null, text: 'Select a transaction type' },
              { value: 'Borrow', text: 'Borrow'},
              { value: 'Return', text: 'Return'}
            ],
            currentPatronTransactions: [
              { Name: 'Titanic', Creator: 'Dickerson', Item_Type: 'Movie', Transaction_Type: 'Return' }
            ]
        }
    },
    methods: {
        onSubmit(event) {
          event.preventDefault()
          alert(JSON.stringify(this.form))
        },
        onReset(event) {
          event.preventDefault()
          // Reset our form values
          this.form.firstName = ''
        //   this.form.name = ''
        //   this.form.food = null
        //   this.form.checked = []
        //   // Trick to reset/clear native browser form validation state
        //   this.show = false
        //   this.$nextTick(() => {
        //     this.show = true
        //   })
        },
      getPatron: function() {
        var userID = document.getElementById("input-userID").value
        AXIOS.get('/patron/'.concat(userID)).then (response => {
            this.currentPatron = response.data
        })
        .catch(e => {
            var errorMsg = e.response.data.message
            console.log(errorMsg)
            this.errorPatron = errorMsg
        })
      }
    }
}
