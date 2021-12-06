import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = process.env.NODE_ENV === 'production' ? "https://libraryservice-backend-g13.herokuapp.com" : 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


export default {
    name: 'roomsearch',
    data () {
      return {
        value: '',
        buttonDisabled: 1,   // "boolean" to keep track of whether the "Reserve Room" button is disabled
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
        this.buttonDisabled = 1   // at the start, the "Reserve Room" button is disabled
      },

    methods: {
        setupRoomTransaction: function (){
            if(this.value != ""){   // if a value from the calender is selected
                // enable "Reserve Room" button
                this.buttonDisabled = 0  
                document.getElementById("reserve-button").disabled = Boolean(this.buttonDisabled)
            }
        },
        /*
        * Creates a room reserve transaction between the currently logged-in
        * user and the event room on the selected date from the calendar (if possible)
        */
        createRoomTransaction: function () {
            var theUserID = sessionStorage.getItem("existingUserID")
            var params = {
                userID: theUserID,
                date: this.value,
            }
            AXIOS.post('/reserve-room', {}, {params})
            .then(response => {
                document.getElementById("status").innerHTML = "Room Booked!"
                this.alertColour = "success"
            })
            // Display different alers or messages based on the error message
            //   from the backend
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