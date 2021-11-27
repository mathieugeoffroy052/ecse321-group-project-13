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
            creator: '',
            transaction: '',
            transactions: [],
            errorTransaction: '',
            newPatron: '',
            newLibrarian: '',
            currentPatron: '',
            errorPatron: '',
            response: [],
            selectedTransactionType: null,
            optionsTransactionType: [
              { value: null, text: 'Select a transaction type' },
              { value: 'Borrow', text: 'Borrow'},
              { value: 'Return', text: 'Return'}
            ],
            currentPatronTransactions: [],
            currentShift: [],
            selectedUser:null,
            optionsUsers: [
              { value: null, text: 'Select a User type' },
              { value: 'Patron', text: 'Patron'},
              { value: 'Librarian', text: 'Librarian'}
            ],
            currentStaff:[]
        }
    },
    methods: {
        createUser: function() {
            var firstName = document.getElementById("input-firstName").value
            var lastName = document.getElementById("input-lastName").value
            var address1 = document.getElementById("input-address").value
            var balance1 = document.getElementById("input-balance").value
            var password1 = document.getElementById("input-password").value
            var onlineAccount1 = document.getElementById("input-onlineAccount").value
            var email1 = document.getElementById("input-email").value
            if(selectedUser == "Patron"){

                AXIOS.post('/createPatron/'.concat(firstName).concat("/").concat(lastName), {},{params: {creatorID:1, onlineAccount:onlineAccount1, address:address1, validatedAccount:true, password:password1, balance:balance1, email:email1}}).then (response => {
                    this.newPatron = response.data
                })
                .catch(e => {
                    this.newPatron = ''
                    alert(e.response.data.message)                
                })
            }
            else if(selectedUser == "Librarian"){
                AXIOS.post('/createLibrarian/'.concat(firstName).concat("/").concat(lastName), {},{params: {online, address, password, balance, email, userID:1 }}).then (response => {
                    this.newLibrarian = response.data
                })
                .catch(e => {
                    this.newLibrarian = ''
                    alert(e.response.data.message)                
                })
            }
        },
        onSubmit(event) {
            this.createUser()
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
      getStaff: function() {
        AXIOS.get('/librarians/').then (response => {
            response.data.forEach(element => {
                this.currentStaff = []
                this.currentStaff.push({First_Name: element.firstName, Last_Name: element.lastName, ID:element.userID })
            });
        })
        .catch(e => {
          this.currentStaff = []
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
      deleteStaff: function() {
        AXIOS.delete('/librarians/').then (response => {
            response.data.forEach(element => {
                this.currentStaff = []
                this.currentStaff.push({First_Name: element.firstName, Last_Name: element.lastName, ID:element.userID })
            });
        })
        .catch(e => {
          this.currentStaff = []
          alert(e.response.data.message)
            
        })
      },
      getTransactionsForPatron: function() {
        var userID = document.getElementById("input-userID").value
        AXIOS.get('/transaction/viewall/id/'.concat(userID)).then (response => {
          response.data.forEach(element => {
            this.currentPatronTransactions.push({ Name: element.borrowableItem.libraryItem.name, Creator: element.borrowableItem.libraryItem.creator, Item: element.borrowableItem.libraryItem.type, Type: element.transactionType, Deadline: element.deadline }) 
          })
        }).catch(e => {
          this.currentPatronTransactions = []
          alert(e.response.data.message)
        })
      },
      loadPatronInfo: function() {
        this.getPatron()
        this.getTransactionsForPatron()
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
