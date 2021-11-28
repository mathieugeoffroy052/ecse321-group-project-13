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
            errorBorrowableItem: '',
            borrowableItem: '',
            creator: '',
            transaction: '',
            transactions: [],
            errorTransaction: '',
            newPatron: '',
            newLibrarian: '',
            newHoliday:'',
            newOpeningHour:'',
            currentPatron: '',
            dateOpeningHour:'',
            dateHoliday:'',
            startHoliday:'',
            endTimeHoliday:'',
            endOpeningHour:'',
            allShifts:[],
            allHolidays:[],
            allOpeningHours:[],
            dateWorkshift:'',
            startTimeWorkshift:'',
            endTimeWorkshift:'',
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
        createHoliday: function() {
            AXIOS.post('/holiday/new', {},{params: {creatorID:1, date:this.dateHoliday, startTime:this.startHoliday.substr(0,5), endTime:this.endTimeHoliday.substr(0,5)}}).then (response => {
                this.newHoliday = response.data
            })
            .catch(e => {
                this.newHoliday = ''
                alert(e.response.data.message)                
            })
        },
        createOpeningHour: function() {
            AXIOS.post('/openinghour/new', {},{params: {day:this.selectedDay, startTime:this.startOpeningHour.substr(0,5), endTime:this.endOpeningHour.substr(0,5)}}).then (response => {
                this.newOpeningHour = response.data
            })
            .catch(e => {
                this.newOpeningHour = ''
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
        onSubmitStaff(event) {
          this.createHoliday()
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
      getAllShifts: function() {
        AXIOS.get('/timeslot/viewall').then (response => {
            this.allShifts = []
            response.data.forEach(element => {
                this.allShifts.push({Date: element.startDate, Start_Time: element.startTime, End_time:element.endTime })
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
                this.allOpeningHours.push({Date: element.startDate, Start_Time: element.startTime, End_time:element.endTime, ID:element.openingHourID })
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
                this.allHolidays.push({Date: element.startDate, Start_Time: element.startTime, End_time:element.endTime, ID:element.holidayID})
            });
        })
        .catch(e => {
          this.allHolidays = []
          alert(e.response.data.message)
            
        })
      },
      deleteStaff: function() {      
        AXIOS.delete('/librarians/deleteAccount/'.concat(userID), {}, {params:{creatorID:1}}).then (response => {
          if(response.data == true){
            alert("Librarian deleted")
          }
          else{
            alert("Delete Unsuccessful.")
          }
        })
        .catch(e => {
          alert(e.response.data.message)
            
        })
      },
      deleteOpeningHour: function() {
        var openingHourID = document.getElementById("input-OpeningHour")
        AXIOS.delete('/openinghour/delete', {}, {params:{openingHourID, creatorID:1}}).then (response => {
            if(response.data == true){
              alert("Opening Hour deleted")
            }
            else{
              alert("Delete Unsuccessful.")
            }
        })
        .catch(e => {
          alert(e.response.data.message)
            
        })
      },
      deleteHoliday: function() {
        var holidayID = document.getElementById("input-Holiday")
        AXIOS.delete('/openinghour/delete', {}, {params:{holidayID, creatorID:1}}).then (response => {
            if(response.data == true){
              alert("Opening Hour deleted")
            }
            else{
              alert("Delete Unsuccessful.")
            }
        })
        .catch(e => {
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
