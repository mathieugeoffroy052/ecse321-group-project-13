import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'createAccount',
    data () {
        return {
            form: {
                firstName: '',
                lastName: '',
                address: '',
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
        /**
         * This method creates a Patron. This is specifically confu=igured for when the Patron creates their account themselves.
         */
        createPatron: function() {
            var firstName = document.getElementById("input-firstName").value
            var lastName = document.getElementById("input-lastName").value
            var address1 = document.getElementById("input-address").value
            var password1 = document.getElementById("input-password").value
            var email1 = document.getElementById("input-email").value

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
        /**
         * This method is called when the create Patron form is submitted. It then clears all the fields
         * @param {*} event 
         */
        onSubmit(event) {
            this.createPatron()
            event.preventDefault()
            alert(JSON.stringify(this.form))
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
        /**
         * This method is called when the Reset button is clicked and it clears all fields.
         * @param {*} event 
         */
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
          
        /**
         * This method is called when the button to select and item is selected
         */
          redirectToItemSelect: function () {
            window.location.href='../#/item-select';
        }
        
      }
    
}
