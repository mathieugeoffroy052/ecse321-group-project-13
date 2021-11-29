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

function TransactionDTO(type, deadline, borrowableItem, userAccount, transactionID)
{
  this.transactionType = type;
  this.deadline = deadline;
  this.borrowableItem = borrowableItem;
  this.userAccount = userAccount;
  this.transactionID = transactionID;
}

export default {
    name: 'librarianview',
    data () {
        return {
            form: {
                firstName: '',
                lastName: '',
                address: '',
                balance: '',
                onlineAccount: false,
                password: null,
                email: null
            },
            errorBorrowableItem: '',
            borrowableItem: '',
            libraryItem: '',
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
              { value: 'Waitlist', text: 'Waitlist'},
              { value: 'Renew', text: 'Renew'},
              { value: 'Return', text: 'Return'},
              { value: 'Reserve-Room', text: 'Reserve a Room'}
            ],
            currentPatronTransactions: [],
            currentShift: [],
            dateRoomReserve: '',
            startTimeRoomReserve: '',
            endTimeRoomReserve: ''
        }
    },
    methods: {
        createPatron: function() {
            var firstName = document.getElementById("input-firstName").value
            var lastName = document.getElementById("input-lastName").value
            var address1 = document.getElementById("input-address").value
            var balance1 = document.getElementById("input-balance").value
            var password1 = document.getElementById("input-password").value
            var onlineAccount1 = document.getElementById("input-onlineAccount").value
            var email1 = document.getElementById("input-email").value
            AXIOS.post('/createPatron/'.concat(firstName).concat("/").concat(lastName), {},{params: {creatorID:1, onlineAccount:onlineAccount1, address:address1, validatedAccount:true, password:password1, balance:balance1, email:email1}}).then (response => {
                this.newPatron = response.data
            })
            .catch(e => {
                this.newPatron = ''
                alert(e.response.data.message)                
            })
        },
        getShifts: function() {
            var creatorID = 1
            AXIOS.get('/timeslot/view/librarianID/'.concat(creatorID)).then (response => {
                this.currentShift = []
                response.data.forEach(element => {
                    this.currentShift.push({Date: element.startDate, Start_Time: element.startTime, End_time:element.endTime })
                });
            })
            .catch(e => {
              this.currentShift = []
              alert(e.response.data.message)
                
            })
        },
        onSubmit(event) {
            this.createPatron()
            event.preventDefault()
            alert(JSON.stringify(this.form))
            this.form.firstName = ''
            this.form.lastName = ''
            this.form.address = ''
            this.form.email = ''
            this.form.password = ''
            this.form.balance = ''
            this.form.onlineAccount = false
            // Trick to reset/clear native browser form validation state
            this.show = false
            this.$nextTick(() => {
                this.show = true
            })
        },
        onReset(event) {
          event.preventDefault()
          // Reset our form values
          this.form.firstName = ''
          this.form.lastName = ''
          this.form.address = ''
          this.form.email = ''
          this.form.password = ''
          this.form.balance = ''
          this.form.onlineAccount = false
          // Trick to reset/clear native browser form validation state
          this.show = false
          this.$nextTick(() => {
            this.show = true
          })
        },
      getPatron: function() {
        var userID = document.getElementById("input-userID").value
        AXIOS.get('/patron/'.concat(userID)).then (response => {
            this.currentPatron = response.data
        })
        .catch(e => {
            this.currentPatron = ''
            alert(e.response.data.message)
            
        })
      },
      getTransactionsForPatron: function() {
        var userID = document.getElementById("input-userID").value
        this.currentPatronTransactions = []
        AXIOS.get('/transaction/viewall/id/'.concat(userID)).then (response => {
          response.data.forEach(element => {
            this.currentPatronTransactions.push({ ID: element.transactionID, Name: element.borrowableItem.libraryItem.name, Creator: element.borrowableItem.libraryItem.creator, Barcode: element.borrowableItem.barCodeNumber, Item: element.borrowableItem.libraryItem.type, Type: element.transactionType, Deadline: element.deadline }) 
          })
        }).catch(e => {
          alert(e.response.data.message)
        })
      },
      loadPatronInfo: function() {
        this.getPatron()
        this.getTransactionsForPatron()
      },
      newTransaction: function() {
        var userIDInput = document.getElementById("input-userID").value
        var transactionType = document.getElementById("input-transactiontype").value
        var barcodeInput = document.getElementById("input-barcode").value
        if(transactionType == "Borrow") {
          AXIOS.post("/borrow", {}, {params: {userID:userIDInput, barCodeNumber: barcodeInput}}).then (response => {
            this.getTransactionsForPatron()
            this.transaction = response.data
            this.borrowableItem = response.data.borrowableItem
            this.libraryItem = this.borrowableItem.libraryItem
          }).catch(e => {
            this.borrowableItem = ''
            this.transaction = ''
            this.libraryItem = ''
            alert(e.response.data.message)
          })
        } else if(transactionType == "Return") {
          AXIOS.post("/return", {}, {params: {userID:userIDInput, barCodeNumber: barcodeInput}}).then (response => {
            this.getTransactionsForPatron()
            this.transaction = response.data
            this.borrowableItem = response.data.borrowableItem
            this.libraryItem = this.borrowableItem.libraryItem
          }).catch(e => {
            this.borrowableItem = ''
            this.transaction = ''
            this.libraryItem = ''
            alert(e.response.data.message)
          })
        } else if(transactionType == "Renew") {
          AXIOS.post("/renew", {}, {params: {userID:userIDInput, barCodeNumber: barcodeInput}}).then (response => {
            this.getTransactionsForPatron()
            this.transaction = response.data
            this.borrowableItem = response.data.borrowableItem
            this.libraryItem = this.borrowableItem.libraryItem
          }).catch(e => {
            this.borrowableItem = ''
            this.transaction = ''
            this.libraryItem = ''
            alert(e.response.data.message)
          })
        } else if(transactionType == "Waitlist") {
          AXIOS.post("/join-waitlist", {}, {params: {userID:userIDInput, barCodeNumber: barcodeInput}}).then (response => {
            this.getTransactionsForPatron()
            this.transaction = response.data
            this.borrowableItem = response.data.borrowableItem
            this.libraryItem = this.borrowableItem.libraryItem
          }).catch(e => {
            this.borrowableItem = ''
            this.transaction = ''
            this.libraryItem = ''
            alert(e.response.data.message)
          })
        } else if(transactionType == "Reserve-Room") {
          AXIOS.post("/reserve-room", {}, {params: {userID:userIDInput, barCodeNumber: barcodeInput, date:this.dateRoomReserve, startTime:this.startTimeRoomReserve.substr(0,5), endTime:this.endTimeRoomReserve.substr(0, 5)}}).then (response => {
            this.getTransactionsForPatron()
            this.transaction = response.data
            this.borrowableItem = response.data.borrowableItem
            this.libraryItem = this.borrowableItem.libraryItem
          }).catch(e => {
            this.borrowableItem = ''
            this.transaction = ''
            this.libraryItem = ''
            alert(e.response.data.message)
          })
        }
      },
      isReservingRoom: function() {
        if (document.getElementById("input-userID") == null) return false
        return document.getElementById("input-transactiontype").value == "Reserve-Room"
      },
      validateCurrentPatron: function() {
        var userID = parseInt(document.getElementById("input-userID").value)
        AXIOS.put("/setAccountValidity", {}, {params: {patronID:userID, validatedAccount:true, creatorID:1}}).then (response => {
          this.currentPatron = response.data
        }).catch(e => {
          alert(e.response.data.message)
        })
      },
      resetBalance: function() {
        var userIDInput = document.getElementById("input-userID").value
        AXIOS.put("/updateBalance", {}, {params: {balance:0, userID:userIDInput}}).then (response => {
          this.currentPatron = response.data
        }).catch(e => {
          alert(e.response.date.message)
        })
      },
      getBorrowableItem: function() {
        var userID = document.getElementById("input-barcode").value
        AXIOS.get('/item/barCodeNumber/').then (response => {
            this.currentPatron = response.data
        })
        .catch(e => {
            this.currentPatron = ''
            alert(e.response.data.message) 
        })
      }
    }
}
