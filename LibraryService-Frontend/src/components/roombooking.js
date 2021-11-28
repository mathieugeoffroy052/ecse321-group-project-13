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
        available: true
      }
    },

    created: function () {
        document.getElementById("button").disabled = true
        document.getElementById("status").innerHTML = ""
        console.log("helloooo")
        AXIOS.get('/rooms/')
        .then(response => {
            this.roomTransactions = response.data
        })
        .catch(e => {
            this.errorRoomTransaction = e
        })
      },

    methods: {
        setupRoomTransaction: function (){
            for (let i = 0; i < this.roomTransactions.length; i++) {
                if(this.roomTransactions[i]["deadline"] == this.value){
                    this.available = false
                }
            }
            if (this.available){
                document.getElementById("button").disabled = false
                document.getElementById("status").innerHTML = "Room booking is available"
            }
            else {
                document.getElementById("button").disabled = true
                document.getElementById("status").innerHTML = "Room booking is not available"
            }
        },
        createRoomTransaction: function () {
            var params = {
                barCodeNumber: 3,
                userID: 7,
                date: this.value,
                startTime: "8:00:00",
                endTime: "11:30:00"
            }
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
            // Reset the name field for new people
            this.newRoomTransaction = ''
            this.errorRoomTransaction = ''
            }
        }
    }