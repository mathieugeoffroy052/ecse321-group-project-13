import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
// this page will display all of the holiday information such as the opninghours and the holidays.
export default {
    name: 'libraryinfo',
    data () {
        return { 
            allOpeningHours: [],
            allHolidays: []
        }

    }, 
    created: function() {
        this.getAllHolidays()
        this.getAllOpeningHours()
    },
    methods: {
        /**
         * This method retrives all of the openinghours in the system
         */
        getAllOpeningHours: function() {
            AXIOS.get('/openinghour/viewall').then (response => {
                this.allOpeningHours = []
                response.data.forEach(element => {
                    this.allOpeningHours.push({Date: element.dayOfWeek, Start_Time: element.startTime, End_time:element.endTime })
                });
            })
            .catch(e => {
                this.allOpeningHours = []
                alert(e.response.data.message)
                
            })
            },
            /**
             * This method retrieves all of the holidays in the system.
             */
            getAllHolidays: function() {
            AXIOS.get('/holiday/viewall').then (response => {
                this.allHolidays = []
                response.data.forEach(element => {
                    this.allHolidays.push({Date: element.date, Start_Time: element.startTime, End_time:element.endTime})
                });
            })
            .catch(e => {
                this.allHolidays = []
                alert(e.response.data.message)
                
            })
            }
    }
}

