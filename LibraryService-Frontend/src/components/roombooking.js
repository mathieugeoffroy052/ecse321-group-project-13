import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


export default {
    name: 'roomsearch',
    data () {
      return {
        roomTransactions: [],
        newRoomTransaction: '',
        errorRoomTransaction: '',
        value: '',
        available: 0,
        buttonDisabled: 1
      }
    },

    created: function () {
        AXIOS.get('/rooms/')
        .then(response => {
            this.roomTransactions = response.data
        })
        .catch(e => {
            this.errorRoomTransaction = e
        })
        this.available = 0
        this.buttonDisabled = 1
      },

    methods: {
        setupRoomTransaction: function (){
            if(this.value != ""){   // if a value from the calender is selected
                // Set room as available, unless there is a transaction that has already been made on that same date
                this.available = 1
                for (let i = 0; i < this.roomTransactions.length; i++) {
                    if(this.roomTransactions[i]["deadline"] == this.value){
                       this.available = 0
                    }
                }
                if (Boolean(this.available)){
                    // Enable button
                    this.buttonDisabled = 0  
                    document.getElementById("reserve-button").disabled = Boolean(this.buttonDisabled)
                    console.log("changed button status to enabled")

                    document.getElementById("status").innerHTML = "Room booking is available"
                }
                else {
                    // Disable button
                    this.buttonDisabled = 1  
                    document.getElementById("reserve-button").disabled = Boolean(this.buttonDisabled)
                    console.log("changed button status disabled")

                    document.getElementById("status").innerHTML = "Room booking is not available"
                }
            }
        },
        createRoomTransaction: function () {
            console.log("entered create room transaction")
            if(!Boolean(this.buttonDisabled)){
                var params = {
                    barCodeNumber: 1,
                    userID: 7,
                    date: this.value,
                    startTime: "08:00",
                    endTime: "11:30"
                }
                console.log("value = " + this.value)
                AXIOS.post('/reserve-room', {}, {params})
                .then(response => {
                    this.roomTransactions.push(response.data)
                    this.errorTransaction = ''
                    this.newTransaction = ''
                    document.getElementById("status").innerHTML = "Room Booked!"
                })
                .catch(e => {
                    var errorMessage = e.response.data.message
                    console.log(errorMessage)
                    this.errorTransaction = errorMessage
                })
            }
            // Reset the name field for new people
            this.newRoomTransaction = ''
            this.errorRoomTransaction = ''
            }
        }
    }