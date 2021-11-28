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
        // roomItems: [],
        // newRoomItem: '',
        // errorRoomItem: '',
        value: '',
        available: 0,
        buttonDisabled: 1,
        alertColour: 'light'
      }
    },

    created: function () {
        AXIOS.get('/rooms/')
        .then(response => {
            this.roomItems = response.data
        })
        .catch(e => {
            this.errorRoomItem = e
        })
        this.available = 0
        this.buttonDisabled = 1
      },

    methods: {
        setupRoomTransaction: function (){
            if(this.value != ""){   // if a value from the calender is selected
                // Enable button
                this.buttonDisabled = 0  
                document.getElementById("reserve-button").disabled = Boolean(this.buttonDisabled)
                console.log("changed button status to enabled")
            }
        },
        createRoomTransaction: function () {
            console.log("entered create room transaction")
            var params = {
                userID: 7,
                date: this.value,
            }
            AXIOS.post('/reserve-room', {}, {params})
            .then(response => {
                document.getElementById("status").innerHTML = "Room Booked!"
                this.alertColour = "success"
            })
            .catch(e => {
                var errorMessage = e.response.data.message
                console.log("Error Message: " + errorMessage)
                if(errorMessage == "Item cannot be null! "){
                    alert("No rooms exist")
                }
                else if(errorMessage == "Cannot book a room in the past"){
                    document.getElementById("status").innerHTML = "Cannot reserve a room in the past, please try again"
                    this.alertColour = "danger"
                }
                else if(errorMessage == "Room already booked on that date, please try another or the watilist."){
                    document.getElementById("status").innerHTML = "Room booking not available on this date, please try again"
                    this.alertColour = "danger"
                }
                else{
                    document.getElementById("status").innerHTML = "OTHER ERROR"
                    this.alertColour = "dark"
                }
            })
        }
    }
}