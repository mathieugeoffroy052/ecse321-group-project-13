import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = process.env.NODE_ENV === 'production' ? "https://libraryservice-backend-g13.herokuapp.com" : 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

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
export default {
    name: 'createAccount',
    data () {
        return {
            form: {
                firstName: '',
                lastName: '',
                address: '',
               // balance: '',
               // onlineAccount: false,
                password: null,
                email: null
            },
            newPatron: '',
            currentPatron: '',
            errorPatron: '',
            response: [],
        }
    },
    methods: {
        createPatron: function() {
            var firstName = document.getElementById("input-firstName").value
            var lastName = document.getElementById("input-lastName").value
            var address1 = document.getElementById("input-address").value
            var password1 = document.getElementById("input-password").value
            var email1 = document.getElementById("input-email").value
            //var onlineAccount1 = document.getElementById("input-onlineAccount").value

            AXIOS.post('/createPatron/'.concat(firstName).concat("/").concat(lastName), {},{params: {creatorID:1, onlineAccount:true, address:address1, validatedAccount:false, password:password1, balance:0, email:email1, patronCreator:false}}).then (response => {
                this.newPatron = response.data
                sessionStorage.setItem("existingUserID", this.newPatron.userID)
                this.redirectToItemSelect()
            })
            .catch(e => {
                this.newPatron = ''
                alert(e.response.data.message)                
            })
        
        },
        onSubmit(event) {
            this.createPatron()
            event.preventDefault()
            alert(JSON.stringify(this.form))
            this.form.firstName = ''
            this.form.lastName = ''
            this.form.address = ''
            
            this.form.password = ''
            this.form.email = ''
           // this.form.balance = 0
           // this.form.onlineAccount = false

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
          
          this.form.password = ''
          this.form.email = ''

          // Trick to reset/clear native browser form validation state
          this.show = false
          this.$nextTick(() => {
            this.show = true
          })
        },
          
          redirectToItemSelect: function () {
            window.location.href='../#/item-select';
        }
        
      }
    
}
