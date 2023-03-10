import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = process.env.NODE_ENV === 'production' ? "https://libraryservice-backend-g13.herokuapp.com" : 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

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
            isLibrarian: false,
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
            dateRoomReserve: ''
        }
    },
    methods: {
        /* Allows librarians to create a patron account (user is automatically validated in this case)*/
        createPatron: function() {
            var firstName = document.getElementById("input-firstName").value
            var lastName = document.getElementById("input-lastName").value
            var address1 = document.getElementById("input-address").value
            var balance1 = document.getElementById("input-balance").value
            var onlineAccount1 = this.form.onlineAccount
            var password1 = ""
            var email1 = ""
            if(onlineAccount1 === true){
               password1 = document.getElementById("input-password").value
               email1= document.getElementById("input-email").value
            }

            AXIOS.post('/createPatron/'.concat(firstName).concat("/").concat(lastName), {},{params: {creatorID:sessionStorage.getItem("existingUserID"), onlineAccount:onlineAccount1, address:address1, validatedAccount:true, password:password1, balance:balance1, email:email1, patronCreator:true}}).then (response => {
                this.newPatron = response.data 
                alert("The Patrons user ID is: ".concat(this.newPatron.userID))            
            })
            .catch(e => {
                this.newPatron = ''
                alert(e.response.data.message)                
            })
        },
        /**
         * this method retrieves all of the shifts for the librarian that is currently logged-in
         */
        getShifts: function() {
            var creatorID = sessionStorage.getItem("existingUserID")
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
        /**
         * This method retrieves a specific user based on the given userID
         */
      getPatron: function() {
        var userID = document.getElementById("input-userID").value 
        if (userID != '') {
          AXIOS.get('/patron/'.concat(userID)).then (response => {
              this.currentPatron = response.data
              this.isLibrarian = false
          })
          .catch(() => {
            AXIOS.get('/account/'.concat(userID)).then (response => {
              this.currentPatron = response.data
              this.isLibrarian = true
            }).catch(e => {
              this.currentPatron = ''
              alert(e.response.data.message)
            })
          })
        } else {
          this.currentPatron = ''
        }
      },
      /**
       * retruns the validation state of the user
       * @returns 
       */
      needsValidation: function() {
        if (this.isLibrarian) return false
        return this.currentPatron != '' && !this.currentPatron.validatedAccount
      },
      /**
       * this method retrieves all of the transactions for a specific user.
       */
      getTransactionsForPatron: function() {
        var userID = document.getElementById("input-userID").value
        this.currentPatronTransactions = []
        if (userID != '') {
          AXIOS.get('/transaction/viewall/id/'.concat(userID)).then (response => {
            response.data.forEach(element => {
              this.currentPatronTransactions.push({ ID: element.transactionID, Name: element.borrowableItem.libraryItem.name, Creator: element.borrowableItem.libraryItem.creator, Barcode: element.borrowableItem.barCodeNumber, Item: element.borrowableItem.libraryItem.type, Type: element.transactionType, Deadline: element.deadline }) 
            })
          }).catch(e => {
            alert(e.response.data.message)
          })
        } 
      },
      /**
       * This method loads the user's info
       */
      loadPatronInfo: function() {
        this.getPatron()
        this.getTransactionsForPatron()
      },
      /* 
      * Allows librarians to create certain transactions (borrow, return, renew, waitlist, reserve room)
      *  associated to any user based on the inputted user ID
      */
      newTransaction: function() {
        var userIDInput = document.getElementById("input-userID").value
        var transactionType = document.getElementById("input-transactiontype").value
        if(transactionType == "Borrow") {
          var barcodeInput = document.getElementById("input-barcode").value
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
          var barcodeInput = document.getElementById("input-barcode").value
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
          var barcodeInput = document.getElementById("input-barcode").value
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
          var barcodeInput = document.getElementById("input-barcode").value
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
          AXIOS.post("/reserve-room", {}, {params: {userID:userIDInput, date:this.dateRoomReserve}}).then (response => {
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
      /* Allows librarians to validated any user (patron) account */
      validateCurrentPatron: function() {
        var userID = parseInt(document.getElementById("input-userID").value)
        AXIOS.put("/setAccountValidity", {}, {params: {patronID:userID, validatedAccount:true, creatorID:sessionStorage.getItem("existingUserID")}}).then (response => {
          this.currentPatron = response.data
        }).catch(e => {
          alert(e.response.data.message)
        })
      },
      /* Allows librarians to reset balance for any user account */
      resetBalance: function() {
        var userIDInput = document.getElementById("input-userID").value
        AXIOS.put("/updateBalance", {}, {params: {balance:0, userID:userIDInput}}).then (response => {
          this.currentPatron.balance = response.data.balance
        }).catch(e => {
          alert(e.response.date.message)
        })
      }
    }
}
