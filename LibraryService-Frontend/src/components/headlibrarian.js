import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

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

export default {
    name: 'librarianview',
    data () {
        return {
            formUser: {
                firstName: '',
                lastName: '',
                address: '',
                balance: '',
                onlineAccount: false,
                password: null,
                email: null,
                selectedUser:null
            },
            formOpeningHour:{
              openingHourID:''
            },
            formHoliday:{
              holiday:''
            },
            formStaff:{
              userID:''
            },
            formCode:{
              barCodeNumber:'',
            },
            formTimeslot:{
              timeslotIDAssign:'',
              timeslotIDDelete:'',
            },
            errorBorrowableItem: '',
            borrowableItem: '',
            creator: '',
            libraryItem: '',
            transaction: '',
            transactions: [],
            errorTransaction: '',
            newPatron: '',
            isLibrarian: false,
            newLibrarian: '',
            newHoliday:'',
            newOpeningHour:'',
            currentPatron: '',
            dateOpeningHour:'',
            dateHoliday:'',
            startTimeHoliday:'',
            endTimeHoliday:'',
            startTimeOpeningHour: '',
            endTimeOpeningHour:'',
            allShifts:[],
            allHolidays:[],
            allOpeningHours:[],
            dateWorkshift:'',
            startTimeWorkshift:'',
            endTimeWorkshift:'',
            newTimeSlot:'',
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
            selectedUser:null,
            optionsUsers: [
              { value: null, text: 'Select a User type' },
              { value: 'Patron', text: 'Patron'},
              { value: 'Librarian', text: 'Librarian'}
            ],
            selectedDay:null,
            optionsDays: [
              { value: null, text: 'Select a Day' },
              { value: 'Monday', text: 'Monday'},
              { value: 'Tuesday', text: 'Tuesday'},
              { value: 'Wednesday', text: 'Wednesday'},
              { value: 'Thursday', text: 'Thursday'},
              { value: 'Friday', text: 'Friday'},
              { value: 'Saturday', text: 'Saturday'},
              { value: 'Sunday', text: 'Sunday'}
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
            var onlineAccount1 = this.formUser.onlineAccount
            var email1 = document.getElementById("input-email").value
            if(this.selectedUser == "Patron"){

                AXIOS.post('/createPatron/'.concat(firstName).concat("/").concat(lastName), {},{params: {creatorID:sessionStorage.getItem("existingUserID"), onlineAccount:onlineAccount1, address:address1, validatedAccount:true, password:password1, balance:balance1, email:email1}}).then (response => {
                    this.newPatron = response.data
                })
                .catch(e => {
                    this.newPatron = ''
                    alert(e.response.data.message)                
                })
            }
            else if(this.selectedUser == "Librarian"){
                AXIOS.post('/createLibrarian/'.concat(firstName).concat("/").concat(lastName), {},{params: {online: onlineAccount1, address: address1, password: password1, balance: balance1, email: email1, userID:sessionStorage.getItem("existingUserID") }}).then (response => {
                    this.newLibrarian = response.data
                })
                .catch(e => {
                    this.newLibrarian = ''
                    alert(e.response.data.message)                
                })
            }
        },
        createHoliday: function() {
            AXIOS.post('/holiday/new', {},{params: {currentUserID:sessionStorage.getItem("existingUserID"), date:this.dateHoliday, startTime:this.startTimeHoliday.substr(0,5), endTime:this.endTimeHoliday.substr(0,5)}}).then (response => {
                this.newHoliday = response.data
                this.getAllHolidays()
            })
            .catch(e => {
                this.newHoliday = ''
                alert(e.response.data.message)                
            })
        },
        createOpeningHour: function() {
            AXIOS.post('/openinghour/new', {},{params: {day:this.selectedDay, startTime:this.startTimeOpeningHour.substr(0,5), endTime:this.endTimeOpeningHour.substr(0,5)}}).then (response => {
                this.newOpeningHour = response.data
                this.getAllOpeningHours()
            })
            .catch(e => {
                this.newOpeningHour = ''
                alert(e.response.data.message)                
            })
        },
        createTimeslot: function() {
          AXIOS.post('timeslot/new', {}, {params: {startDate:this.dateWorkshift, endDate:this.dateWorkshift, startTime:this.startTimeWorkshift.substr(0, 5), endTime:this.endTimeWorkshift.substr(0, 5), currentUserID:sessionStorage.getItem("existingUserID")}}).then (response => {
            this.newTimeSlot = response.data
            this.getAllShifts()
        })
        .catch(e => {
            this.newTimeSlot = ''
            alert(e.response.data.message)                
        })
        },
        assignTimeslot: function() {
          AXIOS.put('/timeslot/assign', {}, {params: {timeslotID:this.formTimeslot.timeslotIDAssign, librarianID:this.formStaff.userID, currentUserID:sessionStorage.getItem("existingUserID")}}).then (response => {
            this.newTimeSlot = response.data
            this.getAllShifts()
        })
        .catch(e => {
            this.newTimeSlot = ''
            alert(e.response.data.message)                
        })
        },
        onSubmitUSER(event) {
            this.createUser()
            event.preventDefault()
            alert(JSON.stringify(this.formUser))
            this.formUser.firstName = ''
            this.formUser.lastName = ''
            this.formUser.address = ''
            this.formUser.email = ''
            this.formUser.password = ''
            this.formUser.balance = ''
            this.formUser.onlineAccount = false
            // Trick to reset/clear native browser form validation state
            this.show = false
            this.$nextTick(() => {
                this.show = true
            })
        },
        onSubmitHour(event) {
          this.createOpeningHour()
          event.preventDefault()
          this.selectedDay = null
          this.startOpeningHour = ''
          this.endOpeningHour = ''
          // Trick to reset/clear native browser form validation state
          this.show = false
          this.$nextTick(() => {
              this.show = true
          })
        },
        onSubmitDelHour(event) {
          this.deleteOpeningHour()
          event.preventDefault()
          this.formOpeningHour.openingHourID = ''
          // Trick to reset/clear native browser form validation state
          this.show = false
          this.$nextTick(() => {
              this.show = true
          })
        },
        onSubmitDelHoliday(event) {
          this.deleteHoliday()
          event.preventDefault()
          this.formHoliday.holiday = ''
          // Trick to reset/clear native browser form validation state
          this.show = false
          this.$nextTick(() => {
              this.show = true
          })
        },
        onSubmitHoliday(event) {
          this.createHoliday()
          event.preventDefault()
          this.startDate = null
          this.startHoliday = ''
          this.endTimeHoliday = ''
          // Trick to reset/clear native browser form validation state
          this.show = false
          this.$nextTick(() => {
              this.show = true
          })
        },
        onResetHoliday(event) {
          event.preventDefault()
          // Reset our form values
          this.startDate = null
          this.startHoliday = ''
          this.endTimeHoliday = ''
          // Trick to reset/clear native browser form validation state
          this.show = false
          this.$nextTick(() => {
            this.show = true
          })
        },
        onResetHour(event) {
          event.preventDefault()
          // Reset our form values
          this.selectedDay = null
          this.startOpeningHour = ''
          this.endOpeningHour = ''
          // Trick to reset/clear native browser form validation state
          this.show = false
          this.$nextTick(() => {
            this.show = true
          })
        },
        onResetDelOpening(event) {
          event.preventDefault()
          // Reset our form values
          this.formOpeningHour.openingHourID = ''
          // Trick to reset/clear native browser form validation state
          this.show = false
          this.$nextTick(() => {
            this.show = true
          })
        },
        onResetDelHoliday(event) {
          event.preventDefault()
          // Reset our form values
          this.formHoliday.holiday = ''
          // Trick to reset/clear native browser form validation state
          this.show = false
          this.$nextTick(() => {
            this.show = true
          })
        },
        onResetUSER(event) {
          event.preventDefault()
          // Reset our form values
          this.formUser.firstName = ''
          this.formUser.lastName = ''
          this.formUser.address = ''
          this.formUser.email = ''
          this.formUser.password = ''
          this.formUser.balance = ''
          this.formUser.onlineAccount = false
          // Trick to reset/clear native browser form validation state
          this.show = false
          this.$nextTick(() => {
            this.show = true
          })
        },
        onDelStaff(event) {
          this.deleteStaff()
          event.preventDefault()
          this.formStaff.userID
          // Trick to reset/clear native browser form validation state
          this.show = false
          this.$nextTick(() => {
              this.show = true
          })
        },
        onResetStaff(event) {
          event.preventDefault()
          // Reset our form values
          this.formStaff.userID
          // Trick to reset/clear native browser form validation state
          this.show = false
          this.$nextTick(() => {
            this.show = true
          })
        },
        onSubmitTimeslot(event) {
          this.createTimeslot()
          event.preventDefault()
          this.dateWorkshift = null
          this.startTimeWorkshift = ''
          this.endTimeWorkshift = ''
          // Trick to reset/clear native browser form validation state
          this.show = false
          this.$nextTick(() => {
              this.show = true
          })
        },
        onResetTimeslot(event) {
          event.preventDefault()
          // Reset our form values
          this.dateWorkshift = null
          this.startTimeWorkshift = ''
          this.endTimeWorkshift = ''
          // Trick to reset/clear native browser form validation state
          this.show = false
          this.$nextTick(() => {
            this.show = true
          })
        },
        onSubmitWorkshift(event) {
          this.assignTimeslot()
          event.preventDefault()
          this.formTimeslot.timeslotID = ''
          this.formStaff.userID = ''
          // Trick to reset/clear native browser form validation state
          this.show = false
          this.$nextTick(() => {
              this.show = true
          })
        },
        onResetWorkshift(event) {
          event.preventDefault()
          // Reset our form values
          this.formTimeslot.timeslotID = ''
          this.formStaff.userID = ''
          // Trick to reset/clear native browser form validation state
          this.show = false
          this.$nextTick(() => {
            this.show = true
          })
        },
        onSubmitDelTimeslot(event) {
          this.deleteTimeslot()
          event.preventDefault()
          this.formTimeslot.timeslotID = ''
          // Trick to reset/clear native browser form validation state
          this.show = false
          this.$nextTick(() => {
              this.show = true
          })
        },
        onResetDelTimeslot(event) {
          event.preventDefault()
          // Reset our form values
          this.formTimeslot.timeslotID = ''
          // Trick to reset/clear native browser form validation state
          this.show = false
          this.$nextTick(() => {
            this.show = true
          })
        },
        getPatron: function() {
          var userID = document.getElementById("input-userID").value 
          if (userID != '') {
            AXIOS.get('/patron/'.concat(userID)).then (response => {
                this.currentPatron = response.data
                this.isLibrarian = false
            })
            .catch(e => {
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
        needsValidation: function() {
          if (this.isLibrarian) return false
          return this.currentPatron != '' && !this.currentPatron.validatedAccount
        },
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
      getAllStaff: function() {
        AXIOS.get('/librarians/').then (response => {
            this.currentStaff = []
            response.data.forEach(element => {
                this.currentStaff.push({First_Name: element.firstName, Last_Name: element.lastName, ID:element.userID })
            });
        })
        .catch(e => {
          this.currentStaff = []
          alert(e.response.data.message)
            
        })
      },
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
      getAllShifts: function() {
        var librarianArray = []
        AXIOS.get('/timeslot/viewall').then (response => {
            this.allShifts = []
            response.data.forEach(element => {
              element.librarians.forEach(e => {librarianArray.push(e.firstName)})
              this.allShifts.push({Date: element.startDate, Start_Time: element.startTime, End_time:element.endTime, Librarian: librarianArray, ID: element.timeSlotID })
            });
        })
        .catch(e => {
          this.allShifts = []
          alert(e.response.data.message)
            
        })
      },
      getAllOpeningHours: function() {
        AXIOS.get('/openinghour/viewall').then (response => {
            this.allOpeningHours = []
            response.data.forEach(element => {
                this.allOpeningHours.push({Date: element.dayOfWeek, Start_Time: element.startTime, End_time:element.endTime, ID:element.openingHourID })
            });
        })
        .catch(e => {
          this.allOpeningHours = []
          alert(e.response.data.message)
            
        })
      },
      getAllHolidays: function() {
        AXIOS.get('/holiday/viewall').then (response => {
            this.allHolidays = []
            response.data.forEach(element => {
                this.allHolidays.push({Date: element.date, Start_Time: element.startTime, End_time:element.endTime, ID:element.holidayID})
            });
        })
        .catch(e => {
          this.allHolidays = []
          alert(e.response.data.message)
            
        })
      },
      deleteStaff: function() {
        var userID = document.getElementById("input-userID-toDelete").value    
        AXIOS.delete('/librarians/deleteAccount/'.concat(userID), {params: {headlibrarianID:sessionStorage.getItem("existingUserID")}}).then (response => {
          if(response.data == true){
            alert("Librarian deleted")
          }
          else{
            alert("Delete Unsuccessful.")
          }
          this.getAllStaff()
        }).catch(e => {
          alert(e.response.data.message)
            
        })
      },
      deleteOpeningHour: function() {
        AXIOS.delete('/openinghour/delete', {params:{openinghourID:this.formOpeningHour.openingHourID, accountID:sessionStorage.getItem("existingUserID")}}).then (response => {
            if(response.data == true){
              alert("Opening Hour deleted")
            }
            else{
              alert("Delete Unsuccessful.")
            }
            this.getAllOpeningHours()
        })
        .catch(e => {
          alert(e.response.data.message)
            
        })
      },
      deleteTimeslot: function() {
        AXIOS.delete('/timeslot/delete', {params:{timeslotID:this.formTimeslot.timeslotIDDelete, accountID:sessionStorage.getItem("existingUserID")}}).then (response => {
            if(response.data == true){
              alert("TimeSlot deleted")
            }
            else{
              alert("Delete Unsuccessful.")
            }
            this.getAllShifts()
        })
        .catch(e => {
          alert(e.response.data.message)
            
        })
      },
      deleteHoliday: function() {
        AXIOS.delete('/holiday/delete', {params: {holidayID:this.formHoliday.holiday, accountID:sessionStorage.getItem("existingUserID")}}).then (response => {
            if(response.data){
              alert("Holiday Hour deleted")
            }
            else{
              alert("Delete Unsuccessful.")
            }
            this.getAllHolidays()
        })
        .catch(e => {
          alert(e.response.data.message)
            
        })
      },
      isReservingRoom: function() {
        if (document.getElementById("input-userID") == null) return false
        return document.getElementById("input-transactiontype").value == "Reserve-Room"
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
      validateCurrentPatron: function() {
        var userID = parseInt(document.getElementById("input-userID").value)
        AXIOS.put("/setAccountValidity", {}, {params: {patronID:userID, validatedAccount:true, creatorID:sessionStorage.getItem("existingUserID")}}).then (response => {
          this.currentPatron = response.data
        }).catch(e => {
          alert(e.response.data.message)
        })
      },
      loadPatronInfo: function() {
        this.getPatron()
        this.getTransactionsForPatron()
      },
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
