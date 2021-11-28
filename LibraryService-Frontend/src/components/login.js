import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function UserAccountDTO(firstName, lastName, userID, password, email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.userID = userID;
    this.password = password;
    this.email = email;
}


export default {
    name: 'login',
    data () {
        return {
            form: {
                firstName: '',
                lastName: '',
                address: '',
                
                password: null,
                email: null
            },
        }
    },

    methods: {


    }

}