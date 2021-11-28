import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


export default {
    name: 'musicsearch',
    data () {
      return {
        roomTransactions: [],
        newRoomTransaction: '',
        errorRoomTransaction: '',
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
      },

    methods: {
      createRoomTransaction: function (date) {
          // Reset the name field for new people
          this.newRoomTransaction = ''
          this.errorRoomTransaction
        }
      }
    }